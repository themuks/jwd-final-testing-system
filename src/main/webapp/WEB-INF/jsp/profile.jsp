<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="user" class="com.kuntsevich.ts.entity.User" scope="request"/>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content.text"/>
<fmt:message key="profile.title" var="title"/>
<tags:general title="${title}">
    <div class="col-lg-3">
        <tags:sidebar/>
    </div>
    <div class="col-lg-9">
        <tags:message/>
        <p class="h4 text-center mt-3"><fmt:message key="profile.title"/></p>
        <div class="border rounded p-3 my-3">
            <form class="my-3" method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="change-user-parameters"/>
                <div class="form-group">
                    <label for="usernameInput"><fmt:message key="profile.username"/></label>
                    <input type="text" name="username" class="form-control" id="usernameInput" value="${user.username}"
                           required>
                </div>
                <div class="form-group">
                    <label for="nameInput"><fmt:message key="profile.name"/></label>
                    <input type="text" name="name" class="form-control" id="nameInput" value="${user.name}" required>
                </div>
                <div class="form-group">
                    <label for="surnameInput"><fmt:message key="profile.surname"/></label>
                    <input type="text" name="surname" class="form-control" id="surnameInput" value="${user.surname}"
                           required>
                </div>
                <div class="form-group">
                    <label for="oldPasswordInput"><fmt:message key="profile.old_password"/></label>
                    <input type="password" name="oldPassword" class="form-control" id="oldPasswordInput">
                </div>
                <div class="form-group">
                    <label for="newPasswordInput"><fmt:message key="profile.new_password"/></label>
                    <input type="password" name="newPassword" class="form-control" id="newPasswordInput">
                </div>
                <div class="form-group">
                    <label for="newPasswordAgainInput"><fmt:message key="profile.new_password_again"/></label>
                    <input type="password" name="newPasswordAgain" class="form-control" id="newPasswordAgainInput">
                </div>
                <div class="d-flex justify-content-center">
                    <button class="btn btn-primary" type="submit"><fmt:message key="profile.submit"/></button>
                </div>
            </form>
        </div>
    </div>
</tags:general>