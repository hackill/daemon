package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;
import cn.ginshell.bong.ble.x2.proto.BtQuietTime;
import cn.ginshell.bong.model.card.SetupModel;

/**
 * Created by hackill on 15/12/15.
 */
public class QuietTimeRequest extends Request<BtQuietTime.bt_quiet_time, BtGetDataRsp.bt_get_data_rsp> {

    public QuietTimeRequest(@NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse, SetupModel setupModel) {
        super(mResponse, BtDataReq.DATA_TYPE.QUIET_TIME_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);

        BtQuietTime.bt_quiet_time timeRequest = BtQuietTime.bt_quiet_time.newBuilder()
                .setOn(setupModel.isNoDisturb())
                .setStartHour(setupModel.getStartHour())
                .setStartMin(setupModel.getStartMin())
                .setEndHour(setupModel.getEndHour())
                .setEndMin(setupModel.getEndMin())
                .build();

        setData(timeRequest.toByteArray());
    }

    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }
}
