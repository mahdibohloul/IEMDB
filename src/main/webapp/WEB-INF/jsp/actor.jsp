<%@ page import="application.models.response.ActorResponseModel" %>
<%@ page import="application.models.response.ActorDetailResponseModel" %>
<%@ page import="java.util.List" %>
<%@ page import="application.models.response.ActorMovieModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Actor</title>
    <style>
        li, td, th {
            padding: 5px;
        }
    </style>
</head>
<body>
<% ActorDetailResponseModel actor = (ActorDetailResponseModel) request.getAttribute("actor"); %>
<a href="/">Home</a>
<p id="email">email: <%=request.getAttribute("userEmail")%>
</p>
<ul>
    <li id="name">name: <%=actor.getName()%>
    </li>
    <li id="birthDate">birthDate: <%=actor.getBirthDate()%></li>
    <li id="nationality">nationality: <%=actor.getNationality()%></li>
    <li id="tma">Total movies acted in: <%=actor.getMovies().size()%>
    </li>
</ul>
<table>
    <tr>
        <th>Movie</th>
        <th>imdb Rate</th>
        <th>rating</th>
        <th>page</th>
    </tr>
    <%
        List<ActorMovieModel> movies = actor.getMovies();
        for (ActorMovieModel movie : movies) {%>
    <tr>
        <td><%=movie.getName()%>
        </td>
        <td><%=movie.getImdbRate()%>
        </td>
        <td><%=movie.getRating()%>
        </td>
        <td><a href="/movies/<%=movie.getId()%>">Link</a></td>
            <%}%>
</table>
</body>
</html>
