<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty subjects}">
    <p class="h4 text-center mt-3">Список учебных предметов</p>
    <c:if test="${not empty sessionScope.role}">
        <c:if test="${(sessionScope.role eq 'Администратор') || (sessionScope.role eq 'Тьютор')}">
            <div class="mx-auto text-center my-3">
                <a class="btn btn-primary" role="button"
                   href="${pageContext.request.contextPath}/controller?command=show-subject-add">Добавить
                    предмет</a>
            </div>
        </c:if>
    </c:if>
    <c:forEach var="subject" items="${subjects}">
        <div class="card my-3">
            <div class="card-body">
                <h5 class="card-title">${subject.name}</h5>
                <p class="card-text">${subject.description}</p>
                <c:if test="${not empty sessionScope.role}">
                    <c:if test="${(sessionScope.role eq 'Администратор') || (sessionScope.role eq 'Тьютор')}">
                        <a class="btn btn-outline-danger" role="button"
                           href="${pageContext.request.contextPath}/controller?command=delete-subject&subjectId=${subject.subjectId}">Удалить</a>
                    </c:if>
                </c:if>
            </div>
        </div>
    </c:forEach>
</c:if>
<c:if test="${empty subjects}">
    <div class="mx-auto my-3 text-center">
        Нет учебных предметов
    </div>
</c:if>