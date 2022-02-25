package application.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class AddActorRequestModel extends BaseModel {
    private String name;
    private String birthDate;
    private String nationality;

    @JsonCreator
    public AddActorRequestModel(
            @JsonProperty(required = true, value = "name") String name,
            @JsonProperty(required = true, value = "birthDate") String birthDate,
            @JsonProperty(required = true, value = "nationality") String nationality,
            @JsonProperty(required = true, value = "id") Integer id
    ) {
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.id = id;
    }

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
