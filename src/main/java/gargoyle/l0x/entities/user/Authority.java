package gargoyle.l0x.entities.user;

import gargoyle.l0x.entities.base.IdEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import static gargoyle.l0x.util.Rnd.generateUid;

@Entity
@Table(name = Authority.TABLE_AUTHORITIES)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "uid")
@ToString(of = {"authority", "user"})
public class Authority implements IdEntity<String>, GrantedAuthority {
    public static final String TABLE_AUTHORITIES = "AUTHORITIES";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_AUTHORITY = "AUTHORITY";
    public static final String COLUMN_USERNAME = "USERNAME";
    private static final long serialVersionUID = -5874286729916009503L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private String id;
    @Basic
    @Column(name = COLUMN_UID)
    @Builder.Default
    private String uid = generateUid();
    @Basic(optional = false)
    @Column(name = COLUMN_AUTHORITY)
    private String authority;
    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = COLUMN_USERNAME)
    private User user;

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
