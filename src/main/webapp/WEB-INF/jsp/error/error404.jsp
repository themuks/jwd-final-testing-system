<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<fmt:message key="error_404.title" var="title"/>
<tags:general title="${title}">
    <div class="col align-self-center text-center my-3 p-5">
        <p class="h1"><fmt:message key="error_404.title"/></p>
        <p class="h2"><fmt:message key="error_404.general"/></p>
        <p class="h2"><fmt:message key="error_404.hint"/></p>
    </div>
</tags:general>