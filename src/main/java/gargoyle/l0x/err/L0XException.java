package gargoyle.l0x.err;

public class L0XException extends RuntimeException {
    private static final long serialVersionUID = -123882319980219385L;

    public L0XException(String message) {
        super(message);
    }

    public L0XException(String message, Throwable cause) {
        super(message, cause);
    }
}
