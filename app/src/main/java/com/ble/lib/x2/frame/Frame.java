package com.ble.lib.x2.frame;

import java.util.Arrays;

import cn.ginshell.bong.ble.x2.Varint;

/**
 * @author hackill
 * @date 10/13/15.
 */
public class Frame {
    public static final int FRAME_SIZE_LIMIT_MAX = 20;
    public static final int FRAME_SIZE_LIMIT_MIN = 1;


    byte[] raw;
    FrameCtl ctl;
    int pos = 0;

    public Frame(byte[] raw, FrameCtl ctl) {
        this.raw = raw;
        this.ctl = ctl;
        pos = Varint.size(ctl.getCtl());
    }

    public byte[] getData() {
        return Arrays.copyOfRange(raw, pos, raw.length);
    }

    public FrameCtl getFrameCtl() {
        return ctl;
    }

    public void setFrameCtl(FrameCtl ctl) {
        this.ctl = ctl;
    }

    public byte[] getRaw() {
        return raw;
    }


    /**
     * NORMAL 起始与结束之间的Frame 00
     * START 起始Frame 01
     * END 结束Frame 10
     * PER 起始与结束在一个的 Frame 11
     */
    public enum FrameType {
        NORMAL,
        START,
        END,
        PER
    }

}
