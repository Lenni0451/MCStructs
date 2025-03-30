package net.lenni0451.mcstructs.networkcodec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import lombok.SneakyThrows;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.io.NbtIO;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.*;

public interface NetType<T> extends NetReader<T>, NetWriter<T> {

    NetType<Boolean> UNIT = unit(() -> true);
    NetType<Boolean> BOOLEAN = new NetType<Boolean>() {
        @Override
        public Boolean read(final ByteBuf buf) {
            return buf.readBoolean();
        }

        @Override
        public void write(final ByteBuf buf, final Boolean value) {
            buf.writeBoolean(value);
        }
    };
    NetType<Byte> BYTE = new NetType<Byte>() {
        @Override
        public Byte read(ByteBuf buf) {
            return buf.readByte();
        }

        @Override
        public void write(ByteBuf buf, Byte value) {
            buf.writeByte(value);
        }
    };
    NetType<Short> UNSIGNED_BYTE = new NetType<Short>() {
        @Override
        public Short read(ByteBuf buf) {
            return buf.readUnsignedByte();
        }

        @Override
        public void write(ByteBuf buf, Short value) {
            buf.writeByte(value);
        }
    };
    NetType<Short> SHORT = new NetType<Short>() {
        @Override
        public Short read(ByteBuf buf) {
            return buf.readShort();
        }

        @Override
        public void write(ByteBuf buf, Short value) {
            buf.writeShort(value);
        }
    };
    NetType<Integer> UNSIGNED_SHORT = new NetType<Integer>() {
        @Override
        public Integer read(ByteBuf buf) {
            return buf.readUnsignedShort();
        }

        @Override
        public void write(ByteBuf buf, Integer value) {
            buf.writeShort(value);
        }
    };
    NetType<Integer> INTEGER = new NetType<Integer>() {
        @Override
        public Integer read(ByteBuf buf) {
            return buf.readInt();
        }

        @Override
        public void write(ByteBuf buf, Integer value) {
            buf.writeInt(value);
        }
    };
    NetType<Long> LONG = new NetType<Long>() {
        @Override
        public Long read(ByteBuf buf) {
            return buf.readLong();
        }

        @Override
        public void write(ByteBuf buf, Long value) {
            buf.writeLong(value);
        }
    };
    NetType<Integer> VAR_INT = new NetType<Integer>() {
        @Override
        public Integer read(ByteBuf buf) {
            int i = 0;
            int frameSize = 0;
            byte currentByte;
            do {
                currentByte = buf.readByte();
                i |= (currentByte & 127) << frameSize++ * 7;
                if (frameSize > 5) throw new RuntimeException("VarInt too big");
            } while ((currentByte & 128) == 128);
            return i;
        }

        @Override
        public void write(ByteBuf buf, Integer value) {
            while ((value & -128) != 0) {
                buf.writeByte(value & 127 | 128);
                value >>>= 7;
            }
            buf.writeByte(value);
        }
    };
    NetType<Float> FLOAT = new NetType<Float>() {
        @Override
        public Float read(ByteBuf buf) {
            return buf.readFloat();
        }

        @Override
        public void write(ByteBuf buf, Float value) {
            buf.writeFloat(value);
        }
    };
    NetType<Double> DOUBLE = new NetType<Double>() {
        @Override
        public Double read(ByteBuf buf) {
            return buf.readDouble();
        }

        @Override
        public void write(ByteBuf buf, Double value) {
            buf.writeDouble(value);
        }
    };
    NetType<byte[]> BYTE_ARRAY = byteArray(Integer.MAX_VALUE);
    NetType<byte[]> READABLE_BYTES = new NetType<byte[]>() {
        @Override
        public byte[] read(ByteBuf buf) {
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            return bytes;
        }

        @Override
        public void write(ByteBuf buf, byte[] value) {
            buf.writeBytes(value);
        }
    };
    NetType<int[]> INT_ARRAY = integerArray(Integer.MAX_VALUE);
    NetType<int[]> VAR_INT_ARRAY = varIntArray(Integer.MAX_VALUE);
    NetType<String> STRING = string(32767);
    NetType<NbtTag> NBT_TAG = nbtTag(new NbtReadTracker());
    NetType<CompoundTag> COMPOUND_TAG = NBT_TAG.map(NbtTag::asCompoundTag, tag -> tag);
    NetType<UUID> UUID = new NetType<UUID>() {
        @Override
        public UUID read(ByteBuf buf) {
            int i1 = buf.readInt();
            int i2 = buf.readInt();
            int i3 = buf.readInt();
            int i4 = buf.readInt();
            return new UUID((long) i1 << 32 | (long) i2 & 4294967295L, (long) i3 << 32 | (long) i4 & 4294967295L);
        }

        @Override
        public void write(ByteBuf buf, UUID value) {
            long mostSignificantBits = value.getMostSignificantBits();
            long leastSignificantBits = value.getLeastSignificantBits();
            int[] ints = new int[]{(int) (mostSignificantBits >> 32), (int) mostSignificantBits, (int) (leastSignificantBits >> 32), (int) leastSignificantBits};
            buf.writeInt(ints[0]);
            buf.writeInt(ints[1]);
            buf.writeInt(ints[2]);
            buf.writeInt(ints[3]);
        }
    };
    NetType<UUID> STRICT_STRING_UUID = STRING.map(java.util.UUID::fromString, java.util.UUID::toString);
    NetType<Identifier> IDENTIFIER = STRING.map(Identifier::of, Identifier::get);

