<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="text"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/css/sticky-footer.css"/>">
    <title>Система тестирования</title>
</head>
<body class="d-flex flex-column h-100">
<nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap navbar-expand-md">
    <div class="container" style="justify-content: space-evenly">
        <a class="navbar-brand ml-sm-3" href="<c:url value="/jsp/home.jsp"/>">Система тестирования</a>
        <ul class="navbar-nav mr-md-auto list-inline">
            <li class="nav-item active">
                <a class="nav-link" href="<c:url value="/jsp/home.jsp"/>">Главная<span
                        class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/jsp/about.jsp"/>">О системе</a>
            </li>
        </ul>
        <c:if test="${not empty sessionScope.role}">
            <a class="nav-link" href="<c:url value="/controller?command=show-profile"/>">Профиль</a>
            <a class="nav-link" href="<c:url value="/controller?command=logout"/>">Выйти</a>
        </c:if>
        <c:if test="${empty sessionScope.role}">
            <div class="form-inline">
                <a class="btn btn-outline-primary mr-2 my-2 my-lg-0" role="button"
                   href="${pageContext.request.contextPath}/jsp/login.jsp">Вход</a>
                <a class="btn btn-primary" role="button"
                   href="${pageContext.request.contextPath}/jsp/registration.jsp">Регистрация</a>
            </div>
        </c:if>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-lg-3">
            <nav class="d-block ml-sm-auto bg-light sidebar mt-3 mb-lg-3 rounded">
                <ul class="nav flex-column">
                    <c:if test="${sessionScope.role eq 'Администратор'}">
                        <li class="nav-item">
                            <a class="nav-link active" href="<c:url value="/controller?command=show-test-create"/>">Создать
                                тест</a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.role eq 'Тьютор'}">
                        <li class="nav-item">
                            <a class="nav-link active" href="<c:url value="/controller?command=show-test-create"/>">Создать
                                тест</a>
                        </li>
                    </c:if>
                    <li class="nav-item">
                        <a class="nav-link active" href="<c:url value="/controller?command=show-all-tests"/>">Тесты</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=show-subjects"/>">Список
                            предметов</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=show-results"/>">Результаты</a>
                    </li>
                </ul>
            </nav>
        </div>

        <div class="col-lg-9">
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger my-3" role="alert">${errorMessage}</div>
            </c:if>
            <c:if test="${not empty infoMessage}">
                <div class="alert alert-info my-3" role="alert">${infoMessage}</div>
            </c:if>
            <c:if test="${not empty templatePath}">
                <c:import url="${templatePath}"/>
            </c:if>
            <c:if test="${empty templatePath}">
                <div class="mx-auto my-3 text-center">
                    Выберите пункт из меню, чтобы начать работу
                </div>
            </c:if>
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