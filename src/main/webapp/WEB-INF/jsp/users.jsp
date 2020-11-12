<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty users}">
    <p class="h4 text-center mt-3">Список пользователей</p>
    <c:forEach var="user" items="${users}">
        <div class="card my-3">
            <div class="card-body">
                <c:if test="${user.status.name eq 'Активный'}">
                    <h5 class="card-title">${user.name} ${user.surname} (${user.username})</h5>
                    <p class="card-text">Активный</p>
                    <a href="<c:url value="/controller?command=show-user-results&userId=${user.userId}"/>"
                       class="btn btn-outline-primary">Результаты пользователя</a>
                    <c:if test="${not empty sessionScope.role eq 'Администратор'}">
                        <a href="<c:url value="/controller?command=show-user-results&userId=${user.userId}"/>"
                           class="btn btn-outline-danger">Деактивировать аккаунт</a>
                    </c:if>
                </c:if>
                <c:if test="${user.status.name eq 'Не активный'}">
                    <h5 class="card-title text-secondary">${user.name} ${user.surname} (${user.username})</h5>
                    <p class="card-text text-secondary">Не активен</p>
                </c:if>
            </div>
        </div>
    </c:forEach>
</c:if>
<c:if test="${empty users}">
    <div class="mx-auto my-3 text-center">
        Нет пользователей
    </div>
</c:if>