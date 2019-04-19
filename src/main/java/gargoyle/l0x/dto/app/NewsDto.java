package gargoyle.l0x.dto.app;

import gargoyle.l0x.dto.app.base.DatedDto;
import gargoyle.l0x.dto.app.base.OwnerDto;
import gargoyle.l0x.dto.app.base.SourceDto;
import gargoyle.l0x.dto.base.Dto;
import gargoyle.l0x.dto.user.UserDto;
import lombok.*;

import java.time.LocalDateTime;

import static gargoyle.l0x.util.Rnd.generateUid;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "uid")
@ToString(of = {"date", "author", "head", "body"})
public class NewsDto implements Dto<LocalDateTime>, SourceDto, OwnerDto, DatedDto {
    public static final String TABLE_NEWS = "NEWS";
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_HEAD = "HEAD";
    public static final String COLUMN_BODY = "BODY";
    public static final String COLUMN_SOURCE = "SOURCE";
    public static final String COLUMN_AUTHOR = "AUTHOR";
    public static final String COLUMN_DATE = "DATE";
    @Builder.Default
    private String uid = generateUid();
    @Builder.Default
    private String head = "";
    @Builder.Default
    private String body = "";
    @Builder.Default
    private String source = "";
    private UserDto author;
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();

    @Override
    public Class<LocalDateTime> getIdClass() {
        return LocalDateTime.class;
    }

    @Override
    public LocalDateTime getId() {
        return date;
    }

    @Override
    public String getUid() {
        return uid;
    }

    @Override
    public void setUid(String uid) {
        this.uid = uid;
    }
}
