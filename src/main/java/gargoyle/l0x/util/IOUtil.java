package gargoyle.l0x.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gargoyle.l0x.err.ItemNotFoundException;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcOperations;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

public enum IOUtil {
    ;

    private static final int BUFFER_SIZE = 1024;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String NL = "\n";
    public static final String SQL_CHECK_TABLE = "select count(*) from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ? and TABLE_SCHEMA = 'PUBLIC';";

    public static String readInputStreamToString(InputStream stream, Charset charset) throws IOException {
        StringBuilder out = new StringBuilder(BUFFER_SIZE);
        try (Reader in = new InputStreamReader(stream, charset)) {
            char[] buffer = new char[BUFFER_SIZE];
            while (true) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (0 <= rsz) {
                    out.append(buffer, 0, rsz);
                } else {
                    break;
                }
            }
        }
        return out.toString();
    }

    public static boolean checkTable(String table, JdbcOperations operations, Logger log) {
        try {
            Long count = operations.queryForObject(SQL_CHECK_TABLE, Long.class, table);
            return null != count && 0 < count;
        } catch (DataAccessException e) {
            log.error("No data in table {}", table, e);
            return false;
        }
    }

    public static void execScript(String script, JdbcOperations operations, Logger log) {
        try {
            operations.execute(readInputStreamToString(
                    Optional.ofNullable(IOUtil.class.getResourceAsStream(script))
                            .orElseThrow(() -> new ItemNotFoundException("script " + script))
                    , UTF_8)
                    .replace(NL, ""));
        } catch (DataAccessException e) {
            log.error("Cannot execute script {}", script, e);
        } catch (IOException e) {
            log.error("Cannot load script{}", script, e);
        }
    }

    public static ResponseEntity<? extends Resource> getResponse(Resource resource) throws IOException {
        return ResponseEntity.ok()
                .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    public static String toJson(Map<String, ? extends Serializable> map, String def) {
        try {
            return toJson(map);
        } catch (JsonProcessingException e) {
            return def;
        }
    }

    public static String toJson(Map<String, ? extends Serializable> map) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(map);
    }
}
