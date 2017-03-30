package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;
import cn.ginshell.bong.ble.x2.proto.BtVibrate;

/**
 * 震动设置
 * Created by hackill on 15/12/15.
 */
public class VibrateRequest extends Request<BtVibrate.bt_vibrate, BtGetDataRsp.bt_get_data_rsp> {

    public VibrateRequest(boolean isShock, @NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.VIBRATE_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);

        BtVibrate.bt_vibrate vibrateRequest = BtVibrate.bt_vibrate.newBuilder()
                .setOn(isShock)
                .build();
        setData(vibrateRequest.toByteArray());
    }

    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }
}
