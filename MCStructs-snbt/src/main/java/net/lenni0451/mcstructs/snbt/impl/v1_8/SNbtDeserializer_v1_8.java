package net.lenni0451.mcstructs.snbt.impl.v1_8;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.*;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtDeserializeException;
import net.lenni0451.mcstructs.snbt.impl.ISNbtDeserializer;

import java.util.Arrays;
import java.util.Stack;

public class SNbtDeserializer_v1_8 implements ISNbtDeserializer<CompoundTag> {

    private static final String ARRAY_PATTERN = "\\[[-+\\d|,\\s]+]";
    private static final String BYTE_PATTERN = "[-+]?[0-9]+[b|B]";
    private static final String SHORT_PATTERN = "[-+]?[0-9]+[s|S]";
    private static final String INT_PATTERN = "[-+]?[0-9]+";
    private static final String LONG_PATTERN = "[-+]?[0-9]+[l|L]";
    private static final String FLOAT_PATTERN = "[-+]?[0-9]*\\.?[0-9]+[f|F]";
    private static final String DOUBLE_PATTERN = "[-+]?[0-9]*\\.?[0-9]+[d|D]";
    private static final String SHORT_DOUBLE_PATTERN = "[-+]?[0-9]*\\.?[0-9]+";

    @Override
    public CompoundTag deserialize(String s) throws SNbtDeserializeException {
        s = s.trim();
        if (!s.startsWith("{")) throw new SNbtDeserializeException("Invalid tag encountered, expected '{' as first char.");
        else if (this.getTagCount(s) != 1) throw new SNbtDeserializeException("Encountered multiple top tags, only one expected");
        else return (CompoundTag) this.parseTag(s);
    }

    @Override
    public NbtTag deserializeValue(String s) throws SNbtDeserializeException {
        return this.parseTag(s);
    }

    private NbtTag parseTag(String value) throws SNbtDeserializeException {
        value = value.trim();
        if (value.startsWith("{")) {
            value = value.substring(1, value.length() - 1);
            CompoundTag compound = new CompoundTag();

            String pair;
            for (; value.length() > 0; value = value.substring(pair.length() + 1)) {
                pair = this.findPair(value, false);
                if (pair.length() > 0) {
                    String subName = this.find(pair, true, false);
                    String subValue = this.find(pair, false, false);
                    compound.add(subName, this.parseTag(subValue));
                }
                if (value.length() < pair.length() + 1) break;
                char nextChar = value.charAt(pair.length());
                if (nextChar != ',' && nextChar != '{' && nextChar != '}' && nextChar != '[' && nextChar != ']') {
                    throw new SNbtDeserializeException("Unexpected token '" + nextChar + "' at: " + value.substring(pair.length()));
                }
            }
            return compound;
        } else if (value.startsWith("[") && !value.matches(ARRAY_PATTERN)) {
            value = value.substring(1, value.length() - 1);
            ListTag<NbtTag> list = new ListTag<>();

            String pair;
            for (; value.length() > 0; value = value.substring(pair.length() + 1)) {
                pair = this.findPair(value, true);
                if (pair.length() > 0) {
                    String subValue = this.find(pair, false, true);
                    try {
                        list.add(this.parseTag(subValue));
                    } catch (IllegalArgumentException e) {
                        //1.8 skips invalid list entries
                    }
                }
                if (value.length() < pair.length() + 1) break;
                char nextChar = value.charAt(pair.length());
                if (nextChar != ',' && nextChar != '{' && nextChar != '}' && nextChar != '[' && nextChar != ']') {
                    throw new SNbtDeserializeException("Unexpected token '" + nextChar + "' at: " + value.substring(pair.length()));
                }
            }
            return list;
        } else {
            return this.parsePrimitive(value);
        }
    }

