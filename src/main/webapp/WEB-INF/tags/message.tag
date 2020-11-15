<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger my-3" role="alert">${errorMessage}</div>
</c:if>
<c:if test="${not empty infoMessage}">
    <div class="alert alert-info my-3" role="alert">${infoMessage}</div>
</c:if>