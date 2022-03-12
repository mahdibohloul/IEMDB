<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<% if (request.getAttribute("message") != null) { %>
<p><%=request.getAttribute("message")%>
</p> <% } %>
<form action="/login" method="POST">
    <label>Email:</label>
    <input type="text" name="email" value="">
    <button type="submit">Login</button>
</form>
</body>
</html>