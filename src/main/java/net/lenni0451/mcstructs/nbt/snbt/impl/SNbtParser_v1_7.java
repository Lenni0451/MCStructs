package net.lenni0451.mcstructs.nbt.snbt.impl;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.snbt.ISNbtParser;
import net.lenni0451.mcstructs.nbt.snbt.SNbtParseException;
import net.lenni0451.mcstructs.nbt.tags.*;

import java.util.Stack;

public class SNbtParser_v1_7 implements ISNbtParser<INbtTag> {

    private static final String ARRAY_PATTERN = "\\[[-\\d|,\\s]+]";
    private static final String BYTE_PATTERN = "[-+]?[0-9]+[b|B]";
    private static final String SHORT_PATTERN = "[-+]?[0-9]+[s|S]";
    private static final String INT_PATTERN = "[-+]?[0-9]+";
    private static final String LONG_PATTERN = "[-+]?[0-9]+[l|L]";
    private static final String FLOAT_PATTERN = "[-+]?[0-9]*\\.?[0-9]+[f|F]";
    private static final String DOUBLE_PATTERN = "[-+]?[0-9]*\\.?[0-9]+[d|D]";
    private static final String SHORT_DOUBLE_PATTERN = "[-+]?[0-9]*\\.?[0-9]+";

    @Override
    public INbtTag parse(String s) throws SNbtParseException {
        s = s.trim();
        int tagCount = this.getTagCount(s);
        if (tagCount != 1) throw new SNbtParseException("Encountered multiple top tags, only one expected");
        INbtTag tag;
        if (s.startsWith("{")) tag = this.parse("tag", s);
        else tag = this.parse(this.find(s, true, false), this.find(s, false, false));
        return tag;
    }

    private INbtTag parse(final String name, String value) throws SNbtParseException {
        value = value.trim();
        getTagCount(value);

        if (value.startsWith("{")) {
            if (!value.endsWith("}")) throw new SNbtParseException("Unable to locate ending bracket } for: " + value);

            value = value.substring(1, value.length() - 1);
            CompoundNbt compound = new CompoundNbt();
            while (value.length() > 0) {
                String pair = this.findPair(value, false);
                if (pair.length() > 0) {
                    String subName = this.find(pair, true, false);
                    String subValue = this.find(pair, false, false);
                    compound.add(subName, this.parse(subName, subValue));

                    if (value.length() < pair.length() + 1) break;
                    char next = value.charAt(pair.length());
                    if (next != ',' && next != '{' && next != '}' && next != '[' && next != ']') {
                        throw new SNbtParseException("Unexpected troken '" + name + "' at: " + value.substring(pair.length()));
                    }
                    value = value.substring(pair.length() + 1);
                }
            }
            return compound;
        } else if (value.startsWith("[") && !value.matches(ARRAY_PATTERN)) {
            if (!value.endsWith("]")) throw new SNbtParseException("Unable to locate ending bracket ] for: " + value);

            value = value.substring(1, value.length() - 1);
            ListNbt<INbtTag> list = new ListNbt<>();
            while (value.length() > 0) {
                String pair = this.findPair(value, true);
                if (pair.length() > 0) {
                    String subName = this.find(pair, true, true);
                    String subValue = this.find(pair, false, true);
                    list.add(this.parse(subName, subValue));

                    if (value.length() < pair.length() + 1) break;
                    char next = value.charAt(pair.length());
                    if (next != ',' && next != '{' && next != '}' && next != '[' && next != ']') {
                        throw new SNbtParseException("Unexpected troken '" + name + "' at: " + value.substring(pair.length()));
                    }
                    value = value.substring(pair.length() + 1);
                }
            }
            return list;
        } else {
            return this.parsePrimitive(value);
        }
    }

