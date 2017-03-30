package com.ble.lib.x2;

/**
 * @author hackill
 * @date 10/13/15.
 */
public class Varint {
    public static int decode(byte[] data, int off) {
        int pos = off;
        int firstByte = (int) data[pos++];

        if ((firstByte & 0x80) == 0) {
            return firstByte;
        }

        int result = firstByte & 0x7f;
        int offset = 7;
        for (; offset < 32; offset += 7) {
            byte b = data[pos++];
            result |= (b & 0x7f) << offset;
            if ((b & 0x80) == 0) {
                return result;
            }
        }
        for (; offset < 64; offset += 7) {
            byte b = data[pos++];
            if ((b & 0x80) == 0) {
                return result;
            }
        }
        return result;
    }

    public static int size(int value) {
        if ((value & (0xffffffff << 7)) == 0)
            return 1;
        if ((value & (0xffffffff << 14)) == 0)
            return 2;
        if ((value & (0xffffffff << 21)) == 0)
            return 3;
        if ((value & (0xffffffff << 28)) == 0)
            return 4;
        return 5;
    }

    public static int encode(int value, byte[] data, int offset) {
        int pos = offset;
        while (true) {
            if ((value & ~0x7F) == 0) {
                data[pos++] = (byte) value;
                break;
            } else {
                data[pos++] = (byte) ((value & 0x7F) | 0x80);
                value >>>= 7;
            }
        }
        return pos - offset;
    }
}
