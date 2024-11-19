// PicTwin.java
package cl.ucn.disc.dsm.pictwin.model;

import io.ebean.annotation.NotNull;
import jakarta.persistence.Entity;
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
public class PicTwin extends BaseModel {

    @NotNull private Instant expiration;

    @Builder.Default @NotNull private Boolean expired = Boolean.FALSE;

    @Builder.Default @NotNull private Boolean reported = Boolean.FALSE;

    @ManyToOne(optional = false)
    private Persona persona;

    @ToString.Exclude
    @ManyToOne(optional = false)
    private Pic pic;

    @ToString.Exclude
    @ManyToOne(optional = false)
    private Pic twin;
}

