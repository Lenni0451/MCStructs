package net.lenni0451.mcstructs.inventory.impl.v1_7;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.enums.DraggingState;
import net.lenni0451.mcstructs.inventory.enums.InventoryAction;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.types.AContainer;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemTag;

import java.util.ArrayList;
import java.util.List;

public abstract class AContainer_v1_7<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends AContainer<T, I, S> {

    private DraggingState draggingState = DraggingState.NOT_DRAGGING;
    private int draggingButton;
    private final List<Slot<T, I, S>> draggingSlots = new ArrayList<>();

    public AContainer_v1_7(final int windowId) {
        super(windowId);
    }

    @Override
    public S click(InventoryHolder<T, I, S> inventoryHolder, int slotId, int button, InventoryAction action) {
        T playerInventory = inventoryHolder.getPlayerInventory();
        S oldCursorStack = null;
        if (InventoryAction.DRAG.equals(action)) {
            DraggingState oldDraggingState = this.draggingState;
            this.draggingState = DraggingState.getDraggingState(button);
            if ((!DraggingState.DRAGGING.equals(oldDraggingState) || !DraggingState.FINISHED_DRAGGING.equals(this.draggingState)) && !oldDraggingState.equals(this.draggingState)) {
                this.resetDraggingState();
            } else if (playerInventory.getCursorStack() == null) {
                this.resetDraggingState();
            } else if (DraggingState.NOT_DRAGGING.equals(this.draggingState)) {
                this.draggingButton = DraggingState.getButton(button);
                if (DraggingState.isDragging(this.draggingButton)) {
                    this.draggingState = DraggingState.DRAGGING;
                    this.draggingSlots.clear();
                } else {
                    this.resetDraggingState();
                }
            } else if (DraggingState.DRAGGING.equals(this.draggingState)) {
                Slot<T, I, S> slot = this.getSlot(slotId);
                if (this.isValidDraggingSlot(slot, playerInventory.getCursorStack(), this.draggingSlots.size())) this.draggingSlots.add(slot);
            } else if (DraggingState.FINISHED_DRAGGING.equals(this.draggingState)) {
                if (!this.draggingSlots.isEmpty()) {
                    S cursorStack = playerInventory.getCursorStack().copy();
                    int cursorCount = cursorStack.getCount();
                    for (Slot<T, I, S> slot : this.draggingSlots) {
                        if (this.isValidDraggingSlot(slot, cursorStack, this.draggingSlots.size() - 1)) {
                            S newStack = cursorStack.copy();
                            int oldSlotCount = slot.getStack() == null ? 0 : slot.getStack().getCount();

                            if (this.draggingButton == 0) newStack.setCount((int) Math.floor((float) newStack.getCount() / this.draggingSlots.size()));
                            else if (this.draggingButton == 1) newStack.setCount(1);
                            newStack.setCount(newStack.getCount() + oldSlotCount);

                            if (newStack.getCount() > newStack.getMeta().maxCount()) newStack.setCount(newStack.getMeta().maxCount());
                            if (newStack.getCount() > slot.acceptsCount(newStack)) newStack.setCount(slot.acceptsCount(newStack));
                            cursorCount -= newStack.getCount() - oldSlotCount;
                            slot.setStack(newStack);
                        }
                    }
                    cursorStack.setCount(cursorCount);
                    if (cursorStack.getCount() <= 0) playerInventory.setCursorStack(null);
                    else playerInventory.setCursorStack(cursorStack);
                }
                this.resetDraggingState();
            } else {
                this.resetDraggingState();
            }
        } else if (!DraggingState.NOT_DRAGGING.equals(this.draggingState)) {
            this.resetDraggingState();
        } else if ((InventoryAction.TAKE.equals(action) || InventoryAction.QUICK_MOVE.equals(action)) && (button == 0 || button == 1)) {
            if (slotId == -999) {
                if (playerInventory.getCursorStack() != null) {
                    if (button == 0) {
                        //drop all items
                        playerInventory.setCursorStack(null);
                    } else {
                        //drop one item
                        playerInventory.getCursorStack().setCount(playerInventory.getCursorStack().getCount() - 1);
                        if (playerInventory.getCursorStack().getCount() == 0) playerInventory.setCursorStack(null);
                    }
                }
            } else {
                if (slotId < 0) return null;
                Slot<T, I, S> slot = this.getSlot(slotId);
                if (InventoryAction.QUICK_MOVE.equals(action)) {
                    if (slot != null && slot.canTake(inventoryHolder)) {
                        S movedStack = this.moveStack(inventoryHolder, slotId);
                        if (movedStack != null) {
                            oldCursorStack = movedStack.copy();
                            if (slot.getStack() != null && slot.getStack().getItem().equals(movedStack.getItem())) this.click(inventoryHolder, slotId, button);
                        }
                    }
                } else {
                    if (slot != null) {
                        S slotStack = slot.getStack();
                        S cursorStack = playerInventory.getCursorStack();
                        if (slotStack != null) oldCursorStack = slotStack.copy();
                        if (slotStack == null) {
                            if (cursorStack != null && slot.accepts(cursorStack)) {
                                int putCount = button == 0 ? cursorStack.getCount() : 1;
                                if (putCount > slot.acceptsCount(cursorStack)) putCount = slot.acceptsCount(cursorStack);
                                if (cursorStack.getCount() >= putCount) slot.setStack(cursorStack.split(putCount));
                                if (cursorStack.getCount() == 0) playerInventory.setCursorStack(null);
                            }
                        } else if (slot.canTake(inventoryHolder)) {
                            if (cursorStack == null) {
                                int takeCount = button == 0 ? slotStack.getCount() : (slotStack.getCount() + 1) / 2;
                                S leftStack = ((IInventory_v1_7<I, S>) slot.getInventory()).split(slot.getInventoryIndex(), takeCount);
                                playerInventory.setCursorStack(leftStack);
                                if (slotStack.getCount() == 0) slot.setStack(null);
                                slot.onTake(inventoryHolder, playerInventory.getCursorStack());
                            } else if (slot.accepts(cursorStack)) {
                                if (this.isSameItem(slotStack, cursorStack) && this.isSameTag(slotStack, cursorStack)) {
                                    int putCount = button == 0 ? cursorStack.getCount() : 1;
                                    if (putCount > slot.acceptsCount(slotStack) - slotStack.getCount()) putCount = slot.acceptsCount(slotStack) - slotStack.getCount();
                                    if (putCount > cursorStack.getMeta().maxCount() - slotStack.getCount()) putCount = cursorStack.getMeta().maxCount() - slotStack.getCount();
                                    cursorStack.split(putCount);
                                    if (cursorStack.getCount() == 0) playerInventory.setCursorStack(null);
                                    slotStack.setCount(slotStack.getCount() + putCount);
                                } else if (cursorStack.getCount() <= slot.acceptsCount(cursorStack)) {
                                    slot.setStack(cursorStack);
                                    playerInventory.setCursorStack(slotStack);
                                }
                            } else if (this.isSameItem(slotStack, cursorStack) && cursorStack.getMeta().maxCount() > 1 && (!slotStack.getMeta().tags().contains(ItemTag.SUBTYPES) || slotStack.getDamage() == cursorStack.getDamage()) && this.isSameTag(slotStack, cursorStack)) {
                                int takeCount = slotStack.getCount();
                                if (takeCount > 0 && takeCount + cursorStack.getCount() <= cursorStack.getMeta().maxCount()) {
                                    cursorStack.setCount(cursorStack.getCount() + takeCount);
                                    slotStack = ((IInventory_v1_7<I, S>) slot.getInventory()).split(slot.getInventoryIndex(), takeCount);
                                    if (slotStack.getCount() == 0) slot.setStack(null);
                                    slot.onTake(inventoryHolder, playerInventory.getCursorStack());
                                }
                            }
                        }
                        slot.onUpdate();
                    }
                }
            }
        } else if (InventoryAction.SWAP.equals(action) && button >= 0 && button <= 8) {
            Slot<T, I, S> slot = this.getSlot(slotId);
            if (slot.canTake(inventoryHolder)) {
                S hotbarStack = playerInventory.getStack(button);
                boolean canSwap = hotbarStack == null || (slot.getInventory().equals(playerInventory) && slot.accepts(hotbarStack));
                int alternativeSlotId = -1;
                if (!canSwap) {
                    alternativeSlotId = playerInventory.getEmptySlot();
                    canSwap = alternativeSlotId > -1;
                }
                if (slot.getStack() != null && canSwap) {
                    S slotStack = slot.getStack();
                    playerInventory.setStack(button, slotStack.copy());
                    if (hotbarStack != null && (!slot.getInventory().equals(playerInventory) || !slot.accepts(hotbarStack))) {
                        if (alternativeSlotId > -1) {
                            playerInventory.addStack(inventoryHolder, hotbarStack);
                            ((IInventory_v1_7<I, S>) slot.getInventory()).split(slot.getInventoryIndex(), slotStack.getCount());
                            slot.setStack(null);
                            slot.onTake(inventoryHolder, slotStack);
                        }
                    } else {
                        ((IInventory_v1_7<I, S>) slot.getInventory()).split(slot.getInventoryIndex(), slotStack.getCount());
                        slot.setStack(hotbarStack);
                        slot.onTake(inventoryHolder, slotStack);
                    }
                } else if (slot.getStack() == null && hotbarStack != null && slot.accepts(hotbarStack)) {
                    playerInventory.setStack(button, null);
                    slot.setStack(hotbarStack);
                }
            }
        } else if (InventoryAction.COPY.equals(action) && inventoryHolder.isCreativeMode() && playerInventory.getCursorStack() == null && slotId >= 0) {
            Slot<T, I, S> slot = this.getSlot(slotId);
            if (slot != null && slot.getStack() != null && slot.canTake(inventoryHolder)) {
                S copiedStack = slot.getStack().copy();
                copiedStack.setCount(copiedStack.getMeta().maxCount());
                playerInventory.setCursorStack(copiedStack);
            }
        } else if (InventoryAction.THROW.equals(action) && playerInventory.getCursorStack() == null && slotId >= 0) {
            Slot<T, I, S> slot = this.getSlot(slotId);
            if (slot != null && slot.getStack() != null && slot.canTake(inventoryHolder)) {
                S droppedStack = ((IInventory_v1_7<I, S>) slot.getInventory()).split(slot.getInventoryIndex(), button == 0 ? 1 : slot.getStack().getCount());
                slot.onTake(inventoryHolder, droppedStack);
                //drop droppedStack
            }
        } else if (InventoryAction.TAKE_ALL.equals(action) && slotId >= 0) {
            Slot<T, I, S> slot = this.getSlot(slotId);
            S cursorStack = playerInventory.getCursorStack();
            if (cursorStack != null && (slot == null || slot.getStack() == null || !slot.canTake(inventoryHolder))) {
                int startSlot = button == 0 ? 0 : this.getSlotCount() - 1;
                int increment = button == 0 ? 1 : -1;
                for (int i = 0; i < 2; i++) {
                    for (int id = startSlot; id >= 0 && id < this.getSlotCount() && cursorStack.getCount() < cursorStack.getMeta().maxCount(); id += increment) {
                        Slot<T, I, S> slotAtId = this.getSlot(id);
                        if (slotAtId.getStack() != null && this.hasSlotSpace(slotAtId, cursorStack) && slotAtId.canTake(inventoryHolder) && this.canTakeAll(slotAtId, cursorStack) && (i != 0 || slotAtId.getStack().getCount() != slotAtId.getStack().getMeta().maxCount())) {
                            int takeCount = Math.min(cursorStack.getMeta().maxCount() - cursorStack.getCount(), slotAtId.getStack().getCount());
                            S newSlotStack = ((IInventory_v1_7<I, S>) slotAtId.getInventory()).split(slotAtId.getInventoryIndex(), takeCount);
                            cursorStack.setCount(cursorStack.getCount() + takeCount);
                            if (newSlotStack.getCount() <= 0) slotAtId.setStack(null);
                            slotAtId.onTake(inventoryHolder, newSlotStack);
                        }
                    }
                }
            }
        }
        return oldCursorStack;
    }

