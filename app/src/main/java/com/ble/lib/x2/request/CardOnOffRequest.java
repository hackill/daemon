package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.BongApp;
import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;
import cn.ginshell.bong.ble.x2.proto.BtNx2CardOnOff;
import cn.ginshell.bong.common.SettingType;
import cn.ginshell.bong.setting.setting.SettingCollection;

/**
 * *Created by hackill on 6/2/16.
 */
public class CardOnOffRequest extends Request<BtNx2CardOnOff.bt_nx2_card_on_off, BtGetDataRsp.bt_get_data_rsp> {
    public CardOnOffRequest(SettingType type, boolean enable, @NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.NX2_CARD_ON_OFF_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);

        SettingCollection settingCollection = BongApp.getBongComponent().getSettingCollection();
        int set = 0x00000000;

        if (settingCollection.getWeather().isOnOff() && settingCollection.getWeather().getType() != type.getCode())
            set |= BtNx2CardOnOff.NX2_CARD.WEATHER_VALUE;


        if (settingCollection.getStartSport().isOnOff() && settingCollection.getStartSport().getType() != type.getCode())
            set |= BtNx2CardOnOff.NX2_CARD.MANUAL_SPORT_VALUE;


        if (settingCollection.getHeartMeasure().isOnOff() && settingCollection.getHeartMeasure().getType() != type.getCode())
            set |= BtNx2CardOnOff.NX2_CARD.MANUAL_HEART_MEASUARE_VALUE;

        if (settingCollection.getAutoHeartRate().isOnOff() && settingCollection.getAutoHeartRate().getType() != type.getCode())
            set |= BtNx2CardOnOff.NX2_CARD.AUTO_HEART_MEASUARE_VALUE;

        if (settingCollection.getTimer().isOnOff() && settingCollection.getTimer().getType() != type.getCode())
            set |= BtNx2CardOnOff.NX2_CARD.TIMER_VALUE;

        if (settingCollection.getMsgReminder().isOnOff() && settingCollection.getMsgReminder().getType() != type.getCode())
            set |= BtNx2CardOnOff.NX2_CARD.MESSAGE_REMIND_VALUE;

        if (settingCollection.getCallReminder().isOnOff() && settingCollection.getCallReminder().getType() != type.getCode())
            set |= BtNx2CardOnOff.NX2_CARD.CALL_REMIND_VALUE;

        if (enable) {
            switch (type) {
                case CARD_WEATHER:
                    set |= BtNx2CardOnOff.NX2_CARD.WEATHER_VALUE;
                    break;
                case START_SPORT:
                    set |= BtNx2CardOnOff.NX2_CARD.MANUAL_SPORT_VALUE;
                    break;
                case CARD_HEART_RATE:
                    set |= BtNx2CardOnOff.NX2_CARD.MANUAL_HEART_MEASUARE_VALUE;
                    break;
                case AUTO_HEART_RATE:
                    set |= BtNx2CardOnOff.NX2_CARD.AUTO_HEART_MEASUARE_VALUE;
                    break;
                case CARD_TIMER:
                    set |= BtNx2CardOnOff.NX2_CARD.TIMER_VALUE;
                    break;
                case CARD_MSG_REMINDER:
                    set |= BtNx2CardOnOff.NX2_CARD.MESSAGE_REMIND_VALUE;
                    break;
                case CALL_REMINDER:
                    set |= BtNx2CardOnOff.NX2_CARD.CALL_REMIND_VALUE;
                    break;

            }
        }


        BtNx2CardOnOff.bt_nx2_card_on_off bncoo = BtNx2CardOnOff.bt_nx2_card_on_off.newBuilder()
                .setSet(set)
                .build();

        setData(bncoo.toByteArray());
    }

    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }
}
