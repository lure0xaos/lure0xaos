package gargoyle.l0x.dto.app;

import gargoyle.l0x.dto.app.base.AliasedDto;
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
@EqualsAndHashCode(of = "alias")
@ToString(of = {"alias", "date", "author", "genre", "head"})
public class CreationDto implements Dto<String>, SourceDto, OwnerDto, DatedDto, AliasedDto {
    public static final String TABLE_CREATIONS = "CREATIONS";
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_HEAD = "HEAD";
    public static final String COLUMN_BODY = "BODY";
    public static final String COLUMN_SOURCE = "SOURCE";
    public static final String COLUMN_ALIAS = "ALIAS";
    public static final String COLUMN_AUTHOR = "AUTHOR";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_GENRE = "GENRE";
    private String head;
    @Builder.Default
    private String body = "";
    @Builder.Default
    private String source = "";
    private String alias;
    private UserDto author;
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();
    private String genre;
    @Builder.Default
    private String uid = generateUid();

    @Override
    public Class<String> getIdClass() {
        return String.class;
    }

    @Override
    public String getId() {
        return alias;
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
