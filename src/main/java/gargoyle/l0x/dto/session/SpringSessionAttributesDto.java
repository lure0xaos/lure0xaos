package gargoyle.l0x.dto.session;

import gargoyle.l0x.dto.base.Dto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"attributeName", "sessionPrimaryId"})
public class SpringSessionAttributesDto implements Dto<String> {
    public static final String TABLE_SPRING_SESSION_ATTRIBUTES = "SPRING_SESSION_ATTRIBUTES";
    public static final String COLUMN_ATTRIBUTE_NAME = "ATTRIBUTE_NAME";
    public static final String COLUMN_SESSION_PRIMARY_ID = "SESSION_PRIMARY_ID";
    public static final String COLUMN_ATTRIBUTE_BYTES = "ATTRIBUTE_BYTES";
    public static final byte[] BYTES_EMPTY = new byte[0];
    private String attributeName;
    private String sessionPrimaryId;
    @Builder.Default
    private byte[] attributeBytes = BYTES_EMPTY;
    private SpringSessionDto springSessionBySessionPrimaryId;

    @Override
    public Class<String> getIdClass() {
        return String.class;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getUid() {
        return null;
    }

    @Override
    public void setUid(String uid) {
    }
}
