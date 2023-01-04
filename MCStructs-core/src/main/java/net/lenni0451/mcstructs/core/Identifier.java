package net.lenni0451.mcstructs.core;

import java.util.Objects;

/**
 * The identifier used for registries in minecraft.
 */
public class Identifier {

    public static final String VALID_KEY_CHARS = "[_\\-a-z.]+";
    public static final String VALID_VALUE_CHARS = "[_\\-a-z/.]+";

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
        String[] parts = value.split(":");
        if (parts.length == 1) return of("minecraft", parts[0]);
        else if (parts.length == 2) return of(parts[0], parts[1]);
        else throw new IllegalArgumentException("The given value has to be splittable by :");
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
     * @param key The key of the identifier
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
