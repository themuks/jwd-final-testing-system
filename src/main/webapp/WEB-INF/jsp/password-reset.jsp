<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<fmt:message key="password-reset.title" var="title"/>
<tags:general title="${title}">
    <div class="col">
        <p class="h4 text-center mt-3"><fmt:message key="password-reset.title"/></p>
        <form class="my-3" method="post" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="password-reset"/>
            <input type="hidden" name="secretKey" value="${secretKey}"/>
            <input type="hidden" name="userId" value="${userId}"/>
            <div class="form-group">
                <label for="newPasswordInput"><fmt:message key="profile.new_password"/> *</label>
                <input type="password" maxlength="255" name="newPassword" class="form-control" id="newPasswordInput" required>
            </div>
            <div class="form-group">
                <label for="newPasswordAgainInput"><fmt:message key="profile.new_password_again"/> *</label>
                <input type="password" maxlength="255" name="newPasswordAgain" class="form-control" id="newPasswordAgainInput" required>
            </div>
            <div class="d-flex justify-content-center">
                <button class="btn btn-primary" type="submit"><fmt:message key="profile.submit"/></button>
            </div>
        </form>
    </div>
</tags:general>