<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="text"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sticky-footer.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon"/>
    <title>Система тестирования</title>
</head>
<body class="d-flex flex-column h-100">
<nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap navbar-expand-md">
    <div class="container">
        <a class="navbar-brand ml-sm-3" href="${pageContext.request.contextPath}/jsp/home.jsp">
            <img src="${pageContext.request.contextPath}/img/logo.png" width="30" height="30" class="d-inline-block align-top mr-2" alt="logo" loading="lazy">
            Система тестирования
        </a>
        <ul class="navbar-nav mr-md-auto list-inline">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/jsp/home.jsp">Главная</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/jsp/about.jsp">О системе<span
                        class="sr-only">(current)</span></a>
            </li>
        </ul>
        <tags:user-status/>
    </div>
</nav>
<div class="container my-auto">
    <div class="row">
        <div class="col align-self-center text-center">
            <p class="h1">Добро пожаловать!</p>
            <p class="h2">Testing system предназначена для создания тестов и проверки знания учеников</p>
            <p class="h2">Зарегистрируйтесь, если еще не сделали этого ;)</p>
        </div>
    </div>
</div>
<footer class="footer mt-auto py-3">
    <div class="container text-center">
        <span class="text-muted">© 2020 Testing System. All Rights Reserved.</span>
    </div>
</footer>
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