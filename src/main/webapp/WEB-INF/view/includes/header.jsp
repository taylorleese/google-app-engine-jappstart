<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.apache.commons.codec.digest.DigestUtils" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<sec:authorize access="isAuthenticated()">
    <c:set var="email">
        <sec:authentication property="principal.email" />
    </c:set>
    <c:set var="gravatarHash" value='<%= DigestUtils.md5Hex(StringEscapeUtils.unescapeXml((String)pageContext.getAttribute("email")).trim().toLowerCase()) %>' />
    <p><spring:message code="header.info" /> <img src="${pageContext.request.scheme}://<c:choose><c:when test="${pageContext.request.scheme == 'http'}">www</c:when><c:otherwise>secure</c:otherwise></c:choose>.gravatar.com/avatar/${gravatarHash}.jpg?s=25&amp;d=identicon&amp;r=pg" alt="<sec:authentication property="principal.displayName" />" /> <sec:authentication property="principal.displayName" /></p>
</sec:authorize>