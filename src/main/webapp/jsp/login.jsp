<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sign-in.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon"/>
    <title><fmt:message key="login.title"/></title>
</head>
<body class="text-center">
<form class="form-signin" name="LoginForm" method="post" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="login"/>
    <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="login.title"/></h1>

    <tags:message/>

    <c:if test="${not empty sessionScope.origin}">
        <div class="alert alert-info" role="alert"><fmt:message key="login.enter_hint"/></div>
    </c:if>

    <label for="inputEmail" class="sr-only"><fmt:message key="login.email"/></label>
    <input type="email" name="email" id="inputEmail" class="form-control" placeholder="<fmt:message key="login.email"/>"
           required autofocus>
    <label for="inputPassword" class="sr-only"><fmt:message key="login.password"/></label>
    <input type="password" name="password" id="inputPassword" class="form-control"
           placeholder="<fmt:message key="login.password"/>" required>
    <div class="custom-control custom-checkbox mb-3">
        <input type="checkbox" class="custom-control-input" id="customCheck1" name="rememberMe" value="rememberMe">
        <label class="custom-control-label" for="customCheck1"> <fmt:message key="login.remember_me"/></label>
    </div>
    <button class="btn btn-lg btn-primary btn-block mb-3" type="submit"><fmt:message
            key="login.submit_button"/></button>
    <div>
        <fmt:message key="login.password_question"/>
        <a href="${pageContext.request.contextPath}/jsp/password-recovery.jsp"><fmt:message
                key="login.password_question_suggestion"/></a>
    </div>
    <div>
        <fmt:message key="login.account_question"/>
        <a href="${pageContext.request.contextPath}/jsp/registration.jsp"><fmt:message
                key="login.account_question_suggestion"/></a>
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