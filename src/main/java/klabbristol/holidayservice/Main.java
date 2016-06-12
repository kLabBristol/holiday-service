package klabbristol.holidayservice;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.jsonwebtoken.Jwts;
import klabbristol.holidayservice.dao.HolidayRepo;
import klabbristol.holidayservice.handlers.HolidaysHandler;
import klabbristol.holidayservice.handlers.JwtHandler;
import org.skife.jdbi.v2.DBI;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

public class Main {
    public static void main(String[] args) throws Exception {

        Config config = ConfigFactory.load();

        RatpackServer.start(server -> server
            .serverConfig(ServerConfig.embedded().port(config.getInt("port")))
            .registryOf(r -> r
                .add(new ObjectMapper()
                         .registerModule(new JavaTimeModule())
                         .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                )
                .add(new DBI(config.getString("databaseUrl")).onDemand(HolidayRepo.class))
                .add(Jwts.parser().setSigningKey(config.getString("secret").getBytes()))
            )
            .handlers(chain -> chain
                .all(new JwtHandler())
                .path("holidays", new HolidaysHandler())
            )
        );
    }
}
