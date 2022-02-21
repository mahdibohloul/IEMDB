package domain.movie.pubsub.handlers;

import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import domain.movie.pubsub.events.MovieScoredEvent;
import domain.movie.services.MovieRateService;

@Component
public class UpdateMoveRateEventHandler {

    private final MovieRateService movieRateService;
    private final Logger logger;

    public UpdateMoveRateEventHandler(MovieRateService movieRateService, Logger logger) {
        this.movieRateService = movieRateService;
        this.logger = logger;
    }

    @EventListener(MovieScoredEvent.class)
    public void handle(MovieScoredEvent event) {
        logger.info("MovieScoredEvent received: {}", event);
        movieRateService.updateMovieRate(event.getMovieId());
    }

}
