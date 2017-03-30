package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtCardOrder;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;

/**
 * card排序
 * Created by hackill on 15/12/21.
 */
public class CardOrderRequest extends Request<BtCardOrder.bt_card_order, BtGetDataRsp.bt_get_data_rsp> {

    public CardOrderRequest(@NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse, @NonNull ArrayList<Integer> orderList) {
        super(mResponse, BtDataReq.DATA_TYPE.CARD_ORDER_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);


        BtCardOrder.bt_card_order order = BtCardOrder.bt_card_order.newBuilder().
                addAllCardid(orderList).
                build();

        setData(order.toByteArray());
    }

    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }
}
