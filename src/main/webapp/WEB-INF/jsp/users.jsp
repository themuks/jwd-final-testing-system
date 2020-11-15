<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content.text"/>
<fmt:message key="users.title" var="title"/>
<tags:general title="${title}">
    <div class="col-lg-3">
        <tags:sidebar/>
    </div>
    <div class="col-lg-9">
        <tags:message/>
        <c:if test="${not empty users}">
            <p class="h4 text-center mt-3"><fmt:message key="users.title"/></p>
            <c:forEach var="user" items="${users}">
                <div class="card my-3">
                    <div class="card-body">
                        <c:if test="${user.status.name eq 'Активный'}">
                            <h5 class="card-title">${user.name} ${user.surname} (${user.username})</h5>
                            <p class="card-text"><fmt:message key="users.active"/></p>
                            <a href="<c:url value="/controller?command=show-user-results&userId=${user.userId}&page=1"/>"
                               class="btn btn-outline-primary"><fmt:message key="users.results"/></a>
                            <c:if test="${not empty sessionScope.role eq 'Администратор'}">
                                <a href="<c:url value="/controller?command=show-user-results&userId=${user.userId}?"/>"
                                   class="btn btn-outline-danger"><fmt:message key="users.deactivate"/></a>
                            </c:if>
                        </c:if>
                        <c:if test="${user.status.name eq 'Не активный'}">
                            <h5 class="card-title text-secondary">${user.name} ${user.surname} (${user.username})</h5>
                            <p class="card-text text-secondary"><fmt:message key="users.inactive"/></p>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
            <tags:pagination pageCount="${pageCount}" currentPage="${page}"
                             url="/controller?command=show-users"/>
        </c:if>
        <c:if test="${empty users}">
            <div class="mx-auto my-3 text-center">
                <fmt:message key="users.no_users"/>
            </div>
        </c:if>
    </div>
</tags:general>