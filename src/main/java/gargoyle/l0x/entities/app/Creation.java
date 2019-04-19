package gargoyle.l0x.entities.app;

import gargoyle.l0x.entities.app.base.AliasedEntity;
import gargoyle.l0x.entities.app.base.DatedEntity;
import gargoyle.l0x.entities.app.base.OwnerEntity;
import gargoyle.l0x.entities.app.base.SourceEntity;
import gargoyle.l0x.entities.base.IdEntity;
import gargoyle.l0x.entities.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static gargoyle.l0x.util.Rnd.generateUid;

@Entity
@Table(name = Creation.TABLE_CREATIONS)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "alias")
@ToString(of = {"alias", "date", "author", "genre", "head"})
public class Creation implements IdEntity<String>, SourceEntity, OwnerEntity, DatedEntity, AliasedEntity {
    public static final String TABLE_CREATIONS = "CREATIONS";
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_HEAD = "HEAD";
    public static final String COLUMN_BODY = "BODY";
    public static final String COLUMN_SOURCE = "SOURCE";
    public static final String COLUMN_ALIAS = "ALIAS";
    public static final String COLUMN_AUTHOR = "AUTHOR";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_GENRE = "GENRE";
    @Basic(optional = false)
    @Column(name = COLUMN_HEAD)
    private String head;
    @Basic(optional = false)
    @Lob
    @Column(name = COLUMN_BODY)
    @Builder.Default
    private String body = "";
    @Basic(optional = false)
    @Lob
    @Column(name = COLUMN_SOURCE)
    @Builder.Default
    private String source = "";
    @Id
//    @GeneratedValue
    @Column(name = COLUMN_ALIAS, length = 32)
    private String alias;
    @JoinColumn(name = COLUMN_AUTHOR)
    @ManyToOne(targetEntity = User.class, optional = false)
    private User author;
    @Basic(optional = false)
    @Column(name = COLUMN_DATE)
    private LocalDateTime date;
    @Basic(optional = false)
    @Column(name = COLUMN_GENRE, length = 32)
    private String genre;
    @Basic
    @Column(name = COLUMN_UID)
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
