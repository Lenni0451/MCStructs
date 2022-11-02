package net.lenni0451.mcstructs.inventory.enums;

public enum DraggingState {

    NOT_DRAGGING,
    DRAGGING,
    FINISHED_DRAGGING,
    INVALID;

    public static int getButton(final int buttonAndDragState) {
        return (buttonAndDragState >> 2) & 3;
    }

    public static DraggingState getDraggingState(final int buttonAndDragState) {
        return DraggingState.values()[buttonAndDragState & 3];
    }

    public static int setDraggingState(final DraggingState draggingState, final int button) {
        return (draggingState.ordinal() & 3) | ((button & 3) << 2);
    }

    public static boolean isDragging(final int button) {
        return button == 0 || button == 1;
    }

}
