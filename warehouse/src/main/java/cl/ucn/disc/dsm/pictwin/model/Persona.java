// Persona.java
package cl.ucn.disc.dsm.pictwin.model;

import io.ebean.annotation.Index;
import io.ebean.annotation.NotNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.time.Instant;
import java.util.List;

@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Persona extends BaseModel {

    @NotNull
    @Index(unique = true)
    private String email;

    @NotNull
    @Column(length = 72)
    private String password;

    @Builder.Default @NotNull private Integer strikes = 0;

    @Builder.Default @NotNull private Boolean blocked = Boolean.FALSE;

    private Instant blockedAt;

    @ToString.Exclude
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<Pic> pics;

    @ToString.Exclude
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<PicTwin> picTwins;
}


