package domain.user.models;

import infrastructure.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User extends BaseEntity {
    private String email;
    private String password;
    private String nickname;
    private String name;
    private Date birthDate;
}
