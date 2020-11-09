package com.kuntsevich.ts.tag;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ProfileInfoTag extends TagSupport {
    private static final String ROLE = "role";

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        String roleName = (String) session.getAttribute(ROLE);
        String layout;
        if (roleName != null) {
            layout = "";
        } else {
            layout = "<div class=\"form-inline\">\n" +
                    "            <a class=\"btn btn-outline-primary mr-2 my-2 my-lg-0\" role=\"button\"\n" +
                    "               href=\"${pageContext.request.contextPath}/controller?command=show-login-page\">Вход</a>\n" +
                    "            <a class=\"btn btn-outline-secondary\" role=\"button\"\n" +
                    "               href=\"${pageContext.request.contextPath}/controller?command=show-registration-page\">Регистрация</a>\n" +
                    "        </div>";
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
