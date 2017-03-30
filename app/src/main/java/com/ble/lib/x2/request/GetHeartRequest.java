package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtHeartRate;

/**
 * *Created by hackill on 6/6/16.
 */
public class GetHeartRequest extends Request<BtDataReq.bt_data_req, BtHeartRate.bt_heart_rate> {
    public GetHeartRequest(@NonNull Response<BtHeartRate.bt_heart_rate> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.DATA_REQ_VALUE, BtDataReq.DATA_TYPE.HEART_RATE_VALUE);

        BtDataReq.bt_data_req req = BtDataReq.bt_data_req.newBuilder()
                .setDataType(BtDataReq.DATA_TYPE.HEART_RATE)
                .build();

        setData(req.toByteArray());
    }

    @Override
    public BtHeartRate.bt_heart_rate parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtHeartRate.bt_heart_rate.parseFrom(raw);
    }
}
