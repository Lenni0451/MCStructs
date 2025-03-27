package net.lenni0451.mcstructs.text.events.hover.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
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
@EqualsAndHashCode(callSuper = false)
public class ItemHoverEvent extends HoverEvent {

    private DataHolder data;

    public ItemHoverEvent(final DataHolder data) {
        super(HoverEventAction.SHOW_ITEM);
        this.data = data;
    }

    public ItemHoverEvent(final String legacyData) {
        super(HoverEventAction.SHOW_ITEM);
        this.data = new LegacyHolder(legacyData);
    }

    public ItemHoverEvent(final Identifier id, final int count, @Nullable final CompoundTag tag) {
        super(HoverEventAction.SHOW_ITEM);
        this.data = new ModernHolder(id, count, tag);
    }

    public DataHolder getData() {
        return this.data;
    }

    public ItemHoverEvent setData(final String data) {
        this.data = new LegacyHolder(data);
        return this;
    }

    public boolean isLegacy() {
        return this.data instanceof LegacyHolder;
    }

    public boolean isModern() {
        return this.data instanceof ModernHolder;
    }

    public LegacyHolder asLegacy() {
        if (this.data instanceof LegacyHolder) {
            return (LegacyHolder) this.data;
        } else {
            throw new UnsupportedOperationException("Data holder is not a legacy raw holder: " + this.data);
        }
    }

    public ModernHolder asModern() {
        if (this.data instanceof ModernHolder) {
            return (ModernHolder) this.data;
        } else {
            throw new UnsupportedOperationException("Data holder is not a modern holder: " + this.data);
        }
    }

    public ItemHoverEvent setLegacyData(final String data) {
        this.data = new LegacyHolder(data);
        return this;
    }

    public ItemHoverEvent setModernData(final Identifier id, final int count, @Nullable final CompoundTag tag) {
        this.data = new ModernHolder(id, count, tag);
        return this;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("data", this.data)
                .toString();
    }


    public interface DataHolder {
    }

    @Data
    @AllArgsConstructor
    public static class LegacyHolder implements DataHolder {
        private String data;

        @Override
        public String toString() {
            return ToString.of(this)
                    .add("data", this.data)
                    .toString();
        }
    }

    @Data
    @AllArgsConstructor
    public static class ModernHolder implements DataHolder {
        private Identifier id;
        private int count;
        @Nullable
        private CompoundTag tag;

        @Override
        public String toString() {
            return ToString.of(this)
                    .add("id", this.id)
                    .add("count", this.count, c -> c != 1)
                    .add("tag", this.tag, Objects::nonNull)
                    .toString();
        }
    }

}
