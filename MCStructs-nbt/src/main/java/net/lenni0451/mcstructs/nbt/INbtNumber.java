package net.lenni0451.mcstructs.nbt;

/**
 * An interface for all numeric Nbt tags.
 */
public interface INbtNumber extends INbtTag {

    /**
     * @return The value of this tag as a byte
     */
    byte byteValue();

    /**
     * @return The value of this tag as a short
     */
    short shortValue();

    /**
     * @return The value of this tag as an int
     */
    int intValue();

    /**
     * @return The value of this tag as a long
     */
    long longValue();

    /**
     * @return The value of this tag as a float
     */
    float floatValue();

    /**
     * @return The value of this tag as a double
     */
    double doubleValue();

    /**
     * @return The value of this tag as a {@link Number}
     */
    Number numberValue();

}
