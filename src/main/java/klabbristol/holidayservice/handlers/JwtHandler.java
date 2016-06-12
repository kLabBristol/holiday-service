package klabbristol.holidayservice.handlers;


import io.jsonwebtoken.JwtParser;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class JwtHandler implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        try {
            ctx.get(JwtParser.class).parse(ctx.getRequest().getHeaders().get("Authorization"));
            ctx.next();
        } catch (Exception e) {
            ctx.getResponse().status(403).send();
        }
    }
}
