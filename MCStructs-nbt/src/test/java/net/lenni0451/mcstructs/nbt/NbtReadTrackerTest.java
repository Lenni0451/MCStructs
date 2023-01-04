package net.lenni0451.mcstructs.nbt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NbtReadTrackerTest {

    @Test
    void unlimited() {
        assertDoesNotThrow(() -> NbtReadTracker.unlimited().read(Integer.MAX_VALUE));
    }

    @Test
    void pushDepth() {
        NbtReadTracker tracker = new NbtReadTracker();
        for (int i = 0; i < 1024; i++) {
            try {
                tracker.pushDepth();
            } catch (IllegalStateException e) {
                assertEquals(512, i);
                break;
            }
        }
    }

    @Test
    void read() {
        assertThrows(IllegalStateException.class, () -> new NbtReadTracker().read(Integer.MAX_VALUE));
    }

}