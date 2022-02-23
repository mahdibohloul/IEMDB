package domain.comment.services;

import org.springframework.stereotype.Service;

import domain.comment.exceptions.CommentNotFoundException;
import domain.comment.models.Comment;
import domain.comment.repositories.CommentRepository;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment insertComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment findCommentById(Integer id) throws CommentNotFoundException {
        Comment comment = commentRepository.findById(id);
        if (comment == null)
            throw new CommentNotFoundException(id);
        return comment;
    }

    @Override
    public Stream<Comment> searchComments(List<Integer> ids) {
        return commentRepository.searchComments(ids);
    }
}
