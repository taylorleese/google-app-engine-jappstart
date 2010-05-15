<%--
    Copyright (C) 2010 Taylor Leese (tleese22@gmail.com)

    This file is part of jappstart.

    jappstart is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    jappstart is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with jappstart.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <title>Welcome to jappstart</title>
    </head>
    <body>
        <h2><spring:message code="hello" /> World!</h2>
        <p>Click <a href="/login">here</a> to login.</p>
        <p>Working with server: <%= application.getServerInfo() %></p>
        <p>Servlet Specification: <%= application.getMajorVersion() %>.<%= application.getMinorVersion() %></p>
        <p>JSP version: <%= JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion() %></p>
    </body>
</html>