package com.ble.lib.x2.request;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;
import cn.ginshell.bong.ble.x2.proto.BtNotify;

/**
 * @author hackill
 * @date 12/13/15.
 */
public class NotificationRequest extends Request<BtNotify.bt_notify, BtGetDataRsp.bt_get_data_rsp> {
    public NotificationRequest(BtNotify.bt_notify notify) {

        super(mDefaultResponse, BtDataReq.DATA_TYPE.NOTIFY_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);

        setData(notify.toByteArray());
    }


    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }


    private static Response<BtGetDataRsp.bt_get_data_rsp> mDefaultResponse = new Response<BtGetDataRsp.bt_get_data_rsp>() {
        @Override
        public void onReceive(List<BtGetDataRsp.bt_get_data_rsp> rsp) {

        }

        @Override
        public void onReceivePerPackage(BtGetDataRsp.bt_get_data_rsp perRsp) {

        }

        @Override
        public void onSendPerPackage(int index, int total) {

        }

        @Override
        public void onError(Exception e) {

        }
    };
}
