<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content.text"/>
<fmt:message key="tests.title" var="title"/>
<tags:general title="${title}">
    <div class="col-lg-3">
        <tags:sidebar/>
    </div>
    <div class="col-lg-9">
        <tags:message/>
        <c:if test="${not empty tests}">
            <p class="h4 text-center mt-3"><fmt:message key="tests.title"/></p>
            <c:forEach var="test" items="${tests}">
                <div class="card my-3">
                    <div class="card-body">
                        <h5 class="card-title"><c:out value="${test.title}"/></h5>
                        <p class="card-text"><c:out value="${test.description}"/></p>
                        <a href="${pageContext.request.contextPath}/controller?command=show-test&id=<c:out value="${test.testId}"/>"
                           class="btn btn-outline-primary"><fmt:message key="tests.pass_test"/></a>
                        <c:if test="${not empty sessionScope.role}">
                            <c:if test="${(sessionScope.role eq 'Администратор') || (sessionScope.role eq 'Тьютор')}">
                                <a class="btn btn-outline-danger" role="button"
                                   href="${pageContext.request.contextPath}/controller?command=delete-test&testId=${test.testId}"><fmt:message
                                        key="tests.delete"/></a>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
            <tags:pagination pageCount="${pageCount}" currentPage="${page}"
                             url="/controller?command=show-tests"/>
        </c:if>
        <c:if test="${empty tests}">
            <div class="mx-auto my-3 text-center">
                <fmt:message key="tests.no_tests"/>
            </div>
        </c:if>
    </div>
</tags:general>