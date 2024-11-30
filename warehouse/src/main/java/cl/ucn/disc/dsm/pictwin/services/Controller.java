// Controller.java
package cl.ucn.disc.dsm.pictwin.services;

import cl.ucn.disc.dsm.pictwin.model.Persona;
import cl.ucn.disc.dsm.pictwin.model.Pic;
import cl.ucn.disc.dsm.pictwin.model.PicTwin;
import cl.ucn.disc.dsm.pictwin.model.query.QPersona;
import cl.ucn.disc.dsm.pictwin.model.query.QPicTwin;
import cl.ucn.disc.dsm.pictwin.utils.FileUtils;
import com.password4j.Password;
import io.ebean.Database;
import io.ebean.annotation.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.time.Instant;
import java.util.List;

@Slf4j
public class Controller {

    private final Database database;

    public Controller(@NonNull final Database database) {
        this.database = database;
    }

    public Boolean seed() {

        int personaSize = new QPersona().findCount();
        log.debug("Personas in database: {}", personaSize);

        if (personaSize != 0) {
            return Boolean.FALSE;
        }

        log.debug("Can't find data, seeding the database ..");

        Persona persona = this.register("durrutia@ucn.cl", "durrutia123");
        log.debug("Persona registered: {}", persona);

        log.debug("Database seeded.");

        return Boolean.TRUE;
    }

    @Transactional
    public Persona register(@NonNull final String email, @NonNull final String password) {

        String hashedPassword = Password.hash(password).withBcrypt().getResult();

        Persona persona = Persona.builder()
                .email(email)
                .password(hashedPassword)
                .strikes(0)
                .blocked(Boolean.FALSE)
                .build();

        this.database.save(persona);
        log.debug("Persona saved: {}", persona);

        return persona;
    }

    public Persona login(@NonNull final String email, @NonNull final String password) {

        Persona persona = new QPersona().email.equalTo(email).findOne();
        if (persona == null) {
            throw new RuntimeException("User not found");
        }

        if (!Password.check(password, persona.getPassword()).withBcrypt()) {
            throw new RuntimeException("Wrong password");
        }

        return persona;
    }

    @Transactional
    public PicTwin addPic(
            @NonNull String ulidPersona,
            @NonNull Double latitude,
            @NonNull Double longitude,
            @NonNull File picture) {

        byte[] data = FileUtils.readAllBytes(picture);

        Persona persona = new QPersona().ulid.equalTo(ulidPersona).findOne();
        log.debug("Persona found: {}", persona);

        Pic pic = Pic.builder()
                .latitude(latitude)
                .longitude(longitude)
                .reports(0)
                .date(Instant.now())
                .photo(data)
                .bloqued(false)
                .views(0)
                .persona(persona)
                .build();
        log.debug("Pic to save: {}", pic);
        this.database.save(pic);

        PicTwin picTwin = PicTwin.builder()
                .expiration(Instant.now().plusSeconds(7 * 24 * 60 * 60))
                .expired(false)
                .reported(false)
                .persona(persona)
                .pic(pic)
                .twin(pic) // FIXME: retrieve a new pic from the database
                .build();
        log.debug("PicTwin to save: {}", picTwin);
        this.database.save(picTwin);

        return picTwin;
    }

    public List<PicTwin> getPicTwins(@NonNull String ulidPersona) {
        return new QPicTwin().persona.ulid.equalTo(ulidPersona).findList();
    }
}

