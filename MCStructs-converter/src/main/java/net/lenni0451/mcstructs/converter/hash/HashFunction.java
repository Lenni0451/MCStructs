package net.lenni0451.mcstructs.converter.hash;

import net.lenni0451.mcstructs.converter.hash.function.CRC32C;

public abstract class HashFunction {

    public static final HashFunction CRC32C = new CRC32C();

    public abstract HashCode hash(final byte[] data);

    public HashBuilder builder() {
        return new HashBuilder(this);
    }

    public HashBuilder builder(final int initialSize) {
        return new HashBuilder(this, initialSize);
    }

}
