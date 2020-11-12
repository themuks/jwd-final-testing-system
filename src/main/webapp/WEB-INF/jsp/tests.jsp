<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty tests}">
    <p class="h4 text-center mt-3   ">Список доступных тестов</p>
    <c:forEach var="test" items="${tests}">
        <div class="card my-3">
            <div class="card-body">
                <h5 class="card-title"><c:out value="${test.title}"/></h5>
                <p class="card-text"><c:out value="${test.description}"/></p>
                <a href="${pageContext.request.contextPath}/controller?command=show-test&id=<c:out value="${test.testId}"/>"
                   class="btn btn-outline-primary">Пройти тест</a>
                <c:if test="${not empty sessionScope.role}">
                    <c:if test="${(sessionScope.role eq 'Администратор') || (sessionScope.role eq 'Тьютор')}">
                        <a class="btn btn-outline-danger" role="button"
                           href="${pageContext.request.contextPath}/controller?command=delete-test&testId=${test.testId}">Удалить</a>
                    </c:if>
                </c:if>
            </div>
        </div>
    </c:forEach>
</c:if>
<c:if test="${empty tests}">
    <div class="mx-auto my-3 text-center">
        Нет тестов
    </div>
</c:if>