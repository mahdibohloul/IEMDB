package domain.movie.pubsub.events;

import lombok.Getter;

@Getter
public class MovieScoredEvent {
    private Integer movieId;

    public MovieScoredEvent(Integer movieId) {
        this.movieId = movieId;
    }
}
