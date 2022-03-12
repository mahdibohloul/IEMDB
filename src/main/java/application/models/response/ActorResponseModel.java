package application.models.response;

import domain.actor.models.Actor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ActorResponseModel {
    private Integer actorId;
    private String name;
    private Integer age;
    private String birthDate;
    private String nationality;

    public ActorResponseModel(Actor actor) {
        this.actorId = actor.getId();
        this.name = actor.getName();
        this.age = getYear(actor.getBirthDate());
        this.birthDate = new SimpleDateFormat("yyyy-MM-dd").format(actor.getBirthDate());
        this.nationality = actor.getNationality();
    }

    private Integer getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
}
