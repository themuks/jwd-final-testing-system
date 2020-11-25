<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="test" class="com.kuntsevich.ts.entity.Test" scope="request"/>
<jsp:useBean id="question" class="com.kuntsevich.ts.entity.Question" scope="request"/>
<jsp:useBean id="answer" class="com.kuntsevich.ts.entity.Answer" scope="request"/>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content.text"/>
<fmt:message key="create_test.title" var="title"/>
<tags:general title="${title}">
    <div class="col-lg-3">
        <tags:sidebar/>
    </div>
    <div class="col-lg-9">
        <tags:message/>
        <p class="h4 text-center mt-3"><fmt:message key="create_test.title"/></p>
        <div class="border rounded p-3 my-3">
            <form class="m-0" method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="show-test-edit"/>
                <div class="form-group">
                    <label for="questionsCountInput"><fmt:message key="create_test.question_count"/> *</label>
                    <input type="number" name="questionCount" class="form-control" id="questionsCountInput" min="1"
                           required>
                </div>
                <div class="d-flex justify-content-center">
                    <button class="btn btn-primary" type="submit"><fmt:message key="create_test.submit"/></button>
                </div>
            </form>
        </div>
    </div>
</tags:general>