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
                    <form:label for="firstName" path="firstName" cssErrorClass="error"><spring:message code="create.label.firstName" /></form:label>
                    <form:input path="firstName" />
                    <form:errors path="firstName" cssClass="error" />          
                </p>
                <p> 
                    <form:label for="lastName" path="lastName" cssErrorClass="error"><spring:message code="create.label.lastName" /></form:label>
                    <form:input path="lastName" />
                    <form:errors path="lastName" cssClass="error" />
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