    static <T> NetType<T> invalid(final String message) {
        return new NetType<T>() {
            @Override
            public T read(ByteBuf buf) {
                throw new DecoderException(message);
            }

            @Override
            public void write(ByteBuf buf, T value) {
                throw new EncoderException(message);
            }
        };
    }

    static <T> NetType<T> unit(final Supplier<T> constant) {
        return new NetType<T>() {
            @Override
            public T read(ByteBuf buf) {
                return constant.get();
            }

            @Override
            public void write(ByteBuf buf, T value) {
            }
        };
    }

    static NetType<byte[]> byteArray(final int maxSize) {
        return new NetType<byte[]>() {
            @Override
            public byte[] read(ByteBuf buf) {
                byte[] bytes;
                int size = VAR_INT.read(buf);
                if (size > maxSize) throw new DecoderException("The received byte array is larger than maximum allowed (" + size + " > " + maxSize + ")");
                bytes = new byte[size];
                buf.readBytes(bytes);
                return bytes;
            }

            @Override
            public void write(ByteBuf buf, byte[] value) {
                if (value.length > maxSize) throw new DecoderException("The byte array is larger than maximum allowed (" + value.length + " > " + maxSize + ")");
                VAR_INT.write(buf, value.length);
                buf.writeBytes(value);
            }
        };
    }

    static NetType<BitSet> bitSet(final int byteCount) {
        return new NetType<BitSet>() {
            @Override
            public BitSet read(ByteBuf buf) {
                byte[] bytes = new byte[byteCount];
                buf.readBytes(bytes);
                return BitSet.valueOf(bytes);
            }

            @Override
            public void write(ByteBuf buf, BitSet value) {
                buf.writeBytes(value.toByteArray(), 0, byteCount);
            }
        };
    }

    static NetType<int[]> integerArray(final int maxSize) {
        return new NetType<int[]>() {
            @Override
            public int[] read(ByteBuf buf) {
                int count = VAR_INT.read(buf);
                if (count > maxSize) throw new DecoderException("The received integer array is larger than maximum allowed (" + count + " > " + maxSize + ")");
                int[] ints = new int[count];
                for (int i = 0; i < count; i++) ints[i] = buf.readInt();
                return ints;
            }

            @Override
            public void write(ByteBuf buf, int[] value) {
                if (value.length > maxSize) throw new DecoderException("The integer array is larger than maximum allowed (" + value.length + " > " + maxSize + ")");
                VAR_INT.write(buf, value.length);
                for (int i : value) buf.writeInt(i);
            }
        };
    }

    static NetType<int[]> varIntArray(final int maxSize) {
        return new NetType<int[]>() {
            @Override
            public int[] read(ByteBuf buf) {
                int count = VAR_INT.read(buf);
                if (count > maxSize) throw new DecoderException("The received integer array is larger than maximum allowed (" + count + " > " + maxSize + ")");
                int[] ints = new int[count];
                for (int i = 0; i < count; i++) ints[i] = VAR_INT.read(buf);
                return ints;
            }

            @Override
            public void write(ByteBuf buf, int[] value) {
                if (value.length > maxSize) throw new DecoderException("The integer array is larger than maximum allowed (" + value.length + " > " + maxSize + ")");
                VAR_INT.write(buf, value.length);
                for (int i : value) VAR_INT.write(buf, i);
            }
        };
    }

