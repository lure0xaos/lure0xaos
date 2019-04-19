package gargoyle.l0x.entities.app;

import gargoyle.l0x.entities.base.IdEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static gargoyle.l0x.util.Rnd.generateUid;

@Entity
@Table(name = Vote.TABLE_VOTES)
@IdClass(VoteId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"creationAlias", "userUsername"})
@ToString(of = {"creationAlias", "userUsername"})
public class Vote implements IdEntity<String> {
    public static final String TABLE_VOTES = "VOTES";
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_CREATION_ALIAS = "CREATION_ALIAS";
    public static final String COLUMN_USER_USERNAME = "USER_USERNAME";
    public static final String COLUMN_RATE = "RATE";
    public static final String COLUMN_DATE = "DATE";
    @Id
    @Column(name = COLUMN_CREATION_ALIAS)
    private String creationAlias;
    @Id
    @Column(name = COLUMN_USER_USERNAME)
    private String userUsername;
    @Basic
    @Column(name = COLUMN_UID)
    @Builder.Default
    private String uid = generateUid();
    @Column(name = COLUMN_DATE)
//    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();
    @Basic
    @Column(name = COLUMN_RATE)
    private int rate;
//    @JoinColumn(name = COLUMN_CREATION_ALIAS)
//    @ManyToOne(optional = false)
//    private Creation creation;
//    @JoinColumn(name = COLUMN_USER_USERNAME)
//    @ManyToOne(optional = false)
//    private User user;

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
        this.uid = id;
    }
}
