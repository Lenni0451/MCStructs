package net.lenni0451.mcstructs.text.events.hover.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

/**
 * The implementation for entity hover events.
 */
@EqualsAndHashCode(callSuper = false)
public class EntityHoverEvent extends HoverEvent {

    private DataHolder data;

    public EntityHoverEvent(final DataHolder data) {
        super(HoverEventAction.SHOW_ENTITY);
        this.data = data;
    }

    public EntityHoverEvent(final TextComponent legacyData) {
        super(HoverEventAction.SHOW_ENTITY);
        this.data = new LegacyHolder(legacyData);
    }

    public EntityHoverEvent(final Identifier type, final UUID uuid, @Nullable final TextComponent name) {
        super(HoverEventAction.SHOW_ENTITY);
        this.data = new ModernHolder(type, uuid, name);
    }

    public DataHolder getData() {
        return this.data;
    }

    public EntityHoverEvent setData(final DataHolder data) {
        this.data = data;
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
            throw new UnsupportedOperationException("Data holder is not a legacy string holder: " + this.data);
        }
    }

    public ModernHolder asModern() {
        if (this.data instanceof ModernHolder) {
            return (ModernHolder) this.data;
        } else {
            throw new UnsupportedOperationException("Data holder is not a modern holder: " + this.data);
        }
    }

    public EntityHoverEvent setLegacyData(final TextComponent data) {
        this.data = new LegacyHolder(data);
        return this;
    }

    public EntityHoverEvent setModernData(final Identifier type, final UUID uuid, @Nullable final TextComponent name) {
        this.data = new ModernHolder(type, uuid, name);
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
        private TextComponent data;

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
        private Identifier type;
        private UUID uuid;
        @Nullable
        private TextComponent name;

        @Override
        public String toString() {
            return ToString.of(this)
                    .add("type", this.type)
                    .add("uuid", this.uuid)
                    .add("name", this.name, Objects::nonNull)
                    .toString();
        }
    }

}
