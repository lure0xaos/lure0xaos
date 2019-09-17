package gargoyle.l0x.err;

public class ItemNotFoundException extends L0XException {
    private static final long serialVersionUID = 1051457429965771300L;

    public ItemNotFoundException(String id) {
        super(id);
    }
}
