package gargoyle.l0x.data.alert;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;

@Value
@EqualsAndHashCode(of = {"type", "message"})
@ToString
public final class Alert implements Serializable {
    private static final long serialVersionUID = 3527140452140486102L;
    private final AlertType type;
    private final String message;
    private final String description;
    private final boolean dismiss;
    private final boolean raw;

    public Alert(AlertType type, String message) {
        this(type, message, "");
    }

    public Alert(AlertType type, String message, String description) {
        this(type, message, description, false);
    }

    public Alert(AlertType type, String message, String description, boolean dismiss) {
        this(type, message, description, dismiss, false);
    }

    public Alert(AlertType type, String message, String description, boolean dismiss, boolean raw) {
        this.type = type;
        this.message = message;
        this.description = description;
        this.dismiss = dismiss;
        this.raw = raw;
    }

}
