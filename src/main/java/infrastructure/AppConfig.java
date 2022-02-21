package infrastructure;

import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    static String CommandLineEnv = "commandline";

    String activeProfile = CommandLineEnv;
}
