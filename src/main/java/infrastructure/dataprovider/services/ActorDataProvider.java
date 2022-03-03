package infrastructure.dataprovider.services;

import domain.actor.models.Actor;
import domain.actor.services.ActorService;
import infrastructure.AppConfig;
import infrastructure.dataprovider.models.ActorModel;
import infrastructure.http.services.HttpClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Stream;

@Component
@Order(value = 1)
public class ActorDataProvider implements DataProvider<ActorModel> {

    private final HttpClient httpClient;
    private final ActorService actorService;
    private final AppConfig appConfig;
    private final static String ACTORS_URL = "actors";

    public ActorDataProvider(HttpClient httpClient, ActorService actorService, AppConfig appConfig) {
        this.httpClient = httpClient;
        this.actorService = actorService;
        this.appConfig = appConfig;
    }

    @Override
    public Stream<ActorModel> provide() throws IOException {
        String requestUrl = appConfig.getDataProviderUrl() + ACTORS_URL;
        ActorModel[] actorModels = httpClient.get(requestUrl, ActorModel[].class);
        return Stream.of(actorModels);
    }

    @Override
    public void populateData(Stream<ActorModel> data) {
        data.map(this::toActor).forEach(actorService::insertActor);
    }

    private Actor toActor(ActorModel actorModel) {
        Actor actor = new Actor();
        actor.setId(actorModel.getId());
        actor.setName(actorModel.getName());
        actor.setNationality(actorModel.getNationality());
        actor.setBirthDate(actorModel.getBirthDate());
        return actor;
    }
}
