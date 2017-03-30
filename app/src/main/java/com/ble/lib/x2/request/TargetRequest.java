package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;
import cn.ginshell.bong.ble.x2.proto.BtUserTarget;

/**
 * Created by hackill on 16/1/13.
 */
public class TargetRequest extends Request<BtUserTarget.bt_user_target, BtGetDataRsp.bt_get_data_rsp> {
    public TargetRequest(@NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse, int targetSleep, int targetEnergy) {
        super(mResponse, BtDataReq.DATA_TYPE.USER_TARGET_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);

        BtUserTarget.bt_user_target target = BtUserTarget.bt_user_target.newBuilder()
                .setTargetSleep(targetSleep)
                .setTargetEnergy(targetEnergy)
                .build();
        setData(target.toByteArray());
    }

    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }
}
