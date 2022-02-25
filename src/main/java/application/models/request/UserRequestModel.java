package application.models.request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import domain.user.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestModel {
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String birthDate;

    @JsonCreator
    public UserRequestModel(@JsonProperty(required = true, value = "email") String email,
            @JsonProperty(required = true, value = "password") String password,
            @JsonProperty(required = true, value = "nickname") String nickname,
            @JsonProperty(required = true, value = "name") String name,
            @JsonProperty(required = true, value = "birthDate") String birthDate) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.birthDate = birthDate;
    }

    public User toUser() throws ParseException {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setNickname(this.nickname);
        user.setName(this.name);
        Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(this.birthDate);
        user.setBirthDate(birthDate);
        return user;
    }
}
