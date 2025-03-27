package net.lenni0451.mcstructs.text.translation;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BasicTranslator implements Translator {

    private final Map<String, String> translations;

    public BasicTranslator() {
        this(new HashMap<>());
    }

    public BasicTranslator(final Map<String, String> translations) {
        this.translations = translations;
    }

    public Map<String, String> getTranslations() {
        return Collections.unmodifiableMap(this.translations);
    }

    public synchronized void addTranslation(final String key, final String value) {
        this.translations.put(key, value);
    }

    public synchronized void removeTranslation(final String key) {
        this.translations.remove(key);
    }

    @Override
    @Nullable
    public String translate(String key) {
        return this.translations.get(key);
    }

    @Override
    public String translateOrKey(String key) {
        return this.translations.getOrDefault(key, key);
    }

}
