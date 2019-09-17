package gargoyle.l0x.entities.app.base;

import java.time.LocalDateTime;

public interface DatedEntity {
    LocalDateTime getDate();

    void setDate(LocalDateTime date);
}
