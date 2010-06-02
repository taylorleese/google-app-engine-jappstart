<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
    <p><spring:message code="header.info" /> <sec:authentication property="principal.username" /></p>
</sec:authorize>