package net.lenni0451.mcstructs.items.info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemMeta {

    private final List<ItemType> types = new ArrayList<>();
    private final List<ItemTag> tags = new ArrayList<>();
    private int maxCount = 64;
    private int maxDamage = 0;

    public List<ItemType> types() {
        return this.types;
    }

    public ItemMeta types(final ItemType... types) {
        Collections.addAll(this.types, types);
        return this;
    }

    public List<ItemTag> tags() {
        return this.tags;
    }

    public ItemMeta tags(final ItemTag... tags) {
        Collections.addAll(this.tags, tags);
        return this;
    }

    public int maxCount() {
        return this.maxCount;
    }

    public ItemMeta maxCount(final int maxStackSize) {
        this.maxCount = maxStackSize;
        return this;
    }

    public int maxDamage() {
        return this.maxDamage;
    }

    public ItemMeta maxDamage(final int maxDamage) {
        this.maxDamage = maxDamage;
        return this;
    }

}
