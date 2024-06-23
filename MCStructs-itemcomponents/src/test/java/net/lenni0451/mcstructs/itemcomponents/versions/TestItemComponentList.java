package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentList;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class TestItemComponentList {

    @Test
    void testIterator() {
        ItemComponentList itemComponents = ItemComponentRegistry.LATEST.getComponentList();
        int counted = 0;
        Iterator<ItemComponent<?>> it = itemComponents.iterator();
        while (it.hasNext()) {
            assertNotNull(it.next());
            counted++;
        }
        assertEquals(itemComponents.size(), counted);
        assertFalse(it.hasNext());
        assertThrows(UnsupportedOperationException.class, it::remove);
        assertThrows(IndexOutOfBoundsException.class, it::next);
    }

}
