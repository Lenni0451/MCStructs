package net.lenni0451.mcstructs.text.events.hover.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;

import javax.annotation.Nullable;

@EqualsAndHashCode(callSuper = false)
public class LegacyHoverEvent extends HoverEvent {

    private LegacyData data;

    public LegacyHoverEvent(final HoverEventAction action, final LegacyData data) {
        super(action);
        this.data = data;
    }

    /**
     * Set the action of the hover event.
     *
     * @param action The new action
     * @return This instance for chaining
     */
    public LegacyHoverEvent setAction(final HoverEventAction action) {
        this.action = action;
        return this;
    }

    /**
     * @return The data of this hover event
     */
    public LegacyData getData() {
        return this.data;
    }

    /**
     * Set the data of this hover event.
     *
     * @param data The new data
     * @return This instance for chaining
     */
    public LegacyHoverEvent setData(final LegacyData data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("data", this.data)
                .toString();
    }


    public interface LegacyData {
    }

    @Data
    @AllArgsConstructor
    public static class LegacyInvalidData implements LegacyData {
        private TextComponent raw;
    }

    @Data
    @AllArgsConstructor
    public static class LegacyIntItemData implements LegacyData {
        private short id;
        private byte count;
        private short damage;
        private CompoundTag tag;
    }

    @Data
    @AllArgsConstructor
    public static class LegacyStringItemData implements LegacyData {
        private String id;
        private byte count;
        private short damage;
        private CompoundTag tag;
    }

    @Data
    @AllArgsConstructor
    public static class LegacyEntityData implements LegacyData {
        private String name;
        @Nullable
        private String type;
        private String id; //UUID#toString()
    }

}
