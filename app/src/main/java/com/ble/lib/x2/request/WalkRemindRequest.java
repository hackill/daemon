package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;
import cn.ginshell.bong.ble.x2.proto.BtWalkRemind;

/**
 * *Created by hackill on 6/6/16.
 */
public class WalkRemindRequest extends Request<BtWalkRemind.bt_walk_remind, BtGetDataRsp.bt_get_data_rsp> {

    public WalkRemindRequest(boolean isOn, Iterable<? extends Boolean> dayonList, int startHour, int startMin, int endHour, int endMin, @NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.WALK_REMIND_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);
        BtWalkRemind.bt_walk_remind req = BtWalkRemind.bt_walk_remind.newBuilder()
                .addAllDayOn(dayonList)
                .setStartHour(startHour)
                .setStartMinute(startMin)
                .setEndHour(endHour)
                .setEndMinute(endMin)
                .setOnOff(isOn)
                .build();


        setData(req.toByteArray());
    }

    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }
}
