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
@ToString(of = {"date", "author", "body"})
public class CommentDto implements Dto<Long>, SourceDto, OwnerDto, DatedDto {
    public static final String TABLE_COMMENTS = "COMMENTS";
    public static final String COLUMN_CREATION_ALIAS = "CREATION_ALIAS";
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PID = "PID";
    public static final String COLUMN_HEAD = "HEAD";
    public static final String COLUMN_BODY = "BODY";
    public static final String COLUMN_SOURCE = "SOURCE";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_AUTHOR = "AUTHOR";
    public static final String COLUMN_FLAGS = "FLAGS";
    private CreationDto creation;
    private Long id;
    private CommentDto parent;
    @Builder.Default
    private String uid = generateUid();
    @Builder.Default
    private String head = "";
    @Builder.Default
    private String body = "";
    @Builder.Default
    private String source = "";
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();
    private UserDto author;
    @Builder.Default
    private char[] flags = new char[8];

    @Override
    public Class<Long> getIdClass() {
        return Long.class;
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
