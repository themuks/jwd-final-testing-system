<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="test" class="com.kuntsevich.ts.entity.Test" scope="request"/>
<jsp:useBean id="question" class="com.kuntsevich.ts.entity.Question" scope="request"/>
<jsp:useBean id="answer" class="com.kuntsevich.ts.entity.Answer" scope="request"/>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="content.text"/>
<fmt:message key="test_edit.title" var="title"/>
<tags:general title="${title}">
    <div class="col-lg-3">
        <tags:sidebar/>
    </div>
    <div class="col-lg-9">
        <tags:message/>
        <p class="h4 text-center mt-3"><fmt:message key="test_edit.title"/></p>
        <div class="border rounded p-3 my-3">
            <form class="m-0" method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="create-test"/>
                <div class="form-group">
                    <label for="titleInput"><fmt:message key="test_edit.test_title"/></label>
                    <input type="text" name="title" class="form-control" id="titleInput" required>
                </div>
                <div class="form-group">
                    <label for="subjectInput"><fmt:message key="test_edit.subject"/></label>
                    <select class="form-control" name="subject" id="subjectInput" required>
                        <c:forEach var="subject" items="${subjects}">
                            <option>${subject.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="descriptionInput"><fmt:message key="test_edit.description"/></label>
                    <textarea class="form-control" name="description" id="descriptionInput" rows="3"
                              required></textarea>
                </div>
                <div class="form-group">
                    <label for="pointsToPassInput"><fmt:message key="test_edit.points_needed"/></label>
                    <input type="number" name="points" class="form-control" id="pointsToPassInput" min="1" required>
                </div>
                <c:forEach begin="1" end="${questionCount}" varStatus="loop">
                    <div class="border rounded p-3 my-3">
                        <p class="h4 text-center"><fmt:message key="test_edit.question_number"/>${loop.index}</p>
                        <div class="form-group">
                            <label class="" for="question${loop.index}Input"><fmt:message key="test_edit.text"/></label>
                            <input type="text" name="q-${loop.index}" class="form-control"
                                   id="question${loop.index}Input"
                                   required>
                        </div>
                        <div class="form-row mb-3">
                            <div class="col-auto d-flex align-items-center">
                                <label class="m-0" for="questionPointsInput"><fmt:message
                                        key="test_edit.points"/></label>
                            </div>
                            <div class="col">
                                <input type="number" name="q-${loop.index} p" class="form-control"
                                       id="questionPointsInput"
                                       min="1" required>
                            </div>
                        </div>
                        <div class="form-group">
                        </div>
                        <c:forEach begin="1" end="4" varStatus="innerLoop">
                            <div class="form-row mb-3">
                                <div class="col-auto d-flex align-items-center">
                                    <label class="m-0" for="answer${innerLoop.index}Input"><fmt:message
                                            key="test_edit.answer_number"/>${innerLoop.index}</label>
                                </div>
                                <div class="col">
                                    <input type="text" name="q-${loop.index} a-${innerLoop.index}" class="form-control"
                                           id="answer${innerLoop.index}Input" required>
                                </div>
                                <div class="col-auto d-flex align-items-center">
                                    <div class="custom-control custom-checkbox">
                                        <input class="custom-control-input" type="checkbox"
                                               id="correctCheck${loop.index}${innerLoop.index}"
                                               name="checkboxes"
                                               value="q-${loop.index} c-${innerLoop.index}">
                                        <label class="custom-control-label"
                                               for="correctCheck${loop.index}${innerLoop.index}"> <fmt:message
                                                key="test_edit.is_right"/></label>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
                <div class="d-flex justify-content-center">
                    <button class="btn btn-primary" type="submit"><fmt:message key="test_edit.submit"/></button>
                </div>
            </form>
        </div>
    </div>
</tags:general>