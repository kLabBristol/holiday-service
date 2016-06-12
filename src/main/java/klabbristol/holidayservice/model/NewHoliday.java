package klabbristol.holidayservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class NewHoliday {

    public final String user;
    public final LocalDate from;
    public final LocalDate to;

    // region constructor
    public NewHoliday(
        @JsonProperty("user") String user,
        @JsonProperty("from") LocalDate from,
        @JsonProperty("to") LocalDate to
    ) {
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
