<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="test" class="com.kuntsevich.ts.entity.Test" scope="request"/>
<jsp:useBean id="question" class="com.kuntsevich.ts.entity.Question" scope="request"/>
<jsp:useBean id="answer" class="com.kuntsevich.ts.entity.Answer" scope="request"/>
<p class="h4 text-center mt-3">Создание теста</p>
<div class="border rounded p-3 my-3">
    <form method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="show-test-edit"/>
        <div class="form-group">
            <label for="questionsCountInput">Количество вопросов в тесте</label>
            <input type="number" name="questionCount" class="form-control" id="questionsCountInput" min="1" required>
        </div>
        <div class="d-flex justify-content-center">
            <button class="btn btn-primary" type="submit">Подтвердить</button>
        </div>
    </form>
</div>