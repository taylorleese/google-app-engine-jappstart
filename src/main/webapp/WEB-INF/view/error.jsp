<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <title><spring:message code="error.title" /></title>
    </head>
    <body>
        <h2><spring:message code="error.header" /></h2>
        <c:if test="${not empty exception}">
            <h3><spring:message code="error.exception" /> <c:out value="${exception}" /></h3>
        </c:if>
        <c:if test="${not empty statusCode}">
            <h3><spring:message code="error.status" /> <c:out value="${statusCode}" /></h3>
        </c:if>
    </body>
</html>