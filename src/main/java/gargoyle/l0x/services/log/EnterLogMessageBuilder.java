package gargoyle.l0x.services.log;

import java.lang.reflect.Method;

public class EnterLogMessageBuilder extends AbstractLogMessageBuilder {

    @Override
    protected void appendResult(Method method, Object result) {
    }

    @Override
    protected void appendException(Throwable exception) {
    }

    @Override
    protected void appendExecutionTime(long executionTime) {
    }

    protected void appendType() {
        sb.append(TYPE_METHOD_ENTER);
    }
}
