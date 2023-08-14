package net.lenni0451.mcstructs.text.events.hover.impl;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtSerializeException;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.events.hover.AHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

import javax.annotation.Nullable;
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
    @Nullable
    public CompoundTag getNbt() {
        return this.nbt;
    }

    @Override
    public TextHoverEvent toLegacy(TextComponentSerializer textComponentSerializer, SNbtSerializer<?> sNbtSerializer) {
        CompoundTag tag = new CompoundTag();
        tag.addString("id", this.item.getValue());
        tag.addByte("Count", (byte) this.count);
        if (this.nbt != null) tag.addCompound("tag", this.nbt);
        try {
            return new TextHoverEvent(this.getAction(), new StringComponent(sNbtSerializer.serialize(tag)));
        } catch (SNbtSerializeException e) {
            throw new RuntimeException("This should never happen! Please report to the developer immediately!", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemHoverEvent that = (ItemHoverEvent) o;
        return this.count == that.count && this.getAction() == that.getAction() && Objects.equals(this.item, that.item) && Objects.equals(this.nbt, that.nbt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getAction(), this.item, this.count, this.nbt);
    }

    @Override
    public String toString() {
        return "ItemHoverEvent{action=" + this.getAction() + ", item=" + this.item + ", count=" + this.count + ", nbt=" + this.nbt + "}";
    }

}
