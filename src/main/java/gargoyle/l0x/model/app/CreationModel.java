package gargoyle.l0x.model.app;

import gargoyle.l0x.config.WebMvcConfig;
import gargoyle.l0x.model.app.base.AliasedModel;
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
@EqualsAndHashCode(of = "alias")
@ToString(of = {"alias", "date", "author", "genre", "head"})
public class CreationModel implements Model<String>, SourceModel, OwnerModel, DatedModel, AliasedModel {
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
    private UserModel author;
    @Builder.Default
    @DateTimeFormat(pattern = WebMvcConfig.DATE_TIME_FORMAT)
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
