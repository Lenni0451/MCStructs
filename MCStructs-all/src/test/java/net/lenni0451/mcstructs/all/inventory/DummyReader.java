package net.lenni0451.mcstructs.all.inventory;

import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

/**
 * A dummy reader. You will obviously have to implement your own reader.
 */
public class DummyReader {

    public DummyReader(final byte[] data) {
    }

    public boolean readBoolean() {
        return false;
    }

    public int readUnsignedByte() {
        return 0;
    }

    public int readInt() {
        return 0;
    }

    public LegacyItemStack<Integer> readItem() {
        return null;
    }

}
