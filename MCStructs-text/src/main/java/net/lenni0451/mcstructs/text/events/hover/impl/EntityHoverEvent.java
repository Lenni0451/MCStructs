package net.lenni0451.mcstructs.text.events.hover.impl;

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
@EqualsAndHashCode
public class EntityHoverEvent extends HoverEvent {

    private Identifier entityType;
    private UUID uuid;
    @Nullable
    private TextComponent name;

    public EntityHoverEvent(final Identifier entityType, final UUID uuid, @Nullable final TextComponent name) {
        super(HoverEventAction.SHOW_ENTITY);
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
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("entityType", this.entityType)
                .add("uuid", this.uuid)
                .add("name", this.name, Objects::nonNull)
                .toString();
    }

}
