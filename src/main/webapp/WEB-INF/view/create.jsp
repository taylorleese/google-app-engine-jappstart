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
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <title>Create Account</title>
    </head>
    <body>
		<form:form modelAttribute="register" action="/register/create" method="post">
            <fieldset>      
                <legend>Create Account</legend>
                <p>
                    <form:label for="firstName" path="firstName" cssErrorClass="error">First Name:</form:label>
                    <form:input path="firstName" />
                    <form:errors path="firstName" cssClass="error" />          
                </p>
                <p> 
                    <form:label for="lastName" path="lastName" cssErrorClass="error">Last Name:</form:label>
                    <form:input path="lastName" />
                    <form:errors path="lastName" cssClass="error" />
                </p>
                <p>
                    <form:label for="email" path="email" cssErrorClass="error">E-mail:</form:label>
                    <form:input path="email" />
                    <form:errors path="email" cssClass="error" />
                </p>
                <p> 
                    <form:label for="password" path="password" cssErrorClass="error">Password:</form:label>
                    <form:password path="password" />
                    <form:errors path="password" cssClass="error" />
                </p>
                <p> 
                    <input name="submit" type="submit" value="Submit" />
                </p>
            </fieldset>
        </form:form>
	</body>
</html>