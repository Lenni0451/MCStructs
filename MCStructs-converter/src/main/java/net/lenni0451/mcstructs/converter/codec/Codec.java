package net.lenni0451.mcstructs.converter.codec;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.Result;
import net.lenni0451.mcstructs.core.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface Codec<T> extends DataSerializer<T>, DataDeserializer<T> {

    Codec<Boolean> UNIT = new Codec<Boolean>() {
        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, Boolean element) {
            return Result.success(converter.emptyMap());
        }

        @Override
        public <S> Result<Boolean> deserialize(DataConverter<S> converter, S data) {
            return converter.asMap(data).map(map -> true);
        }
    };
    Codec<Boolean> BOOLEAN = new Codec<Boolean>() {
        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, Boolean element) {
            return Result.success(converter.createBoolean(element));
        }

        @Override
        public <S> Result<Boolean> deserialize(DataConverter<S> converter, S data) {
            return converter.asBoolean(data);
        }
    };
    Codec<Byte> BYTE = new Codec<Byte>() {
        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, Byte element) {
            return Result.success(converter.createByte(element));
        }

        @Override
        public <S> Result<Byte> deserialize(DataConverter<S> converter, S data) {
            return converter.asNumber(data).map(Number::byteValue);
        }
    };
    Codec<Short> SHORT = new Codec<Short>() {
        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, Short element) {
            return Result.success(converter.createShort(element));
        }

        @Override
        public <S> Result<Short> deserialize(DataConverter<S> converter, S data) {
            return converter.asNumber(data).map(Number::shortValue);
        }
    };
    Codec<Integer> INTEGER = new Codec<Integer>() {
        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, Integer element) {
            return Result.success(converter.createInt(element));
        }

        @Override
        public <S> Result<Integer> deserialize(DataConverter<S> converter, S data) {
            return converter.asNumber(data).map(Number::intValue);
        }
    };
    Codec<Long> LONG = new Codec<Long>() {
        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, Long element) {
            return Result.success(converter.createLong(element));
        }

        @Override
        public <S> Result<Long> deserialize(DataConverter<S> converter, S data) {
            return converter.asNumber(data).map(Number::longValue);
        }
    };
    Codec<Float> FLOAT = new Codec<Float>() {
        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, Float element) {
            return Result.success(converter.createFloat(element));
        }

        @Override
        public <S> Result<Float> deserialize(DataConverter<S> converter, S data) {
            return converter.asNumber(data).map(Number::floatValue);
        }
    };
    Codec<Double> DOUBLE = new Codec<Double>() {
        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, Double element) {
            return Result.success(converter.createDouble(element));
        }

        @Override
        public <S> Result<Double> deserialize(DataConverter<S> converter, S data) {
            return converter.asNumber(data).map(Number::doubleValue);
        }
    };
    Codec<String> STRING = new Codec<String>() {
        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, String element) {
            return Result.success(converter.createString(element));
        }

        @Override
        public <S> Result<String> deserialize(DataConverter<S> converter, S data) {
            return converter.asString(data);
        }
    };
    Codec<byte[]> BYTE_ARRAY = new Codec<byte[]>() {
        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, byte[] element) {
            return Result.success(converter.createByteArray(element));
        }

        @Override
        public <S> Result<byte[]> deserialize(DataConverter<S> converter, S data) {
            return converter.asByteArray(data);
        }
    };
    Codec<int[]> INT_ARRAY = new Codec<int[]>() {
        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, int[] element) {
            return Result.success(converter.createIntArray(element));
        }

        @Override
        public <S> Result<int[]> deserialize(DataConverter<S> converter, S data) {
            return converter.asIntArray(data);
        }
    };
    Codec<long[]> LONG_ARRAY = new Codec<long[]>() {
        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, long[] element) {
            return Result.success(converter.createLongArray(element));
        }

        @Override
        public <S> Result<long[]> deserialize(DataConverter<S> converter, S data) {
            return converter.asLongArray(data);
        }
    };
    Codec<Identifier> STRING_IDENTIFIER = STRING.mapThrowing(Identifier::get, Identifier::of);

    static <T> Codec<T> ofThrowing(final DataSerializer<T> serializer, final DataDeserializer<T> deserializer) {
        return new Codec<T>() {
            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, T element) {
                try {
                    return serializer.serialize(converter, element);
                } catch (Throwable t) {
                    return Result.error(t);
                }
            }

            @Override
            public <S> Result<T> deserialize(DataConverter<S> converter, S data) {
                try {
                    return deserializer.deserialize(converter, data);
                } catch (Throwable t) {
                    return Result.error(t);
                }
            }
        };
    }

    static Codec<Integer> minInt(final int minInclusive) {
        return rangedInt(minInclusive, Integer.MAX_VALUE);
    }

    static Codec<Integer> rangedInt(final int minInclusive, final int maxInclusive) {
        return INTEGER.verified(i -> {
            if (i < minInclusive) return Result.error("Value is smaller than minimum: " + i + " < " + minInclusive);
            if (i > maxInclusive) return Result.error("Value is bigger than maximum: " + i + " > " + maxInclusive);
            return null;
        });
    }

    static Codec<String> sizedString(final int minInclusive, final int maxInclusive) {
        return STRING.verified(s -> {
            if (s.length() < minInclusive) return Result.error("String is shorter than minimum: " + s.length() + " < " + minInclusive);
            if (s.length() > maxInclusive) return Result.error("String is longer than maximum: " + s.length() + " > " + maxInclusive);
            return null;
        });
    }

    static <T extends NamedType> Codec<T> named(final T[] values) {
        return STRING.flatMap(named -> Result.success(named.getName()), name -> {
            for (T value : values) {
                if (value.getName().equals(name)) return Result.success(value);
            }
            String available = "";
            for (T value : values) available += value.getName() + ", ";
            if (!available.isEmpty()) available = available.substring(0, available.length() - 2);
            return Result.error("Unknown named value: " + name + " (" + available + ")");
        });
    }

    @SafeVarargs
    static <T> Codec<T> oneOf(final Codec<T>... codecs) {
        if (codecs.length == 0) throw new IllegalArgumentException("At least one codec is required");
        return new Codec<T>() {
            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, T element) {
                List<Result<S>> failed = null;
                for (Codec<T> codec : codecs) {
                    Result<S> result = codec.serialize(converter, element);
                    if (!result.isError()) return result;
                    if (failed == null) failed = new ArrayList<>();
                    failed.add(result.mapError());
                }
                return Result.mergeErrors("Failed to serialize with " + codecs.length + " codecs", failed);
            }

            @Override
            public <S> Result<T> deserialize(DataConverter<S> converter, S data) {
                List<Result<T>> failed = null;
                for (Codec<T> codec : codecs) {
                    Result<T> result = codec.deserialize(converter, data);
                    if (!result.isError()) return result;
                    if (failed == null) failed = new ArrayList<>();
                    failed.add(result.mapError());
                }
                return Result.mergeErrors("Failed to deserialize with " + codecs.length + " codecs", failed);
            }
        };
    }

    default <N> Codec<N> map(final Function<N, T> serializer, final Function<T, N> deserializer) {
        return new Codec<N>() {
            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, N element) {
                return Codec.this.serialize(converter, serializer.apply(element));
            }

            @Override
            public <S> Result<N> deserialize(DataConverter<S> converter, S data) {
                return Codec.this.deserialize(converter, data).map(deserializer);
            }
        };
    }

    default <N> Codec<N> mapThrowing(final Function<N, T> serializer, final Function<T, N> deserializer) {
        return this.flatMap(n -> {
            try {
                return Result.success(serializer.apply(n));
            } catch (Exception e) {
                return Result.error(e);
            }
        }, t -> {
            try {
                return Result.success(deserializer.apply(t));
            } catch (Exception e) {
                return Result.error(e);
            }
        });
    }

    default <N> Codec<N> flatMap(final Function<N, Result<T>> serializer, final Function<T, Result<N>> deserializer) {
        return new Codec<N>() {
            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, N element) {
                Result<T> result = serializer.apply(element);
                if (result.isError()) return result.mapError();
                return Codec.this.serialize(converter, result.get());
            }

            @Override
            public <S> Result<N> deserialize(DataConverter<S> converter, S data) {
                Result<T> result = Codec.this.deserialize(converter, data);
                if (result.isError()) return result.mapError();
                return deserializer.apply(result.get());
            }
        };
    }

    default Codec<List<T>> listOf() {
        return this.listOf(Integer.MAX_VALUE);
    }

    default Codec<List<T>> listOf(final int maxElements) {
        return new Codec<List<T>>() {
            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, List<T> element) {
                if (element.size() > maxElements) return Result.error("List size is bigger than maximum: " + element.size() + " > " + maxElements);
                List<S> converted = new ArrayList<>();
                for (T value : element) {
                    Result<S> result = Codec.this.serialize(converter, value);
                    if (result.isError()) return result.mapError();
                    converted.add(result.get());
                }
                return Result.success(converter.createList(converted));
            }

            @Override
            public <S> Result<List<T>> deserialize(DataConverter<S> converter, S data) {
                Result<List<S>> result = converter.asList(data);
                if (result.isError()) return result.mapError();
                List<S> list = result.get();
                if (list.size() > maxElements) return Result.error("List size is bigger than maximum: " + list.size() + " > " + maxElements);
                List<T> converted = new ArrayList<>();
                for (S value : list) {
                    Result<T> element = Codec.this.deserialize(converter, value);
                    if (element.isError()) return element.mapError();
                    converted.add(element.get());
                }
                return Result.success(converted);
            }
        };
    }

    default <K> Codec<Map<K, T>> mapOf(final Codec<K> keyCodec) {
        return new Codec<Map<K, T>>() {
            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, Map<K, T> element) {
                Map<S, S> map = new HashMap<>();
                for (Map.Entry<K, T> entry : element.entrySet()) {
                    Result<S> key = keyCodec.serialize(converter, entry.getKey());
                    if (key.isError()) return key.mapError();
                    Result<S> value = Codec.this.serialize(converter, entry.getValue());
                    if (value.isError()) return value.mapError();

                    map.put(key.get(), value.get());
                }
                return Result.success(converter.createMap(map));
            }

            @Override
            public <S> Result<Map<K, T>> deserialize(DataConverter<S> converter, S data) {
                Result<Map<S, S>> map = converter.asMap(data);
                if (map.isError()) return map.mapError();
                Map<K, T> converted = new HashMap<>();
                for (Map.Entry<S, S> entry : map.get().entrySet()) {
                    Result<K> key = keyCodec.deserialize(converter, entry.getKey());
                    if (key.isError()) return key.mapError();
                    Result<T> value = Codec.this.deserialize(converter, entry.getValue());
                    if (value.isError()) return value.mapError();

                    converted.put(key.get(), value.get());
                }
                return Result.success(converted);
            }
        };
    }

    default Codec<Map<String, T>> stringMapOf() {
        return this.mapOf(STRING);
    }

    default Codec<T> verified(final Function<T, Result<Void>> verifier) {
        return new Codec<T>() {
            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, T element) {
                Result<Void> verify = verifier.apply(element);
                if (verify != null && verify.isError()) return verify.mapError();
                return Codec.this.serialize(converter, element);
            }

            @Override
            public <S> Result<T> deserialize(DataConverter<S> converter, S data) {
                Result<T> result = Codec.this.deserialize(converter, data);
                if (result.isError()) return result;
                Result<Void> verify = verifier.apply(result.get());
                if (verify != null && verify.isError()) return verify.mapError();
                return result;
            }
        };
    }

    default MapCodec<String, T> mapCodec(final String key) {
        return this.mapCodec(STRING, key);
    }

    default <K> MapCodec<K, T> mapCodec(final Codec<K> keyCodec, final K key) {
        return MapCodec.of(key, keyCodec, this);
    }

}
