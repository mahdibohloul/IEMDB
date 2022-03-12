<%@ page import="application.models.response.GetUserWatchListResponseModel" %>
<%@ page import="application.models.response.MovieResponseModel" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: tapsi
  Date: 3/12/22
  Time: 7:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GetUserWatchListResponseModel userWatchList = (GetUserWatchListResponseModel) request.getAttribute("userWatchList");
    List<MovieResponseModel> recommendedMovies = (List<MovieResponseModel>) request.getAttribute("recommendedMovies");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WatchList</title>
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
<ul>
    <li id="name">name: <%=userWatchList.getUserName()%>
    </li>
    <li id="nickname">nickname: @<%=userWatchList.getUserNickname()%>
    </li>
</ul>
<h2>Watch List</h2>
<table>
    <tr>
        <th>Movie</th>
        <th>releaseDate</th>
        <th>director</th>
        <th>genres</th>
        <th>imdb Rate</th>
        <th>rating</th>
        <th>duration</th>
        <th></th>
        <th></th>
    </tr>
    <% for (MovieResponseModel movie : userWatchList.getWatchList()) {%>
    <tr>
        <td><%=movie.getName()%>
        </td>
        <td><%=movie.getReleaseDateString()%>
        </td>
        <td><%=movie.getDirector()%>
        </td>
        <td><%=movie.getGenres()%>
        </td>
        <td><%=movie.getImdbRate()%>
        </td>
        <td><%=movie.getRating()%>
        </td>
        <td><%=movie.getDuration()%>
        </td>
        <td><a href="/movies/<%=movie.getMovieId()%>">Link</a></td>
        <td>
            <form action="/watchList/remove" method="POST">
                <input id="form_movie_id" type="hidden" name="movie_id" value="<%=movie.getMovieId()%>">
                <button type="submit">Remove</button>
            </form>
        </td>
    </tr>
    <%}%>
</table>
<h2>Recommendation List</h2>
<table>
    <tr>
        <th>Movie</th>
        <th>imdb Rate</th>
        <th></th>
    </tr>
    <% for (MovieResponseModel movie : recommendedMovies) {%>
    <tr>
        <td><%=movie.getName()%>
        </td>
        <td><%=movie.getImdbRate()%>
        </td>
        <td><a href="/movies/<%=movie.getMovieId()%>">Link</a></td>
    </tr>
    <%}%>

</table>
</body>
</html>
