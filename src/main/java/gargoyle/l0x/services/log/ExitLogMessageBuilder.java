package gargoyle.l0x.services.log;

public class ExitLogMessageBuilder extends AbstractLogMessageBuilder {

    @Override
    protected void appendException(Throwable exception) {
    }

    protected void appendType() {
        sb.append(TYPE_METHOD_EXIT);
    }
}