    private INbtTag parsePrimitive(String value) {
        try {
            if (value.matches(DOUBLE_PATTERN)) {
                return new DoubleNbt(Double.parseDouble(value.substring(0, value.length() - 1)));
            } else if (value.matches(FLOAT_PATTERN)) {
                return new FloatNbt(Float.parseFloat(value.substring(0, value.length() - 1)));
            } else if (value.matches(BYTE_PATTERN)) {
                return new ByteNbt(Byte.parseByte(value.substring(0, value.length() - 1)));
            } else if (value.matches(LONG_PATTERN)) {
                return new LongNbt(Long.parseLong(value.substring(0, value.length() - 1)));
            } else if (value.matches(SHORT_PATTERN)) {
                return new ShortNbt(Short.parseShort(value.substring(0, value.length() - 1)));
            } else if (value.matches(INT_PATTERN)) {
                return new IntNbt(Integer.parseInt(value));
            } else if (value.matches(SHORT_DOUBLE_PATTERN)) {
                return new DoubleNbt(Double.parseDouble(value));
            } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                return new ByteNbt((byte) (Boolean.parseBoolean(value) ? 1 : 0));
            } else if (value.startsWith("[") && value.endsWith("]")) {
                if (value.length() > 2) {
                    String arrayContent = value.substring(1, value.length() - 1);
                    String[] parts = arrayContent.split(",");
                    try {
                        if (parts.length <= 1) {
                            return new IntArrayNbt(new int[]{Integer.parseInt(arrayContent.trim())});
                        } else {
                            int[] ints = new int[parts.length];
                            for (int i = 0; i < parts.length; i++) ints[i] = Integer.parseInt(parts[i].trim());
                            return new IntArrayNbt(ints);
                        }
                    } catch (NumberFormatException e) {
                        return new StringNbt(value);
                    }
                } else {
                    return new IntArrayNbt(new int[0]);
                }
            } else {
                if (value.startsWith("\"") && value.endsWith("\"") && value.length() > 2) value = value.substring(1, value.length() - 1);
                return new StringNbt(value.replace("\\\\\"", "\""));
            }
        } catch (NumberFormatException e) {
            return new StringNbt(value.replace("\\\\\"", "\""));
        }
    }

    private int getTagCount(final String s) throws SNbtParseException {
        Stack<Character> brackets = new Stack<>();
        boolean quoted = false;
        int count = 0;

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '"') {
                if (i > 0 && chars[i - 1] == '\\') {
                    if (!quoted) throw new SNbtParseException("Illegal use of \\\": " + s);
                } else {
                    quoted = !quoted;
                }
            } else if (!quoted) {
                if (c != '{' && c != '[') {
                    this.checkBrackets(s, c, brackets);
                } else {
                    if (brackets.isEmpty()) count++;
                    brackets.push(c);
                }
            }
        }

        if (quoted) throw new SNbtParseException("Unbalanced quotation: " + s);
        else if (!brackets.isEmpty()) throw new SNbtParseException("Unbalanced brackets " + this.quotesToString(brackets) + ": " + s);
        else if (count == 0 && !s.isEmpty()) return 1;
        else return count;
    }

    private String findPair(final String s, final boolean isArray) throws SNbtParseException {
        int separatorIndex = this.getCharIndex(s, ':');
        if (separatorIndex < 0 && !isArray) throw new SNbtParseException("Unable to locate name/value for string: " + s);
        int entrySeparator = this.getCharIndex(s, ',');
        if (entrySeparator >= 0 && entrySeparator < separatorIndex && !isArray) throw new SNbtParseException("Name error at: " + s);
        if (isArray && (separatorIndex < 0 || separatorIndex > entrySeparator)) separatorIndex = -1;

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
                if (i > 0 && chars[i - 1] == '\\') {
                    if (!quoted) throw new SNbtParseException("Illegal use of \\\": " + s);
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
            }
            hasContent = true;
        }
        return s.substring(0, i);
    }

    private String find(String s, final boolean name, final boolean isArray) throws SNbtParseException {
        if (isArray) {
            s = s.trim();
            if (s.startsWith("{") || s.startsWith("[")) {
                if (name) return "";
                else return s;
            }
        }

        int separatorIndex = s.indexOf(":");
        if (separatorIndex < 0) {
            if (isArray) {
                if (name) return "";
                else return s;
            } else {
                throw new SNbtParseException("Unable to locate name/value separator for string: " + s);
            }
        } else {
            if (name) return s.substring(0, separatorIndex).trim();
            else return s.substring(separatorIndex + 1);
        }
    }

    private int getCharIndex(final String s, final char wanted) {
        boolean quoted = false;

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '"') {
                if (i <= 0 || chars[i - 1] != '\\') quoted = !quoted;
            } else {
                if (c == wanted) return i;
                if (c == '{' || c == '[') return -1;
            }
        }
        return -1;
    }

    private void checkBrackets(final String s, final char close, final Stack<Character> brackets) throws SNbtParseException {
        if (close == '}' && (brackets.isEmpty() || brackets.pop() != '{')) throw new SNbtParseException("Unbalanced curly brackets {}: " + s);
        else if (close == ']' && (brackets.isEmpty() || brackets.pop() != '[')) throw new SNbtParseException("Unbalanced square brackets []: " + s);
    }

    private String quotesToString(final Stack<Character> quotes) {
        StringBuilder s = new StringBuilder();
        for (Character c : quotes) s.append(c);
        return s.toString();
    }

}
