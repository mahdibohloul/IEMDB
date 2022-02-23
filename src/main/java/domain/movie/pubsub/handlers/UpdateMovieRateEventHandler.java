package domain.movie.pubsub.handlers;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import domain.movie.pubsub.events.MovieScoredEvent;
import domain.movie.services.MovieRateService;

@Component
public class UpdateMovieRateEventHandler {

    private final MovieRateService movieRateService;

    public UpdateMovieRateEventHandler(
            MovieRateService movieRateService) {
        this.movieRateService = movieRateService;
    }

    @EventListener(MovieScoredEvent.class)
    public void handle(MovieScoredEvent event) {
        movieRateService.updateMovieRate(event.getMovieId());
    }

}
