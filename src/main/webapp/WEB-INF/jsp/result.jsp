<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="result" class="com.kuntsevich.ts.entity.Result" scope="request"/>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<fmt:message key="result.title" var="title"/>
<tags:general title="${title}">
    <div class="col-lg-3">
        <tags:sidebar/>
    </div>
    <div class="col-lg-9">
        <tags:message/>
        <div class="border rounded p-3 my-3">
            <c:if test="${not empty result}">
                <p class="h3 text-center"><fmt:message key="result.title"/></p>
                <p class="h4 text-center">${result.points} <fmt:message key="result.of"/> ${result.totalPoints}
                    <fmt:message key="result.points"/></p>
                <p class="h4 text-center"><fmt:message key="result.total_right_answers"/>: ${result.correctAnswers}</p>
                <c:if test="${result.testPassed}">
                    <p class="h4 text-center text-success"><fmt:message key="result.test_passed"/></p>
                </c:if>
                <c:if test="${not result.testPassed}">
                    <p class="h4 text-center text-danger"><fmt:message key="result.test_not_passed"/></p>
                </c:if>
            </c:if>
        </div>
    </div>
</tags:general>