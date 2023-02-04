package net.lenni0451.mcstructs.text.events.hover.impl;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtSerializeException;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.events.hover.AHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

import java.util.Objects;
import java.util.UUID;

/**
 * The implementation for entity hover events.
 */
public class EntityHoverEvent extends AHoverEvent {

    private final Identifier entityType;
    private final UUID uuid;
    private final ATextComponent name;

    public EntityHoverEvent(final HoverEventAction action, final Identifier entityType, final UUID uuid, final ATextComponent name) {
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
     * @return The uuid of this hover event
     */
    public UUID getUuid() {
        return this.uuid;
    }

    /**
     * @return The name of this hover event
     */
    public ATextComponent getName() {
        return this.name;
    }

    @Override
    public TextHoverEvent toLegacy(TextComponentSerializer textComponentSerializer, SNbtSerializer<?> sNbtSerializer) {
        CompoundTag tag = new CompoundTag();
        tag.addString("type", this.entityType.getValue());
        tag.addString("id", this.uuid.toString());
        tag.addString("name", textComponentSerializer.serialize(this.name));
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
        EntityHoverEvent that = (EntityHoverEvent) o;
        return getAction() == that.getAction() && Objects.equals(entityType, that.entityType) && Objects.equals(uuid, that.uuid) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAction(), entityType, uuid, name);
    }

    @Override
    public String toString() {
        return "EntityHoverEvent{action=" + getAction() + ", entityType=" + entityType + ", uuid=" + uuid + ", name=" + name + "}";
    }

}
