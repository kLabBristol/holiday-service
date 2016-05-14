package klabbristol.holidayservice.dao;

import klabbristol.holidayservice.model.NewHoliday;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class HolidayRepo {

    private List<NewHoliday> db = new ArrayList<>();

    public void add(NewHoliday h) {
        db.add(h);
    }

    public List<NewHoliday> findAll() {
        return db;
    }

    public List<NewHoliday> findByUser(String user) {
        return db.stream().filter(h -> h.user.equals(user)).collect(toList());
    }
}
