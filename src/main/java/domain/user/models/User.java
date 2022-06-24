package domain.user.models;

import infrastructure.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    private String email;
    private String password;
    private String nickname;
    private String name;
    private Date birthDate;
}
