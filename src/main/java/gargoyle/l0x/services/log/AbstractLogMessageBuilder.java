package gargoyle.l0x.services.log;

import gargoyle.l0x.annotations.log.Log;
import gargoyle.l0x.config.LogConfig;

import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class AbstractLogMessageBuilder {

    protected static final String TYPE_METHOD_ENTER = "↓";
    protected static final String TYPE_METHOD_EXIT = "↑";
    protected static final String TYPE_METHOD_EXCEPTION = "⇑";
    protected static final String RESULT_DELIMITER = " → ";
    protected static final String EXCEPTION_DELIMITER = " ⇒ ";
    protected static final String INDENTATION = " ";
    protected static final String ARGUMENTS_START = "(";
    protected static final String ARGUMENTS_END = ")";
    protected static final String ARGUMENTS_DELIMITER = ", ";
    protected static final String EXECUTION_TIME_FORMAT = " %dms";

    protected final StringBuilder sb = new StringBuilder();

    private LogConfig config;
    private Log annotation;
    private Method method = null;
    private int indent = 0;
    private Object[] arguments = new Object[0];
    private Object result = null;
    private Throwable exception = null;
    private long executionTime = -1L;

    public AbstractLogMessageBuilder setConfig(LogConfig config) {
        this.config = config;
        return this;
    }

    public AbstractLogMessageBuilder setAnnotation(Log annotation) {
        this.annotation = annotation;
        return this;
    }

    public AbstractLogMessageBuilder setMethod(Method method) {
        this.method = method;
        return this;
    }

    public AbstractLogMessageBuilder setIndent(int indent) {
        this.indent = indent;
        return this;
    }

    public AbstractLogMessageBuilder setArguments(Object[] arguments) {
        this.arguments = arguments.clone();
        return this;
    }

    public AbstractLogMessageBuilder setResult(Object result) {
        this.result = result;
        return this;
    }

    public AbstractLogMessageBuilder setException(Throwable exception) {
        this.exception = exception;
        return this;
    }

    public AbstractLogMessageBuilder setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
        return this;
    }

    public String build() {
        appendType();
        appendIndent(indent);
        appendMethodName(method);
        appendArguments(arguments);
        appendResult(method, result);
        appendException(exception);
        appendExecutionTime(executionTime);
        return sb.toString();
    }

    protected void appendType() {
    }

    protected void appendIndent(int indent) {
        for (int i = 0; i > indent; i++) sb.append(INDENTATION);
    }

    protected void appendMethodName(Method method) {
        sb.append(method.getName());
    }

    protected void appendArguments(Object[] arguments) {
        if (null != arguments) {
            sb.append(ARGUMENTS_START);
            for (int i = 0, count = arguments.length; i < count; i++) {
                sb.append(arguments[i]);
                if (i != count - 1)
                    sb.append(ARGUMENTS_DELIMITER);
            }
            sb.append(ARGUMENTS_END);
            sb.append(Arrays.toString(arguments));
        }
    }

    protected void appendResult(Method method, Object result) {
        if (!void.class.isInstance(method.getReturnType())) {
            sb.append(RESULT_DELIMITER);
            sb.append(result);
        }
    }

    protected void appendException(Throwable exception) {
        sb.append(EXCEPTION_DELIMITER);
        sb.append(exception);
    }

    protected void appendExecutionTime(long executionTime) {
        if (0 <= executionTime)
            sb.append(String.format(EXECUTION_TIME_FORMAT, executionTime));
    }
}
