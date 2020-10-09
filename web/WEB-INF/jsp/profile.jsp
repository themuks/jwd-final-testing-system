<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" class="com.kuntsevich.testsys.entity.User" scope="request"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Профиль</title>
</head>
<body>
    <p><c:out value="user.username"/></p>
    <p><c:out value="user.name"/></p>
    <p><c:out value="user.surname"/></p>
    <p><c:out value="user.email"/></p>
    <p><c:out value="user.role.name"/></p>
</body>
</html>
