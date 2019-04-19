package gargoyle.l0x.util;

import lombok.SneakyThrows;

public enum Reflect {
    ;

    @SneakyThrows
    static <T> T newInstance(Class<T> type) {
        return type.getConstructor().newInstance();
    }
}

















