package domain.comment.models;

import infrastructure.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Comment extends BaseEntity {
    private String userEmail;
    private String text;
    private Long createdTimestamp;
}
