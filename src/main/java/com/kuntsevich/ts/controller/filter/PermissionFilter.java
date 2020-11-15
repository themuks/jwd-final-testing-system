package com.kuntsevich.ts.controller.filter;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.PagePath;
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
    private static final String ADMIN = "Администратор";
    private static final String STUDENT = "Студент";
    private static final String TUTOR = "Тьютор";
    private static final String GUEST = "Гость";
    private static final String COMMAND = "command";
    private static final Map<String, List<String>> permissions = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        List<String> guestCommands = List.of(
                "login",
                "registration",
                "show-tests",
                "show-subjects",
                "show-welcome-page",
                "change-language"
        );
        List<String> studentCommands = new ArrayList<>(guestCommands);
        studentCommands.addAll(List.of(
                "logout",
                "show-tests",
                "show-test",
                "show-profile",
                "submit-test",
                "change-user-parameters",
                "show-user-results"
        ));
        List<String> tutorCommands = new ArrayList<>(studentCommands);
        tutorCommands.addAll(List.of(
                "show-test-edit",
                "show-test-create",
                "show-subject-add",
                "show-users",
                "create-test",
                "show-user-results",
                "delete-result",
                "add-subject",
                "delete-subject",
                "delete-test"
        ));
        permissions.put(TUTOR, tutorCommands);
        permissions.put(STUDENT, studentCommands);
        permissions.put(GUEST, guestCommands);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute(AttributeName.ROLE);
        String command = request.getParameter(COMMAND);
        if (command == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (role == null) {
            List<String> commands = permissions.get(GUEST);
            if (commands == null || !commands.contains(command)) {
                response.sendRedirect(request.getContextPath() + PagePath.LOGIN);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
            return;
        }
        if (ADMIN.equals(role)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        List<String> commands = permissions.get(role);
        if (commands == null || !commands.contains(command)) {
            response.sendRedirect(request.getContextPath() + PagePath.LOGIN);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
