// Home.java
package cl.ucn.disc.dsm.pictwin.web.routes;

import cl.ucn.disc.dsm.pictwin.web.Route;

public final class Home extends Route {

    public Home() {
        super(Method.GET, "/");

        this.handler = ctx -> {
            ctx.result("Welcome to PicTwin");
        };
    }
}
