<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="result" class="com.kuntsevich.ts.entity.Result" scope="request"/>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content.text"/>
<fmt:message key="results.title" var="title"/>
<tags:general title="${title}">
    <div class="col-lg-3">
        <tags:sidebar/>
    </div>
    <div class="col-lg-9">
        <c:if test="${not empty results}">
            <p class="h4 text-center mt-3"><fmt:message key="results.title"/></p>
            <c:forEach var="result" items="${results}">
                <div class="card my-3">
                    <div class="card-body">
                        <h5 class="card-title"><fmt:message key="results.test_number"/> ${result.test.testId}</h5>
                        <p class="card-text">${result.test.title} (${result.points} <fmt:message
                                key="results.of"/> ${result.totalPoints} <fmt:message key="results.points"/>)</p>
                        <p class="card-text"><fmt:message
                                key="results.total_right_answers"/>: ${result.correctAnswers}</p>
                        <c:if test="${result.testPassed}">
                            <p class="card-text text-success"><fmt:message key="results.test_passed"/></p>
                        </c:if>
                        <c:if test="${not result.testPassed}">
                            <p class="card-text text-danger"><fmt:message key="results.test_not_passed"/></p>
                        </c:if>
                        <c:if test="${not empty sessionScope.role eq 'Администратор' || sessionScope.role eq 'Тьютор'}">
                            <a href="<c:url value="/controller?command=delete-result&userId=${userId}&resultId=${result.resultId}"/>"
                               class="btn btn-outline-danger"><fmt:message key="results.delete"/></a>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${empty results}">
            <div class="mx-auto my-3 text-center">
                <fmt:message key="results.no_results"/>
            </div>
        </c:if>
    </div>
</tags:general>