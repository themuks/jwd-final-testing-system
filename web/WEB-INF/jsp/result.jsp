<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="result" class="com.kuntsevich.testsys.entity.Result" scope="request"/>
<div class="border rounded p-3 my-3">
    <c:if test="${not empty result}">
        <p class="h3 text-center">Ваш результат</p>
        <p class="h4 text-center">${result.points} из ${result.totalPoints}</p>
        <p class="h4 text-center">Всего правильных ответов: ${result.correctAnswers}</p>
    </c:if>
</div>