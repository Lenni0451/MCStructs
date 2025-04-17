package net.lenni0451.mcstructs.itemcomponents.registry;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.core.utils.ToString;

import javax.annotation.Nonnull;
import java.util.Objects;

@EqualsAndHashCode
public class EitherEntry<T> {

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

    public boolean isLeft() {
        return this.value != null;
    }

    public T getLeft() {
        if (this.value == null) throw new IllegalStateException("Either is not left");
        return this.value;
    }

    public boolean isRight() {
        return this.entry != null;
    }

    public RegistryEntry getRight() {
        if (this.entry == null) throw new IllegalStateException("Either is not right");
        return this.entry;
    }

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
