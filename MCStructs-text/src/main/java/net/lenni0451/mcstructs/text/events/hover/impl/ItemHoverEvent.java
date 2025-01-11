package net.lenni0451.mcstructs.text.events.hover.impl;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * The implementation for item hover events.
 */
@EqualsAndHashCode
public class ItemHoverEvent extends HoverEvent {

    private Identifier item;
    private int count;
    @Nullable
    private CompoundTag nbt;

    public ItemHoverEvent(final Identifier item, final int count, @Nullable final CompoundTag nbt) {
        super(HoverEventAction.SHOW_ITEM);
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
     * Set the item of this hover event.
     *
     * @param item The new item
     * @return This instance for chaining
     */
    public ItemHoverEvent setItem(final Identifier item) {
        this.item = item;
        return this;
    }

    /**
     * @return The count of this hover event
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Set the count of this hover event.
     *
     * @param count The new count
     * @return This instance for chaining
     */
    public ItemHoverEvent setCount(final int count) {
        this.count = count;
        return this;
    }

    /**
     * @return The nbt of this hover event
     */
    @Nullable
    public CompoundTag getNbt() {
        return this.nbt;
    }

    /**
     * Set the nbt of this hover event.
     *
     * @param nbt The new nbt
     * @return This instance for chaining
     */
    public ItemHoverEvent setNbt(@Nullable final CompoundTag nbt) {
        this.nbt = nbt;
        return this;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("item", this.item)
                .add("count", this.count, count -> count != 1)
                .add("nbt", this.nbt, Objects::nonNull)
                .toString();
    }

}
