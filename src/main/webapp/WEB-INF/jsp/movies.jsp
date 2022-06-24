<%@ page import="application.models.response.MovieResponseModel" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: tapsi
  Date: 3/12/22
  Time: 1:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String searchQuery = request.getAttribute("searchQuery") == null ? "" : request.getAttribute("searchQuery").toString(); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Movies</title>
    <style>
        li, td, th {
            padding: 5px;
        }
    </style>
</head>
<body>
<a href="/">Home</a>
<p id="email">email: <%=request.getAttribute("userEmail")%>
</p>
<br><br>
<form action="" method="POST">
    <label>Search:</label>
    <input type="text" name="search" value="<%=searchQuery%>">
    <button type="submit" name="action" value="search">Search</button>
    <button type="submit" name="action" value="clear">Clear Search</button>
</form>
<br><br>
<form action="/movies" method="POST">
    <label>Sort By:</label>
    <button type="submit" name="action" value="sort_by_imdb">imdb Rate</button>
    <button type="submit" name="action" value="sort_by_date">releaseDate</button>
</form>
<br>
<table>
    <tr>
        <th>name</th>
        <th>summary</th>
        <th>releaseDate</th>
        <th>director</th>
        <th>writers</th>
        <th>genres</th>
        <th>cast</th>
        <th>imdb Rate</th>
        <th>rating</th>
        <th>duration</th>
        <th>ageLimit</th>
        <th>Links</th>
    </tr>
    <%
        List<MovieResponseModel> movies = (List<MovieResponseModel>) request.getAttribute("movies");
        for (MovieResponseModel movie : movies) {%>
    <tr>
        <td><%=movie.getName()%>
        </td>
        <td><%=movie.getSummary()%>
        </td>
        <td><%=movie.getReleaseDateString()%>
        </td>
        <td><%=movie.getDirector()%>
        </td>
        <td><%=movie.getWriters()%>
        </td>
        <td><%=movie.getGenres()%>
        </td>
        <td><%=movie.getCast()%>
        </td>
        <td><%=movie.getImdbRate()%>
        </td>
        <td><%=movie.getRating()%>
        </td>
        <td><%=movie.getDuration()%>
        </td>
        <td><%=movie.getAgeLimit()%>
        </td>
        <td><a href="/movies/<%=movie.getMovieId()%>">Link</a></td>
    </tr>
    <%}%>
</table>
</body>
</html>
