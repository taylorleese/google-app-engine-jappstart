<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <title><spring:message code="index.title" /></title>
    </head>
    <body>
        <p><spring:message code="global.login" htmlEscape="false" /></p>
        <p>
            Server: <%= application.getServerInfo() %><br/>
            Servlet Specification: <%= application.getMajorVersion() %>.<%= application.getMinorVersion() %><br/>
            JSP Version: <%= JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion() %>
        </p>
    </body>
</html>