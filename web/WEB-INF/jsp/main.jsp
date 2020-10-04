<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/css/sticky-footer.css"/>">
    <title>Система тестирования</title>
</head>
<body class="d-flex flex-column h-100">
    <header>
        <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap">
            <a class="navbar-brand" href="#">Система тестирования</a>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="<c:url value="/WEB-INF/jsp/main.jsp"/>">Главная<span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">О разработчике</a>
                    </li>
                </ul>
            </div>

        </nav>
    </header>
    <main role="main" class="flex-shrink-0">
        <div class="container-fluid">
            <div class="row">
                <nav class="col-lg-3 d-block ml-sm-auto sidebar collapse">
                    <ul class="nav flex-column bg-light">
                        <li class="nav-item">
                            <a class="nav-link active" href="<c:url value="/controller?command=test_find_all"/>">Тесты</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Список предметов</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Результаты</a>
                        </li>
                    </ul>
                </nav>
                <div class="col-lg-9">
                    <c:forEach var="test" items="${tests}">
                        <div class="card my-4">
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
    </main>
    <footer class="footer mt-auto py-3">
        <div class="container text-center">
            <span class="text-muted">© 2020 Testing System. All Rights Reserved.</span>
        </div>
    </footer>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
</body>
</html>
