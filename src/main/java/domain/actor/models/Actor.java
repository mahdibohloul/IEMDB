package domain.actor.models;

import infrastructure.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Actor extends BaseEntity {
    private String name;
    private String birthDate;
    private String nationality;
}
