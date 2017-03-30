package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;
import cn.ginshell.bong.ble.x2.proto.BtWeather;
import cn.ginshell.bong.model.WeatherStruct;
import cn.ginshell.bong.utils.NumberUtil;

/**
 * 上传天气到手表
 * Created by hackill on 15/11/26.
 */
public class WeatherUploadRequest extends Request<BtWeather.bt_weather_info, BtGetDataRsp.bt_get_data_rsp> {

    public WeatherUploadRequest(@NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse, WeatherStruct weatherStruct) {
        super(mResponse, BtDataReq.DATA_TYPE.WEATHER_INFO_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);
        setData(generateRequest(weatherStruct));
    }

    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }

    private byte[] generateRequest(WeatherStruct weatherStruct) {
        if (weatherStruct == null) {
            return null;
        }

        // 构造day
        WeatherStruct.CardContent content = weatherStruct.getCardContent();

        ArrayList<WeatherStruct.WeatherDaily> list = content.getDaily();
        ArrayList<BtWeather.WEATHER> weatherList = new ArrayList<>();
        for (WeatherStruct.WeatherDaily daily : list) {
            BtWeather.WEATHER tempWeather = BtWeather.WEATHER.newBuilder()
                    .setDate(daily.getDate() / 1000)
                    .setWeather(daily.getDay_code())
                    .setTmpHigh(daily.getHighTEMP())
                    .setTmpLow(daily.getLowTEMP())
                    .build();
            weatherList.add(tempWeather);
        }

        BtWeather.NOW now = BtWeather.NOW.newBuilder()
                .setTime(content.getUpdateTime() / 1000)
                .setTmp(content.getCurrentTEMP())
                .build();

        int pm25 = 0;
        try {
            pm25 = NumberUtil.convertToint((String) content.getOther().get("pm25"), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BtWeather.TODAY today = BtWeather.TODAY.newBuilder()
                .setPM25(pm25)
                .setDay(content.getDay_code())
                .setNight(content.getNight_code())
                .build();


        BtWeather.bt_weather_info info = BtWeather.bt_weather_info.newBuilder()
                .addAllWeather(weatherList)
                .setNow(now)
                .setToday(today)
                .build();

        return info.toByteArray();
    }
}
