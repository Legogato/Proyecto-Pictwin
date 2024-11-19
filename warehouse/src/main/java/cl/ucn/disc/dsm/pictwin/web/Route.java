// Route.java
package cl.ucn.disc.dsm.pictwin.web;

import io.javalin.http.Handler;
import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class Route {

    protected Method method;
    protected String path;
    protected Handler handler;

    protected Route(@NonNull final Method method, @NonNull final String path) {
        this.method = method;
        this.path = path;
    }

    public enum Method {
        GET,
        POST,
        PUT,
    }
}
