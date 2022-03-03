package infrastructure.http.services;

import infrastructure.json.services.JsonUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class HttpClientImpl implements HttpClient {

    private final JsonUtil jsonUtil;

    public HttpClientImpl(JsonUtil jsonUtil) {
        this.jsonUtil = jsonUtil;
    }

    @Override
    public <T> T get(String stringUrl, Class<T> tClass) throws IOException {
        URL url = new URL(stringUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Accept", "application/json");
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)
        )) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            connection.disconnect();
            return jsonUtil.fromJson(response.toString(), tClass);
        }
    }
}
