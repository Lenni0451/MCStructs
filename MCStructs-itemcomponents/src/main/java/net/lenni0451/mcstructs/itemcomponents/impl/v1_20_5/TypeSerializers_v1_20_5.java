package net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.ItemStack;
import net.lenni0451.mcstructs.itemcomponents.serialization.Verifier;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import static net.lenni0451.mcstructs.itemcomponents.serialization.TypeValidator.*;

class TypeSerializers_v1_20_5 {

    static class ItemStackSerializer {
        static JsonObject toJson(final ItemComponentRegistry registry, final ItemStack stack) {
            JsonObject object = new JsonObject();
            object.addProperty(ItemStack.ID, stack.getId().get());
            object.addProperty(ItemStack.COUNT, stack.getCount());
            if (!stack.getComponents().isEmpty()) object.add(ItemStack.COMPONENTS, registry.mapToJson(stack.getComponents()));
            return object;
        }

        static ItemStack fromJson(final ItemComponentRegistry registry, final Verifier<Identifier> itemVerifier, final JsonElement element) {
            JsonObject object = requireJsonObject(element);
            Identifier id = Identifier.of(object.get(ItemStack.ID).getAsString());
            itemVerifier.check("item", id);
            int count = object.has(ItemStack.COUNT) ? requireInt(object.get(ItemStack.COUNT)) : 1;
            if (count < 1) throw new IllegalArgumentException("Item count must be at least 1");
            ItemComponentMap components = object.has(ItemStack.COMPONENTS) ? registry.mapFromJson(object.get(ItemStack.COMPONENTS)) : new ItemComponentMap(registry);
            return new ItemStack(id, count, components);
        }

        static CompoundTag toNbt(final ItemComponentRegistry registry, final ItemStack stack) {
            CompoundTag compound = new CompoundTag();
            compound.addString(ItemStack.ID, stack.getId().get());
            compound.addInt(ItemStack.COUNT, stack.getCount());
            if (!stack.getComponents().isEmpty()) compound.add(ItemStack.COMPONENTS, registry.mapToNbt(stack.getComponents()));
            return compound;
        }

        static ItemStack fromNbt(final ItemComponentRegistry registry, final Verifier<Identifier> itemVerifier, final INbtTag tag) {
            CompoundTag compound = requireCompoundTag(tag);
            Identifier id = Identifier.of(compound.getString(ItemStack.ID));
            itemVerifier.check("item", id);
            int count = compound.contains(ItemStack.COUNT) ? requireInt(compound.<INbtTag>get(ItemStack.COUNT)) : 1;
            if (count < 1) throw new IllegalArgumentException("Item count must be at least 1");
            ItemComponentMap components = compound.contains(ItemStack.COMPONENTS) ? registry.mapFromNbt(compound.get(ItemStack.COMPONENTS)) : new ItemComponentMap(registry);
            return new ItemStack(id, count, components);
        }
    }

}
