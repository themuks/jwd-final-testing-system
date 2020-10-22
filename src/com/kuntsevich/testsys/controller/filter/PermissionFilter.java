package com.kuntsevich.testsys.controller.filter;

import com.kuntsevich.testsys.controller.manager.ConfigurationManager;
import com.kuntsevich.testsys.model.service.UserService;
import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class PermissionFilter implements Filter {
    private static final Logger log = Logger.getLogger(PermissionFilter.class);
    private static final String USER_ID = "userId";
    private static final String USER_HASH = "userHash";
    private static final String AUTHORIZATION_FAIL = "authorization.fail";
    private static final String AUTHORIZATION_ERROR = "authorization.error";
    private static final Map<String, List<String>> permissions = new HashMap<>();
    private static final String ORIGIN = "origin";
    private static final String WELCOME_PAGE = "welcome_page";
    private static final String LOGIN_PAGE = "login_page";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        List<String> studentPages = new ArrayList<>();
        studentPages.add("/WEB-INF/jsp/test.jsp");
        studentPages.add("/WEB-INF/jsp/result.jsp");
        studentPages.add("/WEB-INF/jsp/tests.jsp");
        studentPages.add("/WEB-INF/jsp/profile.jsp");
        studentPages.add("/jsp/main.jsp");
        studentPages.add("/jsp/login.jsp");
        studentPages.add("/jsp/registration.jsp");
        List<String> tutorPages = new ArrayList<>(studentPages);
        tutorPages.add("/WEB-INF/jsp/subjects.jsp");
        tutorPages.add("/WEB-INF/jsp/test-edit.jsp");
        List<String> adminPages = new ArrayList<>(tutorPages);
        adminPages.add("/WEB-INF/jsp/admin.jsp");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /*HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        // TODO: 08.10.2020 Filter users by roles
        // If a user is logged, then we pass it through
        // Else redirect to the login page
        String userId = (String) session.getAttribute(USER_ID);
        String userHash = (String) session.getAttribute(USER_HASH);
        if (userId == null || userHash == null) {
            String page = request.getContextPath() + ConfigurationManager.getProperty(AUTHORIZATION_FAIL);
            response.sendRedirect(page);
            return;
        }
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        try {
            Optional<Role> optionalRole = userService.authorization(userId, userHash);
            if (optionalRole.isPresent()) {
                StringBuffer requestUrl = request.getRequestURL();
                List<String> pages = permissions.get(optionalRole.get().getName());
                if (!pages.contains(requestUrl)) {
                    String page = request.getContextPath() + ConfigurationManager.getProperty(AUTHORIZATION_FAIL);
                    response.sendRedirect(page);
                    return;
                }
            } else {
                String targetUri = request.getRequestURI();
                request.getSession().setAttribute(ORIGIN, targetUri);
            }
        } catch (ServiceException e) {
            log.error("Error authorizing user", e);
            String page = request.getContextPath() + ConfigurationManager.getProperty(AUTHORIZATION_ERROR);
            response.sendRedirect(page);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);*/
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String origin = request.getParameter(ORIGIN);
        boolean redirectToOrigin = origin != null;
        if (isAuthenticated(request)) {
            if (isRedirectRequired(request)) {
                if (!redirectToOrigin) {
                    String path = request.getContextPath() + ConfigurationManager.getProperty(WELCOME_PAGE);
                    response.sendRedirect(path);
                } else {
                    filterChain.doFilter(request, response);
                }
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }
        // if not authenticated
        if (!isAuthenticationRequired(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        // try to authenticate
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();
            HttpSession session = request.getSession();
            String userId = (String) session.getAttribute(USER_ID);
            String userHash = (String) session.getAttribute(USER_HASH);
            userService.authorization(userId, userHash);
        } catch (ServiceException e) {
            log.warn("Authentication failed", e);
        }
        if (isAuthenticated(request)) {
            filterChain.doFilter(request, response);
            return;
        } else {
            String targetUri = request.getRequestURI();
            HttpSession session = request.getSession();
            session.setAttribute(ORIGIN, targetUri);
            String page = request.getContextPath() + ConfigurationManager.getProperty(LOGIN_PAGE);
            response.sendRedirect(page);
        }
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        return false;
    }

    private boolean isRedirectRequired(HttpServletRequest request) {
        return false;
    }

    private boolean isAuthenticationRequired(HttpServletRequest request) {
        return false;
    }
}
