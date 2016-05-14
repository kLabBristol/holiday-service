package klabbristol.holidayservice.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:application.properties")
public interface AppConfig extends Config {
    int port();
    String databaseUrl();
}
