package net.lenni0451.mcstructs.text.serializer.legacy;

import net.lenni0451.mcstructs.snbt.SNbt;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class SerializerMap<R, A extends Enum<A>, IO> {

    public static <R, A extends Enum<A>, IO> Builder<R, A, IO> create(final SNbt<?> sNbt) {
        return new Builder<>(sNbt);
    }


    private final SNbt<?> sNbt;
    private final List<EventSerializer<R, ? extends R, A, IO>> serializers;
    private final Map<A, List<EventSerializer<R, ? extends R, A, IO>>> serializerMap; //For faster action based access
    private final Function<R, A> toActionFunction;
    @Nullable
    private final FallbackSerializer<R, IO> fallbackSerializer;
    @Nullable
    private final FallbackDeserializer<IO, R, A> fallbackDeserializer;

    private SerializerMap(final SNbt<?> sNbt, final List<EventSerializer<R, ? extends R, A, IO>> serializers, final Function<R, A> toActionFunction, @Nullable final FallbackSerializer<R, IO> fallbackSerializer, @Nullable final FallbackDeserializer<IO, R, A> fallbackDeserializer) {
        this.sNbt = sNbt;
        this.serializers = serializers;
        this.toActionFunction = toActionFunction;
        this.fallbackSerializer = fallbackSerializer;
        this.fallbackDeserializer = fallbackDeserializer;

        if (serializers.isEmpty()) {
            this.serializerMap = Collections.emptyMap();
        } else {
            Map<A, List<EventSerializer<R, ? extends R, A, IO>>> map = new HashMap<>();
            for (EventSerializer<R, ? extends R, A, IO> serializer : serializers) {
                map.computeIfAbsent(serializer.getAction(), k -> new ArrayList<>()).add(serializer);
            }
            this.serializerMap = new EnumMap<>(map);
        }
    }

    public IO serialize(final R event) {
        A action = this.toActionFunction.apply(event);
        List<EventSerializer<R, ? extends R, A, IO>> serializers = this.serializerMap.get(action);
        if (serializers == null || serializers.isEmpty()) throw new UnsupportedOperationException("No serializer found for " + event);
        for (EventSerializer<R, ? extends R, A, IO> serializer : serializers) {
            if (serializer.matches(event)) {
                try {
                    return serializer.serialize(this.sNbt, this.cast(event));
                } catch (Throwable ignored) {
                }
            }
        }
        if (this.fallbackSerializer != null) {
            IO fallback = this.fallbackSerializer.serialize(this.sNbt, event);
            if (fallback != null) return fallback;
        }
        throw new UnsupportedOperationException("No serializer found for " + event);
    }

    public R deserialize(final A action, final IO value) {
        List<EventSerializer<R, ? extends R, A, IO>> serializers = this.serializerMap.get(action);
        if (serializers == null || serializers.isEmpty()) return null; //Unknown actions are skipped
        for (EventSerializer<R, ? extends R, A, IO> serializer : serializers) {
            if (serializer.matches(action)) {
                try {
                    return serializer.deserialize(this.sNbt, value);
                } catch (Throwable ignored) {
                }
            }
        }
        if (this.fallbackDeserializer != null) {
            R fallback = this.fallbackDeserializer.deserialize(this.sNbt, action, value);
            if (fallback != null) return fallback;
        }
        throw new UnsupportedOperationException("No serializer found for " + action + " - " + value);
    }

    private <X> X cast(final Object o) {
        return (X) o;
    }


    public static class Builder<R, A extends Enum<A>, IO> {
        private final SNbt<?> sNbt;
        private final List<EventSerializer<R, ? extends R, A, IO>> serializers = new ArrayList<>();

        protected Builder(final SNbt<?> sNbt) {
            this.sNbt = sNbt;
        }

        public Builder<R, A, IO> add(final EventSerializer<R, ? extends R, A, IO> serializer) {
            this.serializers.add(serializer);
            return this;
        }

        public SerializerMap<R, A, IO> finalize(final Function<R, A> toActionFunction, @Nullable final FallbackSerializer<R, IO> fallbackSerializer, @Nullable final FallbackDeserializer<IO, R, A> fallbackDeserializer) {
            return new SerializerMap<>(this.sNbt, this.serializers, toActionFunction, fallbackSerializer, fallbackDeserializer);
        }
    }

    @FunctionalInterface
    public interface FallbackSerializer<I, O> {
        @Nullable
        O serialize(final SNbt<?> sNbt, final I value);
    }

    @FunctionalInterface
    public interface FallbackDeserializer<I, O, A extends Enum<A>> {
        @Nullable
        O deserialize(final SNbt<?> sNbt, final A action, final I value);
    }

}
