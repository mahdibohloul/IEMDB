package application.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;

import application.models.request.AddActorRequestModel;
import application.models.request.GetActorByIdRequestModel;
import application.models.response.ActorDetailResponseModel;
import application.models.response.ActorMovieModel;
import application.models.response.GenericResponseModel;
import domain.actor.exceptions.ActorNotFoundException;
import domain.actor.models.Actor;
import domain.actor.services.ActorService;
import domain.movie.models.Movie;
import domain.movie.services.MovieRateService;
import domain.movie.services.MovieService;
import framework.router.commandline.exceptions.InvalidCommandException;

@Controller
public class ActorController {
    private final ActorService actorService;
    private final MovieService movieService;
    private final MovieRateService movieRateService;

    public ActorController(ActorService actorService, MovieService movieService,
            MovieRateService movieRateService) {
        this.actorService = actorService;
        this.movieService = movieService;
        this.movieRateService = movieRateService;
    }

    public GenericResponseModel addActor(AddActorRequestModel addActorRequestModel) {
        GenericResponseModel response = new GenericResponseModel();
        try {
            Actor actor = addActorRequestModel.toActor();
            actorService.insertActor(actor);
            response.setSuccessfulResponse("actor added successfully");
        } catch (ParseException e) {
            response.setUnsuccessfulResponse(new InvalidCommandException("Invalid date format").toString());
        }
        return response;
    }

    public ActorDetailResponseModel getActorById(GetActorByIdRequestModel getActorByIdRequestModel)
            throws ActorNotFoundException {
        Actor actor = actorService.findActorById(getActorByIdRequestModel.getActorId());
        List<Movie> movies = movieService.searchMovies(null, null, null, null, null,
                Collections.singletonList(actor.getId()), null, null,
                null, null, null, null).toList();
        Map<Integer, Double> movieRates =
                movies.stream().collect(Collectors.toMap(Movie::getId, movie -> {
                    Double rate = movieRateService.getMovieRate(movie);
                    if (rate == null)
                        return 0.0;
                    return rate;
                }));

        List<ActorMovieModel> actorMovies = movies.stream()
                .map(mv -> new ActorMovieModel(mv.getId(), mv.getName(), mv.getImdbRate(),
                        movieRates.get(mv.getId()))).toList();

        String birthDate = new SimpleDateFormat("yyyy-MM-dd").format(actor.getBirthDate());
        return new ActorDetailResponseModel(actor.getName(),
                birthDate,
                actor.getNationality(),
                actorMovies);

    }
}
