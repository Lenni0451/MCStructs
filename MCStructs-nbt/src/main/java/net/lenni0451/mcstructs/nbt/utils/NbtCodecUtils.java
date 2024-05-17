package net.lenni0451.mcstructs.nbt.utils;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.ListTag;

import java.util.ArrayList;
import java.util.List;

public class NbtCodecUtils {

    private static final String MARKER_KEY = "";

    public static List<INbtTag> unwrapMarkers(final ListTag<?> listTag) {
        List<INbtTag> tags = new ArrayList<>(listTag.getValue());
        if (NbtType.COMPOUND.equals(listTag.getType())) {
            for (int i = 0; i < tags.size(); i++) {
                CompoundTag tag = tags.get(i).asCompoundTag();
                INbtTag wrapped = tag.get(MARKER_KEY);
                if (wrapped != null) tags.set(i, wrapped);
            }
        }
        return tags;
    }

    public static ListTag<CompoundTag> wrapMarkers(final List<INbtTag> tags) {
        ListTag<CompoundTag> listTag = new ListTag<>(NbtType.COMPOUND);
        for (INbtTag tag : tags) {
            if (tag.isCompoundTag()) {
                listTag.add(tag.asCompoundTag());
            } else {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.add(MARKER_KEY, tag);
                listTag.add(compoundTag);
            }
        }
        return listTag;
    }

}
