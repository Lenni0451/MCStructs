package net.lenni0451.mcstructs.text.utils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

/**
 * Minecraft 1.12 updated from Gson 2.2.4 to 2.8.0 which changed some behavior in the JsonReader.<br>
 * This class performs checks before passing the json to the new Gson version to make sure the behavior is consistent with the old Gson version.<br>
 * The changed code is luckily only relevant for the first character of the json string.
 */
public class LegacyGsonCheck {

    private static final String JSON_EXCEPTION = "Use JsonReader.setLenient(true) to accept malformed JSON";

    public static void check(final String json, final boolean lenient) {
        if (lenient) return;
        char c = nextNonWhitespace(json);
        if (c == ']' || c == ';' || c == ',' || c == '[' || c == '{') return;
        //' or "
        throw new JsonSyntaxException(new MalformedJsonException(JSON_EXCEPTION));
    }

    private static char nextNonWhitespace(final String s) {
        char[] chars = s.toCharArray();
        int i = 0;
        while (i < chars.length) {
            char c = chars[i++];
            if (c == '\n' || c == ' ' || c == '\r' || c == '\t') continue;
            if (c == '/' || c == '#') throw new JsonSyntaxException(new MalformedJsonException(JSON_EXCEPTION));
            return c;
        }
        return '{'; //Do not throw an exception if the string is empty
    }

}
