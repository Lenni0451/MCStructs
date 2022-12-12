package net.lenni0451.mcstructs.snbt.impl.v1_8;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.exceptions.SNbtSerializeException;
import net.lenni0451.mcstructs.nbt.tags.*;
import net.lenni0451.mcstructs.snbt.ISNbtSerializer;

import java.util.Map;

public class SNbtSerializer_v1_8 implements ISNbtSerializer {

    @Override
    public String serialize(INbtTag tag) throws SNbtSerializeException {
        if (tag instanceof ByteTag) {
            ByteTag byteTag = (ByteTag) tag;
            return byteTag.getValue() + "b";
        } else if (tag instanceof ShortTag) {
            ShortTag shortTag = (ShortTag) tag;
            return shortTag.getValue() + "s";
        } else if (tag instanceof IntTag) {
            IntTag intTag = (IntTag) tag;
            return String.valueOf(intTag.getValue());
        } else if (tag instanceof LongTag) {
            LongTag longTag = (LongTag) tag;
            return longTag.getValue() + "L";
        } else if (tag instanceof FloatTag) {
            FloatTag floatTag = (FloatTag) tag;
            return floatTag.getValue() + "f";
        } else if (tag instanceof DoubleTag) {
            DoubleTag doubleTag = (DoubleTag) tag;
            return doubleTag.getValue() + "d";
        } else if (tag instanceof ByteArrayTag) {
            ByteArrayTag byteArrayTag = (ByteArrayTag) tag;
            return "[" + byteArrayTag.getValue().length + " bytes]";
        } else if (tag instanceof StringTag) {
            StringTag stringTag = (StringTag) tag;
            return "\"" + stringTag.getValue().replace("\"", "\\\"") + "\"";
        } else if (tag instanceof ListTag) {
            ListTag<?> listTag = (ListTag<?>) tag;
            StringBuilder out = new StringBuilder("[");
            for (int i = 0; i < listTag.size(); i++) {
                if (i != 0) out.append(",");
                out.append(i).append(":").append(this.serialize(listTag.get(i)));
            }
            return out.append("]").toString();
        } else if (tag instanceof CompoundTag) {
            CompoundTag compoundTag = (CompoundTag) tag;
            StringBuilder out = new StringBuilder("{");
            for (Map.Entry<String, INbtTag> entry : compoundTag.getValue().entrySet()) {
                if (out.length() != 1) out.append(",");
                out.append(entry.getKey()).append(":").append(this.serialize(entry.getValue()));
            }
            return out.append("}").toString();
        } else if (tag instanceof IntArrayTag) {
            IntArrayTag intArrayTag = (IntArrayTag) tag;
            StringBuilder out = new StringBuilder("[");
            for (int i : intArrayTag.getValue()) out.append(i).append(",");
            return out.append("]").toString();
        }
        throw new SNbtSerializeException(tag.getNbtType());
    }

}
