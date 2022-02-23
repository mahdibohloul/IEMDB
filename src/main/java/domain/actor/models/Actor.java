package domain.actor.models;

import java.util.Date;

import infrastructure.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Actor extends BaseEntity {
    private String name;
    private Date birthDate;
    private String nationality;
}