    private NbtTag parsePrimitive(String value) {
        try {
            if (value.matches(DOUBLE_PATTERN)) return new DoubleTag(Double.parseDouble(value.substring(0, value.length() - 1)));
            else if (value.matches(FLOAT_PATTERN)) return new FloatTag(Float.parseFloat(value.substring(0, value.length() - 1)));
            else if (value.matches(BYTE_PATTERN)) return new ByteTag(Byte.parseByte(value.substring(0, value.length() - 1)));
            else if (value.matches(LONG_PATTERN)) return new LongTag(Long.parseLong(value.substring(0, value.length() - 1)));
            else if (value.matches(SHORT_PATTERN)) return new ShortTag(Short.parseShort(value.substring(0, value.length() - 1)));
            else if (value.matches(INT_PATTERN)) return new IntTag(Integer.parseInt(value));
            else if (value.matches(SHORT_DOUBLE_PATTERN)) return new DoubleTag(Double.parseDouble(value));
            else if (value.equalsIgnoreCase("false")) return new ByteTag((byte) 0);
            else if (value.equalsIgnoreCase("true")) return new ByteTag((byte) 1);
        } catch (NumberFormatException e) {
            return new StringTag(value.replace("\\\"", "\""));
        }
        if (value.startsWith("[") && value.endsWith("]")) {
            String arrayContent = value.substring(1, value.length() - 1);
            String[] parts = this.trimSplit(arrayContent);
            try {
                int[] ints = new int[parts.length];
                for (int i = 0; i < parts.length; i++) ints[i] = Integer.parseInt(parts[i].trim());
                return new IntArrayTag(ints);
            } catch (NumberFormatException e) {
                return new StringTag(value);
            }
        } else {
            if (value.startsWith("\"") && value.endsWith("\"")) value = value.substring(1, value.length() - 1);
            value = value.replace("\\\"", "\"");
            StringBuilder out = new StringBuilder();
            char[] chars = value.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (i < chars.length - 1 && c == '\\' && chars[i + 1] == '\\') {
                    out.append("\\");
                    i++;
                } else {
                    out.append(c);
                }
            }
            return new StringTag(out.toString());
        }
    }

    private int getTagCount(final String s) throws SNbtDeserializeException {
        Stack<Character> brackets = new Stack<>();
        boolean quoted = false;
        int count = 0;

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '"') {
                if (this.isEscaped(s, i)) {
                    if (!quoted) throw new SNbtDeserializeException("Illegal use of \\\": " + s);
                } else {
                    quoted = !quoted;
                }
            } else if (!quoted) {
                if (c == '{' || c == '[') {
                    if (brackets.isEmpty()) count++;
                    brackets.push(c);
                } else {
                    this.checkBrackets(s, c, brackets);
                }
            }
        }

        if (quoted) throw new SNbtDeserializeException("Unbalanced quotation: " + s);
        else if (!brackets.isEmpty()) throw new SNbtDeserializeException("Unbalanced brackets " + this.quotesToString(brackets) + ": " + s);
        else if (count == 0 && !s.isEmpty()) return 1;
        else return count;
    }

    private String findPair(final String s, final boolean isArray) throws SNbtDeserializeException {
        int separatorIndex = this.getCharIndex(s, ':');
        if (separatorIndex == -1 && !isArray) throw new SNbtDeserializeException("Unable to locate name/value separator for string: " + s);
        int pairSeparator = this.getCharIndex(s, ',');
        if (pairSeparator != -1 && pairSeparator < separatorIndex && !isArray) throw new SNbtDeserializeException("Name error at: " + s);
        if (isArray && (separatorIndex == -1 || separatorIndex > pairSeparator)) separatorIndex = -1;

        Stack<Character> brackets = new Stack<>();
        int i = separatorIndex + 1;
        int quoteEnd = 0;
        boolean quoted = false;
        boolean hasContent = false;
        boolean isString = false;

        char[] chars = s.toCharArray();
        for (; i < chars.length; i++) {
            char c = chars[i];
            if (c == '"') {
                if (this.isEscaped(s, i)) {
                    if (!quoted) throw new SNbtDeserializeException("Illegal use of \\\": " + s);
                } else {
                    quoted = !quoted;
                    if (quoted && !hasContent) isString = true;
                    if (!quoted) quoteEnd = i;
                }
            } else if (!quoted) {
                if (c == '{' || c == '[') {
                    brackets.push(c);
                } else {
                    this.checkBrackets(s, c, brackets);
                    if (c == ',' && brackets.isEmpty()) return s.substring(0, i);
                }
            }
            if (!Character.isWhitespace(c)) {
                if (!quoted && isString && quoteEnd != i) return s.substring(0, quoteEnd + 1);
                hasContent = true;
            }
        }
        return s.substring(0, i);
    }

    private String find(String s, final boolean name, final boolean isArray) throws SNbtDeserializeException {
        if (isArray) {
            s = s.trim();
            if (s.startsWith("{") || s.startsWith("[")) {
                if (name) return "";
                else return s;
            }
        }

        int separatorIndex = this.getCharIndex(s, ':');
        if (separatorIndex == -1) {
            if (isArray) {
                if (name) return "";
                else return s;
            } else {
                throw new SNbtDeserializeException("Unable to locate name/value separator for string: " + s);
            }
        } else {
            if (name) return s.substring(0, separatorIndex).trim();
            else return s.substring(separatorIndex + 1).trim();
        }
    }

    private int getCharIndex(final String s, final char wanted) {
        boolean quoted = true;

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '"') {
                if (!this.isEscaped(s, i)) quoted = !quoted;
            } else if (quoted) {
                if (c == wanted) return i;
                if (c == '{' || c == '[') return -1;
            }
        }
        return -1;
    }

    private String[] trimSplit(final String s) {
        String[] split = s.split(",");
        String[] clean = new String[split.length];
        int index = 0;
        for (String value : split) {
            if (!value.isEmpty()) clean[index++] = value;
        }
        return Arrays.copyOfRange(clean, 0, index);
    }

    private boolean isEscaped(final String s, final int index) {
        return index > 0 && s.charAt(index - 1) == '\\' && !this.isEscaped(s, index - 1);
    }

    private void checkBrackets(final String s, final char close, final Stack<Character> brackets) throws SNbtDeserializeException {
        if (close == '}' && (brackets.isEmpty() || brackets.pop() != '{')) throw new SNbtDeserializeException("Unbalanced curly brackets {}: " + s);
        else if (close == ']' && (brackets.isEmpty() || brackets.pop() != '[')) throw new SNbtDeserializeException("Unbalanced square brackets []: " + s);
    }

    private String quotesToString(final Stack<Character> quotes) {
        StringBuilder s = new StringBuilder();
        for (Character c : quotes) s.append(c);
        return s.toString();
    }

}
