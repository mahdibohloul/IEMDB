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
public class UserModel {
    private String email;
    private String name;
    private String password;
    private String nickname;
    private Date birthDate;

    @SneakyThrows
    @JsonCreator
    public UserModel(
            @JsonProperty(value = "email", required = true) String email,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "nickname", required = true) String nickname,
            @JsonProperty(value = "birthDate", required = true) String birthDate,
            @JsonProperty(value = "password", required = true) String password
    ) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
        this.password = password;
    }
}
