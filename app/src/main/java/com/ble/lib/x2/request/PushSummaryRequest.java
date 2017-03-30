package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;
import cn.ginshell.bong.ble.x2.proto.BtSummary;

/**
 * *Created by hackill on 6/6/16.
 */
public class PushSummaryRequest extends Request<BtSummary.bt_summary, BtGetDataRsp.bt_get_data_rsp> {

    /**
     * @param calorie   unit j
     * @param distance  unit m
     * @param step      unit one
     * @param mResponse response
     */
    public PushSummaryRequest(int calorie, int distance, int step, @NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.SUMMARY_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);

        BtSummary.bt_summary summary = BtSummary.bt_summary.newBuilder()
                .setCalorie(calorie)
                .setDistance(distance)
                .setStep(step)
                .build();


        setData(summary.toByteArray());


    }

    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }
}
