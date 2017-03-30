package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtDeviceInfo;

/**
 * @author hackill
 * @date 11/21/15.
 */
public class GetVersionRequest extends Request<BtDataReq.bt_data_req, BtDeviceInfo.bt_device_info> {
    public GetVersionRequest(@NonNull Response<BtDeviceInfo.bt_device_info> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.DATA_REQ_VALUE, BtDataReq.DATA_TYPE.DEVIDE_INFO_VALUE);
        BtDataReq.bt_data_req req = BtDataReq.bt_data_req.newBuilder()
                .setDataType(BtDataReq.DATA_TYPE.DEVIDE_INFO)
                .build();

        setData(req.toByteArray());
    }

    @Override
    public BtDeviceInfo.bt_device_info parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtDeviceInfo.bt_device_info.parseFrom(raw);
    }
}
