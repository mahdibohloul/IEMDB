package application.controllers;

import org.springframework.stereotype.Controller;

import application.models.request.CommentVoteRequestModel;
import domain.comment.exceptions.CommentNotFoundException;
import domain.comment.exceptions.InvalidVoteValueException;
import domain.comment.models.Comment;
import domain.comment.services.CommentService;
import domain.comment.services.CommentVoteService;
import domain.user.exceptions.UserNotFoundException;
import domain.user.models.User;
import domain.user.services.UserService;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final CommentVoteService commentVoteService;
    private final UserService userService;

    public CommentController(CommentService commentService,
            CommentVoteService commentVoteService, UserService userService) {
        this.commentService = commentService;
        this.commentVoteService = commentVoteService;
        this.userService = userService;
    }

    public void voteComment(CommentVoteRequestModel commentVoteRequestModel)
            throws CommentNotFoundException, UserNotFoundException, InvalidVoteValueException {
        Comment comment = commentService.findCommentById(commentVoteRequestModel.getCommentId());
        User user = userService.findUserByEmail(commentVoteRequestModel.getUserEmail());
        commentVoteService.voteComment(user, comment, commentVoteRequestModel.getVote());
    }
}
