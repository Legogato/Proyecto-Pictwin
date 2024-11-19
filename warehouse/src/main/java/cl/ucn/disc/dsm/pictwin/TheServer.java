// TheServer.java
package cl.ucn.disc.dsm.pictwin;

import cl.ucn.disc.dsm.pictwin.services.Controller;
import cl.ucn.disc.dsm.pictwin.web.Route;
import cl.ucn.disc.dsm.pictwin.web.routes.Home;
import cl.ucn.disc.dsm.pictwin.web.routes.PersonaPicTwins;
import io.ebean.DB;
import io.javalin.Javalin;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class TheServer {

    private static Javalin createJavalin() {
        return Javalin.create(config -> {
            config.requestLogger.http((ctx, ms) -> log.debug("Served {} in {} ms.", ctx.fullUrl(), ms));
            config.http.gzipOnlyCompression(9);
            config.jetty.modifyServer(server -> server.setStopTimeout(5_000));
        });
    }

    private static void addRoute(final @NonNull Route route, final @NonNull Javalin javalin) {
        log.debug(
                "Adding route {} with verb {} in path: {}",
                route.getClass().getSimpleName(),
                route.getMethod(),
                route.getPath()
        );

        switch (route.getMethod()) {
            case GET:
                javalin.get(route.getPath(), route.getHandler());
                break;
            case POST:
                javalin.post(route.getPath(), route.getHandler());
                break;
            case PUT:
                javalin.put(route.getPath(), route.getHandler());
                break;
            default:
                throw new IllegalArgumentException("Method not supported: " + route.getMethod());
        }
    }

    public static void main(String[] args) {

        log.debug("Configuring Controller ..");
        Controller controller = new Controller(DB.getDefault());
        if (controller.seed()) {
            log.debug("Database seeded.");
        }

        log.debug("Configure TheServer ..");

        Javalin javalin = createJavalin();

        log.debug("Adding routes ..");

        addRoute(new Home(), javalin);
        addRoute(new PersonaPicTwins(controller), javalin);

        CountDownLatch latch = new CountDownLatch(1);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            javalin.stop();
            DB.getDefault().shutdown();
            latch.countDown();
        }));

        log.debug("Starting the server ..");
        javalin.start(7000);

        try {
            latch.await();
        } catch (InterruptedException e) {
            log.debug("Server shutdown interrupted.", e);
            Thread.currentThread().interrupt();
        }

        log.debug("Done.");
    }
}
