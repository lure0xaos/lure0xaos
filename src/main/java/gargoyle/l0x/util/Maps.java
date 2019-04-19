package gargoyle.l0x.util;

import java.util.*;
import java.util.function.Function;

public enum Maps {
    ;

    @SuppressWarnings("unchecked")
    public static <T> T path(Object data, String path) {
        Object cur = data;
        for (String part : path.split("\\.")) {
            if (cur == null)
                return null;
            if (cur instanceof Map) {
                cur = ((Map) cur).get(part);
                continue;
            }
            if (cur instanceof List) {
                cur = ((List) cur).get(Integer.parseInt(part));
                //noinspection UnnecessaryContinue
                continue;
            }
        }
        return (T) cur;
    }

    @SuppressWarnings("unchecked")
    public static <T> T transform(Object data, Function transform) {
        if (data == null)
            return null;
        if (data instanceof Map) {
            Map map = (Map) data;
            Map collect = new LinkedHashMap(map.size());
            for (Map.Entry entry : (Set<Map.Entry>) map.entrySet())
                collect.put(entry.getKey(), transform(entry.getValue(), transform));
            return (T) collect;
        }
        if (data instanceof Collection) {
            Collection list = (Collection) data;
            Collection collect = new ArrayList(list.size());
            for (Object item : list)
                collect.add(transform(item, transform));
            return (T) collect;
        }
        return (T) transform.apply(data);
    }
}
