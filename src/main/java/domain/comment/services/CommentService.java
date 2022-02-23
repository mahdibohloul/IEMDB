package domain.comment.services;

import domain.comment.exceptions.CommentNotFoundException;
import domain.comment.models.Comment;

import java.util.List;
import java.util.stream.Stream;

public interface CommentService {
    Comment insertComment(Comment comment);

    Comment findCommentById(Integer id) throws CommentNotFoundException;

    Stream<Comment> searchComments(List<Integer> ids);
}
