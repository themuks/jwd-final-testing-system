<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="test" class="com.kuntsevich.testsys.entity.Test" scope="request"/>
<jsp:useBean id="question" class="com.kuntsevich.testsys.entity.Question" scope="request"/>
<jsp:useBean id="answer" class="com.kuntsevich.testsys.entity.Answer" scope="request"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
    ${errorMessage}
    <c:if test="${not empty test}">
        <h1><c:out value="${test.title}"/></h1>
        <h2><c:out value="${test.subject.name}"/></h2>
        <h3><c:out value="${test.description}"/></h3>
        <c:forEach var="question" items="${test.questions}">
            <h4><c:out value="${question.text}"/></h4>
            <c:forEach var="answer" items="${question.answers}">
                <p><c:out value="${answer.text}"/></p>
            </c:forEach>
        </c:forEach>
    </c:if>
</body>
</html>
