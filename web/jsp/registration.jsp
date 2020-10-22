<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/css/sign-up.css"/>">
    <title><fmt:message key="registration.title"/></title>
</head>
<body class="text-center">
<form class="form-signup" name="RegisterForm" method="POST" action="${pageContext.request.contextPath}/controller"
      accept-charset="utf-8">
    <input type="hidden" name="command" value="registration"/>
    <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="registration.title"/></h1>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">${errorMessage}</div>
    </c:if>

    <label for="inputUsername" class="sr-only"><fmt:message key="registration.username"/></label>
    <input type="text" name="username" id="inputUsername" class="form-control form-top"
           placeholder="<fmt:message key="registration.username"/>" required
           autofocus>

    <label for="inputName" class="sr-only"><fmt:message key="registration.name"/></label>
    <input type="text" name="name" id="inputName" class="form-control"
           placeholder="<fmt:message key="registration.name"/>" required>

    <label for="inputSurname" class="sr-only"><fmt:message key="registration.surname"/></label>
    <input type="text" name="surname" id="inputSurname" class="form-control"
           placeholder="<fmt:message key="registration.surname"/>" required>

    <label for="inputEmail" class="sr-only"><fmt:message key="registration.email"/></label>
    <input type="email" name="email" id="inputEmail" class="form-control"
           placeholder="<fmt:message key="registration.email"/>" required>

    <label for="inputPassword" class="sr-only"><fmt:message key="registration.password"/></label>
    <input type="password" name="password" id="inputPassword" class="form-control"
           placeholder="<fmt:message key="registration.password"/>" required>

    <label for="inputPasswordAgain" class="sr-only"><fmt:message key="registration.password_again"/></label>
    <input type="password" name="password-again" id="inputPasswordAgain" class="form-control form-bottom"
           placeholder="<fmt:message key="registration.password_again"/>" required>

    <label for="role" class="sr-only">Роль</label>
    <select id="role" class="custom-select" name="role" required>
        <option selected>Выберите роль</option>
        <option value="Тьютор">Тьютор</option>
        <option value="Студент">Студент</option>
    </select>

    <button class="btn btn-lg btn-primary btn-block my-3" type="submit"><fmt:message key="registration.submit_button"/></button>

    <div>
        <fmt:message key="registration.question"/>
        <a href="${pageContext.request.contextPath}/jsp/login.jsp"><fmt:message key="registration.question_suggestion"/></a>
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
