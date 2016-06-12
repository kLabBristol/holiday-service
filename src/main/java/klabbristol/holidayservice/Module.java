package klabbristol.holidayservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import klabbristol.holidayservice.dao.HolidayRepo;
import org.skife.jdbi.v2.DBI;


public class Module extends AbstractModule {
    @Override
    protected void configure() {
        Config config = ConfigFactory.load();

        bind(ObjectMapper.class)
            .toInstance(
                new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            );

        bind(HolidayRepo.class)
            .toInstance(
                new DBI(config.getString("databaseUrl")).onDemand(HolidayRepo.class)
            );

        bind(JwtParser.class)
            .toInstance(
                Jwts.parser().setSigningKey(config.getString("secret").getBytes())
            );
    }
}
