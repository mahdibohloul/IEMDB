package domain.comment.services;

import domain.comment.models.Comment;

public interface CommentService {
    Comment insertComment(Comment comment);
}
