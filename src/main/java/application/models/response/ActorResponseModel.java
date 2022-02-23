package application.models.response;

import domain.actor.models.Actor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActorResponseModel {
    private Integer actorId;
    private String name;

    public ActorResponseModel(Actor actor) {
        this.actorId = actor.getId();
        this.name = actor.getName();
    }
}
