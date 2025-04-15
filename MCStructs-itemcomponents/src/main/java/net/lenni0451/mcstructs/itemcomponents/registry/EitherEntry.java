package net.lenni0451.mcstructs.itemcomponents.registry;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.converter.model.Result;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class EitherEntry<T> {

    public static <T> Codec<EitherEntry<T>> codec(final Registry registry, final Codec<T> codec) {
        return Codec.either(
                codec,
                registry.entryCodec().flatMap((Function<RegistryEntry, Result<RegistryEntry>>) Result::success, registryEntry -> Result.error("Cannot parse entry without registry"))
        ).map(EitherEntry::either, EitherEntry::new);
    }


    private final T value;
    private final RegistryEntry entry;

    public EitherEntry(final Either<T, RegistryEntry> either) {
        if (either.isLeft()) {
            this.value = either.getLeft();
            this.entry = null;
        } else {
            this.value = null;
            this.entry = either.getRight();
        }
    }

    public EitherEntry(@Nonnull final T value) {
        this.value = value;
        this.entry = null;
    }

    public EitherEntry(@Nonnull final RegistryEntry entry) {
        this.value = null;
        this.entry = entry;
    }

    public Either<T, RegistryEntry> either() {
        if (this.value != null) {
            return Either.left(this.value);
        } else {
            return Either.right(this.entry);
        }
    }

}
