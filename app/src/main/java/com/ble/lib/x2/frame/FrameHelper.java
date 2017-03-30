package com.ble.lib.x2.frame;

import java.nio.ByteBuffer;

import cn.ginshell.bong.ble.x2.Varint;

/**
 * @author hackill
 * @date 10/13/15.
 */
public class FrameHelper {


    /**
     * decode raw data to frame
     *
     * @param raw raw data
     * @return frame and never will be null
     */
    public static Frame decode(byte[] raw) {
        if (raw == null)
            return null;

        FrameCtl frameCtl = new FrameCtl(Varint.decode(raw, 0));

        Frame frame = null;
        switch (frameCtl.getFrameType()) {
            case START:
                frame = new StartFrame(raw, frameCtl);
                break;
            case NORMAL:
                frame = new Frame(raw, frameCtl);
                break;
            case END:
                frame = new EndFrame(raw, frameCtl);
                break;
            case PER:
                frame = new PerFrame(raw, frameCtl);
                break;
        }
        return frame;
    }


    public static byte[] encodeNormalOrEnd(int channel, int sequence, ByteBuffer dataBuffer) {
        int ctl = new FrameCtl.Builder()
                .setChannel(channel)
                .setType(Frame.FrameType.NORMAL)
                .setSequence(sequence)
                .buildCtl();

        byte[] ctlArray = new byte[Varint.size(ctl)];
        Varint.encode(ctl, ctlArray, 0);


        byte[] data;

        // TODO: 11/8/15 headSize can not bigger than FRAME_SIZE_LIMIT_MAX

        if (ctlArray.length + dataBuffer.remaining() <= Frame.FRAME_SIZE_LIMIT_MAX) {
            data = new byte[dataBuffer.remaining()];

            // TODO: 11/8/15 frame ctl 大过一个字节时可能会使ctl varint。size 变大
            ctl = new FrameCtl.Builder()
                    .setChannel(channel)
                    .setType(Frame.FrameType.END)
                    .setSequence(sequence)
                    .buildCtl();

        } else {
            data = new byte[Frame.FRAME_SIZE_LIMIT_MAX - ctlArray.length];
        }
        dataBuffer.get(data);

        ByteBuffer buffer = ByteBuffer.allocate(ctlArray.length + data.length);

        Varint.encode(ctl, ctlArray, 0);

        buffer.put(ctlArray);
        buffer.put(data);

        return buffer.array();
    }


    /**
     * before we assemble a frame we do not know whether we can put data all in a frame ,
     * so let it decided inside
     *
     * @param channel    channel to execute
     * @param sequence   frame sequence ,usually be 0 ,because this is start frame
     * @param type       command id
     * @param len        package len
     * @param crc        package crc value
     * @param dataBuffer data to put in
     * @return
     */
    public static byte[] encodeStartOrPer(int channel, int sequence, int type, int len, int crc, int packageFlag, ByteBuffer dataBuffer) {


        int ctl = new FrameCtl.Builder()
                .setChannel(channel)
                .setType(Frame.FrameType.START)
                .setSequence(sequence)
                .buildCtl();
        byte[] typeArray, lenArray, crcArray, ctlArray, packageFlagArray;

        ctlArray = new byte[Varint.size(ctl)];
        typeArray = new byte[Varint.size(type)];
        lenArray = new byte[Varint.size(len)];
        crcArray = new byte[Varint.size(crc)];
        packageFlagArray = new byte[Varint.size(packageFlag)];


        // TODO: 11/8/15 headSize can not bigger than FRAME_SIZE_LIMIT_MAX
        int headSize = typeArray.length + lenArray.length + crcArray.length + ctlArray.length + packageFlagArray.length;
        byte[] data;

        if (headSize + dataBuffer.remaining() <= Frame.FRAME_SIZE_LIMIT_MAX) {
            data = new byte[dataBuffer.remaining()];

            // TODO: 11/8/15 frame ctl 大过一个字节时可能会使ctl varint。size 变大
            ctl = new FrameCtl.Builder()
                    .setChannel(channel)
                    .setType(Frame.FrameType.PER)
                    .setSequence(sequence)
                    .buildCtl();
        } else {
            data = new byte[Frame.FRAME_SIZE_LIMIT_MAX - headSize];
        }

        Varint.encode(ctl, ctlArray, 0);
        Varint.encode(type, typeArray, 0);
        Varint.encode(len, lenArray, 0);
        Varint.encode(crc, crcArray, 0);
        Varint.encode(packageFlag, packageFlagArray, 0);
        dataBuffer.get(data);


        ByteBuffer buffer = ByteBuffer.allocate(headSize + data.length);

        buffer.put(ctlArray);
        buffer.put(typeArray);
        buffer.put(lenArray);
        buffer.put(crcArray);
        buffer.put(packageFlagArray);
        buffer.put(data);

        return buffer.array();
    }

    @Deprecated
    public static byte[] encodeEnd(int channel, int sequence, ByteBuffer dataBuffer) {
        int ctl = new FrameCtl.Builder()
                .setChannel(channel)
                .setType(Frame.FrameType.END)
                .setSequence(sequence)
                .buildCtl();
        byte[] ctlArray = new byte[Varint.size(ctl)];

        Varint.encode(ctl, ctlArray, 0);

        byte[] data;

        // TODO: 11/8/15 headSize can not bigger than FRAME_SIZE_LIMIT_MAX

        if (ctlArray.length + dataBuffer.remaining() <= Frame.FRAME_SIZE_LIMIT_MAX) {
            data = new byte[dataBuffer.remaining()];
        } else {
            data = new byte[Frame.FRAME_SIZE_LIMIT_MAX - ctlArray.length];
        }
        dataBuffer.get(data);

        ByteBuffer buffer = ByteBuffer.allocate(ctlArray.length + data.length);

        Varint.encode(ctl, ctlArray, 0);

        buffer.put(ctlArray);
        buffer.put(data);

        return buffer.array();
    }

    @Deprecated
    public static byte[] encodePer(int channel, int sequence, int type, int len, int crc, ByteBuffer dataBuffer) {
        int ctl = new FrameCtl.Builder()
                .setChannel(channel)
                .setType(Frame.FrameType.PER)
                .setSequence(sequence)
                .buildCtl();
        byte[] typeArray, lenArray, crcArray, ctlArray;

        ctlArray = new byte[Varint.size(ctl)];
        typeArray = new byte[Varint.size(type)];
        lenArray = new byte[Varint.size(len)];
        crcArray = new byte[Varint.size(crc)];

        Varint.encode(ctl, ctlArray, 0);
        Varint.encode(type, typeArray, 0);
        Varint.encode(len, lenArray, 0);
        Varint.encode(crc, crcArray, 0);

        // TODO: 11/8/15 headSize can not bigger than FRAME_SIZE_LIMIT_MAX
        int headSize = typeArray.length + lenArray.length + crcArray.length + ctlArray.length;
        byte[] data;

        if (headSize + dataBuffer.remaining() <= Frame.FRAME_SIZE_LIMIT_MAX) {
            data = new byte[dataBuffer.remaining()];
        } else {
            data = new byte[Frame.FRAME_SIZE_LIMIT_MAX - headSize];
        }
        dataBuffer.get(data);


        ByteBuffer buffer = ByteBuffer.allocate(headSize + data.length);

        buffer.put(ctlArray);
        buffer.put(typeArray);
        buffer.put(lenArray);
        buffer.put(crcArray);
        buffer.put(data);

        return buffer.array();
    }


}
