package gargoyle.l0x.util;

import gargoyle.l0x.services.app.admin.base.BeanModel;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public enum Rnd {
    ;
    private static final Random r = new SecureRandom();
    private static final char[] NUMBER_CHARS = "1324567890".toCharArray();

    public static String generateUid() {
        return UUID.randomUUID().toString();
    }

    public static int rnd(int min, int max) {
        return r.nextInt(max - min) + min;
    }

    public static <T> T rnd(T[] array) {
        return array[rnd(0, array.length)];
    }

    public static char rnd(char[] array) {
        return array[rnd(0, array.length)];
    }

    public static <T> T rnd(List<T> array) {
        return array.get(rnd(0, array.size()));
    }

    public static <T extends Enum<T>> T rnd(Class<T> array) {
        return rnd(array.getEnumConstants());
    }

    public static String str(int len, char[] values) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(rnd(values));
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> T randomObject(Class<T> type) {
        if (String.class == type) {
            return randomString();
        }
        if (Boolean.class == type || boolean.class == type) {
            return randomBoolean();
        }
        if (Number.class.isAssignableFrom(type)) {
            return randomNumber(type);
        }
        if (LocalDateTime.class == type) {
            return (T) LocalDateTime.now();
        }
        if (List.class.isAssignableFrom(type)) {
            return (T) new ArrayList<>(1);
        }
        if (Set.class.isAssignableFrom(type)) {
            return (T) new HashSet<>(1);
        }
        if (Map.class.isAssignableFrom(type)) {
            return (T) new HashMap<>(1);
        }
        return randomBean(type);
    }

    private static <T> T randomBean(Class<T> type) {
        T bean = BeanUtils.instantiateClass(type);
        for (Map.Entry<String, PropertyDescriptor> entry : BeanModel.of(type).propertyMap().entrySet()) {
            PropertyDescriptor propertyDescriptor = entry.getValue();
            try {
                Method writeMethod = propertyDescriptor.getWriteMethod();
                if (null != writeMethod) {
                    writeMethod.invoke(bean, randomObject(propertyDescriptor.getPropertyType()));
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException(e.getLocalizedMessage(), e);
            }
        }
        return bean;
    }

    @SuppressWarnings("unchecked")
    private static <T> T randomNumber(Class<T> type) {
        try {
            return (T) type.getMethod("valueOf", String.class)
                    .invoke(null, String.valueOf(rnd(Integer.MIN_VALUE, Integer.MAX_VALUE)));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e.getLocalizedMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T randomBoolean() {
        return (T) rnd(new Boolean[]{Boolean.TRUE, Boolean.FALSE});
    }

    @SuppressWarnings("unchecked")
    private static <T> T randomString() {
        return (T) str(10, NUMBER_CHARS);
    }
}
