<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="test" class="com.kuntsevich.ts.entity.Test" scope="request"/>
<jsp:useBean id="question" class="com.kuntsevich.ts.entity.Question" scope="request"/>
<jsp:useBean id="answer" class="com.kuntsevich.ts.entity.Answer" scope="request"/>
<p class="h4 text-center mt-3">Добавление предмета</p>
<div class="border rounded p-3 my-3">
    <form method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="add-subject"/>
        <div class="form-group">
            <label for="subjectNameInput">Название предмета</label>
            <input type="text" name="subjectName" class="form-control" id="subjectNameInput" required>
        </div>
        <div class="form-group">
            <label for="subjectDescriptionInput">Описание предмета</label>
            <textarea class="form-control" name="subjectDescription" id="subjectDescriptionInput" rows="3"
                      required></textarea>
        </div>
        <div class="d-flex justify-content-center">
            <button class="btn btn-primary" type="submit">Подтвердить</button>
        </div>
    </form>
</div>