package net.lenni0451.mcstructs.nbt.utils;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.ListTag;

import java.util.ArrayList;
import java.util.List;

public class NbtCodecUtils {

    private static final String MARKER_KEY = "";

    public static List<NbtTag> unwrapMarkers(final ListTag<?> listTag) {
        List<NbtTag> tags = new ArrayList<>(listTag.getValue());
        if (NbtType.COMPOUND.equals(listTag.getType())) {
            for (int i = 0; i < tags.size(); i++) {
                CompoundTag tag = tags.get(i).asCompoundTag();
                NbtTag wrapped = tag.get(MARKER_KEY);
                if (wrapped != null) tags.set(i, wrapped);
            }
        }
        return tags;
    }

    public static ListTag<CompoundTag> wrapMarkers(final List<NbtTag> tags) {
        ListTag<CompoundTag> listTag = new ListTag<>(NbtType.COMPOUND);
        for (NbtTag tag : tags) {
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
