package com.ble.lib.x2;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.proto.BtAck;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;

/**
 * @author hackill
 * @date 11/10/15.
 */
public class AckPackage extends BlePackage {

    private static final int ACK_OPT_CODE = BtDataReq.DATA_TYPE.ACK_VALUE;

    public AckPackage(int mChannel) {
        super(mChannel, ACK_OPT_CODE, true);
    }


    public static int getAckOptCode() {
        return ACK_OPT_CODE;
    }

    public BtAck.bt_ack getAck() throws InvalidProtocolBufferException {
        return BtAck.bt_ack.parseFrom(getData());
    }

}
