<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="result" class="com.kuntsevich.ts.entity.Result" scope="request"/>
<c:if test="${not empty results}">
    <p class="h4 text-center mt-3">Список результатов по тестам</p>
    <c:forEach var="result" items="${results}">
        <div class="border rounded p-3 my-3">
            <p class="h4 text-center">Тест № ${result.test.testId}</p>
            <p class="h4 text-center">${result.test.title}</p>
            <p class="h5 text-center">${result.points} из ${result.totalPoints} баллов</p>
            <p class="h5 text-center">Всего правильных ответов: ${result.correctAnswers}</p>
            <c:if test="${result.testPassed}">
                <p class="h4 text-center text-success">Тест сдан</p>
            </c:if>
            <c:if test="${not result.testPassed}">
                <p class="h4 text-center text-danger">Тест не сдан</p>
            </c:if>
        </div>
    </c:forEach>
</c:if>
<c:if test="${empty results}">
    <div class="mx-auto my-3 text-center">
        У вас нет результатов
    </div>
</c:if>