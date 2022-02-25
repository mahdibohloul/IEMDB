package domain.comment.services;

import domain.comment.exceptions.CommentNotFoundException;
import domain.comment.models.Comment;
import domain.comment.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

public class CommentServiceTest {
    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private final CommentRepository commentRepository;


    private final PodamFactory podamFactory = new PodamFactoryImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    public CommentServiceTest(CommentServiceImpl commentService, CommentRepository commentRepository) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
    }

    @Test
    @DisplayName("should insert comment with success")
    void should_insert_comment_with_success() {
        Comment comment = podamFactory.manufacturePojo(Comment.class);
        Mockito.when(commentRepository.save(comment)).thenReturn(comment);
        assert commentService.insertComment(comment).equals(comment);
    }

    @Test
    @DisplayName("should get comment by id with success")
    void should_get_comment_by_id_with_success() throws CommentNotFoundException {
        Comment comment = podamFactory.manufacturePojo(Comment.class);
        Mockito.when(commentRepository.findById(comment.getId())).thenReturn(comment);
        assert commentService.findCommentById(comment.getId()).equals(comment);
    }

    @Test
    @DisplayName("should search comment with success")
    void should_search_comment_by_with_success() throws CommentNotFoundException {
        Comment comment = podamFactory.manufacturePojo(Comment.class);
        List<Integer> ids = new ArrayList<Integer>() ;
        List<Comment> comments = new ArrayList<Comment>() ;
        ids.add(comment.getId());
        Mockito.when(commentRepository.findById(comment.getId())).thenReturn(comment);
        assert commentService.searchComments(ids).equals(comments);
    }

}
