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
<%@ page isELIgnored="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <title><spring:message code="create.title" /></title>
    </head>
    <body>
		    <form:form modelAttribute="register" action="/register/create" method="post">
            <fieldset>      
                <legend><spring:message code="create.legend" /></legend>
                <p>
                    <form:label for="displayName" path="displayName" cssErrorClass="error"><spring:message code="create.label.displayName" /></form:label>
                    <form:input path="displayName" />
                    <form:errors path="displayName" cssClass="error" />         
                </p>
                <p> 
                    <form:label for="username" path="username" cssErrorClass="error"><spring:message code="create.label.username" /></form:label>
                    <form:input path="username" />
                    <form:errors path="username" cssClass="error" />
                </p>
                <p>
                    <form:label for="email" path="email" cssErrorClass="error"><spring:message code="create.label.email" /></form:label>
                    <form:input path="email" />
                    <form:errors path="email" cssClass="error" />
                </p>
                <p> 
                    <form:label for="password" path="password" cssErrorClass="error"><spring:message code="create.label.password" /></form:label>
                    <form:password path="password" />
                    <form:errors path="password" cssClass="error" />
                </p>
                <p>
                    <input name="reset" type="reset" value="<spring:message code="global.reset" />" /> 
                    <input name="submit" type="submit" value="<spring:message code="global.submit" />" />
                </p>
            </fieldset>
        </form:form>
    </body>
</html>
