package klabbristol.holidayservice.model;

import java.time.LocalDate;

public class NewHoliday {

    public final String user;
    public final LocalDate from;
    public final LocalDate to;

    // region constructor
    public NewHoliday(String user, LocalDate from, LocalDate to) {
        this.user = user;
        this.from = from;
        this.to = to;
    }
    // endregion

    // region getters for JDBI
    public String getUser() {
        return user;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }
    // endregion
}
