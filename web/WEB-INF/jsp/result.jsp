<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="border rounded p-3 my-3">
    <c:if test="${not empty result}">
        <p class="h3 text-center">Ваш результат</p>
        <p class="h3 text-center">Ваш результат</p>
        <p class="h3 text-center">Тест</p>
        <p class="h4">Название теста: <c:out value="${test.title}"/></p>
        <p class="h4">Предмет: <c:out value="${test.subject.name}"/></p>
        <p class="h4">Описание: <c:out value="${test.description}"/></p>
        <form class="m-0" method="post" action="${pageContext.request.contextPath}/controller?command=submit-test">
            <input type="hidden" name="test-id" value="<c:out value="${test.testId}"/>">
            <c:forEach var="question" items="${test.questions}" varStatus="questionLoop">
                <div class="mb-3">
                    <p class="h5">Вопрос ${questionLoop.count}. <c:out value="${question.text}"/></p>
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
            </c:forEach>
            <div class="d-flex justify-content-center">
                <button class="btn btn-primary" type="submit">Отправить ответы</button>
            </div>
        </form>
    </c:if>
</div>