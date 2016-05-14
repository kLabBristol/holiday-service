package klabbristol.holidayservice.model;

import java.time.LocalDate;

public class Holiday {

    public final int id;
    public final String user;
    public final LocalDate from;
    public final LocalDate to;

    // region constructor
    public Holiday(int id, String user, LocalDate from, LocalDate to) {
        this.id = id;
        this.user = user;
        this.from = from;
        this.to = to;
    }
    // endregion
}
