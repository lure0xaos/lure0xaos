package gargoyle.l0x.model.app.base;

import java.time.LocalDateTime;

public interface DatedModel {
    LocalDateTime getDate();

    void setDate(LocalDateTime date);
}
