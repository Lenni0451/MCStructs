package net.lenni0451.mcstructs.itemcomponents;

import com.google.gson.JsonElement;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.INbtTag;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Function;

public class ItemComponent<T> {

    private final Identifier name;
    final Function<T, JsonElement> toJson;
    final Function<JsonElement, T> fromJson;
    final Function<T, INbtTag> toNbt;
    final Function<INbtTag, T> fromNbt;
    @Nullable
    final Consumer<T> verifier;

    public ItemComponent(final String name, final Function<T, JsonElement> toJson, final Function<JsonElement, T> fromJson, final Function<T, INbtTag> toNbt, final Function<INbtTag, T> fromNbt, @Nullable final Consumer<T> verifier) {
        this.name = Identifier.of(name);
        this.toJson = toJson;
        this.fromJson = fromJson;
        this.toNbt = toNbt;
        this.fromNbt = fromNbt;
        this.verifier = verifier;
    }

    public Identifier getName() {
        return this.name;
    }

    public JsonElement toJson(final T value) {
        return this.toJson.apply(value);
    }

    public T fromJson(final JsonElement jsonElement) {
        T t = this.fromJson.apply(jsonElement);
        if (this.verifier != null) this.verifier.accept(t);
        return t;
    }

    public INbtTag toNbt(final T value) {
        return this.toNbt.apply(value);
    }

    public T fromNbt(final INbtTag tag) {
        T t = this.fromNbt.apply(tag);
        if (this.verifier != null) this.verifier.accept(t);
        return t;
    }

}
