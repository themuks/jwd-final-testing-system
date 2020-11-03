package com.kuntsevich.testsys.tag;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.GregorianCalendar;

public class SideMenuTag extends TagSupport {
    private static final String ROLE = "role";

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        String roleName = (String) session.getAttribute(ROLE);
        String layout = "<nav class=\"d-block ml-sm-auto bg-light sidebar mt-3 mb-lg-3 rounded\">\n" +
                "                <ul class=\"nav flex-column\">\n" +
                "                    <li class=\"nav-item\">\n" +
                "                        <a class=\"nav-link active\" href=\"<c:url value=\"/controller?command=show-all-tests\"/>\">Тесты</a>\n" +
                "                    </li>\n" +
                "                    <li class=\"nav-item\">\n" +
                "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=show-subjects\"/>\">Список предметов</a>\n" +
                "                    </li>\n" +
                "                </ul>\n" +
                "            </nav>";
        if (roleName != null) {
            switch (roleName) {
                case "Администратор":
                    layout = "<nav class=\"d-block ml-sm-auto bg-light sidebar mt-3 mb-lg-3 rounded\">\n" +
                            "                <ul class=\"nav flex-column\">\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=create-test\"/>\">Создать тест</a>\n" +
                            "                    </li>\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=show-my-tests\"/>\">Мои тесты</a>\n" +
                            "                    </li>\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=show-all-tests\"/>\">Доступные для прохождения тесты</a>\n" +
                            "                    </li>\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=show-users\"/>\">Список пользователей</a>\n" +
                            "                    </li>\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=show-subjects\"/>\">Список предметов</a>\n" +
                            "                    </li>\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=show-results\"/>\">Мои результаты</a>\n" +
                            "                    </li>\n" +
                            "                </ul>\n" +
                            "            </nav>";
                    break;
                case "Тьютор":
                    layout = "<nav class=\"d-block ml-sm-auto bg-light sidebar mt-3 mb-lg-3 rounded\">\n" +
                            "                <ul class=\"nav flex-column\">\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=create-test\"/>\">Создать тест</a>\n" +
                            "                    </li>\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=show-my-tests\"/>\">Мои тесты</a>\n" +
                            "                    </li>\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=show-all-tests\"/>\">Доступные для прохождения тесты</a>\n" +
                            "                    </li>\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=show-students\"/>\">Студенты</a>\n" +
                            "                    </li>\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=show-subjects\"/>\">Список предметов</a>\n" +
                            "                    </li>\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=show-results\"/>\">Мои результаты</a>\n" +
                            "                    </li>\n" +
                            "                </ul>\n" +
                            "            </nav>";
                    break;
                case "Студент":
                    layout = "<nav class=\"d-block ml-sm-auto bg-light sidebar mt-3 mb-lg-3 rounded\">\n" +
                            "                <ul class=\"nav flex-column\">\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link active\" href=\"<c:url value=\"/controller?command=show-all-tests\"/>\">Тесты</a>\n" +
                            "                    </li>\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=show-subjects\"/>\">Список предметов</a>\n" +
                            "                    </li>\n" +
                            "                    <li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href=\"<c:url value=\"/controller?command=show-results\"/>\">Результаты</a>\n" +
                            "                    </li>\n" +
                            "                </ul>\n" +
                            "            </nav>";
                    break;
            }
        }
        try {
            JspWriter out = pageContext.getOut();
            out.write(layout);
        } catch (IOException e) {
            throw new JspException("Jsp writing error", e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
