package net.lenni0451.mcstructs.converter;

import com.google.gson.JsonArray;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.JsonConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.impl.v1_20_5.JsonConverter_v1_20_5;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.ListTag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static net.lenni0451.mcstructs.nbt.utils.NbtCodecUtils.MARKER_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConverterTest {

    @Test
    void create() {
        NbtConverter_v1_20_3 nbtConverter = new NbtConverter_v1_20_3();
        this.testList(nbtConverter);
        this.testMap(nbtConverter);

        JsonConverter_v1_20_5 jsonConverter = new JsonConverter_v1_20_5();
        this.testList(jsonConverter);
        this.testMap(jsonConverter);
    }

    <T> void testList(final DataConverter<T> converter) {
        List<T> list = converter.asList(this.createList(converter)).get();
        assertEquals(3, list.size());
        assertTrue(converter.asNumber(list.get(0)).map(n -> n.intValue() == 12).orElse(false));
        assertTrue(converter.asBoolean(list.get(1)).map(Boolean::booleanValue).orElse(false));
        assertTrue(converter.asString(list.get(2)).map(s -> s.equals("test")).orElse(false));
    }

    <T> void testMap(final DataConverter<T> converter) {
        Map<T, T> map = converter.asMap(this.createMap(converter)).get();
        assertEquals(3, map.size());
        assertTrue(converter.asNumber(map.get(converter.createString("key1"))).map(n -> n.intValue() == 12).orElse(false));
        assertTrue(converter.asBoolean(map.get(converter.createString("key2"))).map(Boolean::booleanValue).orElse(false));
        assertTrue(converter.asString(map.get(converter.createString("key3"))).map(s -> s.equals("test")).orElse(false));
    }

    <T> void testStringTypeMap(final DataConverter<T> converter) {
        Map<String, T> map = converter.asStringTypeMap(this.createMap(converter)).get();
        assertEquals(3, map.size());
        assertTrue(converter.asNumber(map.get("key1")).map(n -> n.intValue() == 12).orElse(false));
        assertTrue(converter.asBoolean(map.get("key2")).map(Boolean::booleanValue).orElse(false));
        assertTrue(converter.asString(map.get("key3")).map(s -> s.equals("test")).orElse(false));
    }

    <T> T createList(final DataConverter<T> converter) {
        return converter.mergeList(
                null,
                converter.createInt(12),
                converter.createBoolean(true),
                converter.createString("test")
        ).get();
    }

    <T> T createMap(final DataConverter<T> converter) {
        return converter.mergeMap(
                null,
                converter.createString("key1"), converter.createInt(12),
                converter.createString("key2"), converter.createBoolean(true),
                converter.createString("key3"), converter.createString("test")
        ).get();
    }

    @Test
    void convertJsonToNbt() {
        JsonArray array = new JsonArray();
        array.add(12);
        array.add(true);
        array.add("test");

        ListTag<?> list = new JsonConverter_v1_20_3().convertTo(new NbtConverter_v1_20_3(), array).asListTag();
        ListTag<CompoundTag> expected = new ListTag<>();
        expected.add(new CompoundTag().addByte(MARKER_KEY, (byte) 12));
        expected.add(new CompoundTag().addBoolean(MARKER_KEY, true));
        expected.add(new CompoundTag().addString(MARKER_KEY, "test"));

        assertEquals(expected, list);
    }

    @Test
    void convertNbtToJson() {
        ListTag<CompoundTag> list = new ListTag<>();
        list.add(new CompoundTag().addByte(MARKER_KEY, (byte) 12));
        list.add(new CompoundTag().addBoolean(MARKER_KEY, true));
        list.add(new CompoundTag().addString(MARKER_KEY, "test"));

        JsonArray array = new NbtConverter_v1_20_3().convertTo(new JsonConverter_v1_20_3(), list).getAsJsonArray();
        JsonArray expected = new JsonArray();
        expected.add(12);
        expected.add(1); //nbt has no boolean type, so it will be converted to a byte
        expected.add("test");

        assertEquals(expected, array);
    }

}
