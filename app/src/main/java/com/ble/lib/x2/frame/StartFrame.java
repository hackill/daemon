package com.ble.lib.x2.frame;

import cn.ginshell.bong.ble.x2.Varint;

/**
 * @author hackill
 * @date 10/13/15.
 */
public class StartFrame extends Frame {
    public final static int FLAG_END_PACKAGE = 0x00000001;

    int type;
    int len;
    int crc;
    int packageFlag;

    public StartFrame(byte[] raw, FrameCtl ctl) {
        super(raw, ctl);
        type = Varint.decode(raw, pos);
        pos += Varint.size(type);

        len = Varint.decode(raw, pos);
        pos += Varint.size(len);

        crc = Varint.decode(raw, pos);
        pos += Varint.size(crc);

        packageFlag = Varint.decode(raw, pos);
        pos += Varint.size(packageFlag);
    }


    public int getOptType() {
        return type;
    }

    public int getLen() {
        return len;
    }

    public int getCrc() {
        return crc;
    }

    public int getPackageFlag() {
        return packageFlag;
    }

}
