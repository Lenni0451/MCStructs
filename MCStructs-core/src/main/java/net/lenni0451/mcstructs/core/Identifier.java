package net.lenni0451.mcstructs.core;

import lombok.EqualsAndHashCode;

/**
 * The identifier used for registries in minecraft.
 */
@EqualsAndHashCode
public class Identifier {

    public static final String DEFAULT_NAMESPACE = "minecraft";
    public static final String VALID_KEY_CHARS = "[_\\-a-z0-9.]*";
    public static final String VALID_VALUE_CHARS = "[_\\-a-z0-9/.]*";

    /**
     * Create a new identifier with the default namespace {@code "minecraft"}.
     *
     * @param value The value of the identifier
     * @return The created identifier
     */
    public static Identifier defaultNamespace(final String value) {
        return of(DEFAULT_NAMESPACE, value);
    }

    /**
     * Create a new identifier.<br>
     * If {@code value} contains a colon, it will be split into {@code namespace} and {@code value}.<br>
     * If {@code value} does not contain a colon, {@code namespace} will be set to {@code "minecraft"}.
     *
     * @param value The value of the identifier
     * @return The created identifier
     * @throws IllegalArgumentException If the value is not a valid identifier
     */
    public static Identifier of(final String value) {
        int splitIndex = value.indexOf(':');
        String key = splitIndex <= 0 ? DEFAULT_NAMESPACE : value.substring(0, splitIndex);
        String val = splitIndex == -1 ? value : value.substring(splitIndex + 1);
        return of(key, val);
    }

    /**
     * Try to create a new identifier.<br>
     * If {@code value} contains a colon, it will be split into {@code namespace} and {@code value}.<br>
     * If {@code value} does not contain a colon, {@code namespace} will be set to {@code "minecraft"}.
     *
     * @param value The value of the identifier
     * @return The created identifier or null if the value is not a valid identifier
     */
    public static Identifier tryOf(final String value) {
        try {
            return of(value);
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * Create a new identifier from key and value.
     *
     * @param key   The key of the identifier
     * @param value The value of the identifier
     * @return The created identifier
     * @throws IllegalArgumentException If the key or value is not valid
     */
    public static Identifier of(final String key, final String value) {
        return new Identifier(key, value);
    }


    private final String key;
    private final String value;

    private Identifier(final String key, final String value) {
        if (!key.matches(VALID_KEY_CHARS)) throw new IllegalArgumentException("Key contains illegal chars");
        if (!value.matches(VALID_VALUE_CHARS)) throw new IllegalArgumentException("Value contains illegal chars");

        this.key = key;
        this.value = value;
    }

    /**
     * @return The {code key:value} of the identifier
     */
    public String get() {
        return this.key + ":" + this.value;
    }

    /**
     * @return The key of the identifier
     */
    public String getKey() {
        return this.key;
    }

    /**
     * @return The value of the identifier
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Check if the key and value are equal to the given key and value.
     *
     * @param key   The key to check
     * @param value The value to check
     * @return If the key and value are equal
     */
    public boolean equals(final String key, final String value) {
        return this.key.equals(key) && this.value.equals(value);
    }

    @Override
    public String toString() {
        return this.get();
    }

}
