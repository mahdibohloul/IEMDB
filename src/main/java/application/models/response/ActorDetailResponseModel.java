package application.models.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActorDetailResponseModel {
    private String name;
    private String birthDate;
    private String nationality;
    private List<ActorMovieModel> movies;
}
