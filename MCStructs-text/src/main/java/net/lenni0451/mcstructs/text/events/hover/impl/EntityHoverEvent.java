package net.lenni0451.mcstructs.text.events.hover.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.utils.Compatibility;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

/**
 * The implementation for entity hover events.
 */
@EqualsAndHashCode(callSuper = false)
public class EntityHoverEvent extends HoverEvent {

    private DataHolder data;

    public EntityHoverEvent(final TextComponent data) {
        super(HoverEventAction.SHOW_ENTITY);
        this.data = new LegacyRawHolder(data);
    }

    public EntityHoverEvent(final String type, @Nullable final String uuid, final String name) {
        super(HoverEventAction.SHOW_ENTITY);
        this.data = new LegacyHolder(name, type, uuid);
    }

    public EntityHoverEvent(final Identifier type, final UUID uuid, @Nullable final TextComponent name) {
        super(HoverEventAction.SHOW_ENTITY);
        this.data = new ModernHolder(type, uuid, name);
    }

    public boolean isLegacyRaw() {
        return this.data instanceof LegacyRawHolder;
    }

    public boolean isLegacy() {
        return this.data instanceof LegacyHolder;
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
            throw new UnsupportedOperationException("Data holder is not a legacy string holder: " + this.data);
        }
    }

    public LegacyHolder asLegacyHolder() {
        if (this.data instanceof LegacyHolder) {
            return (LegacyHolder) this.data;
        } else {
            throw new UnsupportedOperationException("Data holder is not a legacy holder: " + this.data);
        }
    }

    public ModernHolder asModernHolder() {
        if (this.data instanceof ModernHolder) {
            return (ModernHolder) this.data;
        } else {
            throw new UnsupportedOperationException("Data holder is not a modern holder: " + this.data);
        }
    }

    public EntityHoverEvent setData(final DataHolder data) {
        this.data = data;
        return this;
    }

    public EntityHoverEvent setLegacyRawData(final TextComponent data) {
        this.data = new LegacyRawHolder(data);
        return this;
    }

    public EntityHoverEvent setLegacyData(final String type, @Nullable final String uuid, final String name) {
        this.data = new LegacyHolder(name, type, uuid);
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
        Compatibility[] getCompatibility();
    }

    @Data
    @AllArgsConstructor
    public static class LegacyRawHolder implements DataHolder {
        private TextComponent data;

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
    public static class LegacyHolder implements DataHolder {
        private String name;
        @Nullable
        private String type;
        private String id;

        @Override
        public Compatibility[] getCompatibility() {
            return Compatibility.ranged(Compatibility.V1_7, Compatibility.V1_15);
        }

        @Override
        public String toString() {
            return ToString.of(this)
                    .add("name", this.name)
                    .add("type", this.type, Objects::nonNull)
                    .add("id", this.id)
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
        public Compatibility[] getCompatibility() {
            return Compatibility.ranged(Compatibility.V1_16, null);
        }

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
