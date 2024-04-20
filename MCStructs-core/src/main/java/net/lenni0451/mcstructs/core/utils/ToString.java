package net.lenni0451.mcstructs.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ToString {

    public static ToString of(final Object object) {
        return ToString.of(object.getClass());
    }

    public static ToString of(final Class<?> clazz) {
        return new ToString(clazz);
    }


    private final Class<?> clazz;
    private final List<String> fields;

    private ToString(final Class<?> clazz) {
        this.clazz = clazz;
        this.fields = new ArrayList<>();
    }

    public <T> ToString add(final String name, final T value) {
        return this.add(name, value, v -> true);
    }

    public <T> ToString add(final String name, final T value, final Predicate<T> condition) {
        return this.add(name, value, condition, String::valueOf);
    }

    public <T> ToString add(final String name, final T value, final Predicate<T> condition, final Function<T, String> mapper) {
        if (condition.test(value)) {
            String val = mapper.apply(value);
            if (value instanceof String) val = "'" + val + "'";
            this.fields.add(name + "=" + val);
        }
        return this;
    }

    @Override
    public String toString() {
        return this.clazz.getSimpleName() + "{" + String.join(", ", this.fields) + "}";
    }

}
