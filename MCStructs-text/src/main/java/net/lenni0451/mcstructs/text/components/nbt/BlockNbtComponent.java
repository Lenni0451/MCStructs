package net.lenni0451.mcstructs.text.components.nbt;

import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.NbtComponent;

import javax.annotation.Nullable;
import java.util.Objects;

public class BlockNbtComponent extends NbtComponent {

    private String pos;

    public BlockNbtComponent(final String rawComponent, final boolean resolve, final String pos) {
        super(rawComponent, resolve);
        this.pos = pos;
    }

    public BlockNbtComponent(final String rawComponent, final boolean resolve, @Nullable final TextComponent separator, final String pos) {
        super(rawComponent, resolve, separator);
        this.pos = pos;
    }

    /**
     * @return The position of this component
     */
    public String getPos() {
        return this.pos;
    }

    /**
     * Set the position of this component.
     *
     * @param pos The position
     * @return This component
     */
    public BlockNbtComponent setPos(final String pos) {
        this.pos = pos;
        return this;
    }

    @Override
    public TextComponent copy() {
        return this.copyMetaTo(this.shallowCopy());
    }

    @Override
    public TextComponent shallowCopy() {
        if (this.getSeparator() == null) return new BlockNbtComponent(this.getComponent(), this.isResolve(), this.getSeparator(), this.pos).setStyle(this.getStyle().copy());
        else return new BlockNbtComponent(this.getComponent(), this.isResolve(), this.getSeparator().copy(), this.pos).setStyle(this.getStyle().copy());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockNbtComponent that = (BlockNbtComponent) o;
        return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.pos, that.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSiblings(), this.getStyle(), this.pos);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty())
                .add("style", this.getStyle(), style -> !style.isEmpty())
                .add("pos", this.pos)
                .toString();
    }

}
