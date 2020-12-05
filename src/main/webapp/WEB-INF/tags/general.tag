<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="true" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sticky-footer.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon"/>
    <title>${title}</title>
</head>
<body class="d-flex flex-column h-100">
<header>

    <nav class="navbar navbar-dark sticky-top bg-dark navbar-expand-lg">
        <div class="container d-flex justify-content-center justify-content-sm-between">
            <a class="navbar-brand ml-sm-3" href="${pageContext.request.contextPath}/jsp/home.jsp">
                <img src="${pageContext.request.contextPath}/img/logo.png" width="30" height="30"
                     class="d-inline-block align-top mr-2" alt="logo" loading="lazy">
                <fmt:message key="home.title"/>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggler"
                    aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-between" id="navbarToggler">
                <ul class="navbar-nav mr-md-auto list-inline">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/jsp/home.jsp"><fmt:message
                                key="home.general"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/jsp/about.jsp"><fmt:message
                                key="home.about"/></a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown"
                           aria-haspopup="true" aria-expanded="false">
                            <fmt:message key="home.language"/>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item"
                               href="#"
                               data-href="/controller?command=change-language&lang=ru"
                               data-toggle="modal"
                               data-target="#languageModal">Русский</a>
                            <a class="dropdown-item"
                               href="#"
                               data-href="/controller?command=change-language&lang=en"
                               data-toggle="modal"
                               data-target="#languageModal">English</a>
                        </div>
                    </li>
                </ul>
                <tags:user-status/>
            </div>
        </div>
    </nav>
</header>
<main class="content">

    <div class="modal fade" id="languageModal" tabindex="-1" aria-labelledby="languageModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="languageModalLabel"><fmt:message
                            key="general.modal.title"/></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <fmt:message key="general.modal.body"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                            key="general.modal.close"/></button>
                    <a class="btn btn-primary btn-ok"><fmt:message
                            key="general.modal.ok"/></a>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <jsp:doBody/>
        </div>
    </div>
</main>
<footer class="footer bg-light d-flex align-items-center">
    <div class="container text-center">
        <span class="text-muted">© 2020 <fmt:message key="general.copyright"/></span>
    </div>
</footer>
<script src="${pageContext.request.contextPath}/js/update-block.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
        crossorigin="anonymous"></script>
<script>
    $('#languageModal').on('show.bs.modal', function (e) {
        $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
    });
</script>
</body>
</html>