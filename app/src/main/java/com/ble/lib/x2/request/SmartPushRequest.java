package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;
import cn.ginshell.bong.ble.x2.proto.BtSmartPush;

/**
 * smart 智能开关 通用设置
 * Created by hackill on 15/12/25.
 */
public class SmartPushRequest extends Request<BtSmartPush.bt_smart_push, BtGetDataRsp.bt_get_data_rsp> {

    public SmartPushRequest(@NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse, HashMap<Integer, Boolean> config) {
        super(mResponse, BtDataReq.DATA_TYPE.SMART_PUSH_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);

        // 构造smart集合
        ArrayList<BtSmartPush.smart_push_config> configList = new ArrayList<>();
        Iterator iterator = config.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Boolean> entry = (Map.Entry<Integer, Boolean>) iterator.next();
            BtSmartPush.smart_push_config smart_push_config = BtSmartPush.smart_push_config
                    .newBuilder().setCardID(entry.getKey()).setOn(entry.getValue()).build();
            configList.add(smart_push_config);
        }
        BtSmartPush.bt_smart_push push = BtSmartPush.bt_smart_push.newBuilder()
                .addAllConfig(configList)
                .build();

        setData(push.toByteArray());
    }


    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }


}
