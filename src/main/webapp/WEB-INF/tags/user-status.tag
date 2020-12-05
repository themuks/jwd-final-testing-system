<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<c:if test="${not empty sessionScope.role}">
    <ul class="nav navbar justify-content-center flex-row flex-nowrap">
        <li class="nav-item">
            <p class="m-0 px-3 text-light"><fmt:message key="user_status.hello"/>, ${sessionScope.username}</p>
        </li>
        <li class="nav-item">
            <a class="nav-link text-light" href="<c:url value="/controller?command=show-profile"/>"><fmt:message
                    key="user_status.profile"/></a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-light" href="<c:url value="/controller?command=logout"/>"><fmt:message
                    key="user_status.exit"/></a>
        </li>
    </ul>
    <%--<li class="nav-item ml-auto">
        <p class="m-0 px-3 py-2 text-light"><fmt:message key="user_status.hello"/>, ${sessionScope.username}</p>
    </li>
    <li class="nav-item">
        <a class="nav-link text-light" href="<c:url value="/controller?command=show-profile"/>"><fmt:message key="user_status.profile"/></a>
    </li>
    <li class="nav-item">
        <a class="nav-link text-light" href="<c:url value="/controller?command=logout"/>"><fmt:message key="user_status.exit"/></a>
    </li>--%>
</c:if>
<c:if test="${empty sessionScope.role}">
    <div class="form-inline">
        <a class="btn btn-outline-primary mr-2 my-2 my-lg-0" role="button"
           href="${pageContext.request.contextPath}/jsp/login.jsp"><fmt:message key="login.title"/></a>
        <a class="btn btn-primary" role="button"
           href="${pageContext.request.contextPath}/jsp/registration.jsp"><fmt:message key="registration.title"/></a>
    </div>
</c:if>