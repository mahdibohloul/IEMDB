package infrastructure.http.services;

import java.io.IOException;

public interface HttpClient {
    <T> T get(String stringUrl, Class<T> tClass) throws IOException;
}
