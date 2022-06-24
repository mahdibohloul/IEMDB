package infrastructure.dataprovider.services;

import domain.user.exceptions.DuplicateUserEmailException;
import domain.user.models.User;
import domain.user.services.UserService;
import infrastructure.AppConfig;
import infrastructure.dataprovider.models.UserModel;
import infrastructure.http.services.HttpClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Stream;

@Component
@Order(value = 1)
public class UserDataProvider implements DataProvider<UserModel> {
    private final HttpClient httpClient;
    private final AppConfig appConfig;
    private final UserService userService;
    private static final String USERS_URL = "users";

    public UserDataProvider(HttpClient httpClient, AppConfig appConfig, UserService userService) {
        this.httpClient = httpClient;
        this.appConfig = appConfig;
        this.userService = userService;
    }


    @Override
    public Stream<UserModel> provide() throws IOException {
        String requestUrl = appConfig.getDataProviderUrl() + USERS_URL;
        UserModel[] userModels = httpClient.get(requestUrl, UserModel[].class);
        return Stream.of(userModels);
    }

    @Override
    public void populateData(Stream<UserModel> data) {
        data.map(this::toUser).forEach(user -> {
            try {
                userService.insertUser(user);
            } catch (DuplicateUserEmailException e) {
                e.printStackTrace();
            }
        });
    }

    private User toUser(UserModel userModel) {
        return new User(userModel.getEmail(), userModel.getPassword(), userModel.getNickname(), userModel.getName(),
                userModel.getBirthDate());
    }
}
