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
@EqualsAndHashCode
public class EitherEntry<T> {

    /**
     * Create a codec for this class bound to the given registry.
     *
     * @param registry The registry owning the entry
     * @param codec    The codec for the value
     * @param <T>      The type of the value
     * @return The codec for this class
     */
    public static <T> Codec<EitherEntry<T>> codec(final Registry registry, final Codec<T> codec) {
        return Codec.either(codec, registry.entryCodec()).map(Either::swap, Either::swap).map(EitherEntry::either, EitherEntry::new);
    }


    private final RegistryEntry entry;
    private final T value;

    public EitherEntry(final Either<RegistryEntry, T> either) {
        if (either.isLeft()) {
            this.entry = either.getLeft();
            this.value = null;
        } else {
            this.entry = null;
            this.value = either.getRight();
        }
    }

    public EitherEntry(@Nonnull final RegistryEntry entry) {
        this.entry = entry;
        this.value = null;
    }

    public EitherEntry(@Nonnull final T value) {
        this.entry = null;
        this.value = value;
    }

    /**
     * @return If this either is a {@link RegistryEntry}
     */
    public boolean isLeft() {
        return this.entry != null;
    }

    /**
     * @return The {@link RegistryEntry}
     * @throws IllegalStateException If this either is not a {@link RegistryEntry}
     */
    public RegistryEntry getLeft() {
        if (this.entry == null) throw new IllegalStateException("Either is not left");
        return this.entry;
    }

    /**
     * @return If this either is a value
     */
    public boolean isRight() {
        return this.value != null;
    }

    /**
     * @return The value
     * @throws IllegalStateException If this either is not a value
     */
    public T getRight() {
        if (this.value == null) throw new IllegalStateException("Either is not right");
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
