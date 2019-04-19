package gargoyle.l0x.dto.app.base;

import java.time.LocalDateTime;

public interface DatedDto {
    LocalDateTime getDate();

    void setDate(LocalDateTime date);
}
