package net.lenni0451.mcstructs.items.info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The metadata of items.
 */
public class ItemMeta {

    private final List<ItemType> types = new ArrayList<>();
    private final List<ItemTag> tags = new ArrayList<>();
    private int maxCount = 64;
    private int maxDamage = 0;

    /**
     * @return The types of the item
     */
    public List<ItemType> getTypes() {
        return this.types;
    }

    /**
     * Add types to the item.
     *
     * @param types The types to add
     * @return The current instance
     */
    public ItemMeta addTypes(final ItemType... types) {
        Collections.addAll(this.types, types);
        return this;
    }

    /**
     * Get the tags of the item.
     *
     * @return The tags of the item
     */
    public List<ItemTag> getTags() {
        return this.tags;
    }

    /**
     * Add tags to the item.
     *
     * @param tags The tags to add
     * @return The current instance
     */
    public ItemMeta addTags(final ItemTag... tags) {
        Collections.addAll(this.tags, tags);
        return this;
    }

    /**
     * @return The max count of the item
     */
    public int getMaxCount() {
        return this.maxCount;
    }

    /**
     * Set the max count of the item.
     *
     * @param maxCount The max count of the item
     * @return The current instance
     */
    public ItemMeta setMaxCount(final int maxCount) {
        this.maxCount = maxCount;
        return this;
    }

    /**
     * @return The max damage of the item
     */
    public int getMaxDamage() {
        return this.maxDamage;
    }

    /**
     * Set the max damage of the item.
     *
     * @param maxDamage The max damage of the item
     * @return The current instance
     */
    public ItemMeta setMaxDamage(final int maxDamage) {
        this.maxDamage = maxDamage;
        return this;
    }

}
