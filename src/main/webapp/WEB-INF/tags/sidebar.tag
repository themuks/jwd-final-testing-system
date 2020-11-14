<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content.text"/>
<nav class="d-block ml-sm-auto bg-light sidebar mt-3 mb-lg-3 rounded">
    <ul class="nav flex-column">
        <c:if test="${(sessionScope.role eq 'Администратор') || (sessionScope.role eq 'Тьютор')}">
            <li class="nav-item">
                <a class="nav-link active" href="<c:url value="/controller?command=show-test-create"/>"><fmt:message
                        key="home.create_test"/></a>
            </li>
        </c:if>
        <c:if test="${(sessionScope.role eq 'Администратор') || (sessionScope.role eq 'Тьютор')}">
            <li class="nav-item">
                <a class="nav-link active" href="<c:url value="/controller?command=show-all-users"/>"><fmt:message
                        key="home.user_list"/></a>
            </li>
        </c:if>
        <li class="nav-item">
            <a class="nav-link active" href="<c:url value="/controller?command=show-all-tests"/>"><fmt:message
                    key="home.tests"/></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/controller?command=show-subjects"/>"><fmt:message
                    key="home.subject_list"/></a>
        </li>
        <li class="nav-item">
            <c:if test="${not empty sessionScope.userId}">
                <a class="nav-link"
                   href="<c:url value="/controller?command=show-user-results&userId=${sessionScope.userId}"/>"><fmt:message
                        key="home.results"/></a>
            </c:if>
            <c:if test="${empty sessionScope.userId}">
                <a class="nav-link" href="${pageContext.request.contextPath}/jsp/login.jsp"><fmt:message
                        key="home.results"/></a>
            </c:if>
        </li>
    </ul>
</nav>