package net.lenni0451.mcstructs.nbt;

public class NbtHeader<T extends INbtTag> {

    public static NbtHeader<INbtTag> END = new NbtHeader<>(null, null);


    private final Class<T> type;
    private final String name;

    public NbtHeader(final Class<T> type, final String name) {
        this.type = type;
        this.name = name;
    }

    public Class<T> getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public boolean isEnd() {
        return this.type == null && this.name == null;
    }

}
