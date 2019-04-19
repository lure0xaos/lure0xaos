package gargoyle.l0x.util;

import gargoyle.l0x.services.app.admin.base.BeanModel;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

public enum Rnd {
    ;
    private static final Random r = new Random();

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
        if (type == String.class) {
            return (T) str(10, "1324567890".toCharArray());
        }
        if (type == Boolean.class || type == boolean.class) {
            return (T) rnd(new Boolean[]{Boolean.TRUE, Boolean.FALSE});
        }
        if (Number.class.isAssignableFrom(type)) {
            try {
                return (T) type.getMethod("valueOf", String.class).invoke(null, String.valueOf(rnd(Integer.MIN_VALUE, Integer.MAX_VALUE)));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException(e.getLocalizedMessage(), e);
            }
        }
        if (type == LocalDateTime.class) {
            return (T) LocalDateTime.now();
        }
        if (List.class.isAssignableFrom(type)) {
            return (T) new ArrayList();
        }
        if (Set.class.isAssignableFrom(type)) {
            return (T) new HashSet();
        }
        if (Map.class.isAssignableFrom(type)) {
            return (T) new HashMap();
        }

        T bean = Reflect.newInstance(type);
        for (Map.Entry<String, PropertyDescriptor> entry : BeanModel.of(type).propertyMap(bean).entrySet()) {
            PropertyDescriptor propertyDescriptor = entry.getValue();
            try {
                Method writeMethod = propertyDescriptor.getWriteMethod();
                if (writeMethod != null) {
                    writeMethod.invoke(bean, randomObject(propertyDescriptor.getPropertyType()));
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException(e.getLocalizedMessage(), e);
            }
        }
        return bean;
    }
}
