package gargoyle.l0x.services.app.admin.base;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class BeanModel<T> {
    private final BeanInfo model;

    private BeanModel(Class<T> entityType) {
        model = lazyInit(entityType);
    }

    public static <E> BeanModel<E> of(Class<E> type) {
        return new BeanModel<>(type);
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
                .filter(propertyDescriptor -> prop.equals(propertyDescriptor.getName()))
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
        Optional<Object> value = Optional.ofNullable(bean);
        for (String part : path.split("\\.")) {
            value = value.map((val) -> of((Class<Object>) val.getClass()).field(val, part));
        }
        return (R) value.orElse(null);
    }

    public Set<String> propertyNames(T bean) {
        return Arrays.stream(model.getPropertyDescriptors()).map(FeatureDescriptor::getName).collect(Collectors.toSet());
    }

    public Map<String, PropertyDescriptor> propertyMap(T bean) {
        return Arrays.stream(model.getPropertyDescriptors()).collect(Collectors.toMap(FeatureDescriptor::getName, Function.identity()));
    }
}
