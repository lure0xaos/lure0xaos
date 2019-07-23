package gargoyle.l0x.util;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

public enum IOUtil {
    ;

    public static String readInputStreamToString(InputStream stream, Charset charset) throws IOException {
        final StringBuilder out = new StringBuilder();
        try (Reader in = new InputStreamReader(stream, charset)) {
            final int bufferSize = 1024;
            final char[] buffer = new char[bufferSize];
            while (true) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);
            }
        }
        return out.toString();
    }

    public static boolean checkTable(String table, JdbcOperations operations, Logger log) {
        try {
            String sql = String.format("select count(*) from \"%s\"", table);
            Long count = operations.queryForObject(sql, Long.class);
            return count != null && count > 0;
        } catch (DataAccessException e) {
            log.error("No data in table " + table, e);
            return false;
        }
    }

    public static void execScript(String script, JdbcOperations operations, Logger log) {
        try {
            operations.execute(readInputStreamToString(
                    Optional.ofNullable(IOUtil.class.getResourceAsStream(script))
                            .orElseThrow(() -> new RuntimeException("script not found: " + script))
                    , UTF_8)
                    .replace("\n", ""));
        } catch (DataAccessException e) {
            log.error("Cannot execute script " + script, e);
        } catch (IOException e) {
            log.error("Cannot load script" + script, e);
        }
    }
}
