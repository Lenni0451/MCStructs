package net.lenni0451.mcstructs.text.components.nbt;

import net.lenni0451.mcstructs.core.utils.ToString;

import java.util.Objects;

public class BlockNbtSource implements NbtDataSource {

    private String pos;

    public BlockNbtSource(final String pos) {
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
    public BlockNbtSource setPos(final String pos) {
        this.pos = pos;
        return this;
    }

    @Override
    public BlockNbtSource copy() {
        return new BlockNbtSource(this.pos);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BlockNbtSource that = (BlockNbtSource) o;
        return Objects.equals(this.pos, that.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.pos);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("pos", this.pos)
                .toString();
    }

}
