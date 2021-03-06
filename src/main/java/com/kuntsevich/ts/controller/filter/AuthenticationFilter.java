package com.kuntsevich.ts.controller.filter;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.entity.User;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.UserDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AuthenticationFilter implements Filter {
    private static final Logger log = Logger.getLogger(AuthenticationFilter.class);
    private static final String USER_HASH = "userHash";
    private static final String EMAIL = "userEmail";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (request.getCookies() == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        List<Cookie> cookies = List.of(request.getCookies());
        String userHash = null;
        String email = null;
        for (var cookie : cookies) {
            if (cookie.getName().equals(USER_HASH)) {
                userHash = cookie.getValue();
            }
            if (cookie.getName().equals(EMAIL)) {
                email = cookie.getValue();
            }
        }
        if (userHash != null && email != null) {
            UserDao userDao = DaoFactory.getInstance().getUserDao();
            try {
                Optional<User> optionalUser = userDao.findByEmailAndUserHash(userHash, email);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    session.setAttribute(AttributeName.USER_ID, user.getUserId());
                    session.setAttribute(AttributeName.ROLE, user.getRole().getName());
                    session.setAttribute(AttributeName.USER_NAME, user.getUsername());
                }
            } catch (DaoException e) {
                log.error("Error authorizing user", e);
                String page = request.getContextPath() + PagePath.LOGIN;
                response.sendRedirect(page);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
