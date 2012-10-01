<%--
    Copyright (C) 2010-2012 Taylor Leese (tleese22@gmail.com)

    This file is part of jappstart.

    jappstart is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    jappstart is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with jappstart.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
    <head>
        <title><spring:message code="index.title" /></title>
    </head>
    <body>
        <sec:authorize access="isAnonymous()">
            <p><spring:message code="global.login" htmlEscape="false" /></p>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <p><spring:message code="global.logout" htmlEscape="false" /></p>
        </sec:authorize>
        <p>
            Server: <%= application.getServerInfo() %><br/>
            Servlet Specification: <%= application.getMajorVersion() %>.<%= application.getMinorVersion() %><br/>
            JSP Version: <%= JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion() %>
        </p>
    </body>
</html>
