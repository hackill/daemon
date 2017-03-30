package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtAlarm;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;
import cn.ginshell.bong.model.card.AlarmModel;

/**
 * 添加、删除闹钟
 * Created by hackill on 15/12/1.
 */
public class AlarmUploadRequest extends Request<BtAlarm.bt_alarm, BtGetDataRsp.bt_get_data_rsp> {

    public AlarmUploadRequest(AlarmModel alarmModel, boolean isDelete, @NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.ALARM_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);

        byte[] result = generateCmd(alarmModel, isDelete);
        if (result != null) {
            setData(result);
        }
    }

    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }

    private byte[] generateCmd(AlarmModel alarmModel, boolean isDelete) {
        try {
            int action = isDelete ? 1 : 0;
            BtAlarm.bt_alarm alarm = BtAlarm.bt_alarm.newBuilder()
                    .setAlarmid(alarmModel.getAlarmID())
                    .setOn(alarmModel.isOn())
                    .setLazyMode(alarmModel.isLazyMode())
                    .setHour(alarmModel.getHour())
                    .setMin(alarmModel.getMinute())
                    .addAllDayOn(alarmModel.getDayOn())
                    .setLstime(alarmModel.getLstime())
                    .setInfo(ByteString.copyFrom(alarmModel.getInfo().getBytes()))
                    .setAction(action)
                    .build();
            return alarm.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
