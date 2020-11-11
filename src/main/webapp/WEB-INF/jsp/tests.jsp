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
                   class="btn btn-primary">Пройти тест</a>
            </div>
        </div>
    </c:forEach>
</c:if>
