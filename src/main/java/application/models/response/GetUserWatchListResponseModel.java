package application.models.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetUserWatchListResponseModel {
    private List<MovieResponseModel> watchList;
}
