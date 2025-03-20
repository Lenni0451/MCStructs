package net.lenni0451.mcstructs.nbt;

import net.lenni0451.mcstructs.nbt.exceptions.NbtReadException;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NbtReadTrackerTest {

    @Test
    void unlimitedDepth() {
        assertDoesNotThrow(() -> NbtReadTracker.unlimitedDepth().read(Integer.MAX_VALUE));
    }

    @Test
    void pushDepth() {
        NbtReadTracker tracker = new NbtReadTracker();
        for (int i = 0; i < 1024; i++) {
            try {
                tracker.pushDepth();
            } catch (NbtReadException e) {
                assertEquals(512, i);
                break;
            }
        }
    }

    @Test
    void read() {
        assertThrows(NbtReadException.class, () -> new NbtReadTracker().read(Integer.MAX_VALUE));
    }

}
