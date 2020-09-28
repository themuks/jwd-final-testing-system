<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <title>Система тестирования</title>
</head>
<body>
    <div class="container">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <!--<a class="navbar-brand" href="#">
                <img src="/docs/4.5/assets/brand/bootstrap-solid.svg" width="30" height="30" class="d-inline-block align-top" alt="" loading="lazy">Система тестирования
            </a> -->
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">Главная<span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">О разработчике</a>
                    </li>
                </ul>
            </div>
            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="email" placeholder="Email" aria-label="Email">
                <input class="form-control mr-sm-2" type="password" placeholder="Пароль" aria-label="Пароль">
                <button class="btn btn-outline-primary" type="submit">Войти</button>
            </form>
        </nav>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-sm-3">
                <ul class="nav flex-column bg-light">
                    <li class="nav-item">
                        <a class="nav-link active" href="#">Тесты</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Список предметов</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Результаты</a>
                    </li>
                </ul>
            </div>
            <div class="col-sm-9">
                <c:forEach var="test" items="${tests}">
                    <div class="card my-sm-4">
                        <div class="card-body">
                            <h5 class="card-title"><c:out value="${test.title}" /></h5>
                            <p class="card-text"><c:out value="${test.description}" /></p>
                            <a href="#" class="btn btn-primary">Пройти тест</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
</body>
</html>
