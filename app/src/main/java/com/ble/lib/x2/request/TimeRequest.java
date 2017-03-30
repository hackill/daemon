package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.Date;
import java.util.TimeZone;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;
import cn.ginshell.bong.ble.x2.proto.BtTime;

/**
 * @author hackill
 * @date 11/19/15.
 */
public class TimeRequest extends Request<BtTime.bt_time, BtGetDataRsp.bt_get_data_rsp> {
    public TimeRequest(@NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.TIME_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);

        TimeZone timeZone = TimeZone.getDefault();
        Date date = new Date();

//        Log.d(TAG, "TimeRequest ctm:" + System.currentTimeMillis() / 1000 + " inDaylight:" + timeZone.inDaylightTime(date) + " rawOffset:" + timeZone.getRawOffset() / 1000);

        BtTime.bt_time time = BtTime.bt_time.newBuilder()
                .setTimeStamp(System.currentTimeMillis() / 1000)
                .setIsDaylightSaveTime(timeZone.inDaylightTime(date))
                .setTimeZoneSecond(timeZone.getRawOffset() / 1000)
                .build();

        setData(time.toByteArray());

    }


    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }
}
