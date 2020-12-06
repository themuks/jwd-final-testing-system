<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="test" class="com.kuntsevich.ts.entity.Test" scope="request"/>
<jsp:useBean id="question" class="com.kuntsevich.ts.entity.Question" scope="request"/>
<jsp:useBean id="answer" class="com.kuntsevich.ts.entity.Answer" scope="request"/>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<fmt:message key="subject_add.title" var="title"/>
<tags:general title="${title}">
    <div class="col-lg-3">
        <tags:sidebar/>
    </div>
    <div class="col-lg-9">
        <tags:message/>
        <p class="h4 text-center mt-3"><fmt:message key="subject_add.title"/></p>
        <div class="border rounded p-3 my-3">
            <form method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="add-subject"/>
                <div class="form-group">
                    <label for="subjectNameInput"><fmt:message key="subject_add.subject_name"/> *</label>
                    <input type="text" maxlength="255" name="subjectName" class="form-control" id="subjectNameInput" required>
                </div>
                <div class="form-group">
                    <label for="subjectDescriptionInput"><fmt:message key="subject_add.subject_description"/> *</label>
                    <textarea class="form-control" maxlength="10000" name="subjectDescription" id="subjectDescriptionInput" rows="3"
                              required></textarea>
                </div>
                <div class="d-flex justify-content-center">
                    <button class="btn btn-primary" type="submit"><fmt:message key="subject_add.submit"/></button>
                </div>
            </form>
        </div>
    </div>
</tags:general>