package net.lenni0451.mcstructs.snbt.impl.v1_12;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.*;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtSerializeException;
import net.lenni0451.mcstructs.snbt.impl.SNbtSerializer;

import java.util.Map;
import java.util.regex.Pattern;

public class SNbtSerializer_v1_12 implements SNbtSerializer {

    private static final Pattern ESCAPE_PATTERN = Pattern.compile("[A-Za-z0-9._+-]+");

    @Override
    public String serialize(NbtTag tag) throws SNbtSerializeException {
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
            StringBuilder out = new StringBuilder("[B;");
            for (int i = 0; i < byteArrayTag.getLength(); i++) {
                if (i != 0) out.append(",");
                out.append(byteArrayTag.get(i)).append("B");
            }
            return out.append("]").toString();
        } else if (tag instanceof StringTag) {
            StringTag stringTag = (StringTag) tag;
            return this.escape(stringTag.getValue());
        } else if (tag instanceof ListTag) {
            ListTag<?> listTag = (ListTag<?>) tag;
            StringBuilder out = new StringBuilder("[");
            for (int i = 0; i < listTag.size(); i++) {
                if (i != 0) out.append(",");
                out.append(this.serialize(listTag.get(i)));
            }
            return out.append("]").toString();
        } else if (tag instanceof CompoundTag) {
            CompoundTag compoundTag = (CompoundTag) tag;
            StringBuilder out = new StringBuilder("{");
            for (Map.Entry<String, NbtTag> entry : compoundTag.getValue().entrySet()) {
                if (out.length() != 1) out.append(",");
                out.append(this.checkEscape(entry.getKey())).append(":").append(this.serialize(entry.getValue()));
            }
            return out.append("}").toString();
        } else if (tag instanceof IntArrayTag) {
            IntArrayTag intArrayTag = (IntArrayTag) tag;
            StringBuilder out = new StringBuilder("[I;");
            for (int i = 0; i < intArrayTag.getLength(); i++) {
                if (i != 0) out.append(",");
                out.append(intArrayTag.get(i));
            }
            return out.append("]").toString();
        } else if (tag instanceof LongArrayTag) {
            LongArrayTag longArrayTag = (LongArrayTag) tag;
            StringBuilder out = new StringBuilder("[L;");
            for (int i = 0; i < longArrayTag.getLength(); i++) {
                if (i != 0) out.append(",");
                out.append(longArrayTag.get(i)).append("L");
            }
            return out.append("]").toString();
        }
        throw new SNbtSerializeException(tag.getNbtType());
    }

    protected String checkEscape(final String s) {
        if (ESCAPE_PATTERN.matcher(s).matches()) return s;
        return this.escape(s);
    }

    protected String escape(final String s) {
        StringBuilder out = new StringBuilder("\"");
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (c == '\\' || c == '"') out.append("\\");
            out.append(c);
        }
        return out.append("\"").toString();
    }

}
