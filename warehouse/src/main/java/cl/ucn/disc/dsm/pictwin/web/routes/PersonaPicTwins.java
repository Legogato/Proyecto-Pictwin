// PersonaPicTwins.java
package cl.ucn.disc.dsm.pictwin.web.routes;

import cl.ucn.disc.dsm.pictwin.services.Controller;
import cl.ucn.disc.dsm.pictwin.web.Route;
import io.javalin.http.Handler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PersonaPicTwins extends Route {

    public PersonaPicTwins(@NonNull final Controller controller) {
        super(Method.GET, "/api/personas/{ulid}/pictwins");

        this.handler = buildHandler(controller);
    }

    private static Handler buildHandler(Controller controller) {
        return ctx -> {
            String ulid = ctx.pathParam("ulid");
            log.debug("Detected ulid={} for Persona.", ulid);

            ctx.json(controller.getPicTwins(ulid));
        };
    }
}
