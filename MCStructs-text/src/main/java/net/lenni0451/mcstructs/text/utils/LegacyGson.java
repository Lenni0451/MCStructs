package net.lenni0451.mcstructs.text.utils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

/**
 * Minecraft 1.12 updated from Gson 2.2.4 to 2.8.0 which changed some behavior in the JsonReader.<br>
 * This class performs checks and modifications before passing the json to the new Gson version to make sure the behavior is consistent with the old Gson version.
 */
public class LegacyGson {

    private static final String MODERN_ALLOWED_ESCAPES = "utbnrf\n'\"\\/"; //From JsonReader#readEscapeCharacter()
    private static final String LENIENT_EXCEPTION = "Use JsonReader.setLenient(true) to accept malformed JSON";

    public static String fixInvalidEscapes(final String json) {
        StringBuilder fixedJson = new StringBuilder();
        boolean quote = false;
        char quoteChar = 0;
        boolean escape = false;
        for (char c : json.toCharArray()) {
            if (!escape) {
                if (c == '"' || c == '\'') {
                    if (!quote) {
                        quote = true;
                        quoteChar = c;
                    } else if (quoteChar == c) {
                        quote = false;
                    }
                } else if (quote && c == '\\') {
                    escape = true;
                }
            } else {
                if (MODERN_ALLOWED_ESCAPES.indexOf(c) == -1) {
                    //If the escape is not supported by the new Gson version we remove the escape character
                    //This will mimic the behavior of the old Gson version
                    fixedJson.setLength(fixedJson.length() - 1);
                }
                escape = false;
            }
            fixedJson.append(c);
        }
        return fixedJson.toString();
    }

    public static void checkStartingType(final String json, final boolean lenient) {
        if (lenient) return;
        char c = nextNonWhitespace(json);
        if (c == ']' || c == ';' || c == ',' || c == '[' || c == '{') return;
        //' or "
        throw new JsonSyntaxException(new MalformedJsonException(LENIENT_EXCEPTION));
    }

    private static char nextNonWhitespace(final String s) {
        char[] chars = s.toCharArray();
        int i = 0;
        while (i < chars.length) {
            char c = chars[i++];
            if (c == '\n' || c == ' ' || c == '\r' || c == '\t') continue;
            if (c == '/' || c == '#') throw new JsonSyntaxException(new MalformedJsonException(LENIENT_EXCEPTION));
            return c;
        }
        return '{'; //Do not throw an exception if the string is empty
    }

}
