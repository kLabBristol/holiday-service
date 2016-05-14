package klabbristol.holidayservice;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import klabbristol.holidayservice.dao.HolidayRepo;
import klabbristol.holidayservice.model.NewHoliday;
import klabbristol.holidayservice.utils.LocalDateAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

public class Main {
    public static void main(String[] args) {
        port(Optional.ofNullable(System.getenv("PORT")).map(Integer::parseInt).orElse(4567));

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        HolidayRepo repo = new HolidayRepo();

        get("/", (req, res) -> "hello");

        post("/holidays", (req, res) -> {
            repo.add(gson.fromJson(req.body(), NewHoliday.class));

            res.status(201);
            return "";
        });

        get("/holidays", (req, res) -> {
            List<NewHoliday> holidays =
                    Optional.ofNullable(req.queryParams("user"))
                            .map(repo::findByUser)
                            .orElse(repo.findAll());

            return gson.toJson(holidays);
        });

        after((req, res) -> res.type("application/json"));
    }
}
