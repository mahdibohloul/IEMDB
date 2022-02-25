package application.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetMoviesByGenreRequestModel {
    private String genre;

    @JsonCreator
    public GetMoviesByGenreRequestModel(
            @JsonProperty(value = "genre", required = true) String genre) {
        this.genre = genre;
    }
}
