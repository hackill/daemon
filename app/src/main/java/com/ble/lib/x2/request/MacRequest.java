package com.ble.lib.x2.request;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtMac;
import cn.ginshell.bong.utils.ParserUtils;

/**
 * @author hackill
 * @date 11/10/15.
 */
public class MacRequest extends Request<BtDataReq.bt_data_req, BtMac.bt_mac> {
    public final static String TAG = MacRequest.class.getSimpleName();

    public MacRequest(@NonNull Response<BtMac.bt_mac> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.DATA_REQ_VALUE, BtDataReq.DATA_TYPE.MAC_VALUE);

        BtDataReq.bt_data_req req = BtDataReq.bt_data_req.newBuilder()
                .setDataType(BtDataReq.DATA_TYPE.MAC)
                .build();

        setData(req.toByteArray());
    }


    @Override
    public BtMac.bt_mac parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        Log.d(TAG, "parseFrom data:" + ParserUtils.parse(raw));

        BtMac.bt_mac mac = BtMac.bt_mac.parseFrom(raw);
        Log.d(TAG, "parseFrom " + mac.toString());

        return mac;
    }

}
