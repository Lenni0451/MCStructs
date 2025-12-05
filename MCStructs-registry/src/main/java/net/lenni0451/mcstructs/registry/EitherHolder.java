package net.lenni0451.mcstructs.registry;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.core.utils.ToString;

@EqualsAndHashCode
public class EitherHolder<T> {

    public static <T> Codec<EitherHolder<T>> fixedCodec(final Registry registry) {
        return codec(registry, Holder.fixedCodec(registry));
    }

    public static <T> Codec<EitherHolder<T>> fileCodec(final Registry registry, final Codec<T> codec) {
        return codec(registry, Holder.fileCodec(registry, codec));
    }

    public static <T> Codec<EitherHolder<T>> codec(final Registry registry, final Codec<Holder<T>> codec) {
        return Codec.either(codec, ResourceKey.codec(registry).flatMap(Result::success, key -> Result.<ResourceKey>error("Cannot parse as key without registry"))).map(EitherHolder::either, EitherHolder::new);
    }


    private final Either<Holder<T>, ResourceKey> either;

    public EitherHolder(final Holder<T> holder) {
        this.either = Either.left(holder);
    }

    public EitherHolder(final ResourceKey resourceKey) {
        this.either = Either.right(resourceKey);
    }

    public EitherHolder(final Either<Holder<T>, ResourceKey> either) {
        this.either = either;
    }

    public Either<Holder<T>, ResourceKey> either() {
        return this.either;
    }

    @Override
    public String toString() {
        return ToString.of(EitherHolder.class)
                .add("either", this.either)
                .toString();
    }

}
