package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;
import cn.ginshell.bong.ble.x2.proto.BtWatchSkin;

/**
 * @author hackill
 * @date 2/1/16.
 */
public class DialRequest extends Request<BtWatchSkin.bt_watch_skin, BtGetDataRsp.bt_get_data_rsp> {
    public DialRequest(int dialId, @NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.WATCH_SKIN_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);

        BtWatchSkin.bt_watch_skin skin = BtWatchSkin.bt_watch_skin.newBuilder()
                .setSkinId(dialId)
                .build();

        setData(skin.toByteArray());

    }

    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return null;
    }
}
