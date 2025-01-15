package net.lenni0451.mcstructs.text.events.hover.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.utils.Compatibility;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * The implementation for item hover events.
 */
@EqualsAndHashCode(callSuper = false)
public class ItemHoverEvent extends HoverEvent {

    private DataHolder data;

    public ItemHoverEvent(final String data) {
        super(HoverEventAction.SHOW_ITEM);
        this.data = new LegacyRawHolder(data);
    }

    public ItemHoverEvent(final short id, final byte count, final short damage, @Nullable final CompoundTag tag) {
        super(HoverEventAction.SHOW_ITEM);
        this.data = new LegacyShortHolder(id, count, damage, tag);
    }

    public ItemHoverEvent(final String id, final byte count, final short damage, @Nullable final CompoundTag tag) {
        super(HoverEventAction.SHOW_ITEM);
        this.data = new LegacyStringHolder(id, count, damage, tag);
    }

    public ItemHoverEvent(final Identifier id, final int count, @Nullable final CompoundTag tag) {
        super(HoverEventAction.SHOW_ITEM);
        this.data = new ModernHolder(id, count, tag);
    }

    public boolean isLegacyRaw() {
        return this.data instanceof LegacyRawHolder;
    }

    public boolean isLegacyShort() {
        return this.data instanceof LegacyShortHolder;
    }

    public boolean isLegacyString() {
        return this.data instanceof LegacyStringHolder;
    }

    public boolean isModern() {
        return this.data instanceof ModernHolder;
    }

    public DataHolder getData() {
        return this.data;
    }

    public LegacyRawHolder asLegacyRawHolder() {
        if (this.data instanceof LegacyRawHolder) {
            return (LegacyRawHolder) this.data;
        } else {
            throw new UnsupportedOperationException("Data holder is not a legacy raw holder: " + this.data);
        }
    }

    public LegacyShortHolder asLegacyShortHolder() {
        if (this.data instanceof LegacyShortHolder) {
            return (LegacyShortHolder) this.data;
        } else {
            throw new UnsupportedOperationException("Data holder is not a legacy short holder: " + this.data);
        }
    }

    public LegacyStringHolder asLegacyStringHolder() {
        if (this.data instanceof LegacyStringHolder) {
            return (LegacyStringHolder) this.data;
        } else {
            throw new UnsupportedOperationException("Data holder is not a legacy string holder: " + this.data);
        }
    }

    public ModernHolder asModernHolder() {
        if (this.data instanceof ModernHolder) {
            return (ModernHolder) this.data;
        } else {
            throw new UnsupportedOperationException("Data holder is not a modern holder: " + this.data);
        }
    }

    public ItemHoverEvent setData(final String data) {
        this.data = new LegacyRawHolder(data);
        return this;
    }

    public ItemHoverEvent setLegacyRawData(final String data) {
        this.data = new LegacyRawHolder(data);
        return this;
    }

    public ItemHoverEvent setLegacyShortData(final short id, final byte count, final short damage, @Nullable final CompoundTag tag) {
        this.data = new LegacyShortHolder(id, count, damage, tag);
        return this;
    }

    public ItemHoverEvent setLegacyStringData(final String id, final byte count, final short damage, @Nullable final CompoundTag tag) {
        this.data = new LegacyStringHolder(id, count, damage, tag);
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
        Compatibility[] getCompatibility();
    }

    @Data
    @AllArgsConstructor
    public static class LegacyRawHolder implements DataHolder {
        private String data;

        @Override
        public Compatibility[] getCompatibility() {
            return Compatibility.ranged(Compatibility.V1_7, Compatibility.V1_15);
        }

        @Override
        public String toString() {
            return ToString.of(this)
                    .add("data", this.data)
                    .toString();
        }
    }

    @Data
    @AllArgsConstructor
    public static class LegacyShortHolder implements DataHolder {
        private short id;
        private byte count;
        private short damage;
        @Nullable
        private CompoundTag tag;

        @Override
        public Compatibility[] getCompatibility() {
            return Compatibility.ranged(Compatibility.V1_7, Compatibility.V1_8);
        }

        @Override
        public String toString() {
            return ToString.of(this)
                    .add("id", this.id)
                    .add("count", this.count, c -> c != 1)
                    .add("damage", this.damage, d -> d != 0)
                    .add("tag", this.tag, Objects::nonNull)
                    .toString();
        }
    }

    @Data
    @AllArgsConstructor
    public static class LegacyStringHolder implements DataHolder {
        private String id;
        private byte count;
        private short damage;
        @Nullable
        private CompoundTag tag;

        @Override
        public Compatibility[] getCompatibility() {
            return Compatibility.ranged(Compatibility.V1_8, Compatibility.V1_15);
        }

        @Override
        public String toString() {
            return ToString.of(this)
                    .add("id", this.id)
                    .add("count", this.count, c -> c != 1)
                    .add("damage", this.damage, d -> d != 0)
                    .add("tag", this.tag, Objects::nonNull)
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
        public Compatibility[] getCompatibility() {
            return Compatibility.ranged(Compatibility.V1_16, null);
        }

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
