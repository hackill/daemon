package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtNx2WatchSkin;

/**
 * *Created by hackill on 6/3/16.
 */
public class ChangePlateRequest extends Request<BtNx2WatchSkin.bt_nx2_watch_skin_switch, BtNx2WatchSkin.bt_nx2_watch_skin_rsp> {

    public ChangePlateRequest(byte[] uuid, @NonNull Response<BtNx2WatchSkin.bt_nx2_watch_skin_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.NX2_WATCH_SKIN_SWITCH_VALUE, BtDataReq.DATA_TYPE.NX2_WATCH_SKIN_RSP_VALUE);

        BtNx2WatchSkin.nx2_watch.Builder builder = BtNx2WatchSkin.nx2_watch.newBuilder()
                .setUuid(ByteString.copyFrom(uuid));

        BtNx2WatchSkin.bt_nx2_watch_skin_switch req = BtNx2WatchSkin.bt_nx2_watch_skin_switch.newBuilder()
                .setInfo(builder)
                .build();

        setData(req.toByteArray());
    }

    @Override
    public BtNx2WatchSkin.bt_nx2_watch_skin_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtNx2WatchSkin.bt_nx2_watch_skin_rsp.parseFrom(raw);
    }
}
