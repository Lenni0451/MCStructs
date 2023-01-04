package net.lenni0451.mcstructs.inventory.enums;

/**
 * The inventory actions when clicking on a slot.
 */
public enum InventoryAction {

    /**
     * Left/Right click with/without item in cursor
     */
    TAKE,
    /**
     * Shift left/right click
     */
    QUICK_MOVE,
    /**
     * Left/Right click with item in cursor<br>
     * Pressing 1-9 without item in cursor
     */
    SWAP,
    /**
     * Middle click in creative
     */
    COPY,
    /**
     * Q
     */
    THROW,
    /**
     * Left/Right dragging with item in cursor
     */
    DRAG,
    /**
     * Double left click without item in cursor
     */
    TAKE_ALL

}
