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
@Table(name = Comment.TABLE_COMMENTS)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "uid")
@ToString(of = {"date", "author", "body"})
public class Comment implements IdEntity<Long>, SourceEntity, OwnerEntity, DatedEntity {
    public static final String TABLE_COMMENTS = "COMMENTS";
    public static final String COLUMN_CREATION_ALIAS = "CREATION_ALIAS";
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PID = "PID";
    public static final String COLUMN_HEAD = "HEAD";
    public static final String COLUMN_BODY = "BODY";
    public static final String COLUMN_SOURCE = "SOURCE";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_AUTHOR = "AUTHOR";
    public static final String COLUMN_FLAGS = "FLAGS";
    public static final char[] NO_FLAGS = new char[0];
    @JoinColumn(name = COLUMN_CREATION_ALIAS)
    @ManyToOne(optional = false)
    private Creation creation;
    @Id
    @GeneratedValue
    @Column(name = COLUMN_ID)
    private Long id;
    @ManyToOne
    @JoinColumn(name = COLUMN_PID)
    private Comment parent;
    @Basic
    @Column(name = COLUMN_UID)
    @Builder.Default
    private String uid = generateUid();
    @Basic
    @Column(name = COLUMN_HEAD)
    @Builder.Default
    private String head = "";
    @Basic
    @Lob
    @Column(name = COLUMN_BODY)
    @Builder.Default
    private String body = "";
    @Basic(optional = false)
    @Lob
    @Column(name = COLUMN_SOURCE)
    @Builder.Default
    private String source = "";
    @Basic(optional = false)
    @Column(name = COLUMN_DATE)
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();
    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = COLUMN_AUTHOR)
    private User author;
    @Basic(optional = false)
    @Column(name = COLUMN_FLAGS, length = 8)
    @Builder.Default
    private char[] flags = new char[8];

    @Override
    public Class<Long> getIdClass() {
        return Long.class;
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
