package gargoyle.l0x.model.app;

import gargoyle.l0x.model.base.Model;
import gargoyle.l0x.model.user.UserModel;
import lombok.*;

import java.time.LocalDateTime;

import static gargoyle.l0x.util.Rnd.generateUid;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "creation")
@ToString(of = "creation")
public class VoteModel implements Model<String> {
    public static final String TABLE_VOTES = "VOTES";
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_CREATION_ALIAS = "CREATION_ALIAS";
    public static final String COLUMN_USER_USERNAME = "USER_USERNAME";
    public static final String COLUMN_RATE = "RATE";
    public static final String COLUMN_DATE = "DATE";
    @Builder.Default
    private String uid = generateUid();
    private CreationModel creation;
    private UserModel user;
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();
    private int rate;

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

    public String getId() {
        return uid;
    }

    public void setId(String id) {
        uid = id;
    }
}
