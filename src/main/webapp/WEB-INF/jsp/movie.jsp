<%@ page import="application.models.response.MovieDetailResponseModel" %>
<%@ page import="java.util.List" %>
<%@ page import="application.models.response.ActorResponseModel" %>
<%@ page import="application.models.response.CommentResponseModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MovieDetailResponseModel movie = (MovieDetailResponseModel) request.getAttribute("movie");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Movie</title>
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
    <li id="name"><%=movie.getName()%>
    </li>
    <li id="summary">
        summary: <%=movie.getSummary()%>
    </li>
    <li id="releaseDate">releaseDate: <%=movie.getReleaseDate()%>
    </li>
    <li id="director">director: <%=movie.getDirector()%>
    </li>
    <li id="writers">writers: <%=movie.getWriters()%>
    </li>
    <li id="genres">genres: <%=movie.getGenres()%>
    </li>
    <li id="imdbRate">imdb Rate: <%=movie.getImdbRate()%>
    </li>
    <li id="rating">rating: <%=movie.getRating()%>
    </li>
    <li id="duration">duration: <%=movie.getDuration()%> minutes</li>
    <li id="ageLimit">ageLimit: <%=movie.getAgeLimit()%>
    </li>
</ul>
<h3>Cast</h3>
<table>
    <tr>
        <th>name</th>
        <th>age</th>
    </tr>
    <%
        List<ActorResponseModel> actors = movie.getCast();
        for (ActorResponseModel actor : actors) {%>
    <tr>
        <td><%=actor.getName()%>
        </td>
        <td><%=actor.getAge()%>
        </td>
        <td><a href="/actors/<%=actor.getActorId()%>">Link</a></td>
    </tr>
    <%}%>
</table>
<br>
<form action="/movies/<%=movie.getMovieId()%>" method="POST">
    <label>Rate(between 1 and 10):</label>
    <input type="number" id="quantity" name="quantity" min="1" max="10">
    <input type="hidden" id="form_action" name="action" value="rate">
    <%--    <input type="hidden" id="form_movie_id" name="movie_id" value="<%=movie.getMovieId()%>">--%>
    <button type="submit">rate</button>
</form>
<br>
<form action="/movies/<%=movie.getMovieId()%>" method="POST">
    <input type="hidden" id="form_action" name="action" value="add">
    <%--    <input type="hidden" id="form_movie_id" name="movie_id" value="<%=movie.getMovieId()%>">--%>
    <button type="submit">Add to WatchList</button>
</form>
<br/>
<table>
    <tr>
        <th>nickname</th>
        <th>comment</th>
        <th></th>
        <th></th>
    </tr>
    <%
        List<CommentResponseModel> comments = movie.getComments();
        for (CommentResponseModel comment : comments) {%>
    <tr>
        <td>@<%=comment.getUserNickname()%>
        </td>
        <td><%=comment.getText()%>
        </td>
        <td>
            <form action="/comment/<%=comment.getCommentId()%>" method="POST">
                <label for=""><%=comment.getLike()%>
                </label>
                <input type="hidden" id="form_action" name="action" value="like">
                <input type="hidden" id="form_movie_id" name="movie_id" value="<%=movie.getMovieId()%>">
                <button type="submit">like</button>
            </form>
        </td>
        <td>
            <form action="/comment/<%=comment.getCommentId()%>" method="POST">
                <label for=""><%=comment.getDislike()%>
                </label>
                <input type="hidden" id="form_action" name="action" value="dislike">
                <input type="hidden" id="form_movie_id" name="movie_id" value="<%=movie.getMovieId()%>">
                <button type="submit">dislike</button>
            </form>
        </td>
    </tr>
    <%}%>
</table>
<br><br>
<form action="/movies/<%=movie.getMovieId()%>" method="POST">
    <label>Your Comment:</label>
    <input type="text" name="comment" value="">
    <input type="hidden" id="form_action" name="action" value="comment">
    <button type="submit">Add Comment</button>
</form>
</body>
</html>

