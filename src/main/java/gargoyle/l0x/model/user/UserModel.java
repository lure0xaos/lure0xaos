package gargoyle.l0x.model.user;

import gargoyle.l0x.model.base.Model;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "username")
@ToString(of = "username")
public class UserModel implements Model<String> {
    public static final String TABLE_USERS = "USERS";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_EMAIL = "EMAIL";
    private String username;
    private String password;
    private String email;
    @Builder.Default
    private Set<AuthorityModel> authorities = new HashSet<>();
    @Builder.Default
    private boolean enabled = true;
    @Builder.Default
    private boolean accountNonExpired = true;
    @Builder.Default
    private boolean credentialsNonExpired = true;
    @Builder.Default
    private boolean accountNonLocked = true;

    @Override
    public Class<String> getIdClass() {
        return String.class;
    }

    @Override
    public String getId() {
        return username;
    }

    @Override
    public String getUid() {
        return username;
    }

    @Override
    public void setUid(String uid) {
        username = uid;
    }
}
