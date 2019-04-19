package gargoyle.l0x.dto.app;

import gargoyle.l0x.dto.app.base.BiSourceDto;
import gargoyle.l0x.dto.base.Dto;
import lombok.*;

import static gargoyle.l0x.util.Rnd.generateUid;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "uid")
@ToString(of = {"primaryBody", "secondaryBody"})
public class QuoteDto implements Dto<String>, BiSourceDto {
    public static final String TABLE_QUOTES = "QUOTES";
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_HEAD = "HEAD";
    public static final String COLUMN_PRIMARY_BODY = "PRIMARY_BODY";
    public static final String COLUMN_PRIMARY_SOURCE = "PRIMARY_SOURCE";
    public static final String COLUMN_SECONDARY_BODY = "SECONDARY_BODY";
    public static final String COLUMN_SECONDARY_SOURCE = "SECONDARY_SOURCE";
    public static final String COLUMN_AUTHOR = "AUTHOR";
    public static final String COLUMN_ID = "ID";
    @Builder.Default
    private String head = "";
    @Builder.Default
    private String uid = generateUid();
    @Builder.Default
    private String primaryBody = "";
    @Builder.Default
    private String primarySource = "";
    @Builder.Default
    private String secondaryBody = "";
    @Builder.Default
    private String secondarySource = "";
    @Builder.Default
    private String author = "";
    private String id;

    @Override
    public Class<String> getIdClass() {
        return String.class;
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
