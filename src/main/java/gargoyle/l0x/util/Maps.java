package gargoyle.l0x.util;

import java.util.*;
import java.util.function.Function;

public enum Maps {
    ;

    @SuppressWarnings("unchecked")
    public static <T> T path(Object data, String path) {
        Object cur = data;
        for (String part : path.split("\\.")) {
            if (null == cur)
                return null;
            if (cur instanceof Map) {
                cur = ((Map<?, ?>) cur).get(part);
                continue;
            }
            if (cur instanceof List) {
                cur = ((List<?>) cur).get(Integer.parseInt(part));
                //noinspection UnnecessaryContinue
                continue;
            }
        }
        return (T) cur;
    }

    @SuppressWarnings("unchecked")
    public static <T> T transform(Object data, Function transform) {
        if (null == data)
            return null;
        if (data instanceof Map) {
            Map<Object, Object> map = (Map<Object, Object>) data;
            Map<Object, Object> collect = new LinkedHashMap<>(map.size());
            for (Map.Entry<Object, Object> entry : map.entrySet())
                collect.put(entry.getKey(), transform(entry.getValue(), transform));
            return (T) collect;
        }
        if (data instanceof Collection) {
            Collection<Object> list = (Collection<Object>) data;
            Collection<Object> collect = new ArrayList<>(list.size());
            for (Object item : list)
                collect.add(transform(item, transform));
            return (T) collect;
        }
        return (T) transform.apply(data);
    }

    public static <K, V> Map<K, V> of(Object... data) {
        Map<K, V> map = new LinkedHashMap<>(data.length / 2);
        for (int i = 0; i < data.length; i += 2) {
            map.put((K) data[i], (V) data[i + 1]);
        }
        return map;
    }
}
