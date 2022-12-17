package net.lenni0451.mcstructs.items.info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemMeta {

    private final List<ItemType> types = new ArrayList<>();
    private final List<ItemTag> tags = new ArrayList<>();
    private int maxCount = 64;
    private int maxDamage = 0;

    public List<ItemType> getTypes() {
        return this.types;
    }

    public ItemMeta addTypes(final ItemType... types) {
        Collections.addAll(this.types, types);
        return this;
    }

    public List<ItemTag> getTags() {
        return this.tags;
    }

    public ItemMeta addTags(final ItemTag... tags) {
        Collections.addAll(this.tags, tags);
        return this;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    public ItemMeta setMaxCount(final int maxStackSize) {
        this.maxCount = maxStackSize;
        return this;
    }

    public int getMaxDamage() {
        return this.maxDamage;
    }

    public ItemMeta setMaxDamage(final int maxDamage) {
        this.maxDamage = maxDamage;
        return this;
    }

}
