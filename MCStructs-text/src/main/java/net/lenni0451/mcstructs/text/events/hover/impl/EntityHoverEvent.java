package net.lenni0451.mcstructs.text.events.hover.impl;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtSerializeException;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

/**
 * The implementation for entity hover events.
 */
public class EntityHoverEvent extends HoverEvent {

    private Identifier entityType;
    private UUID uuid;
    @Nullable
    private TextComponent name;

    public EntityHoverEvent(final Identifier entityType, final UUID uuid, @Nullable final TextComponent name) {
        this(HoverEventAction.SHOW_ENTITY, entityType, uuid, name);
    }

    public EntityHoverEvent(final HoverEventAction action, final Identifier entityType, final UUID uuid, @Nullable final TextComponent name) {
        super(action);
        this.entityType = entityType;
        this.uuid = uuid;
        this.name = name;
    }

    /**
     * @return The entity type of this hover event
     */
    public Identifier getEntityType() {
        return this.entityType;
    }

    /**
     * Set the entity type of this hover event.
     *
     * @param entityType The new entity type
     * @return This instance for chaining
     */
    public EntityHoverEvent setEntityType(final Identifier entityType) {
        this.entityType = entityType;
        return this;
    }

    /**
     * @return The uuid of this hover event
     */
    public UUID getUuid() {
        return this.uuid;
    }

    /**
     * Set the uuid of this hover event.
     *
     * @param uuid The new uuid
     * @return This instance for chaining
     */
    public EntityHoverEvent setUuid(final UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * @return The name of this hover event
     */
    @Nullable
    public TextComponent getName() {
        return this.name;
    }

    /**
     * Set the name of this hover event.
     *
     * @param name The new name
     * @return This instance for chaining
     */
    public EntityHoverEvent setName(@Nullable final TextComponent name) {
        this.name = name;
        return this;
    }

    @Override
    @Deprecated
    public TextHoverEvent toLegacy(TextComponentSerializer textComponentSerializer, SNbt<?> sNbt) {
        CompoundTag tag = new CompoundTag();
        tag.addString("type", this.entityType.getValue());
        tag.addString("id", this.uuid.toString());
        tag.addString("name", textComponentSerializer.serialize(this.name == null ? new StringComponent("") : this.name));
        try {
            return new TextHoverEvent(this.getAction(), new StringComponent(sNbt.serialize(tag)));
        } catch (SNbtSerializeException e) {
            throw new RuntimeException("This should never happen! Please report to the developer immediately!", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityHoverEvent that = (EntityHoverEvent) o;
        return Objects.equals(this.entityType, that.entityType) && Objects.equals(this.uuid, that.uuid) && Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.entityType, this.uuid, this.name);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("entityType", this.entityType)
                .add("uuid", this.uuid)
                .add("name", this.name, Objects::nonNull)
                .toString();
    }

}
