package gargoyle.l0x.entities.user;

import gargoyle.l0x.entities.base.IdEntity;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = User.TABLE_USERS)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "username")
@ToString(of = "username")
public class User implements IdEntity<String>, UserDetails {
    public static final String TABLE_USERS = "USERS";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_EMAIL = "EMAIL";
    private static final long serialVersionUID = 843971577667670476L;
    @Id
    @Column(name = COLUMN_USERNAME)
    private String username;
    @Basic(optional = false)
    @Column(name = COLUMN_PASSWORD)
    private String password;
    @Basic(optional = false)
    @Column(name = COLUMN_EMAIL)
    private String email;
    @OneToMany(targetEntity = Authority.class, fetch = FetchType.EAGER, mappedBy = Authority_.USER)
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();
    @Basic(optional = false)
    @Builder.Default
    private boolean enabled = true;
    @Basic(optional = false)
    @Transient
    @Builder.Default
    private boolean accountNonExpired = true;
    @Basic(optional = false)
    @Transient
    @Builder.Default
    private boolean credentialsNonExpired = true;
    @Basic(optional = false)
    @Transient
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
