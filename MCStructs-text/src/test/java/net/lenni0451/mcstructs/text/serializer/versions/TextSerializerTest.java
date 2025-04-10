package net.lenni0451.mcstructs.text.serializer.versions;

import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TextSerializerTest {

    protected static TextComponent DESERIALIZE_FAIL = new FailedComponent();
    private static final String[] DESERIALIZE_TESTS = {
            "{\"text\":\"test\"}",
            "{\"text\":\n//test\n\"test\"}",
            "[]",
            "null",
            "123",
            "\n",
            "\0",
            "te st",
            "{\"text\":[],\"translate\":\"test\"}",
            "{\"text\":\"test1\",\"translate\":\"test2\"}",
            "{\"text\":\"test1\",\"translate\":\"test2\",\"type\":\"translatable\"}",
            "{\"translate\":\"%s %s\",\"with\":[\"abc\",123]}",
            "{\"text\":\"test\",\"color\":\"#-FFF\"}",
            "{\"text\":\"test\",\"color\":\"#FFFFFFFF\"}",
            "'te\\$st'",
            "{\"text\":\"\",\"hoverEvent\":{\"action\":\"show_item\",\"contents\":\"minecraft:stone\"}}"
    };

    protected static String SERIALIZE_FAIL = "--test fail--";
    private static final TextComponent[] SERIALIZE_TESTS = {
            new StringComponent("test")
    };


    protected abstract TextComponent deserialize(final String json);

    protected abstract String serialize(final TextComponent component);

    @Test
    protected abstract void runTests();

    protected void executeDeserializeTests(final TextComponent... deserializeResults) {
        for (int i = 0; i < DESERIALIZE_TESTS.length; i++) {
            String test = DESERIALIZE_TESTS[i];
            TextComponent expected = deserializeResults[i];

            try {
                TextComponent deserialized = this.deserialize(test);
                assertEquals(expected, deserialized, "Test " + i + " failed: " + test);
            } catch (Exception e) {
                assertEquals(expected, DESERIALIZE_FAIL, "Test " + i + " failed: " + test);
            }
        }
    }

    protected void executeSerializeTests(final String... serializeResults) {
        for (int i = 0; i < SERIALIZE_TESTS.length; i++) {
            TextComponent test = SERIALIZE_TESTS[i];
            String expected = serializeResults[i];

            try {
                String serialized = this.serialize(test);
                assertEquals(expected, serialized, "Test " + i + " failed: " + test);
            } catch (Exception e) {
                assertEquals(expected, SERIALIZE_FAIL, "Test " + i + " failed: " + test);
            }
        }
    }


    private static class FailedComponent extends TextComponent {
        @Override
        public String asSingleString() {
            return "failed";
        }

        @Override
        public TextComponent copy() {
            return this;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof FailedComponent;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString() {
            return "failed";
        }
    }

}
