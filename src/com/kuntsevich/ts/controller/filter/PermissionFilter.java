package com.kuntsevich.ts.controller.filter;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.RequestParameter;
import com.kuntsevich.ts.model.service.UserService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PermissionFilter implements Filter {
    private static final Logger log = Logger.getLogger(PermissionFilter.class);
    private static final String USER_ID = "userId";
    private static final String USER_HASH = "userHash";
    private static final String ORIGIN = "origin";
    private static final String ADMIN = "Администратор";
    private static final String STUDENT = "Студент";
    private static final String TUTOR = "Тьютор";
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
                if (ADMIN.equals(role)) {
                    if (adminPages.contains(requestUrl)) {
                        isAllowed = true;
                    }
                } else if (STUDENT.equals(role)) {
                    if (tutorPages.contains(requestUrl)) {
                        isAllowed = true;
                    }
                } else if (TUTOR.equals(role)) {
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
