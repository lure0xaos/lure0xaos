package gargoyle.l0x.aspect;

public final class LogIndentation {
    private static final ThreadLocal<Integer> indentation = new ThreadLocal<>();

    private LogIndentation() {
    }

    private static int indent(int delta) {
        Integer indent = indentation.get();
        if (null == indent) indent = 0;
        indent += delta;
        indentation.set(indent);
        return indent;
    }

    public static void decrementIndentation() {
        indent(-1);
    }

    public static int incrementIndentation() {
        return indent(1);
    }
}
