package net.lenni0451.mcstructs.text.translation;

import javax.annotation.Nullable;

@FunctionalInterface
public interface Translator {

    BasicTranslator GLOBAL = new BasicTranslator();

    /**
     * Translate the given key.<br>
     * Return {@code null} if the key is not found.
     *
     * @param key The key to translate
     * @return The translated key or {@code null}
     */
    @Nullable
    String translate(final String key);

    /**
     * Translate the given key.<br>
     * If the key is not found return the key itself.
     *
     * @param key The key to translate
     * @return The translated key or the key itself
     */
    default String translateOrKey(final String key) {
        String translation = this.translate(key);
        return translation != null ? translation : key;
    }

}
