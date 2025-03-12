package net.lenni0451.mcstructs.converter.impl.v1_21_5;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.model.Result;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class HashConverter_v1_21_5 implements DataConverter<HashCode> {

    private static final byte TAG_EMPTY = 1;
    private static final byte TAG_MAP_START = 2;
    private static final byte TAG_MAP_END = 3;
    private static final byte TAG_LIST_START = 4;
    private static final byte TAG_LIST_END = 5;
    private static final byte TAG_BYTE = 6;
    private static final byte TAG_SHORT = 7;
    private static final byte TAG_INT = 8;
    private static final byte TAG_LONG = 9;
    private static final byte TAG_FLOAT = 10;
    private static final byte TAG_DOUBLE = 11;
    private static final byte TAG_STRING = 12;
    private static final byte TAG_BOOLEAN = 13;
    private static final byte TAG_BYTE_ARRAY_START = 14;
    private static final byte TAG_BYTE_ARRAY_END = 15;
    private static final byte TAG_INT_ARRAY_START = 16;
    private static final byte TAG_INT_ARRAY_END = 17;
    private static final byte TAG_LONG_ARRAY_START = 18;
    private static final byte TAG_LONG_ARRAY_END = 19;
    private static final byte[] EMPTY_HASH = {TAG_EMPTY};
    private static final byte[] EMPTY_MAP = {TAG_MAP_START, TAG_MAP_END};
    private static final byte[] EMPTY_LIST = {TAG_LIST_START, TAG_LIST_END};
    private static final byte[] FALSE = {TAG_BOOLEAN, 0};
    private static final byte[] TRUE = {TAG_BOOLEAN, 1};
    private static final Comparator<HashCode> HASH_CODE_COMPARATOR = Comparator.comparingLong(HashCode::padToLong);
    private static final Comparator<Map.Entry<HashCode, HashCode>> MAP_COMPARATOR = Map.Entry.<HashCode, HashCode>comparingByKey(HASH_CODE_COMPARATOR).thenComparing(Map.Entry.comparingByValue(HASH_CODE_COMPARATOR));
    private static final String TAG_ERROR = "Unable to convert HashCode to %s";

    public static final HashConverter_v1_21_5 CRC32C = new HashConverter_v1_21_5(Hashing.crc32c());

    private final HashFunction hashFunction;
    private final HashCode emptyHash;
    private final HashCode emptyMapHash;
    private final HashCode emptyListHash;
    private final HashCode trueHash;
    private final HashCode falseHash;

    public HashConverter_v1_21_5(final HashFunction hashFunction) {
        this.hashFunction = hashFunction;
        this.emptyHash = hashFunction.hashBytes(EMPTY_HASH);
        this.emptyMapHash = hashFunction.hashBytes(EMPTY_MAP);
        this.emptyListHash = hashFunction.hashBytes(EMPTY_LIST);
        this.trueHash = hashFunction.hashBytes(TRUE);
        this.falseHash = hashFunction.hashBytes(FALSE);
    }

    @Override
    public <N> N convertTo(DataConverter<N> converter, @Nullable HashCode element) {
        throw new UnsupportedOperationException("Can't convert HashCode to other types");
    }

    @Override
    public HashCode empty() {
        return this.emptyHash;
    }

    @Override
    public HashCode emptyList() {
        return this.emptyListHash;
    }

    @Override
    public HashCode emptyMap() {
        return this.emptyMapHash;
    }

    @Override
    public HashCode createBoolean(boolean value) {
        return value ? this.trueHash : this.falseHash;
    }

    @Override
    public HashCode createByte(byte value) {
        return this.hashFunction.newHasher(2).putByte(TAG_BYTE).putByte(value).hash();
    }

    @Override
    public HashCode createShort(short value) {
        return this.hashFunction.newHasher(3).putByte(TAG_SHORT).putShort(value).hash();
    }

    @Override
    public HashCode createInt(int value) {
        return this.hashFunction.newHasher(5).putByte(TAG_INT).putInt(value).hash();
    }

    @Override
    public HashCode createLong(long value) {
        return this.hashFunction.newHasher(9).putByte(TAG_LONG).putLong(value).hash();
    }

    @Override
    public HashCode createFloat(float value) {
        return this.hashFunction.newHasher(5).putByte(TAG_FLOAT).putFloat(value).hash();
    }

    @Override
    public HashCode createDouble(double value) {
        return this.hashFunction.newHasher(9).putByte(TAG_DOUBLE).putDouble(value).hash();
    }

    @Override
    public HashCode createString(String value) {
        return this.hashFunction.newHasher().putByte(TAG_STRING).putInt(value.length()).putUnencodedChars(value).hash();
    }

    @Override
    public HashCode createNumber(Number number) {
        if (number instanceof Byte) return this.createByte((byte) number);
        else if (number instanceof Short) return this.createShort((short) number);
        else if (number instanceof Integer) return this.createInt((int) number);
        else if (number instanceof Long) return this.createLong((long) number);
        else if (number instanceof Float) return this.createFloat((float) number);
        else if (number instanceof Double) return this.createDouble((double) number);
        else return this.createDouble(number.doubleValue());
    }

    @Override
    public HashCode createList(List<HashCode> values) {
        Hasher hasher = this.hashFunction.newHasher().putByte(TAG_LIST_START);
        for (HashCode value : values) hasher.putBytes(value.asBytes());
        return hasher.putByte(TAG_LIST_END).hash();
    }

    @Override
    public Result<HashCode> mergeList(@Nullable HashCode list, List<HashCode> values) {
        return Result.error("Can't merge already hashed lists");
    }

    @Override
    public HashCode createByteArray(byte[] value) {
        return this.hashFunction.newHasher(value.length + 2)
                .putByte(TAG_BYTE_ARRAY_START)
                .putBytes(value)
                .putByte(TAG_BYTE_ARRAY_END)
                .hash();
    }

    @Override
    public HashCode createIntArray(int[] value) {
        Hasher hasher = this.hashFunction.newHasher(value.length * 4 + 2).putByte(TAG_INT_ARRAY_START);
        for (int i : value) hasher.putInt(i);
        return hasher.putByte(TAG_INT_ARRAY_END).hash();
    }

    @Override
    public HashCode createLongArray(long[] value) {
        Hasher hasher = this.hashFunction.newHasher(value.length * 8 + 2).putByte(TAG_LONG_ARRAY_START);
        for (long l : value) hasher.putLong(l);
        return hasher.putByte(TAG_LONG_ARRAY_END).hash();
    }

    @Override
    public HashCode createUnsafeMap(Map<HashCode, HashCode> values) {
        return this.createMergedMap(values).getOrThrow();
    }

    @Override
    public Result<HashCode> createMergedMap(Map<HashCode, HashCode> values) {
        Hasher hasher = this.hashFunction.newHasher();
        hasher.putByte(TAG_MAP_START);
        values.entrySet().stream()
                .sorted(MAP_COMPARATOR)
                .forEach(entry -> hasher.putBytes(entry.getKey().asBytes())
                        .putBytes(entry.getValue().asBytes()));
        return Result.success(hasher.putByte(TAG_MAP_END).hash());
    }

    @Override
    public Result<HashCode> mergeMap(@Nullable HashCode map, Map<HashCode, HashCode> values) {
        return Result.error("Can't merge already hashed maps");
    }

    @Override
    public Result<Boolean> asBoolean(HashCode element) {
        return Result.error(String.format(TAG_ERROR, "boolean"));
    }

    @Override
    public Result<Number> asNumber(HashCode element) {
        return Result.error(String.format(TAG_ERROR, "number"));
    }

    @Override
    public Result<String> asString(HashCode element) {
        return Result.error(String.format(TAG_ERROR, "string"));
    }

    @Override
    public Result<List<HashCode>> asList(HashCode element) {
        return Result.error(String.format(TAG_ERROR, "list"));
    }

    @Override
    public Result<Map<HashCode, HashCode>> asMap(HashCode element) {
        return Result.error(String.format(TAG_ERROR, "map"));
    }

    @Override
    public Result<Map<String, HashCode>> asStringTypeMap(HashCode element) {
        return Result.error(String.format(TAG_ERROR, "string type map"));
    }

    @Override
    public Result<byte[]> asByteArray(HashCode element) {
        return Result.error(String.format(TAG_ERROR, "byte array"));
    }

    @Override
    public Result<int[]> asIntArray(HashCode element) {
        return Result.error(String.format(TAG_ERROR, "int array"));
    }

    @Override
    public Result<long[]> asLongArray(HashCode element) {
        return Result.error(String.format(TAG_ERROR, "long array"));
    }

}
