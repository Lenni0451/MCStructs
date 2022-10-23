package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.text.ATextComponent;

public class NbtComponent extends ATextComponent {

    private final NbtComponentType type;
    private final String rawComponent;
    private final boolean resolve;
    private final String typeComponent;

    public NbtComponent(final NbtComponentType type, final String rawComponent, final boolean resolve, final String typeComponent) {
        this.type = type;
        this.rawComponent = rawComponent;
        this.resolve = resolve;
        this.typeComponent = typeComponent;
    }

    public NbtComponentType getType() {
        return this.type;
    }

    public String getRawComponent() {
        return this.rawComponent;
    }

    public boolean isResolve() {
        return this.resolve;
    }

    public String getTypeComponent() {
        return this.typeComponent;
    }

    @Override
    public ATextComponent copy() {
        return new NbtComponent(this.type, this.rawComponent, this.resolve, this.typeComponent);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }


    public enum NbtComponentType {
        BLOCK("block"),
        ENTITY("entity");

        private final String name;

        NbtComponentType(final String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

}
