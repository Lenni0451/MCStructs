package net.lenni0451.mcstructs.text.events.hover.impl;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.events.hover.AHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;

import java.util.Objects;

/**
 * The implementation for item hover events.
 */
public class ItemHoverEvent extends AHoverEvent {

    private final Identifier item;
    private final int count;
    private final CompoundTag nbt;

    public ItemHoverEvent(final HoverEventAction action, final Identifier item, final int count, final CompoundTag nbt) {
        super(action);

        this.item = item;
        this.count = count;
        this.nbt = nbt;
    }

    /**
     * @return The item of this hover event
     */
    public Identifier getItem() {
        return this.item;
    }

    /**
     * @return The count of this hover event
     */
    public int getCount() {
        return this.count;
    }

    /**
     * @return The nbt of this hover event
     */
    public CompoundTag getNbt() {
        return this.nbt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemHoverEvent that = (ItemHoverEvent) o;
        return count == that.count && getAction() == that.getAction() && Objects.equals(item, that.item) && Objects.equals(nbt, that.nbt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAction(), item, count, nbt);
    }

    @Override
    public String toString() {
        return "ItemHoverEvent{action=" + getAction() + ", item=" + item + ", count=" + count + ", nbt=" + nbt + "}";
    }

}
