<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>

<c:if test="${not empty sessionScope.role}">
    <p class="m-0 px-3 py-2 text-light">Здравствуйте, ${sessionScope.username}</p>
    <a class="nav-link text-light" href="<c:url value="/controller?command=show-profile"/>">Профиль</a>
    <a class="nav-link text-light" href="<c:url value="/controller?command=logout"/>">Выйти</a>
</c:if>
<c:if test="${empty sessionScope.role}">
    <div class="form-inline">
        <a class="btn btn-outline-primary mr-2 my-2 my-lg-0" role="button"
           href="${pageContext.request.contextPath}/jsp/login.jsp">Вход</a>
        <a class="btn btn-primary" role="button"
           href="${pageContext.request.contextPath}/jsp/registration.jsp">Регистрация</a>
    </div>
</c:if>