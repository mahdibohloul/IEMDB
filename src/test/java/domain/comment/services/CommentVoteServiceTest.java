package domain.comment.services;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import domain.comment.exceptions.InvalidVoteValueException;
import domain.comment.models.Comment;
import domain.comment.models.CommentVote;
import domain.comment.repositories.CommentVoteRepository;
import domain.comment.valueobjects.VoteType;
import domain.user.models.User;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class CommentVoteServiceTest {
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
        Integer vote = 1;
        CommentVote commentVote = new CommentVote();
        commentVote.setCommentId(comment.getId());
        commentVote.setUserEmail(user.getEmail());
        commentVote.setType(VoteType.fromInt(vote));

        Mockito.when(commentVoteRepository.save(commentVote)).thenReturn(commentVote);
        assert commentVoteService.voteComment(user, comment, vote).equals(commentVote);
    }

    @Test
    @DisplayName("should find commentVote by id with success")
    void should_finn_all_comment_vote_by_comment_id_with_success() {
        Comment comment = podamFactory.manufacturePojo(Comment.class);
        List<CommentVote> commentVotes = podamFactory.manufacturePojo(List.class, CommentVote.class);
        Mockito.when(commentVoteRepository.findAllByCommentId(comment.getId())).thenReturn(commentVotes.stream());
        Assertions.assertArrayEquals(commentVoteService.findAllByCommentId(comment.getId()).toArray(),
                commentVotes.toArray());
    }
}
