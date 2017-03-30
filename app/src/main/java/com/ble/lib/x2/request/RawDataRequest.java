package com.ble.lib.x2.request;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtRawData;

/**
 * *Created by hackill on 6/2/16.
 */
public class RawDataRequest extends Request<BtRawData.bt_raw_data_req, BtRawData.bt_raw_data_rsp> {
    private static final String TAG = "RawDataRequest";

    public RawDataRequest(long startTime, long endTime, @NonNull Response<BtRawData.bt_raw_data_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.RAW_DATA_REQ_VALUE, BtDataReq.DATA_TYPE.RAW_DATA_RSP_VALUE);
        Log.d(TAG, "RawDataRequest: s = " + startTime + " end = " + endTime);
        BtRawData.bt_raw_data_req req = BtRawData.bt_raw_data_req.newBuilder()
                .setStartTime((int) startTime)
                .setEndTime((int) endTime)
                .build();

        setData(req.toByteArray());
    }

    @Override
    public BtRawData.bt_raw_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtRawData.bt_raw_data_rsp.parseFrom(raw);
    }
}
