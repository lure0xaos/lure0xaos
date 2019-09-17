package gargoyle.l0x.entities.app;

import gargoyle.l0x.entities.app.base.BiSourceEntity;
import gargoyle.l0x.entities.base.IdEntity;
import lombok.*;

import javax.persistence.*;

import static gargoyle.l0x.util.Rnd.generateUid;

@Entity
@Table(name = Quote.TABLE_QUOTES)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "uid")
@ToString(of = {"primaryBody", "secondaryBody"})
public class Quote implements IdEntity<String>, BiSourceEntity {
    public static final String TABLE_QUOTES = "QUOTES";
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_HEAD = "HEAD";
    public static final String COLUMN_PRIMARY_BODY = "PRIMARY_BODY";
    public static final String COLUMN_PRIMARY_SOURCE = "PRIMARY_SOURCE";
    public static final String COLUMN_SECONDARY_BODY = "SECONDARY_BODY";
    public static final String COLUMN_SECONDARY_SOURCE = "SECONDARY_SOURCE";
    public static final String COLUMN_AUTHOR = "AUTHOR";
    public static final String COLUMN_ID = "ID";
    @Basic
    @Column(name = COLUMN_HEAD)
    @Builder.Default
    private String head = "";
    @Basic
    @Column(name = COLUMN_UID)
    @Builder.Default
    private String uid = generateUid();
    @Basic(optional = false)
    @Lob
    @Column(name = COLUMN_PRIMARY_BODY)
    @Builder.Default
    private String primaryBody = "";
    @Basic(optional = false)
    @Lob
    @Column(name = COLUMN_PRIMARY_SOURCE)
    @Builder.Default
    private String primarySource = "";
    @Basic(optional = false)
    @Lob
    @Column(name = COLUMN_SECONDARY_BODY)
    @Builder.Default
    private String secondaryBody = "";
    @Basic(optional = false)
    @Lob
    @Column(name = COLUMN_SECONDARY_SOURCE)
    @Builder.Default
    private String secondarySource = "";
    @Basic
    @Column(name = COLUMN_AUTHOR, length = 32)
    @Builder.Default
    private String author = "";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private String id;

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
