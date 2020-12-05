<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<fmt:message key="subjects.title" var="title"/>
<tags:general title="${title}">
    <div class="col-lg-3">
        <tags:sidebar/>
    </div>
    <div class="col-lg-9">
        <tags:message/>
        <c:if test="${not empty subjects}">
            <p class="h4 text-center mt-3"><fmt:message key="subjects.title"/></p>
            <c:if test="${not empty sessionScope.role}">
                <c:if test="${(sessionScope.role eq 'Администратор') || (sessionScope.role eq 'Тьютор')}">
                    <div class="mx-auto text-center my-3">
                        <a class="btn btn-primary" role="button"
                           href="${pageContext.request.contextPath}/controller?command=show-subject-add"><fmt:message
                                key="subject_add.title"/></a>
                    </div>
                </c:if>
            </c:if>
            <c:forEach var="subject" items="${subjects}">
                <div class="card my-3">
                    <div class="card-body">
                        <h5 class="card-title">${subject.name}</h5>
                        <p class="card-text">${subject.description}</p>
                        <c:if test="${not empty sessionScope.role}">
                            <c:if test="${(sessionScope.role eq 'Администратор') || (sessionScope.role eq 'Тьютор')}">
                                <a class="btn btn-outline-danger" role="button"
                                   href="${pageContext.request.contextPath}/controller?command=delete-subject&subjectId=${subject.subjectId}"><fmt:message
                                        key="subjects.delete_subject"/></a>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${empty subjects}">
            <div class="mx-auto my-3 text-center">
                <fmt:message key="subjects.no_subjects"/>
            </div>
        </c:if>
    </div>
</tags:general>