package domain.comment.services;

import domain.comment.exceptions.InvalidVoteValueException;
import domain.comment.models.Comment;
import domain.comment.models.CommentVote;
import domain.comment.repositories.CommentVoteRepository;
import domain.user.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class CommentVoteService {
    @InjectMocks
    private CommentVoteServiceImpl commentVoteService;

    @Mock
    private CommentVoteRepository commentVoteRepository;

    private final PodamFactory podamFactory = new PodamFactoryImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should vote comment with success")
    void should_vote_comment_with_success() throws InvalidVoteValueException {
        User user = podamFactory.manufacturePojo(User.class);
        Comment comment = podamFactory.manufacturePojo(Comment.class);
        CommentVote commentVote = podamFactory.manufacturePojo(CommentVote.class);
        Integer vote = 5;

        Mockito.when(commentVoteRepository.save(commentVote)).thenReturn(commentVote);
        assert commentVoteService.voteComment(user, comment, vote).equals(commentVote);
    }

    @Test
    @DisplayName("should find commentVote by id with success")
    void should_vote_comment_by_id_with_success() throws InvalidVoteValueException {
        Comment comment = podamFactory.manufacturePojo(Comment.class);
        CommentVote commentVote = podamFactory.manufacturePojo(CommentVote.class);
        Mockito.when(commentVoteRepository.save(commentVote)).thenReturn(commentVote);
        assert commentVoteService.findAllByCommentId(comment.getId()).equals(commentVote);
    }
}
