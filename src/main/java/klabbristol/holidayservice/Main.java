package klabbristol.holidayservice;

import java.util.Optional;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {
    public static void main(String[] args) {
        port(Optional.ofNullable(System.getenv("PORT")).map(Integer::parseInt).orElse(4567));

        get("/", (req, res) -> "hello");
    }
}
