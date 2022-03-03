package infrastructure.dataprovider.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class ActorModel {
    private Integer id;
    private String name;
    private Date birthDate;
    private String nationality;

    @SneakyThrows
    @JsonCreator
    public ActorModel(
            @JsonProperty(value = "id", required = true) Integer id,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "birthDate", required = true) String birthDate,
            @JsonProperty(value = "nationality", required = true) String nationality
    ) {
        this.id = id;
        this.name = name;
        this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
        this.nationality = nationality;
    }
}
