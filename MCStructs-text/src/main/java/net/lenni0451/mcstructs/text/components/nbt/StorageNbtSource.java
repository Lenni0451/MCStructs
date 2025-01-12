package net.lenni0451.mcstructs.text.components.nbt;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;

@EqualsAndHashCode
public class StorageNbtSource implements NbtDataSource {

    private Identifier id;

    public StorageNbtSource(final Identifier id) {
        this.id = id;
    }

    /**
     * @return The id of this component
     */
    public Identifier getId() {
        return this.id;
    }

    /**
     * Set the id of this component.
     *
     * @param id The id
     * @return This component
     */
    public StorageNbtSource setId(final Identifier id) {
        this.id = id;
        return this;
    }

    @Override
    public StorageNbtSource copy() {
        return new StorageNbtSource(this.id);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("id", this.id)
                .toString();
    }

}
