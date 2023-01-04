package net.lenni0451.mcstructs.inventory.enums;

/**
 * The dragging state of a container.
 */
public enum DraggingState {

    NOT_DRAGGING,
    DRAGGING,
    FINISHED_DRAGGING,
    INVALID;


    /**
     * Get the button from the given button and dragging state.
     *
     * @param buttonAndDragState The button and dragging state
     * @return The button
     */
    public static int getButton(final int buttonAndDragState) {
        return (buttonAndDragState >> 2) & 3;
    }

    /**
     * Get the dragging state from the given button and dragging state.
     *
     * @param buttonAndDragState The button and dragging state
     * @return The dragging state
     */
    public static DraggingState getDraggingState(final int buttonAndDragState) {
        return DraggingState.values()[buttonAndDragState & 3];
    }

    /**
     * Append the dragging state to the given button.
     *
     * @param draggingState The dragging state
     * @param button        The button
     * @return The button and dragging state
     */
    public static int setDraggingState(final DraggingState draggingState, final int button) {
        return (draggingState.ordinal() & 3) | ((button & 3) << 2);
    }

    /**
     * Check if the button can be used for dragging.
     *
     * @param button The button
     * @return True if the button can be used for dragging
     */
    public static boolean isDragging(final int button) {
        return button == 0 || button == 1;
    }

}
