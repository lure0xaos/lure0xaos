package gargoyle.l0x.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Timer implements AutoCloseable {
    private long start;
    private String message;

    public Timer(String message) {
        if (log.isDebugEnabled()) {
            this.message = message;
            start = now();
            log.debug("Start operation * " + message + " *");
        }
    }

    private static long now() {
        return System.currentTimeMillis();
    }

    @Override
    public void close() {
        if (log.isDebugEnabled()) {
            long end = now();
            long duration = end - start;
            log.debug("End operation *" + message + " * (" + duration + "ms)");
        }
    }
}
