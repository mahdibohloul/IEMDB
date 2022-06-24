package application.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetMoviesByYearRequestModel {
    private Integer startYear;
    private Integer endYear;
}
