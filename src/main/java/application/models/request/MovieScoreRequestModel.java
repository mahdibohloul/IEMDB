package application.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieScoreRequestModel {
    private String userEmail;
    private Integer movieId;
    private Integer score;
}
