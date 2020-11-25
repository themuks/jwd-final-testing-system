<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content.text"/>
<fmt:message key="password-recovery.title" var="title"/>
<tags:general title="${title}">
    <div class="col">
        <p class="h4 text-center mt-3"><fmt:message key="password-recovery.title"/></p>
        <form class="my-3" method="post" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="recover-password"/>
            <div class="form-group">
                <label for="emailInput"><fmt:message key="password-recovery.email"/> *</label>
                <input type="email" name="email" class="form-control" id="emailInput" required>
            </div>
            <div class="d-flex justify-content-center">
                <button class="btn btn-primary" type="submit"><fmt:message key="profile.submit"/></button>
            </div>
        </form>
    </div>
</tags:general>