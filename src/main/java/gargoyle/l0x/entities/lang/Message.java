package gargoyle.l0x.entities.lang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = Message.MESSAGES)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(of = {"locale", "key", "content"})
public class Message {
    public static final String MESSAGES = "MESSAGES";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_KEY = "MESSAGE_KEY";
    private static final String COLUMN_LOCALE = "LOCALE";
    private static final String COLUMN_CONTENT = "MESSAGE_CONTENT";

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = COLUMN_ID)
    private Long id;

    @Column(name = COLUMN_LOCALE)
    private String locale;

    @Column(name = COLUMN_KEY)
    private String key;

    @Column(name = COLUMN_CONTENT)
    private String content;

}
