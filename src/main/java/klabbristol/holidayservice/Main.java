package klabbristol.holidayservice;

import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {
        get("/", (req, res) -> "hello");
    }
}
