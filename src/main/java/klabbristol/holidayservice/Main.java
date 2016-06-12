package klabbristol.holidayservice;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import klabbristol.holidayservice.dao.HolidayRepo;
import klabbristol.holidayservice.model.Holiday;
import klabbristol.holidayservice.model.NewHoliday;
import org.skife.jdbi.v2.DBI;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

import java.util.List;
import java.util.Optional;

import static ratpack.jackson.Jackson.fromJson;
import static ratpack.jackson.Jackson.json;

public class Main {
    public static void main(String[] args) throws Exception {

        Config config = ConfigFactory.load();

        DBI dbi = new DBI(config.getString("databaseUrl"));
        HolidayRepo repo = dbi.onDemand(HolidayRepo.class);

        JwtParser jwtParser = Jwts.parser().setSigningKey(config.getString("secret").getBytes());

        RatpackServer.start(server -> server
            .serverConfig(ServerConfig.embedded().port(config.getInt("port")))
            .registryOf(r -> r.add(
                new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            ))
            .handlers(chain -> chain
                .all(ctx -> {
                    try {
                        jwtParser.parse(ctx.getRequest().getHeaders().get("Authorization"));
                        ctx.next();
                    } catch (Exception e) {
                        ctx.getResponse().status(403).send();
                    }
                })
                .path("holidays", ctx -> ctx
                    .byMethod(m -> m
                        .post(() -> {
                            ctx.parse(fromJson(NewHoliday.class))
                                .then(holiday -> {
                                    repo.add(holiday);
                                    ctx.getResponse().status(201).send();
                                });
                        })
                        .get(() -> {
                            List<Holiday> holidays =
                                Optional.ofNullable(ctx.getRequest().getQueryParams().get("user"))
                                    .map(repo::findByUser)
                                    .orElse(repo.findAll());

                            ctx.render(json(holidays));
                        })
                    )
                )
            )
        );
    }
}
