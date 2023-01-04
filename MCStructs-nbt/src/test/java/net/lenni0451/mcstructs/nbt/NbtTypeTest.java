package net.lenni0451.mcstructs.nbt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NbtTypeTest {

    @Test
    void byName() {
        for (int i = 0; i < NbtType.values().length; i++) {
            NbtType type = NbtType.values()[i];
            assertEquals(type, NbtType.byName(type.name()));
        }
        assertNull(NbtType.byName("not found"));
    }

    @Test
    void byId() {
        for (int i = 0; i < NbtType.values().length; i++) assertEquals(NbtType.values()[i], NbtType.byId(i));
        assertNull(NbtType.byId(-1));
    }

    @Test
    void byClass() {
        for (int i = 0; i < NbtType.values().length; i++) {
            NbtType type = NbtType.values()[i];
            assertEquals(type, NbtType.byClass(type.getTagClass()));
        }
        assertNotNull(NbtType.byClass(null));
    }

    @Test
    void byDataType() {
        for (int i = 0; i < NbtType.values().length; i++) {
            NbtType type = NbtType.values()[i];
            assertEquals(type, NbtType.byDataType(type.getDataType()));
        }
        assertNull(NbtType.byDataType(NbtTypeTest.class));
    }

    @Test
    void newInstance() {
        assertThrows(IllegalStateException.class, NbtType.END::newInstance);
        for (int i = 1; i < NbtType.values().length; i++) {
            NbtType type = NbtType.values()[i];
            assertInstanceOf(type.getTagClass(), type.newInstance());
        }
    }

    @Test
    void isNumber() {
        for (int i = 0; i < NbtType.values().length; i++) {
            NbtType type = NbtType.values()[i];
            if (i >= 1 && i <= 6) assertTrue(type.isNumber());
            else assertFalse(type.isNumber());
        }
    }

}