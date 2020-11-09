<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" class="com.kuntsevich.ts.entity.User" scope="request"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<p class="h4 text-center mt-3">Профиль</p>
<div class="border rounded p-3 my-3">
    <form class="my-3" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="change-user-parameters"/>
        <div class="form-group">
            <label for="usernameInput">Имя пользователя</label>
            <input type="text" name="username" class="form-control" id="usernameInput" value="${user.username}"
                   required>
        </div>
        <div class="form-group">
            <label for="nameInput">Имя</label>
            <input type="text" name="name" class="form-control" id="nameInput" value="${user.name}" required>
        </div>
        <div class="form-group">
            <label for="surnameInput">Фамилия</label>
            <input type="text" name="surname" class="form-control" id="surnameInput" value="${user.surname}" required>
        </div>
        <div class="form-group">
            <label for="oldPasswordInput">Старый пароль</label>
            <input type="password" name="oldPassword" class="form-control" id="oldPasswordInput">
        </div>
        <div class="form-group">
            <label for="newPasswordInput">Новый пароль</label>
            <input type="password" name="newPassword" class="form-control" id="newPasswordInput">
        </div>
        <div class="form-group">
            <label for="newPasswordAgainInput">Новый пароль еще раз</label>
            <input type="password" name="newPasswordAgain" class="form-control" id="newPasswordAgainInput">
        </div>
        <div class="d-flex justify-content-center">
            <button class="btn btn-primary" type="submit">Подтвердить</button>
        </div>
    </form>
</div>