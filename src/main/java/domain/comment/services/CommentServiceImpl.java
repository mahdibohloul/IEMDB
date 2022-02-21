package domain.comment.services;

import domain.comment.models.Comment;
import domain.comment.repositories.CommentRepository;
import org.springframework.stereotype.Service;

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
}
