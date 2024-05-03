package net.lenni0451.mcstructs.itemcomponents.serialization;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.exceptions.InvalidTypeException;
import net.lenni0451.mcstructs.itemcomponents.serialization.interfaces.MergedComponentSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseTypes {

    public static final MergedComponentSerializer<Boolean> UNIT = new MergedComponentSerializer<Boolean>() {
        @Override
        public <T> Boolean deserialize(DataConverter<T> converter, T data) {
            converter.asMap(data).orElseThrow(cause -> InvalidTypeException.of(data, "unit").with(cause));
            return true;
        }

        @Override
        public <T> T serialize(DataConverter<T> converter, Boolean component) {
            return converter.emptyMap();
        }
    };
    public static final MergedComponentSerializer<Boolean> BOOLEAN = new MergedComponentSerializer<Boolean>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, Boolean component) {
            return converter.createBoolean(component);
        }

        @Override
        public <T> Boolean deserialize(DataConverter<T> converter, T data) {
            return converter.asBoolean(data).getOrThrow(cause -> InvalidTypeException.of(data, "boolean").with(cause));
        }
    };
    public static final MergedComponentSerializer<Integer> INTEGER = new MergedComponentSerializer<Integer>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, Integer component) {
            return converter.createInt(component);
        }

        @Override
        public <T> Integer deserialize(DataConverter<T> converter, T data) {
            return converter.asNumber(data).map(Number::intValue).getOrThrow(cause -> InvalidTypeException.of(data, "int").with(cause));
        }
    };
    public static final MergedComponentSerializer<Long> LONG = new MergedComponentSerializer<Long>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, Long component) {
            return converter.createLong(component);
        }

        @Override
        public <T> Long deserialize(DataConverter<T> converter, T data) {
            return converter.asNumber(data).map(Number::longValue).getOrThrow(cause -> InvalidTypeException.of(data, "long").with(cause));
        }
    };
    public static final MergedComponentSerializer<Float> FLOAT = new MergedComponentSerializer<Float>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, Float component) {
            return converter.createFloat(component);
        }

        @Override
        public <T> Float deserialize(DataConverter<T> converter, T data) {
            return converter.asNumber(data).map(Number::floatValue).getOrThrow(cause -> InvalidTypeException.of(data, "float").with(cause));
        }
    };
    public static final MergedComponentSerializer<Double> DOUBLE = new MergedComponentSerializer<Double>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, Double component) {
            return converter.createDouble(component);
        }

        @Override
        public <T> Double deserialize(DataConverter<T> converter, T data) {
            return converter.asNumber(data).map(Number::doubleValue).getOrThrow(cause -> InvalidTypeException.of(data, "double").with(cause));
        }
    };
    public static final MergedComponentSerializer<int[]> INT_ARRAY = new MergedComponentSerializer<int[]>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, int[] component) {
            return converter.createIntArray(component);
        }

        @Override
        public <T> int[] deserialize(DataConverter<T> converter, T data) {
            return converter.asIntArray(data).getOrThrow(cause -> InvalidTypeException.of(data, "int array").with(cause));
        }
    };
    public static final MergedComponentSerializer<String> STRING = new MergedComponentSerializer<String>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, String component) {
            return converter.createString(component);
        }

        @Override
        public <T> String deserialize(DataConverter<T> converter, T data) {
            return converter.asString(data).getOrThrow(cause -> InvalidTypeException.of(data, "string").with(cause));
        }
    };
    public static final MergedComponentSerializer<Identifier> IDENTIFIER = new MergedComponentSerializer<Identifier>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, Identifier component) {
            return converter.createString(component.get());
        }

        @Override
        public <T> Identifier deserialize(DataConverter<T> converter, T data) {
            return Identifier.of(STRING.deserialize(converter, data));
        }
    };

    public static <T> List<T> asList(final DataConverter<T> converter, final T list) {
        return converter.asList(list).getOrThrow(cause -> InvalidTypeException.of(list, "list").with(cause));
    }

    public static <T> Map<String, T> asStringTypeMap(final DataConverter<T> converter, final T map) {
        return converter.asStringTypeMap(map).getOrThrow(cause -> InvalidTypeException.of(map, "map").with(cause));
    }

    public static <N extends Named> MergedComponentSerializer<N> named(final N[] values) {
        return new MergedComponentSerializer<N>() {
            @Override
            public <T> T serialize(DataConverter<T> converter, N component) {
                return converter.createString(component.getName());
            }

            @Override
            public <T> N deserialize(DataConverter<T> converter, T data) {
                String name = STRING.deserialize(converter, data);
                for (N value : values) {
                    if (value.getName().equals(name)) return value;
                }
                throw new IllegalArgumentException("Unknown named value: " + name);
            }
        };
    }

    @SafeVarargs
    public static <V> MergedComponentSerializer<V> oneOf(final MergedComponentSerializer<V>... serializers) {
        return new MergedComponentSerializer<V>() {
            @Override
            public <T> T serialize(DataConverter<T> converter, V component) {
                List<Throwable> causes = null;
                for (MergedComponentSerializer<V> serializer : serializers) {
                    try {
                        return serializer.serialize(converter, component);
                    } catch (Throwable cause) {
                        if (causes == null) causes = new ArrayList<>();
                        causes.add(cause);
                    }
                }
                IllegalArgumentException exception = new IllegalArgumentException("Could not serialize component");
                if (causes != null) causes.forEach(exception::addSuppressed);
                throw exception;
            }

            @Override
            public <T> V deserialize(DataConverter<T> converter, T data) {
                List<Throwable> causes = null;
                for (MergedComponentSerializer<V> serializer : serializers) {
                    try {
                        return serializer.deserialize(converter, data);
                    } catch (Throwable cause) {
                        if (causes == null) causes = new ArrayList<>();
                        causes.add(cause);
                    }
                }
                IllegalArgumentException exception = new IllegalArgumentException("Could not deserialize component");
                if (causes != null) causes.forEach(exception::addSuppressed);
                throw exception;
            }
        };
    }

}
