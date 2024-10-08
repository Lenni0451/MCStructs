package net.lenni0451.mcstructs.text.components.nbt;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.NbtComponent;

import java.util.Objects;

public class StorageNbtComponent extends NbtComponent {

    private Identifier id;

    public StorageNbtComponent(final String component, final boolean resolve, final Identifier id) {
        super(component, resolve);
        this.id = id;
    }

    public StorageNbtComponent(final String component, final boolean resolve, final ATextComponent separator, final Identifier id) {
        super(component, resolve, separator);
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
    public StorageNbtComponent setId(final Identifier id) {
        this.id = id;
        return this;
    }

    @Override
    public ATextComponent copy() {
        return this.putMetaCopy(this.shallowCopy());
    }

    @Override
    public ATextComponent shallowCopy() {
        if (this.getSeparator() == null) return new StorageNbtComponent(this.getComponent(), this.isResolve(), null, this.id).setStyle(this.getStyle().copy());
        else return new StorageNbtComponent(this.getComponent(), this.isResolve(), this.getSeparator(), this.id).setStyle(this.getStyle().copy());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StorageNbtComponent that = (StorageNbtComponent) o;
        return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSiblings(), this.getStyle(), this.id);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty())
                .add("style", this.getStyle(), style -> !style.isEmpty())
                .add("id", this.id)
                .toString();
    }

}
