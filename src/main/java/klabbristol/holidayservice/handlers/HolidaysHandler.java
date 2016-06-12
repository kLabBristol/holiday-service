package klabbristol.holidayservice.handlers;


import klabbristol.holidayservice.dao.HolidayRepo;
import klabbristol.holidayservice.model.Holiday;
import klabbristol.holidayservice.model.NewHoliday;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import java.util.List;
import java.util.Optional;

import static ratpack.jackson.Jackson.fromJson;
import static ratpack.jackson.Jackson.json;

public class HolidaysHandler implements Handler {
    @Override
    public void handle(Context ctx) throws Exception {
        HolidayRepo repo = ctx.get(HolidayRepo.class);

        ctx.byMethod(m -> m
            .post(() -> {
                ctx.parse(fromJson(NewHoliday.class))
                    .then(holiday -> {
                        repo.add(holiday);
                        ctx.getResponse().status(201).send();
                    });
            })
            .get(() -> {
                List<Holiday> holidays =
                    Optional.ofNullable(ctx.getRequest().getQueryParams().get("user"))
                        .map(repo::findByUser)
                        .orElse(repo.findAll());

                ctx.render(json(holidays));
            })
        );
    }
}
