package application.models.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserWatchListResponseModel {
    private String userEmail;
    private String userNickname;
    private String userName;
    private List<MovieResponseModel> watchList;
}
