package gargoyle.l0x.dto.user;

import gargoyle.l0x.dto.base.Dto;
import lombok.*;

import static gargoyle.l0x.util.Rnd.generateUid;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "uid")
@ToString(of = {"authority", "user"})
public class AuthorityDto implements Dto<String> {
    public static final String TABLE_AUTHORITIES = "AUTHORITIES";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_AUTHORITY = "AUTHORITY";
    public static final String COLUMN_USERNAME = "USERNAME";
    private String id;
    @Builder.Default
    private String uid = generateUid();
    private String authority;
    private UserDto user;

    @Override
    public Class<String> getIdClass() {
        return String.class;
    }

    @Override
    public String getUid() {
        return null;
    }

    @Override
    public void setUid(String uid) {
        this.uid = uid;
    }
}
