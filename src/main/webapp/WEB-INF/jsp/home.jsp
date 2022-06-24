<%@ page import="infrastructure.startup.ApplicationStartup" %>
<%@ page import="infrastructure.workcontext.services.WorkContext" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>
<ul>
    <li id="email"><%=request.getAttribute("userEmail")%>
    </li>
    <li>
        <a href="/movies">Movies</a>
    </li>
    <li>
        <a href="/watchList">Watch List</a>
    </li>
    <li>
        <a href="/logout">Log Out</a>
    </li>
</ul>
</body>
</html>
