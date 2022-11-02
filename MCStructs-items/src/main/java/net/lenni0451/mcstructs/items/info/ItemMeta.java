package net.lenni0451.mcstructs.items.info;

public class ItemMeta {

    public static final ItemMeta EMPTY = new ItemMeta() {
        private final String exceptionMessage = "Unable to modify empty item meta";

        @Override
        public ItemMeta maxCount(int maxStackSize) {
            throw new UnsupportedOperationException(this.exceptionMessage);
        }

        @Override
        public ItemMeta damageable(boolean damageable) {
            throw new UnsupportedOperationException(this.exceptionMessage);
        }
    };


    private ItemType type = ItemType.GENERIC;
    private int maxCount = 64;
    private boolean damageable = false;
    private boolean hasSubtypes = false;

    public ItemType type() {
        return this.type;
    }

    public ItemMeta type(final ItemType type) {
        this.type = type;
        return this;
    }

    public int maxCount() {
        return this.maxCount;
    }

    public ItemMeta maxCount(final int maxStackSize) {
        this.maxCount = maxStackSize;
        return this;
    }

    public boolean damageable() {
        return this.damageable;
    }

    public ItemMeta damageable(final boolean damageable) {
        this.damageable = damageable;
        return this;
    }

    public boolean hasSubtypes() {
        return this.hasSubtypes;
    }

    public ItemMeta hasSubtypes(final boolean hasSubtypes) {
        this.hasSubtypes = hasSubtypes;
        return this;
    }

}
