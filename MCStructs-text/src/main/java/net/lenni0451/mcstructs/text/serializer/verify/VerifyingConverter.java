package net.lenni0451.mcstructs.text.serializer.verify;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.DelegatingConverter;
import net.lenni0451.mcstructs.converter.model.Result;

import java.util.function.BiFunction;

public class VerifyingConverter<T> extends DelegatingConverter<T> {

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


    private final TextVerifier textVerifier;

    public VerifyingConverter(final DataConverter<T> delegate, final TextVerifier textVerifier) {
        super(delegate);
        this.textVerifier = textVerifier;
    }

    public TextVerifier getTextVerifier() {
        return this.textVerifier;
    }

}
