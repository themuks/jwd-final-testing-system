<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content.text"/>
<fmt:message key="about.title" var="title"/>
<tags:general title="${title}">
    <div class="col align-self-center text-center my-3">
        <p class="h1"><fmt:message key="about.welcome"/>!</p>
        <p class="h2"><fmt:message key="about.general"/></p>
        <p class="h2"><fmt:message key="about.hint"/></p>
        <a class="btn btn-outline-primary mt-3" href="${pageContext.request.contextPath}/jsp/home.jsp"><fmt:message
                key="about.home"/></a>
    </div>
</tags:general>