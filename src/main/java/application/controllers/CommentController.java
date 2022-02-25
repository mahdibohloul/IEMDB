package application.controllers;

import org.springframework.stereotype.Controller;

import application.models.request.VoteCommentRequestModel;
import application.models.response.GenericResponseModel;
import domain.comment.models.Comment;
import domain.comment.services.CommentService;
import domain.comment.services.CommentVoteService;
import domain.user.models.User;
import domain.user.services.UserService;
import infrastructure.exceptions.IemdbException;

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

    public GenericResponseModel voteComment(VoteCommentRequestModel voteCommentRequestModel) {
        GenericResponseModel response = new GenericResponseModel();
        try {
            Comment comment = commentService.findCommentById(voteCommentRequestModel.getCommentId());
            User user = userService.findUserByEmail(voteCommentRequestModel.getUserEmail());
            commentVoteService.voteComment(user, comment, voteCommentRequestModel.getVote());
            response.setSuccessfulResponse("comment voted successfully");
        } catch (IemdbException e) {
            response.setUnsuccessfulResponse(e.toString());
        }
        return response;
    }
}
