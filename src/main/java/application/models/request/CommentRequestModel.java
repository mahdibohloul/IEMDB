package application.models.request;

import infrastructure.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestModel extends BaseModel {
    private String userEmail;
    private Integer movieId;
    private String text;
}
