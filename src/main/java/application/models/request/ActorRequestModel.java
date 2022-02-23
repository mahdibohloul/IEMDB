package application.models.request;

import domain.actor.models.Actor;
import infrastructure.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorRequestModel extends BaseModel {
    private String name;
    private String birthDate;
    private String nationality;

    public Actor toActor() throws ParseException {
        Actor actor = new Actor();
        actor.setId(this.id);
        actor.setName(this.name);
        actor.setNationality(this.nationality);
        Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(this.birthDate);
        actor.setBirthDate(birthDate);
        return actor;
    }
}
