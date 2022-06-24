package application.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetActorByIdRequestModel {
    private Integer actorId;
}
