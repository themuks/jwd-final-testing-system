<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/css/signup.css"/>">
    <title>Регистрация</title>
</head>
<body class="text-center">
<form class="form-signup" name="RegisterForm" method="POST" action="controller" accept-charset="utf-8">
    <input type="hidden" name="command" value="register"/>
    <h1 class="h3 mb-3 font-weight-normal">Регистрация</h1>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">${errorMessage}</div>
    </c:if>

    <label for="inputUsername" class="sr-only">Имя пользователя</label>
    <input type="text" name="username" id="inputUsername" class="form-control" placeholder="Имя пользователя" required
           autofocus>

    <label for="inputName" class="sr-only">Имя</label>
    <input type="text" name="name" id="inputName" class="form-control" placeholder="Имя" required>

    <label for="inputSurname" class="sr-only">Фамилия</label>
    <input type="text" name="surname" id="inputSurname" class="form-control" placeholder="Фамилия" required>

    <label for="inputEmail" class="sr-only">Почтовый адрес</label>
    <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Почтовый адрес" required>

    <label for="inputPassword" class="sr-only">Пароль</label>
    <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Пароль" required>

    <label for="inputPasswordAgain" class="sr-only">Повторите пароль</label>
    <input type="password" name="password-again" id="inputPasswordAgain" class="form-control" placeholder="Повторите пароль" required>

    <button class="btn btn-lg btn-primary btn-block mb-3" type="submit">Зарегистрироваться</button>

    <div>
        Уже есть аккаунт?
        <a href="${pageContext.request.contextPath}/controller?command=go-to&page=registration">Войти</a>
    </div>
</form>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
        crossorigin="anonymous"></script>
</body>
</html>
