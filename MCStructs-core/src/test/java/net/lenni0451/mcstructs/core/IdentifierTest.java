package net.lenni0451.mcstructs.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentifierTest {

    @Test
    void defaultNamespace() {
        assertEquals(Identifier.of("test"), Identifier.defaultNamespace("test"));
        assertNotEquals(Identifier.of("other:test"), Identifier.defaultNamespace("test"));
        assertThrows(IllegalArgumentException.class, () -> Identifier.defaultNamespace("a:b"));
    }

    @Test
    void of() {
        assertThrows(IllegalArgumentException.class, () -> Identifier.of("a:b:c"));
        assertThrows(IllegalArgumentException.class, () -> Identifier.of("a\0:b"));
        assertThrows(IllegalArgumentException.class, () -> Identifier.of("a", "b:c"));
        assertEquals(Identifier.of("a", "b"), Identifier.of("a:b"));
        assertEquals(Identifier.of(":"), Identifier.of("minecraft", ""));
        assertEquals(Identifier.of(":test"), Identifier.of("minecraft", "test"));
        assertEquals(Identifier.of("test:"), Identifier.of("test", ""));
    }

    @Test
    void tryOf() {
        assertNull(Identifier.tryOf("a:b:c"));
        assertNull(Identifier.tryOf("a\0:b"));
        assertEquals(Identifier.of("a", "b"), Identifier.tryOf("a:b"));
    }

}
