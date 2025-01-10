package net.lenni0451.mcstructs.text.components.nbt;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StorageNbtSource that = (StorageNbtSource) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("id", this.id)
                .toString();
    }

}
