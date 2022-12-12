package net.lenni0451.mcstructs.snbt.impl.v1_12;

import net.lenni0451.mcstructs.nbt.INbtNumber;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.exceptions.SNbtDeserializeException;
import net.lenni0451.mcstructs.nbt.tags.*;
import net.lenni0451.mcstructs.snbt.ISNbtDeserializer;

import java.util.regex.Pattern;

public class SNbtDeserializer_v1_12 implements ISNbtDeserializer<CompoundTag> {

    private static final Pattern BYTE_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", Pattern.CASE_INSENSITIVE);
    private static final Pattern SHORT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", Pattern.CASE_INSENSITIVE);
    private static final Pattern INT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
    private static final Pattern LONG_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", Pattern.CASE_INSENSITIVE);
    private static final Pattern FLOAT_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", Pattern.CASE_INSENSITIVE);
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", Pattern.CASE_INSENSITIVE);
    private static final Pattern SHORT_DOUBLE_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", Pattern.CASE_INSENSITIVE);

    @Override
    public CompoundTag deserialize(String s) throws SNbtDeserializeException {
        StringReader_v1_12 reader = new StringReader_v1_12(s);
        CompoundTag compoundTag = this.readCompound(reader);
        reader.skipWhitespaces();
        if (reader.canRead()) throw this.makeException(reader, "Trailing data found");
        else return compoundTag;
    }

    protected CompoundTag readCompound(final StringReader_v1_12 reader) throws SNbtDeserializeException {
        reader.jumpTo('{');
        CompoundTag compound = new CompoundTag();
        reader.skipWhitespaces();
        while (reader.canRead() && reader.peek() != '}') {
            String key = reader.readString();
            if (key == null) throw this.makeException(reader, "Expected key");
            if (key.isEmpty()) throw this.makeException(reader, "Expected non-empty key");
            reader.jumpTo(':');
            compound.add(key, this.readValue(reader));
            if (!this.hasNextValue(reader)) break;
            if (!reader.canRead()) throw this.makeException(reader, "Expected key");
        }
        reader.jumpTo('}');
        return compound;
    }

    protected INbtTag readListOrArray(final StringReader_v1_12 reader) throws SNbtDeserializeException {
        if (reader.canRead(2) && !this.isQuote(reader.charAt(1)) && reader.charAt(2) == ';') return this.readArray(reader);
        else return this.readList(reader);
    }

    protected ListTag<INbtTag> readList(final StringReader_v1_12 reader) throws SNbtDeserializeException {
        reader.jumpTo('[');
        reader.skipWhitespaces();
        if (!reader.canRead()) throw this.makeException(reader, "Expected value");
        ListTag<INbtTag> list = new ListTag<>();
        while (reader.peek() != ']') {
            INbtTag tag = this.readValue(reader);
            if (!list.canAdd(tag)) throw new SNbtDeserializeException("Unable to insert " + tag.getClass().getSimpleName() + " into ListTag of type " + list.getType().name());
            list.add(tag);
            if (!this.hasNextValue(reader)) break;
            if (!reader.canRead()) throw this.makeException(reader, "Expected value");
        }
        reader.jumpTo(']');
        return list;
    }

    protected <T extends INbtNumber> ListTag<T> readPrimitiveList(final StringReader_v1_12 reader, final Class<T> primitiveType, final Class<? extends INbtTag> arrayType) throws SNbtDeserializeException {
        ListTag<T> list = new ListTag<>();
        while (true) {
            if (reader.peek() != ']') {
                INbtTag tag = this.readValue(reader);
                if (!primitiveType.isAssignableFrom(tag.getClass())) {
                    throw new SNbtDeserializeException("Unable to insert " + tag.getClass().getSimpleName() + " into " + arrayType.getSimpleName());
                }
                list.add((T) tag);
                if (this.hasNextValue(reader)) {
                    if (!reader.canRead()) throw this.makeException(reader, "Expected value");
                    continue;
                }
            }
            reader.jumpTo(']');
            return list;
        }
    }

    protected INbtTag readArray(final StringReader_v1_12 reader) throws SNbtDeserializeException {
        reader.jumpTo('[');
        char c = reader.read();
        reader.read();
        reader.skipWhitespaces();
        if (!reader.canRead()) throw this.makeException(reader, "Expected value");
        else if (c == 'B') return new ByteArrayTag(this.readPrimitiveList(reader, ByteTag.class, ByteArrayTag.class));
        else if (c == 'L') return new LongArrayTag(this.readPrimitiveList(reader, LongTag.class, LongArrayTag.class));
        else if (c == 'I') return new IntArrayTag(this.readPrimitiveList(reader, IntTag.class, IntArrayTag.class));
        else throw new SNbtDeserializeException("Invalid array type '" + c + "' found");
    }

    protected INbtTag readValue(final StringReader_v1_12 reader) throws SNbtDeserializeException {
        reader.skipWhitespaces();
        if (!reader.canRead()) throw this.makeException(reader, "Expected value");
        char c = reader.peek();
        if (c == '{') return this.readCompound(reader);
        else if (c == '[') return this.readListOrArray(reader);
        else return this.readPrimitive(reader);
    }

    protected INbtTag readPrimitive(final StringReader_v1_12 reader) throws SNbtDeserializeException {
        reader.skipWhitespaces();
        if (this.isQuote(reader.peek())) return new StringTag(reader.readQuotedString());
        String value = reader.readUnquotedString();
        if (value.isEmpty()) throw this.makeException(reader, "Expected value");
        else return this.readPrimitive(value);
    }

    protected INbtTag readPrimitive(final String value) {
        try {
            if (FLOAT_PATTERN.matcher(value).matches()) return new FloatTag(Float.parseFloat(value.substring(0, value.length() - 1)));
            else if (BYTE_PATTERN.matcher(value).matches()) return new ByteTag(Byte.parseByte(value.substring(0, value.length() - 1)));
            else if (LONG_PATTERN.matcher(value).matches()) return new LongTag(Long.parseLong(value.substring(0, value.length() - 1)));
            else if (SHORT_PATTERN.matcher(value).matches()) return new ShortTag(Short.parseShort(value.substring(0, value.length() - 1)));
            else if (INT_PATTERN.matcher(value).matches()) return new IntTag(Integer.parseInt(value));
            else if (DOUBLE_PATTERN.matcher(value).matches()) return new DoubleTag(Double.parseDouble(value.substring(0, value.length() - 1)));
            else if (SHORT_DOUBLE_PATTERN.matcher(value).matches()) return new DoubleTag(Double.parseDouble(value));
            else if (value.equalsIgnoreCase("false")) return new ByteTag((byte) 0);
            else if (value.equalsIgnoreCase("true")) return new ByteTag((byte) 1);
        } catch (NumberFormatException ignored) {
        }
        return new StringTag(value);
    }

    protected boolean hasNextValue(final StringReader_v1_12 reader) {
        reader.skipWhitespaces();
        if (reader.canRead() && reader.peek() == ',') {
            reader.skip();
            reader.skipWhitespaces();
            return true;
        } else {
            return false;
        }
    }

    protected SNbtDeserializeException makeException(final StringReader_v1_12 reader, final String message) {
        return new SNbtDeserializeException(message, reader.getString(), reader.getIndex());
    }

    protected boolean isQuote(final char c) {
        return c == '"';
    }

}
