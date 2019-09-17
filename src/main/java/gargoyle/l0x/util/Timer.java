package gargoyle.l0x.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Timer implements AutoCloseable {
    private final long start;
    private final String message;

    public Timer(String message) {
        if (log.isDebugEnabled()) {
            start = now();
            this.message = message;
            log.debug("Start operation * {} *", message);
        } else {
            start = 0L;
            this.message = "";
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
            log.debug("End operation * {} * ({}ms)", message, duration);
        }
    }
}
