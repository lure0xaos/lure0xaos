package gargoyle.l0x.entities.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

import static gargoyle.l0x.entities.app.Vote.COLUMN_CREATION_ALIAS;
import static gargoyle.l0x.entities.app.Vote.COLUMN_USER_USERNAME;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteId implements Serializable {
    private static final long serialVersionUID = 3826488266725551608L;
    @Id
    @Column(name = COLUMN_CREATION_ALIAS)
    private String creationAlias;
    @Id
    @Column(name = COLUMN_USER_USERNAME)
    private String userUsername;
}
