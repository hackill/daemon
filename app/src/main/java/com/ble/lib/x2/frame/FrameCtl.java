package com.ble.lib.x2.frame;

/**
 * @author hackill
 * @date 10/13/15.
 */
public class FrameCtl {
    public final static int MAX_FRAME_SEQUENCE = 8;

    int ctl;

    public FrameCtl(int ctl) {
        this.ctl = ctl;
    }

    public int getCtl() {
        return ctl;
    }

    public int getChannel() {
        return (ctl & 0x60) >> 5;
    }

    public Frame.FrameType getFrameType() {
        return Frame.FrameType.values()[getFrameTypeCode()];
    }

    public int getFrameTypeCode() {
        return (ctl & 0x18) >> 3;
    }

    public int getSequence() {
        return (ctl & 0x07);
    }

    public void setCtl(int ctl) {
        this.ctl = ctl;
    }

    public void setChannel(int channel) {
        ctl |= (channel & 0x00000003) << 5;
    }

    /**
     * sequence 0~4
     *
     * @param sequence more than 4 will mode {@link FrameCtl#MAX_FRAME_SEQUENCE}
     */
    public void setSequence(int sequence) {
        sequence = sequence % MAX_FRAME_SEQUENCE;

        ctl |= (sequence & 0x00000007);
    }

    public void setFrameType(Frame.FrameType type) {
        ctl |= (type.ordinal() & 0x00000003) << 3;
    }


    public static final class Builder {
        int channel;
        Frame.FrameType type;
        int sequence;

        /**
         * @param channel 0~3
         * @return
         */
        public Builder setChannel(int channel) {
            this.channel = channel;
            return this;
        }

        public Builder setType(Frame.FrameType type) {
            this.type = type;
            return this;
        }

        /**
         * @param sequence 0~4
         * @return
         */
        public Builder setSequence(int sequence) {
            this.sequence = sequence;
            return this;
        }

        public FrameCtl build() {
            FrameCtl frameCtl = new FrameCtl(0);

            frameCtl.setChannel(channel);
            frameCtl.setFrameType(type);
            frameCtl.setSequence(sequence);

            return frameCtl;
        }

        public int buildCtl() {
            return build().getCtl();
        }
    }
}