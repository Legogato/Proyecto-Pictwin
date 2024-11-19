// FileUtils.java
package cl.ucn.disc.dsm.pictwin.utils;

import cl.ucn.disc.dsm.pictwin.TheMain;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@UtilityClass
public class FileUtils {

    public byte[] readAllBytes(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file", e);
        }
    }

    public static File getResourceFile(@NonNull String name) {
        return new File(
                Objects.requireNonNull(TheMain.class.getClassLoader().getResource(name)).getFile());
    }
}
