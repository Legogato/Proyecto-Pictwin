// Pic.java
package cl.ucn.disc.dsm.pictwin.model;

import io.ebean.annotation.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.time.Instant;

@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Pic extends BaseModel {

    @NotNull private Double latitude;

    @NotNull private Double longitude;

    @Builder.Default @NotNull private Integer reports = 0;

    @NotNull private Instant date;

    @NotNull @Lob private byte[] photo;

    @Builder.Default @NotNull private Boolean bloqued = Boolean.FALSE;

    @Builder.Default @NotNull private Integer views = 0;

    @ManyToOne(optional = false)
    private Persona persona;
}

