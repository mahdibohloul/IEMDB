package domain.comment.exceptions;

import infrastructure.exceptions.IemdbException;

public class CommentNotFoundException extends IemdbException {
    public CommentNotFoundException(Integer id) {
        super("Comment with id " + id + " not found");
    }
}
