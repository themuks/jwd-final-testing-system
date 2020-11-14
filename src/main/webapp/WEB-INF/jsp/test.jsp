<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="test" class="com.kuntsevich.ts.entity.Test" scope="request"/>
<jsp:useBean id="question" class="com.kuntsevich.ts.entity.Question" scope="request"/>
<jsp:useBean id="answer" class="com.kuntsevich.ts.entity.Answer" scope="request"/>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content.text"/>
<fmt:message key="test.title" var="title"/>
<tags:general title="${title}">
    <div class="col-lg-3">
        <tags:sidebar/>
    </div>
    <div class="col-lg-9">
        <p class="h4 text-center mt-3"><fmt:message key="test.title"/></p>
        <div class="border rounded p-3 my-3">
            <c:if test="${not empty test}">
                <p class="h4"><fmt:message key="test.test_title"/>: <c:out value="${test.title}"/></p>
                <p class="h4"><fmt:message key="test.subject"/>: <c:out value="${test.subject.name}"/></p>
                <p class="h4"><fmt:message key="test.description"/>: <c:out value="${test.description}"/></p>
                <form class="m-0" method="post" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="submit-test"/>
                    <input type="hidden" name="testId" value="<c:out value="${test.testId}"/>">
                    <c:forEach var="question" items="${test.questions}" varStatus="questionLoop">
                        <div class="mb-3">
                            <div class="border rounded p-3 my-3">
                                <p class="h4 text-center"><fmt:message
                                        key="test.question_number"/>${questionLoop.count}</p>
                                <p class="h5">${question.text}</p>
                                <c:forEach var="answer" items="${question.answers}">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" name="q-${question.questionId} a-${answer.answerId}"
                                               value="${answer.text}" class="custom-control-input"
                                               id="customCheck${answer.answerId}">
                                        <label class="custom-control-label" for="customCheck${answer.answerId}"><c:out
                                                value="${answer.text}"/></label>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="d-flex justify-content-center">
                        <button class="btn btn-primary" type="submit"><fmt:message key="test.submit"/></button>
                    </div>
                </form>
            </c:if>
        </div>
    </div>
</tags:general>