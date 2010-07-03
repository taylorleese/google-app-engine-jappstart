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