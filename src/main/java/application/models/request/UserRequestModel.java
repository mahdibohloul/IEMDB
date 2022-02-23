package application.models.request;

import domain.user.models.User;
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
public class UserRequestModel extends BaseModel {
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String birthDate;

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
