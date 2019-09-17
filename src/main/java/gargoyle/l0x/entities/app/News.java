package gargoyle.l0x.entities.app;

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
@Table(name = News.TABLE_NEWS)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "uid")
@ToString(of = {"date", "author", "head", "body"})
public class News implements IdEntity<LocalDateTime>, SourceEntity, OwnerEntity, DatedEntity {
    public static final String TABLE_NEWS = "NEWS";
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_HEAD = "HEAD";
    public static final String COLUMN_BODY = "BODY";
    public static final String COLUMN_SOURCE = "SOURCE";
    public static final String COLUMN_AUTHOR = "AUTHOR";
    public static final String COLUMN_DATE = "DATE";
    @Basic
    @Column(name = COLUMN_UID)
    @Builder.Default
    private String uid = generateUid();
    @Basic
    @Column(name = COLUMN_HEAD)
    @Builder.Default
    private String head = "";
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
    @JoinColumn(name = COLUMN_AUTHOR)
    @ManyToOne(targetEntity = User.class, optional = false)
    private User author;
    @Id
    @GeneratedValue
    @Column(name = COLUMN_DATE)
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();

    @Override
    public Class<LocalDateTime> getIdClass() {
        return LocalDateTime.class;
    }

    @Override
    public LocalDateTime getId() {
        return date;
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
