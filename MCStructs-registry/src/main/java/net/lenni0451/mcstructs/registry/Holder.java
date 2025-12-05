package net.lenni0451.mcstructs.registry;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.core.utils.ToString;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * A class that represents either a {@link RegistryEntry} or a value of type {@code T}.
 *
 * @param <T> The type of the value
 * @see Either
 */
@EqualsAndHashCode(doNotUseGetters = true)
public class Holder<T> {

    public static <T> Codec<Holder<T>> fileCodec(final Registry registry, final Codec<T> codec) {
        return Codec.either(codec, registry.entryCodec()).map(Either::swap, Either::swap).map(Holder::either, Holder::new);
    }

    public static <T> Codec<Holder<T>> fixedCodec(final Registry registry) {
        return Codec.either(Codec.<T>failing("Can't encode/decode value in fixed codec for registry " + registry.getName()), registry.entryCodec()).map(Either::swap, Either::swap).map(Holder::either, Holder::new);
    }


    private final RegistryEntry entry;
    private final T value;

    public Holder(final Either<RegistryEntry, T> either) {
        if (either.isLeft()) {
            this.entry = either.getLeft();
            this.value = null;
        } else {
            this.entry = null;
            this.value = either.getRight();
        }
    }

    public Holder(@Nonnull final RegistryEntry entry) {
        this.entry = entry;
        this.value = null;
    }

    public Holder(@Nonnull final T value) {
        this.entry = null;
        this.value = value;
    }

    /**
     * @return If this either is a {@link RegistryEntry}
     */
    public boolean isEntry() {
        return this.entry != null;
    }

    /**
     * @return The {@link RegistryEntry}
     * @throws IllegalStateException If this either is not a {@link RegistryEntry}
     */
    public RegistryEntry getEntry() {
        if (this.entry == null) throw new IllegalStateException("Either is not an entry");
        return this.entry;
    }

    /**
     * @return If this either is a value
     */
    public boolean isValue() {
        return this.value != null;
    }

    /**
     * @return The value
     * @throws IllegalStateException If this either is not a value
     */
    public T getValue() {
        if (this.value == null) throw new IllegalStateException("Either is not a value");
        return this.value;
    }

    /**
     * Convert this either to a {@link Either}.
     *
     * @return The either
     */
    public Either<RegistryEntry, T> either() {
        if (this.entry != null) {
            return Either.left(this.entry);
        } else {
            return Either.right(this.value);
        }
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("entry", this.entry, Objects::nonNull)
                .add("value", this.value, Objects::nonNull)
                .toString();
    }

}
