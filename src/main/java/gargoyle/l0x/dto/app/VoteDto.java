package gargoyle.l0x.dto.app;

import gargoyle.l0x.dto.base.Dto;
import gargoyle.l0x.dto.user.UserDto;
import lombok.*;

import java.time.LocalDateTime;

import static gargoyle.l0x.util.Rnd.generateUid;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "creation")
@ToString(of = "creation")
public class VoteDto implements Dto<String> {
    public static final String TABLE_VOTES = "VOTES";
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_CREATION_ALIAS = "CREATION_ALIAS";
    public static final String COLUMN_USER_USERNAME = "USER_USERNAME";
    public static final String COLUMN_RATE = "RATE";
    public static final String COLUMN_DATE = "DATE";
    @Builder.Default
    private String uid = generateUid();
    private CreationDto creation;
    private UserDto user;
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();
    private int rate;

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
