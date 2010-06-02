<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <title><spring:message code="login.title" /></title>
    </head>
    <body>
		<h3><spring:message code="login.header" /></h3>
		
		<c:if test="${not empty error}">
  			<p class="error"><spring:message code="login.error" /></p>
		</c:if>
		
		<p id="userWarning" class="error" style="display:none"><spring:message code="login.nouser" /></p>
		
		<form action="/login/submit" method="post">
			<table>
    			<tr><td><spring:message code="login.label.username" /></td><td><input type="text" name="username" value="<c:if test="${not empty error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}" escapeXml="false" /></c:if>"/></td></tr>
    			<tr><td><spring:message code="login.label.password" /></td><td><input type="password" name="password" /></td></tr>
    			<tr><td><spring:message code="login.label.remember" /></td><td><input type="checkbox" name="remember_me" /></td></tr>
    			<tr><td><input name="reset" type="reset" value="<spring:message code="global.reset" />" /></td><td><input name="submit" type="submit" value="<spring:message code="global.submit" />" /></td></tr>
  			</table>
		</form>
		
		<p><spring:message code="login.create" htmlEscape="false" /></p>
		<script type="text/javascript">
            $(document).ready(function () {
                $("input[name='username']").blur(function () {
                    var values = {};
                    values['username'] = this.value;
                    
                    sendJson(
                        values,
                        '/login/validate',
                        function(result) { 
                            if (result.found) {
                                $('#userWarning').hide();
                            } else {
                                $('#userWarning').show();
                            }
                        }
                    );
                });
            });
        </script>
	</body>
</html>