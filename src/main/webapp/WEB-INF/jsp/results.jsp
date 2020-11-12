<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="result" class="com.kuntsevich.ts.entity.Result" scope="request"/>
<c:if test="${not empty results}">
    <p class="h4 text-center mt-3">Список результатов по тестам</p>
    <c:forEach var="result" items="${results}">
        <div class="card my-3">
            <div class="card-body">
                <h5 class="card-title">Тест № ${result.test.testId}</h5>
                <p class="card-text">${result.test.title} (${result.points} из ${result.totalPoints} баллов)</p>
                <p class="card-text">Всего правильных ответов: ${result.correctAnswers}</p>
                <c:if test="${result.testPassed}">
                    <p class="card-text text-success">Тест сдан</p>
                </c:if>
                <c:if test="${not result.testPassed}">
                    <p class="card-text text-danger">Тест не сдан</p>
                </c:if>
                <c:if test="${not empty sessionScope.role eq 'Администратор' || sessionScope.role eq 'Тьютор'}">
                    <a href="<c:url value="/controller?command=delete-result&userId=${userId}&resultId=${result.resultId}"/>"
                       class="btn btn-outline-danger">Удалить</a>
                </c:if>
            </div>
        </div>
    </c:forEach>
</c:if>
<c:if test="${empty results}">
    <div class="mx-auto my-3 text-center">
        Нет результатов
    </div>
</c:if>