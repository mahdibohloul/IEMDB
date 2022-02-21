package domain.user.models;

import infrastructure.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWatchList extends BaseEntity {
    private String userEmail;
    private Integer movieId;
}
