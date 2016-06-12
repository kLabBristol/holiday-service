package klabbristol.holidayservice;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import klabbristol.holidayservice.handlers.HolidaysHandler;
import klabbristol.holidayservice.handlers.JwtHandler;
import ratpack.guice.Guice;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

public class Main {
    public static void main(String[] args) throws Exception {

        Config config = ConfigFactory.load();

        RatpackServer.start(server -> server
            .serverConfig(ServerConfig.embedded().port(config.getInt("port")))
            .registry(Guice.registry(b -> b.module(Module.class)))
            .handlers(chain -> chain
                .all(new JwtHandler())
                .path("holidays", new HolidaysHandler())
            )
        );
    }
}
