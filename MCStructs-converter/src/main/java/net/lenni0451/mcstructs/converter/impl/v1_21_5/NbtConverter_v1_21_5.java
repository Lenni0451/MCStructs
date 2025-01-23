package net.lenni0451.mcstructs.converter.impl.v1_21_5;

import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.*;
import net.lenni0451.mcstructs.snbt.SNbt;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.lenni0451.mcstructs.nbt.utils.NbtCodecUtils.MARKER_KEY;

public class NbtConverter_v1_21_5 extends NbtConverter_v1_20_3 {

    public static final NbtConverter_v1_21_5 INSTANCE = new NbtConverter_v1_21_5();

    public NbtConverter_v1_21_5() {
        this(SNbt.V1_21_5);
    }

    protected NbtConverter_v1_21_5(final SNbt<CompoundTag> sNbt) {
        super(sNbt);
    }

    @Override
    public Result<NbtTag> mergeList(@Nullable NbtTag list, List<NbtTag> values) {
        ListType listType;
        if (list == null) {
            listType = ListType.LIST;
        } else if (list instanceof ByteArrayTag) {
            if (list.asByteArrayTag().isEmpty()) listType = ListType.LIST;
            else listType = ListType.BYTE;
        } else if (list instanceof IntArrayTag) {
            if (list.asIntArrayTag().isEmpty()) listType = ListType.LIST;
            else listType = ListType.INT;
        } else if (list instanceof LongArrayTag) {
            if (list.asLongArrayTag().isEmpty()) listType = ListType.LIST;
            else listType = ListType.LONG;
        } else if (list instanceof ListTag) {
            ListTag<?> listTag = list.asListTag();
            if (NbtType.COMPOUND.equals(listTag.getType())) listType = ListType.MIXED_LIST;
            else listType = ListType.LIST;
        } else {
            return Result.error("Invalid list tag: " + list);
        }

        Result<List<NbtTag>> listResult = list == null ? Result.success(new ArrayList<>()) : this.asList(list);
        if (listResult.isError()) return listResult.mapError();
        List<NbtTag> tags = listResult.get();
        tags.addAll(values);
        if (tags.isEmpty()) return Result.success(new ListTag<>());

        if (listType.equals(ListType.BYTE)) {
            if (tags.stream().allMatch(NbtTag::isByteTag)) {
                byte[] bytes = new byte[tags.size()];
                for (int i = 0; i < tags.size(); i++) bytes[i] = tags.get(i).asByteTag().getValue();
                return Result.success(new ByteArrayTag(bytes));
            }
        } else if (listType.equals(ListType.INT)) {
            if (tags.stream().allMatch(NbtTag::isIntTag)) {
                int[] ints = new int[tags.size()];
                for (int i = 0; i < tags.size(); i++) ints[i] = tags.get(i).asIntTag().getValue();
                return Result.success(new IntArrayTag(ints));
            }
        } else if (listType.equals(ListType.LONG)) {
            if (tags.stream().allMatch(NbtTag::isLongTag)) {
                long[] longs = new long[tags.size()];
                for (int i = 0; i < tags.size(); i++) longs[i] = tags.get(i).asLongTag().getValue();
                return Result.success(new LongArrayTag(longs));
            }
        } else if (listType.equals(ListType.LIST)) {
            NbtType type = null;
            for (NbtTag tag : tags) {
                if (type == null) {
                    type = tag.getNbtType();
                } else if (!type.equals(tag.getNbtType())) {
                    type = null;
                    break;
                }
            }
            if (type != null) return Result.success(new ListTag<>(tags));
        }

        ListTag<NbtTag> out = new ListTag<>();
        for (NbtTag tag : tags) {
            boolean isMarker = tag.isCompoundTag() && tag.asCompoundTag().size() == 1 && tag.asCompoundTag().contains(MARKER_KEY);
            if (tag.isCompoundTag() && !isMarker) {
                out.add(tag);
            } else {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.add(MARKER_KEY, tag);
                out.add(compoundTag);
            }
        }
        return Result.success(out);
    }


    private enum ListType {
        BYTE, INT, LONG, LIST, MIXED_LIST
    }

}
