<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<fmt:message key="home.title" var="title"/>
<tags:general title="${title}">
    <div class="col-lg-3">
        <tags:sidebar/>
    </div>
    <div class="col-lg-9">
        <tags:message/>
        <div class="mx-auto my-3 text-center">
            <fmt:message key="home.choose_menu_item"/>
        </div>
    </div>
</tags:general>