    protected void click(final InventoryHolder<T, I, S> inventoryHolder, final int slotId, final int button) {
        this.click(inventoryHolder, slotId, button, InventoryAction.QUICK_MOVE);
    }

    protected abstract S moveStack(final InventoryHolder<T, I, S> inventoryHolder, final int slotId);

    protected boolean canTakeAll(final Slot<T, I, S> slot, final S stack) {
        return true;
    }

    protected boolean mergeStack(final S stack, final int startId, final int endId, final boolean reverse) {
        boolean success = false;
        int currentSlotId = startId;
        if (reverse) currentSlotId = endId - 1;
        if (this.isStackable(stack)) {
            while (stack.getCount() > 0 && ((!reverse && currentSlotId < endId) || (reverse && currentSlotId >= startId))) {
                Slot<T, I, S> slot = this.getSlot(currentSlotId);
                S slotStack = slot.getStack();
                if (slotStack != null && this.isSameItem(slotStack, stack) && (!stack.getMeta().tags().contains(ItemTag.SUBTYPES) || stack.getDamage() == slotStack.getDamage()) && this.isSameTag(slotStack, stack)) {
                    int mergedCount = slotStack.getCount() + stack.getCount();
                    if (mergedCount <= stack.getMeta().maxCount()) {
                        stack.setCount(0);
                        slotStack.setCount(mergedCount);
                        slot.onUpdate();
                        success = true;
                    } else if (slotStack.getCount() < stack.getMeta().maxCount()) {
                        stack.setCount(stack.getCount() - (stack.getMeta().maxCount() - slotStack.getCount()));
                        slotStack.setCount(stack.getMeta().maxCount());
                        slot.onUpdate();
                        success = true;
                    }
                }
                if (reverse) currentSlotId--;
                else currentSlotId++;
            }
        }
        if (stack.getCount() > 0) {
            if (reverse) currentSlotId = endId - 1;
            else currentSlotId = startId;
            while ((!reverse && currentSlotId < endId) || (reverse && currentSlotId >= startId)) {
                Slot<T, I, S> slot = this.getSlot(currentSlotId);
                S slotStack = slot.getStack();
                if (slotStack == null) {
                    slot.setStack(stack.copy());
                    slot.onUpdate();
                    stack.setCount(0);
                    success = true;
                    break;
                }
                if (reverse) currentSlotId--;
                else currentSlotId++;
            }
        }
        return success;
    }

