package com.kuntsevich.testsys.controller.filter;

import com.kuntsevich.testsys.controller.PagePath;
import com.kuntsevich.testsys.controller.RequestParameter;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static List<String> adminPages;
    private static List<String> tutorPages;
    private static List<String> studentPages;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        studentPages = new ArrayList<>();
        studentPages.add("/WEB-INF/jsp/test.jsp");
        studentPages.add("/WEB-INF/jsp/result.jsp");
        studentPages.add("/WEB-INF/jsp/tests.jsp");
        studentPages.add("/WEB-INF/jsp/profile.jsp");
        studentPages.add("/jsp/home.jsp");
        studentPages.add("/jsp/login.jsp");
        studentPages.add("/jsp/registration.jsp");
        tutorPages = new ArrayList<>(studentPages);
        tutorPages.add("/WEB-INF/jsp/subjects.jsp");
        tutorPages.add("/WEB-INF/jsp/test-edit.jsp");
        adminPages = new ArrayList<>(tutorPages);
        adminPages.add("/WEB-INF/jsp/admin.jsp");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(USER_ID);
        String userHash = (String) session.getAttribute(USER_HASH);
        if (userId == null || userHash == null) {
            String page = request.getContextPath() + PagePath.LOGIN;
            response.sendRedirect(page);
            return;
        }
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        try {
            if (userService.authorization(userId, userHash)) {
                String role = (String) session.getAttribute(RequestParameter.ROLE);
                String requestUrl = request.getRequestURL().toString();
                boolean isAllowed = false;
                if ("Администратор".equals(role)) {
                    if (adminPages.contains(requestUrl)) {
                        isAllowed = true;
                    }
                } else if ("Студент".equals(role)) {
                    if (tutorPages.contains(requestUrl)) {
                        isAllowed = true;
                    }
                } else if ("Тьютор".equals(role)) {
                    if (studentPages.contains(requestUrl)) {
                        isAllowed = true;
                    }
                }
                if (!isAllowed) {
                    String page = request.getContextPath() + PagePath.LOGIN;
                    response.sendRedirect(page);
                    return;
                }
            } else {
                session.removeAttribute(RequestParameter.ROLE);
                session.removeAttribute(RequestParameter.USER_ID);
                session.removeAttribute(RequestParameter.USER_HASH);
                String path = request.getContextPath() + PagePath.LOGIN;
                response.sendRedirect(path);
            }
        } catch (ServiceException e) {
            log.error("Error authorizing user", e);
            String page = request.getContextPath() + PagePath.LOGIN;
            response.sendRedirect(page);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
