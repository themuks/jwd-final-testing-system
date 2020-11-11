<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="test" class="com.kuntsevich.ts.entity.Test" scope="request"/>
<jsp:useBean id="question" class="com.kuntsevich.ts.entity.Question" scope="request"/>
<jsp:useBean id="answer" class="com.kuntsevich.ts.entity.Answer" scope="request"/>
<p class="h4 text-center mt-3">Создание теста</p>
<div class="border rounded p-3 my-3">
    <form class="m-0" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="create-test"/>
        <div class="form-group">
            <label for="titleInput">Название</label>
            <input type="text" name="title" class="form-control" id="titleInput" required>
        </div>
        <div class="form-group">
            <label for="subjectInput">Предмет</label>
            <select class="form-control" name="subject" id="subjectInput" required>
                <c:forEach var="subject" items="${subjects}">
                    <option>${subject.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="descriptionInput">Описание</label>
            <textarea class="form-control" name="description" id="descriptionInput" rows="3" required></textarea>
        </div>
        <div class="form-group">
            <label for="pointsToPassInput">Необходимо баллов для прохождения теста</label>
            <input type="number" name="points" class="form-control" id="pointsToPassInput" min="1" required>
        </div>
        <c:forEach begin="1" end="${questionCount}" varStatus="loop">
            <div class="border rounded p-3 my-3">
                <p class="h4 text-center">Вопрос №${loop.index}</p>
                <div class="form-group">
                    <label class="" for="question${loop.index}Input">Текст вопроса</label>
                    <input type="text" name="q-${loop.index}" class="form-control" id="question${loop.index}Input"
                           required>
                </div>
                <div class="form-row mb-3">
                    <div class="col-auto d-flex align-items-center">
                        <label class="m-0" for="questionPointsInput">Баллов за вопрос</label>
                    </div>
                    <div class="col">
                        <input type="number" name="q-${loop.index} p" class="form-control" id="questionPointsInput"
                               min="1" required>
                    </div>
                </div>
                <div class="form-group">
                </div>
                <c:forEach begin="1" end="4" varStatus="innerLoop">
                    <div class="form-row mb-3">
                        <div class="col-auto d-flex align-items-center">
                            <label class="m-0" for="answer${innerLoop.index}Input">Ответ №${innerLoop.index}</label>
                        </div>
                        <div class="col">
                            <input type="text" name="q-${loop.index} a-${innerLoop.index}" class="form-control"
                                   id="answer${innerLoop.index}Input" required>
                        </div>
                        <div class="col-auto d-flex align-items-center">
                            <div class="form-check custom-control custom-checkbox">
                                <input class="form-check-input" type="checkbox" id="correctCheck" name="checkboxes"
                                       value="q-${loop.index} c-${innerLoop.index}">
                                <label class="form-check-label" for="correctCheck">Правильный</label>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
        <div class="d-flex justify-content-center">
            <button class="btn btn-primary" type="submit">Подтвердить</button>
        </div>
    </form>
</div>