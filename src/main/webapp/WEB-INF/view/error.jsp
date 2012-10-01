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
