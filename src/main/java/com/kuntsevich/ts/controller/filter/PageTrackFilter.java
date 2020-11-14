package com.kuntsevich.ts.controller.filter;

import com.kuntsevich.ts.controller.AttributeName;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PageTrackFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        String currentPage = request.getRequestURL().toString();
        String previousPage = (String) session.getAttribute(AttributeName.CURRENT_PAGE);
        session.setAttribute(AttributeName.PREVIOUS_PAGE, previousPage);
        session.setAttribute(AttributeName.CURRENT_PAGE, currentPage);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