    private void resetDraggingState() {
        this.draggingState = DraggingState.NOT_DRAGGING;
        this.draggingSlots.clear();
    }

    private boolean isValidDraggingSlot(final Slot<T, I, S> slot, final S stack, final int minCount) {
        return slot != null && this.hasSlotSpace(slot, stack) && slot.accepts(stack) && stack.getCount() > minCount/*TODO: canDragIntoSlot check*/;
    }

    private boolean hasSlotSpace(final Slot<T, I, S> slot, final S stack) {
        if (slot == null || slot.getStack() == null) return true;
        if (!this.isSameItem(slot.getStack(), stack) || !this.isSameTag(slot.getStack(), stack)) return false;
        return slot.getStack().getCount() <= stack.getMeta().maxCount();
    }

    private boolean isSameItem(final S stack1, final S stack2) {
        return stack1.getItem().equals(stack2.getItem()) && stack1.getDamage() == stack2.getDamage();
    }

    private boolean isSameTag(final S stack1, final S stack2) {
        if (stack1 == null && stack2 == null) return true;
        if (stack1 == null || stack2 == null) return false;
        if (stack1.getTag() == null && stack2.getTag() == null) return true;
        if (stack1.getTag() == null || stack2.getTag() == null) return false;
        return stack1.getTag().equals(stack2.getTag());
    }

    private boolean isStackable(final S stack) {
        return stack.getMeta().maxCount() > 1 && (!stack.getMeta().tags().contains(ItemTag.DAMAGEABLE) || stack.getDamage() <= 0);
    }

}
