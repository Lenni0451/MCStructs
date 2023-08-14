package net.lenni0451.mcstructs.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentifierTest {

    @Test
    void of() {
        assertThrows(IllegalArgumentException.class, () -> Identifier.of("a:b:c"));
        assertThrows(IllegalArgumentException.class, () -> Identifier.of("a\0:b"));
        assertThrows(IllegalArgumentException.class, () -> Identifier.of("a", "b:c"));
        assertEquals(Identifier.of("a", "b"), Identifier.of("a:b"));
    }

    @Test
    void tryOf() {
        assertNull(Identifier.tryOf("a:b:c"));
        assertNull(Identifier.tryOf("a\0:b"));
        assertEquals(Identifier.of("a", "b"), Identifier.tryOf("a:b"));
    }

}
