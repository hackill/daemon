package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.Calendar;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtAlg;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;

/**
 * @author hackill
 * @date 11/12/15.
 */
public class SportDataRequest extends Request<BtAlg.bt_alg_req, BtAlg.bt_alg_rsp> {

    public SportDataRequest(@NonNull Response<BtAlg.bt_alg_rsp> mResponse, long startTimeS, long endTimes) {
        super(mResponse, BtDataReq.DATA_TYPE.ALGDATA_REQ_VALUE, BtDataReq.DATA_TYPE.ALGDATA_RSP_VALUE);


        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.HOUR_OF_DAY, -8);

        BtAlg.bt_alg_req req = BtAlg.bt_alg_req.newBuilder()
                .setStartTime(startTimeS)
                .setEndTime(endTimes)
                .build();

        setData(req.toByteArray());

    }

    @Override
    public BtAlg.bt_alg_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtAlg.bt_alg_rsp.parseFrom(raw);
    }
}
