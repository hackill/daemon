package com.ble.lib.x2.request;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.Date;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtAppManualSport;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;

/**
 * *Created by hackill on 6/6/16.
 */
public class AppSportRequest extends Request<BtAppManualSport.bt_app_manual_sport, BtGetDataRsp.bt_get_data_rsp> {
    private static final String TAG = "AppSportRequest";

    public AppSportRequest(int distance, int startTime, BtAppManualSport.sport_status status, int typeCode, @NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.APP_MANUAL_SPORT_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);

        Log.d(TAG, "AppSportRequest:  st = " + startTime + " " + new Date(((long) startTime) * 1000) + " distance = " + distance);

        BtAppManualSport.bt_app_manual_sport.Builder builder = BtAppManualSport.bt_app_manual_sport.newBuilder()
                .setStartTime(startTime)
                .setStatus(status)
                .setType(BtAppManualSport.app_manual_sport_type.valueOf(typeCode));


        if (distance > 0)
            builder.setDistance(distance);


        setData(builder.build().toByteArray());
    }

    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }
}