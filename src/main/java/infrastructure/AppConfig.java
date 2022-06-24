package infrastructure;

import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    static String CommandLineEnv = "commandline";
    static Boolean PopulateDatabase = true;

    String dataProviderUrl = "http://138.197.181.131:5000/api/";
    String activeProfile = CommandLineEnv;
    Boolean populateDatabase = PopulateDatabase;

    public String getDataProviderUrl() {
        return dataProviderUrl;
    }

    public Boolean getPopulateDatabase() {
        return populateDatabase;
    }
}
