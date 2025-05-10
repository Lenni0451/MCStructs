package net.lenni0451.mcstructs.converter.hash.function;

import net.lenni0451.mcstructs.converter.hash.HashCode;
import net.lenni0451.mcstructs.converter.hash.HashFunction;

public class CRC32C extends HashFunction {

    private static final int[] CRC32C_TABLE = new int[256];

    static {
        for (int i = 0; i < 256; i++) {
            int crc = i;
            for (int j = 0; j < 8; j++) {
                if ((crc & 1) == 1) {
                    crc = (crc >>> 1) ^ 0x82F63B78;
                } else {
                    crc >>>= 1;
                }
            }
            CRC32C_TABLE[i] = crc;
        }
    }

    @Override
    public HashCode hash(byte[] data) {
        int crc = ~0;
        for (byte b : data) {
            int index = (crc ^ b) & 0xFF;
            crc = (crc >>> 8) ^ CRC32C_TABLE[index];
        }
        return HashCode.of(~crc);
    }

}
