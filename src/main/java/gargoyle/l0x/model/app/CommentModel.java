package gargoyle.l0x.model.app;

import gargoyle.l0x.config.WebMvcConfig;
import gargoyle.l0x.model.app.base.DatedModel;
import gargoyle.l0x.model.app.base.OwnerModel;
import gargoyle.l0x.model.app.base.SourceModel;
import gargoyle.l0x.model.base.Model;
import gargoyle.l0x.model.user.UserModel;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static gargoyle.l0x.util.Rnd.generateUid;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "uid")
@ToString(of = {"date", "author", "body"})
public class CommentModel implements Model<Long>, SourceModel, OwnerModel, DatedModel {
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
    private CreationModel creation;
    private Long id;
    private CommentModel parent;
    @Builder.Default
    private String uid = generateUid();
    @Builder.Default
    private String head = "";
    @Builder.Default
    private String body = "";
    @Builder.Default
    private String source = "";
    @Builder.Default
    @DateTimeFormat(pattern = WebMvcConfig.DATE_TIME_FORMAT)
    private LocalDateTime date = LocalDateTime.now();
    private UserModel author;
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
