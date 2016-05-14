package klabbristol.holidayservice.dao;

import klabbristol.holidayservice.model.NewHoliday;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RegisterMapper(HolidayRepo.HolidayMapper.class)
public interface HolidayRepo {

    @SqlUpdate(
            "INSERT INTO holidays (\"user\", \"from\" ,\"to\") " +
            "VALUES (:user, :from, :to)"
    )
    void add(@BindBean NewHoliday h);

    @SqlQuery("SELECT * FROM holidays")
    List<NewHoliday> findAll();

    @SqlQuery(
            "SELECT * FROM holidays " +
            "WHERE user = :user"
    )
    List<NewHoliday> findByUser(@Bind String user);

    // region mapper
    static class HolidayMapper implements ResultSetMapper<NewHoliday> {

        @Override
        public NewHoliday map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new NewHoliday(
                    r.getString("user"),
                    r.getDate("from").toLocalDate(),
                    r.getDate("to").toLocalDate()
            );
        }
    }
    // endregion
}
