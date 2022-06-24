package application.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRecommendedMoviesRequestModel {
    private String userEmail;
    private Integer count;
}
