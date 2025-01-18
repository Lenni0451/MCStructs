package net.lenni0451.mcstructs.text.serializer.verify;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.DelegatingConverter;
import net.lenni0451.mcstructs.converter.model.Result;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class VerifyingConverter<T> extends DelegatingConverter<T> {

    public static <T, V extends TextVerifier> BiFunction<DataConverter<?>, T, Result<Void>> verify(final Class<V> verifierType, final BiPredicate<V, T> predicate, final String message) {
        return verify(verifierType, (type, verifier) -> {
            if (predicate.test(verifier, type)) return null;
            else return Result.error(message + ": " + type);
        });
    }

    public static <T, V extends TextVerifier> BiFunction<DataConverter<?>, T, Result<Void>> verify(final Class<V> verifierType, final BiFunction<T, V, Result<Void>> verifier) {
        return (converter, type) -> {
            if (!(converter instanceof VerifyingConverter)) return null; //Return valid if not a verifying converter
            VerifyingConverter<?> verifyingConverter = (VerifyingConverter<?>) converter;
            if (!verifierType.isInstance(verifyingConverter.textVerifier)) {
                throw new IllegalStateException("Invalid verifier. Expected " + verifierType.getName() + " but got " + verifyingConverter.textVerifier.getClass().getName());
            }
            return verifier.apply(type, (V) verifyingConverter.textVerifier);
        };
    }

    public static <T, V extends TextVerifier> boolean isValid(final DataConverter<?> converter, final T type, final Class<V> verifierType, final BiPredicate<V, T> predicate) {
        if (!(converter instanceof VerifyingConverter)) return true; //Return valid if not a verifying converter
        VerifyingConverter<?> verifyingConverter = (VerifyingConverter<?>) converter;
        if (!verifierType.isInstance(verifyingConverter.textVerifier)) {
            throw new IllegalStateException("Invalid verifier. Expected " + verifierType.getName() + " but got " + verifyingConverter.textVerifier.getClass().getName());
        }
        return predicate.test((V) verifyingConverter.textVerifier, type);
    }


    private final TextVerifier textVerifier;

    public VerifyingConverter(final DataConverter<T> delegate, final TextVerifier textVerifier) {
        super(delegate instanceof VerifyingConverter ? ((VerifyingConverter<T>) delegate).getDelegate() : delegate);
        this.textVerifier = textVerifier;
    }

    public TextVerifier getTextVerifier() {
        return this.textVerifier;
    }

}
