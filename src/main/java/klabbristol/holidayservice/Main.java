package klabbristol.holidayservice;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import klabbristol.holidayservice.dao.HolidayRepo;
import klabbristol.holidayservice.model.Holiday;
import klabbristol.holidayservice.model.NewHoliday;
import klabbristol.holidayservice.utils.LocalDateAdapter;
import org.skife.jdbi.v2.DBI;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        Config config = ConfigFactory.load();

        port(config.getInt("port"));

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        DBI dbi = new DBI(config.getString("databaseUrl"));
        HolidayRepo repo = dbi.onDemand(HolidayRepo.class);

        get("/", (req, res) -> "hello");

        post("/holidays", (req, res) -> {
            repo.add(gson.fromJson(req.body(), NewHoliday.class));

            res.status(201);
            return "";
        });

        get("/holidays", (req, res) -> {
            List<Holiday> holidays =
                    Optional.ofNullable(req.queryParams("user"))
                            .map(repo::findByUser)
                            .orElse(repo.findAll());

            return gson.toJson(holidays);
        });

        after((req, res) -> res.type("application/json"));
    }
}