    static NetType<String> string(final int maxLength) {
        return new NetType<String>() {
            @Override
            public String read(ByteBuf buf) {
                return VAR_INT.applyRead(buf, stringLength -> {
                    if (stringLength > maxLength * 4) {
                        throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + stringLength + " > " + maxLength * 4 + ")");
                    } else if (stringLength < 0) {
                        throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
                    } else {
                        byte[] bytes = new byte[stringLength];
                        buf.readBytes(bytes);
                        String s = new String(bytes, StandardCharsets.UTF_8);
                        if (s.length() > maxLength) {
                            throw new DecoderException("The received string length is longer than maximum allowed (" + stringLength + " > " + maxLength + ")");
                        } else {
                            return s;
                        }
                    }
                });
            }

            @Override
            public void write(ByteBuf buf, String value) {
                byte[] bs = value.getBytes(StandardCharsets.UTF_8);
                VAR_INT.write(buf, bs.length);
                buf.writeBytes(bs);
            }
        };
    }

    static NetType<NbtTag> nbtTag(final NbtReadTracker readTracker) {
        return new NetType<NbtTag>() {
            @Override
            @SneakyThrows
            public NbtTag read(ByteBuf buf) {
              return NbtIO.LATEST.readUnnamed(new ByteBufInputStream(buf), readTracker);
            }

            @Override
            @SneakyThrows
            public void write(ByteBuf buf, NbtTag value) {
              NbtIO.LATEST.writeUnnamed(new ByteBufOutputStream(buf), value);
            }
        };
    }

    static <T extends Enum<T>> NetType<T> enumType(final T[] values, final Function<Integer, Integer> outOfBoundsMapper) {
        return new NetType<T>() {
            @Override
            public T read(ByteBuf buf) {
                int index = VAR_INT.read(buf);
                if (index < 0 || index >= values.length) {
                    index = outOfBoundsMapper.apply(index);
                }
                return values[index];
            }

            @Override
            public void write(ByteBuf buf, T value) {
                VAR_INT.write(buf, value.ordinal());
            }
        };
    }

    static <L, R> NetType<Either<L, R>> either(final NetType<L> left, final NetType<R> right) {
        return new NetType<Either<L, R>>() {
            @Override
            public Either<L, R> read(ByteBuf buf) {
                if (buf.readBoolean()) {
                    return Either.left(left.read(buf));
                } else {
                    return Either.right(right.read(buf));
                }
            }

            @Override
            public void write(ByteBuf buf, Either<L, R> value) {
                buf.writeBoolean(value.isLeft());
                if (value.isLeft()) {
                    left.write(buf, value.getLeft());
                } else {
                    right.write(buf, value.getRight());
                }
            }
        };
    }

    static <T> NetType<Either<Integer, T>> registryEntry(final NetType<T> directType) {
        return new NetType<Either<Integer, T>>() {
            @Override
            public Either<Integer, T> read(ByteBuf buf) {
                int id = VAR_INT.read(buf);
                if (id == 0) {
                    return Either.right(directType.read(buf));
                } else {
                    return Either.left(id - 1);
                }
            }

            @Override
            public void write(ByteBuf buf, Either<Integer, T> value) {
                if (value.isLeft()) {
                    VAR_INT.write(buf, value.getLeft() + 1);
                } else {
                    VAR_INT.write(buf, 0);
                    directType.write(buf, value.getRight());
                }
            }
        };
    }

    static <T> NetType<T> unwrappedEither(final NetType<T> left, final NetType<T> right, final Predicate<T> isLeft) {
        return new NetType<T>() {
            @Override
            public T read(ByteBuf buf) {
                if (buf.readBoolean()) {
                    return left.read(buf);
                } else {
                    return right.read(buf);
                }
            }

            @Override
            public void write(ByteBuf buf, T value) {
                boolean leftValue = isLeft.test(value);
                buf.writeBoolean(leftValue);
                if (leftValue) {
                    left.write(buf, value);
                } else {
                    right.write(buf, value);
                }
            }
        };
    }

    static <T> NetType<Optional<T>> optional(final NetType<T> type) {
        return new NetType<Optional<T>>() {
            @Override
            public Optional<T> read(ByteBuf buf) {
                if (buf.readBoolean()) return Optional.of(type.read(buf));
                return Optional.empty();
            }

            @Override
            public void write(ByteBuf buf, Optional<T> value) {
                buf.writeBoolean(value.isPresent());
                value.ifPresent(t -> type.write(buf, t));
            }
        };
    }

    static <T> NetType<T> nullable(final NetType<T> type) {
        return new NetType<T>() {
            @Override
            public T read(ByteBuf buf) {
                if (buf.readBoolean()) return type.read(buf);
                return null;
            }

            @Override
            public void write(ByteBuf buf, T value) {
                buf.writeBoolean(value != null);
                if (value != null) type.write(buf, value);
            }
        };
    }

    static <T> NetType<T[]> fixedArray(final NetType<T> type, final Supplier<T[]> arraySupplier) {
        return new NetType<T[]>() {
            @Override
            public T[] read(ByteBuf buf) {
                T[] array = arraySupplier.get();
                for (int i = 0; i < array.length; i++) array[i] = type.read(buf);
                return array;
            }

            @Override
            public void write(ByteBuf buf, T[] value) {
                for (T t : value) type.write(buf, t);
            }
        };
    }

    static <C, T> NetType<T[]> array(final NetType<C> countType, final NetType<T> type, final Function<C, T[]> arrayConstructor, final Function<T[], C> lengthFunction) {
        return countType.postApply(
                (byteBuf, c) -> {
                    T[] array = arrayConstructor.apply(c);
                    for (int i = 0; i < array.length; i++) {
                        array[i] = type.read(byteBuf);
                    }
                    return array;
                },
                lengthFunction,
                (byteBuf, ts) -> {
                    for (T t : ts) {
                        type.write(byteBuf, t);
                    }
                }
        );
    }

    static <T> NetType<List<T>> list(final NetType<Integer> countType, final NetType<T> type, final int maxSize) {
        return countType.postApply(
                (buf, count) -> {
                    if (count > maxSize) throw new DecoderException("The received list size is larger than maximum allowed (" + count + " > " + maxSize + ")");
                    List<T> list = new ArrayList<>(count);
                    for (int i = 0; i < count; i++) list.add(type.read(buf));
                    return list;
                },
                List::size,
                (buf, value) -> {
                    if (value.size() > maxSize) throw new DecoderException("The list size is larger than maximum allowed (" + value.size() + " > " + maxSize + ")");
                    value.forEach(t -> type.write(buf, t));
                }
        );
    }

    static <K, V> NetType<Map<K, V>> map(final NetType<K> keyType, final NetType<V> valueType) {
        return map(keyType, valueType, Integer.MAX_VALUE);
    }

    static <K, V> NetType<Map<K, V>> map(final NetType<K> keyType, final NetType<V> valueType, final int maxSize) {
        return VAR_INT.verified(i -> {
            if (i > maxSize) throw new DecoderException("The received map size is larger than maximum allowed (" + i + " > " + maxSize + ")");
        }).postApply(
                (buf, count) -> {
                    Map<K, V> map = new HashMap<K, V>(count);
                    for (int i = 0; i < count; i++) {
                        K key = keyType.read(buf);
                        V value = valueType.read(buf);
                        map.put(key, value);
                    }
                    return map;
                },
                Map::size,
                (buf, value) -> value.forEach((k, v) -> {
                    keyType.write(buf, k);
                    valueType.write(buf, v);
                })
        );
    }

    static <T> NetType<T> recursive(final Function<NetType<T>, NetType<T>> factory) {
        return new NetType<T>() {
            private final NetType<T> type = factory.apply(this);

            @Override
            public T read(ByteBuf buf) {
                return this.type.read(buf);
            }

            @Override
            public void write(ByteBuf buf, T value) {
                this.type.write(buf, value);
            }
        };
    }

    static <C, T> NetType<T> conditional(final NetType<C> conditionType, final Predicate<C> readCondition, final Predicate<T> writeCondition, final NetType<T> falseType, final NetType<T> trueType) {
        return new NetType<T>() {
            @Override
            public T read(ByteBuf buf) {
                buf.markReaderIndex();
                C condition = conditionType.read(buf);
                buf.resetReaderIndex();
                if (readCondition.test(condition)) return trueType.read(buf);
                else return falseType.read(buf);
            }

            @Override
            public void write(ByteBuf buf, T value) {
                if (writeCondition.test(value)) trueType.write(buf, value);
                else falseType.write(buf, value);
            }
        };
    }


    default NetType<T> verified(final Predicate<T> check, final Supplier<RuntimeException> exceptionSupplier) {
        return this.verified(value -> {
            if (check.test(value)) throw exceptionSupplier.get();
        });
    }

    default NetType<T> verified(final Consumer<T> verifier) {
        return new NetType<T>() {
            @Override
            public T read(ByteBuf buf) {
                T value = NetType.this.read(buf);
                verifier.accept(value);
                return value;
            }

            @Override
            public void write(ByteBuf buf, T value) {
                verifier.accept(value);
                NetType.this.write(buf, value);
            }
        };
    }

    default <R> R applyRead(final ByteBuf buf, final Function<T, R> consumer) {
        return consumer.apply(this.read(buf));
    }

    default <O> NetType<O> map(final Function<T, O> mapper, final Function<O, T> reverseMapper) {
        return new NetType<O>() {
            @Override
            public O read(ByteBuf buf) {
                return mapper.apply(NetType.this.read(buf));
            }

            @Override
            public void write(ByteBuf buf, O value) {
                NetType.this.write(buf, reverseMapper.apply(value));
            }
        };
    }

    default <O> NetType<O> versionedMap(final Function<T, O> mapper, final Function<O, T> reverseMapper) {
        return new NetType<O>() {
            @Override
            public O read(ByteBuf buf) {
                return mapper.apply(NetType.this.read(buf));
            }

            @Override
            public void write(ByteBuf buf, O value) {
                NetType.this.write(buf, reverseMapper.apply(value));
            }
        };
    }

    default <O, D extends O> NetType<O> typedFlatMap(final Function<O, T> mapper, final Function<T, NetType<D>> codecMapper) {
        return new NetType<O>() {
            @Override
            public O read(ByteBuf buf) {
                T type = NetType.this.read(buf);
                return codecMapper.apply(type).read(buf);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void write(ByteBuf buf, O value) {
                T type = mapper.apply(value);
                NetType.this.write(buf, type);
                codecMapper.apply(type).write(buf, (D) value);
            }
        };
    }

    default <O> NetType<O> postApply(final BiFunction<ByteBuf, T, O> reader, final Function<O, T> mapper, final BiConsumer<ByteBuf, O> writer) {
        return new NetType<O>() {
            @Override
            public O read(ByteBuf buf) {
                T value = NetType.this.read(buf);
                return reader.apply(buf, value);
            }

            @Override
            public void write(ByteBuf buf, O value) {
                T mapped = mapper.apply(value);
                NetType.this.write(buf, mapped);
                writer.accept(buf, value);
            }
        };
    }

    default NetType<T> nonNull(final Supplier<T> nonNullSupplier) {
        return new NetType<T>() {
            @Override
            public T read(ByteBuf buf) {
                T value = NetType.this.read(buf);
                if (value == null) return nonNullSupplier.get();
                return value;
            }

            @Override
            public void write(ByteBuf buf, T value) {
                if (value == null) value = nonNullSupplier.get();
                NetType.this.write(buf, value);
            }
        };
    }

    default NetType<Optional<T>> asOptional() {
        return optional(this);
    }

    default NetType<T> asNullable() {
        return nullable(this);
    }

    default NetType<T[]> asFixedArray(final Supplier<T[]> arraySupplier) {
        return fixedArray(this, arraySupplier);
    }

    default NetType<List<T>> asList() {
        return this.asList(Integer.MAX_VALUE);
    }

    default NetType<List<T>> asList(final int maxSize) {
        return list(NetType.VAR_INT, this, maxSize);
    }

    default NetType<T> constant(final T constant) {
        return new NetType<T>() {
            @Override
            public T read(ByteBuf buf) {
                T value = NetType.this.read(buf);
                if (!value.equals(constant)) throw new DecoderException("The received value is not the expected constant value!");
                return value;
            }

            @Override
            public void write(ByteBuf buf, T value) {
                if (!value.equals(constant)) throw new DecoderException("The value is not the expected constant value!");
                NetType.this.write(buf, value);
            }
        };
    }

}
