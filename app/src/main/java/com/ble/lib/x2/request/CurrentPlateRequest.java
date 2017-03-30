package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtNx2WatchSkin;

/**
 * *Created by hackill on 6/3/16.
 */
public class CurrentPlateRequest extends Request<BtDataReq.bt_data_req, BtNx2WatchSkin.bt_nx2_watch_skin_current> {

    public CurrentPlateRequest(@NonNull Response<BtNx2WatchSkin.bt_nx2_watch_skin_current> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.DATA_REQ_VALUE, BtDataReq.DATA_TYPE.NX2_WATCH_SKIN_CURRENT_VALUE);

        BtDataReq.bt_data_req req = BtDataReq.bt_data_req.newBuilder()
                .setDataType(BtDataReq.DATA_TYPE.NX2_WATCH_SKIN_CURRENT)
                .build();

        setData(req.toByteArray());
    }

    @Override
    public BtNx2WatchSkin.bt_nx2_watch_skin_current parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtNx2WatchSkin.bt_nx2_watch_skin_current.parseFrom(raw);
    }
}
