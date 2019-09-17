package gargoyle.l0x.services.app.admin.base;

import java.beans.BeanInfo;
import java.beans.FeatureDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class BeanModel<T> {
    private static final Map<Class<?>, BeanModel<?>> cache = new WeakHashMap<>(10);
    private final BeanInfo model;

    private BeanModel(Class<T> entityType) {
        model = lazyInit(entityType);
    }

    @SuppressWarnings("unchecked")
    public static <E> BeanModel<E> of(Class<E> type) {
        return (BeanModel<E>) cache.computeIfAbsent(type, BeanModel::new);
    }

    private BeanInfo lazyInit(Class<T> entityType) {
        try {
            return Introspector.getBeanInfo(entityType);
        } catch (IntrospectionException e) {
            throw new IllegalStateException(e.getLocalizedMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public <R> R field(T bean, String prop) {
        return (R) Arrays.stream(model.getPropertyDescriptors())
                .filter(propertyDescriptor -> Objects.equals(prop, propertyDescriptor.getName()))
                .findFirst()
                .map(propertyDescriptor -> {
                    try {
                        return propertyDescriptor.getReadMethod().invoke(bean);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new IllegalStateException(e.getLocalizedMessage(), e);
                    }
                }).orElse(null);
    }

    @SuppressWarnings("unchecked")
    public <R> R path(T bean, String path) {
        Object value = bean;
        for (String part : path.split("\\.")) {
            if (null != value) {
                value = of((Class<Object>) value.getClass()).field(value, part);
            }
        }
        return (R) value;
    }

    public Set<String> propertyNames() {
        return Arrays.stream(model.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .collect(Collectors.toSet());
    }

    public Map<String, PropertyDescriptor> propertyMap() {
        return Arrays.stream(model.getPropertyDescriptors()).collect(Collectors.toMap(FeatureDescriptor::getName,
                Function.identity()));
    }
}
