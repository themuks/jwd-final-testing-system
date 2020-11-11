<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty subjects}">
    <p class="h4 text-center mt-3">Список учебных предметов</p>
    <c:forEach var="subject" items="${subjects}">
        <div class="card my-3">
            <div class="card-body">
                <h5 class="card-title">${subject.name}</h5>
                <p class="card-text">${subject.description}</p>
            </div>
        </div>
    </c:forEach>
</c:if>
<c:if test="${empty subjects}">
    <div class="mx-auto my-3 text-center">
        Нет учебных предметов
    </div>
</c:if>
