package gargoyle.l0x.model.session;

import gargoyle.l0x.model.base.Model;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "primaryId")
public class SpringSessionModel implements Model<String> {
    public static final String TABLE_SPRING_SESSION = "SPRING_SESSION";
    public static final String COLUMN_PRIMARY_ID = "PRIMARY_ID";
    public static final String COLUMN_SESSION_ID = "SESSION_ID";
    public static final String COLUMN_CREATION_TIME = "CREATION_TIME";
    public static final String COLUMN_LAST_ACCESS_TIME = "LAST_ACCESS_TIME";
    public static final String COLUMN_MAX_INACTIVE_INTERVAL = "MAX_INACTIVE_INTERVAL";
    public static final String COLUMN_EXPIRY_TIME = "EXPIRY_TIME";
    public static final String COLUMN_PRINCIPAL_NAME = "PRINCIPAL_NAME";
    private String primaryId;
    private String sessionId;
    private long creationTime;
    private long lastAccessTime;
    private int maxInactiveInterval;
    private long expiryTime;
    private String principalName;
    private List<SpringSessionAttributesModel> springSessionAttributes;

    @Override
    public Class<String> getIdClass() {
        return String.class;
    }

    @Override
    public String getId() {
        return primaryId;
    }

    @Override
    public String getUid() {
        return primaryId;
    }

    @Override
    public void setUid(String uid) {
        primaryId = uid;
    }
}
