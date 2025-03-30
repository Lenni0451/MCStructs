package net.lenni0451.mcstructs.networkcodec;

import io.netty.buffer.ByteBuf;
import sun.security.ssl.ProtocolVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.*;

public class NetSerializer<I> {

    private final List<Serializer<I>> serializers = new ArrayList<>();

    public <T> NetSerializer<I> and(final NetType<T> type, final Function<I, T> getter, final BiConsumer<I, T> setter) {
        this.serializers.add(new BaseSerializer<>(type, getter, setter));
        return this;
    }

    public <T> NetSerializer<I> versioned(final NetType<T> type, final Function<I, T> getter, final BiConsumer<I, T> setter) {
        this.serializers.add(new VersionedSerializer<>(type, getter, setter));
        return this;
    }

    public <T> NetSerializer<I> cond(final NetType<T> type, final Function<I, T> getter, final BiConsumer<I, T> setter, final Predicate<I> predicate) {
        this.serializers.add(new ConditionalSerializer<>(type, getter, setter, predicate));
        return this;
    }

    public <T> NetSerializer<I> direct(final BiConsumer<I, ByteBuf> reader, final BiConsumer<I, ByteBuf> writer) {
        this.serializers.add(new DirectSerializer<>(reader, writer));
        return this;
    }

    public NetType<I> build(final Supplier<I> constructor) {
        return new NetPipeline<>(constructor, this.serializers);
    }


    private interface Serializer<I> {
        void read(final I instance, final ByteBuf buf);

        void write(final I instance, final ByteBuf buf);
    }

    private static final class BaseSerializer<I, T> implements Serializer<I> {
        private final NetType<T> netType;
        private final Function<I, T> getter;
        private final BiConsumer<I, T> setter;

        private BaseSerializer(NetType<T> netType, Function<I, T> getter, BiConsumer<I, T> setter) {
            this.netType = netType;
            this.getter = getter;
            this.setter = setter;
        }

        @Override
        public void read(final I instance, final ByteBuf buf) {
            this.setter.accept(instance, this.netType.read(buf));
        }

        @Override
        public void write(final I instance, final ByteBuf buf) {
            this.netType.write(buf, this.getter.apply(instance));
        }

        public NetType<T> netType() {return netType;}

        public Function<I, T> getter() {return getter;}

        public BiConsumer<I, T> setter() {return setter;}

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            BaseSerializer that = (BaseSerializer) obj;
            return Objects.equals(this.netType, that.netType) &&
                Objects.equals(this.getter, that.getter) &&
                Objects.equals(this.setter, that.setter);
        }

        @Override
        public int hashCode() {
            return Objects.hash(netType, getter, setter);
        }

        @Override
        public String toString() {
            return "BaseSerializer[" +
                "netType=" + netType + ", " +
                "getter=" + getter + ", " +
                "setter=" + setter + ']';
        }

    }

    private static final class VersionedSerializer<I, T> implements Serializer<I> {
        private final NetType<T> netType;
        private final Function<I, T> getter;
        private final BiConsumer<I, T> setter;

        private VersionedSerializer(NetType<T> netType, Function<I, T> getter, BiConsumer<I, T> setter) {
            this.netType = netType;
            this.getter = getter;
            this.setter = setter;
        }

        @Override
        public void read(final I instance, final ByteBuf buf) {
            this.setter.accept(instance, this.netType.read(buf));
        }

        @Override
        public void write(final I instance, final ByteBuf buf) {
            this.netType.write(buf, this.getter.apply(instance));
        }

        public NetType<T> netType() {return netType;}

        public Function<I, T> getter() {return getter;}

        public BiConsumer<I, T> setter() {return setter;}
    }

    private static final class ConditionalSerializer<I, T> implements Serializer<I> {
        private final NetType<T> netType;
        private final Function<I, T> getter;
        private final BiConsumer<I, T> setter;
        private final Predicate<I> predicate;

        private ConditionalSerializer(NetType<T> netType, Function<I, T> getter, BiConsumer<I, T> setter, Predicate<I> predicate) {
            this.netType = netType;
            this.getter = getter;
            this.setter = setter;
            this.predicate = predicate;
        }

        @Override
        public void read(final I instance, final ByteBuf buf) {
            if (!this.predicate.test(instance)) return;
            this.setter.accept(instance, this.netType.read(buf));
        }

        @Override
        public void write(final I instance, final ByteBuf buf) {
            if (!this.predicate.test(instance)) return;
            this.netType.write(buf, this.getter.apply(instance));
        }

        public NetType<T> netType() {return netType;}

        public Function<I, T> getter() {return getter;}

        public BiConsumer<I, T> setter() {return setter;}

        public Predicate<I> predicate() {return predicate;}
    }

    private static final class DirectSerializer<I, T> implements Serializer<I> {
        private final BiConsumer<I, ByteBuf> reader;
        private final BiConsumer<I, ByteBuf> writer;

        private DirectSerializer(BiConsumer<I, ByteBuf> reader, BiConsumer<I, ByteBuf> writer) {
            this.reader = reader;
            this.writer = writer;
        }

        @Override
        public void read(final I instance, final ByteBuf buf) {
            this.reader.accept(instance, buf);
        }

        @Override
        public void write(final I instance, final ByteBuf buf) {
            this.writer.accept(instance, buf);
        }

        public BiConsumer<I, ByteBuf> reader() {return reader;}

        public BiConsumer<I, ByteBuf> writer() {return writer;}
    }

    private static final class NetPipeline<I> implements NetType<I> {
        private final Supplier<I> constructor;
        private final List<Serializer<I>> serializers;

        private NetPipeline(Supplier<I> constructor, List<Serializer<I>> serializers) {
            this.constructor = constructor;
            this.serializers = serializers;
        }

        @Override
        public I read(ByteBuf buf) {
            I instance = this.constructor.get();
            for (Serializer<I> serializer : this.serializers) serializer.read(instance, buf);
            return instance;
        }

        @Override
        public void write(ByteBuf buf, I value) {
            for (Serializer<I> serializer : this.serializers) serializer.write(value, buf);
        }

        public Supplier<I> constructor() {return constructor;}

        public List<Serializer<I>> serializers() {return serializers;}
    }
}
