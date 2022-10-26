package net.lenni0451.mcstructs.nbt.snbt.impl.v1_12;

import net.lenni0451.mcstructs.nbt.INbtNumber;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.snbt.ISNbtParser;
import net.lenni0451.mcstructs.nbt.snbt.SNbtParseException;
import net.lenni0451.mcstructs.nbt.tags.*;

import java.util.regex.Pattern;

public class SNbtParser_v1_12 implements ISNbtParser<CompoundNbt> {

    private static final Pattern BYTE_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", Pattern.CASE_INSENSITIVE);
    private static final Pattern SHORT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", Pattern.CASE_INSENSITIVE);
    private static final Pattern INT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
    private static final Pattern LONG_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", Pattern.CASE_INSENSITIVE);
    private static final Pattern FLOAT_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", Pattern.CASE_INSENSITIVE);
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", Pattern.CASE_INSENSITIVE);
    private static final Pattern SHORT_DOUBLE_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", Pattern.CASE_INSENSITIVE);

    @Override
    public CompoundNbt parse(String s) throws SNbtParseException {
        StringReader reader = new StringReader(s);
        CompoundNbt compoundNbt = this.readCompound(reader);
        reader.skipWhitespaces();
        if (reader.canRead()) throw this.makeException(reader, "Trailing data found");
        else return compoundNbt;
    }

    private CompoundNbt readCompound(final StringReader reader) throws SNbtParseException {
        reader.jumpTo('{');
        CompoundNbt compound = new CompoundNbt();
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

    private INbtTag readListOrArray(final StringReader reader) throws SNbtParseException {
        if (reader.canRead(2) && reader.charAt(1) != '"' && reader.charAt(2) == ';') return this.readArray(reader);
        else return this.readList(reader);
    }

    private ListNbt<INbtTag> readList(final StringReader reader) throws SNbtParseException {
        reader.jumpTo('[');
        reader.skipWhitespaces();
        if (!reader.canRead()) throw this.makeException(reader, "Expected value");
        ListNbt<INbtTag> list = new ListNbt<>();
        while (reader.peek() != ']') {
            INbtTag tag = this.readValue(reader);
            if (!list.canAdd(tag)) throw new SNbtParseException("Unable to insert " + tag.getClass().getSimpleName() + " into ListTag of type " + list.getType().getSimpleName());
            list.add(tag);
            if (!this.hasNextValue(reader)) break;
            if (!reader.canRead()) throw this.makeException(reader, "Expected value");
        }
        reader.jumpTo(']');
        return list;
    }

    private <T extends INbtNumber> ListNbt<T> readPrimitiveList(final StringReader reader, final Class<T> primitiveType, final Class<? extends INbtTag> arrayType) throws SNbtParseException {
        ListNbt<T> list = new ListNbt<>();
        while (true) {
            if (reader.peek() != ']') {
                INbtTag tag = this.readValue(reader);
                if (!primitiveType.isAssignableFrom(tag.getClass())) {
                    throw new SNbtParseException("Unable to insert " + tag.getClass().getSimpleName() + " into " + arrayType.getSimpleName());
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

    private INbtTag readArray(final StringReader reader) throws SNbtParseException {
        reader.jumpTo('[');
        char c = reader.read();
        reader.read();
        reader.skipWhitespaces();
        if (!reader.canRead()) throw this.makeException(reader, "Expected value");
        else if (c == 'B') return new ByteArrayNbt(this.readPrimitiveList(reader, ByteNbt.class, ByteArrayNbt.class));
        else if (c == 'L') return new LongArrayNbt(this.readPrimitiveList(reader, LongNbt.class, LongArrayNbt.class));
        else if (c == 'I') return new IntArrayNbt(this.readPrimitiveList(reader, IntNbt.class, IntArrayNbt.class));
        else throw new SNbtParseException("Invalid array type '" + c + "' found");
    }

    private INbtTag readValue(final StringReader reader) throws SNbtParseException {
        reader.skipWhitespaces();
        if (!reader.canRead()) throw this.makeException(reader, "Expected value");
        char c = reader.peek();
        if (c == '{') return this.readCompound(reader);
        else if (c == '[') return this.readListOrArray(reader);
        else return this.readPrimitive(reader);
    }

    private INbtTag readPrimitive(final StringReader reader) throws SNbtParseException {
        reader.skipWhitespaces();
        if (reader.peek() == '"') return new StringNbt(reader.readQuotedString());
        String value = reader.readUnquotedString();
        if (value.isEmpty()) throw this.makeException(reader, "Expected value");
        else return this.readPrimitive(value);
    }

    private INbtTag readPrimitive(final String value) {
        try {
            if (FLOAT_PATTERN.matcher(value).matches()) return new FloatNbt(Float.parseFloat(value.substring(0, value.length() - 1)));
            else if (BYTE_PATTERN.matcher(value).matches()) return new ByteNbt(Byte.parseByte(value.substring(0, value.length() - 1)));
            else if (LONG_PATTERN.matcher(value).matches()) return new LongNbt(Long.parseLong(value.substring(0, value.length() - 1)));
            else if (SHORT_PATTERN.matcher(value).matches()) return new ShortNbt(Short.parseShort(value.substring(0, value.length() - 1)));
            else if (INT_PATTERN.matcher(value).matches()) return new IntNbt(Integer.parseInt(value));
            else if (DOUBLE_PATTERN.matcher(value).matches()) return new DoubleNbt(Double.parseDouble(value.substring(0, value.length() - 1)));
            else if (SHORT_DOUBLE_PATTERN.matcher(value).matches()) return new DoubleNbt(Double.parseDouble(value));
            else if (value.equalsIgnoreCase("false")) return new ByteNbt((byte) 0);
            else if (value.equalsIgnoreCase("true")) return new ByteNbt((byte) 1);
        } catch (NumberFormatException ignored) {
        }
        return new StringNbt(value);
    }

    private boolean hasNextValue(final StringReader reader) {
        reader.skipWhitespaces();
        if (reader.canRead() && reader.peek() == ',') {
            reader.skip();
            reader.skipWhitespaces();
            return true;
        } else {
            return false;
        }
    }

    private SNbtParseException makeException(final StringReader reader, final String message) {
        return new SNbtParseException(message, reader.getString(), reader.getIndex());
    }

}
