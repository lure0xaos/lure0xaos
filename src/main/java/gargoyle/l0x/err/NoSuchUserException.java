package gargoyle.l0x.err;

public class NoSuchUserException extends L0XException {
    private static final long serialVersionUID = -675289581543282761L;

    public NoSuchUserException(String user) {
        super(user);
    }
}
