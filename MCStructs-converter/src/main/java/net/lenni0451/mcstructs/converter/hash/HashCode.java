package net.lenni0451.mcstructs.converter.hash;

public class HashCode {

    private static final String HEX_CHARS = "0123456789abcdef";

    public static HashCode of(final int hashCode) {
        return new HashCode(hashCode);
    }


    private final int hashCode;

    private HashCode(final int hashCode) {
        this.hashCode = hashCode;
    }

    public byte[] asBytes() {
        return new byte[]{(byte) this.hashCode, (byte) (this.hashCode >> 8), (byte) (this.hashCode >> 16), (byte) (this.hashCode >> 24)};
    }

    public int asInt() {
        return this.hashCode;
    }

    public long asLong() {
        return this.hashCode & 0b11111111_11111111_11111111_11111111L;
    }

    @Override
    public String toString() {
        byte[] bytes = this.asBytes();
        char[] chars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            chars[i * 2] = HEX_CHARS.charAt((b >> 4) & 0XF);
            chars[i * 2 + 1] = HEX_CHARS.charAt(b & 0XF);
        }
        return new String(chars);
    }

}
