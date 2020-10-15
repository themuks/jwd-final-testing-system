<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger" role="alert">${errorMessage}</div>
</c:if>
<c:if test="${not empty tests}">
    <c:forEach var="test" items="${tests}">
        <div class="card my-4">
            <div class="card-body">
                <h5 class="card-title"><c:out value="${test.title}"/></h5>
                <p class="card-text"><c:out value="${test.description}"/></p>
                <a href="${pageContext.request.contextPath}/controller?command=show-test&id=<c:out value="${test.testId}"/>"
                   class="btn btn-primary">Пройти тест</a>
            </div>
        </div>
    </c:forEach>
</c:if>
