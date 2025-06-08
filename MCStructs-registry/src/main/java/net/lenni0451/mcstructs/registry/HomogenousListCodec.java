package net.lenni0451.mcstructs.registry;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Either;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class HomogenousListCodec<T> {

    public static <T> Codec<List<T>> codec(final Codec<T> entryCodec, final boolean requireList) {
        Codec<List<T>> listCodec = entryCodec.listOf();
        if (requireList) {
            return listCodec;
        } else {
            return Codec.either(listCodec, entryCodec)
                    .map(registryEntries -> {
                        if (registryEntries.size() == 1) {
                            return Either.right(registryEntries.get(0));
                        } else {
                            return Either.left(registryEntries);
                        }
                    }, either -> either.xmap(Function.identity(), Arrays::asList));
        }
    }

}
