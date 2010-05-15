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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Login</title>
    </head>
    <body>
		<h3>Please enter your username and password.</h3>
		
		<c:if test="${not empty param.login_error}">
  			<p class="error">Your login attempt was not successful, try again.</p>
		</c:if>
		
		<p id="userWarning" class="error" style="display:none">The given username does not exist.</p>
		
		<form action="/login/submit" method="post">
			<table>
    			<tr><td>Username:</td><td><input type="text" name="username" value="<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}" escapeXml="false" /></c:if>"/></td></tr>
    			<tr><td>Password:</td><td><input type="password" name="password" /></td></tr>
    			<tr><td>Remember me:</td><td><input type="checkbox" name="remember_me" /></td></tr>
    			<tr><td><input name="submit" type="submit" value="Submit" /></td><td><input name="reset" type="reset" value="Reset" /></td></tr>
  			</table>
		</form>
		
		<p>Need to create an account? Click <a href="/register/create">here</a>.</p>
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