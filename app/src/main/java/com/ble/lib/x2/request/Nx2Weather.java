package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;
import cn.ginshell.bong.ble.x2.proto.BtNx2Weather;
import cn.ginshell.bong.model.WeatherStruct;
import cn.ginshell.bong.utils.NumberUtil;

/**
 * *Created by hackill on 6/6/16.
 */
public class Nx2Weather extends Request<BtNx2Weather.bt_nx2_weather, BtGetDataRsp.bt_get_data_rsp> {

    public Nx2Weather(boolean onlyToday, WeatherStruct weatherStruct, @NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.NX2_WEATHER_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);

        WeatherStruct.CardContent content = weatherStruct.getCardContent();

        BtNx2Weather.NOW.Builder now = BtNx2Weather.NOW.newBuilder()
                .setTime(content.getUpdateTime() / 1000)
                .setTmp(content.getCurrentTEMP());


        int pm25 = 0;
        try {
            pm25 = NumberUtil.convertToint((String) content.getOther().get("pm25"), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }


        BtNx2Weather.TODAY.Builder today = BtNx2Weather.TODAY.newBuilder()
                .setDay(content.getDay_code())
                .setDayInfo(getWeatherInfoBuilder(content.getDay_txt()))
                .setNight(content.getNight_code())
                .setNightInfo(getWeatherInfoBuilder(content.getNight_txt()))
                .setPM25(pm25);

        ArrayList<WeatherStruct.WeatherDaily> list = content.getDaily();
        ArrayList<BtNx2Weather.WEATHER> weatherList = new ArrayList<>();

        int count = 0;

        for (WeatherStruct.WeatherDaily daily : list) {
            if (count > 1)
                break;

            count++;

            BtNx2Weather.WEATHER tempWeather = BtNx2Weather.WEATHER.newBuilder()
                    .setDate(daily.getDate() / 1000)
                    .setWeather(daily.getDay_code())
                    .setTmpHigh(daily.getHighTEMP())
                    .setTmpLow(daily.getLowTEMP())
                    .setWeatherInfo(getWeatherInfoBuilder(daily.getDay_txt()))
                    .build();
            weatherList.add(tempWeather);
        }

        BtNx2Weather.bt_nx2_weather weather;

        BtNx2Weather.bt_nx2_weather.Builder builder;


        if (onlyToday) {
            builder = BtNx2Weather.bt_nx2_weather.newBuilder()
                    .setCity(ByteString.copyFrom(weatherStruct.getCityName().getBytes()))
                    .addAllWeather(weatherList);
        } else {
            builder = BtNx2Weather.bt_nx2_weather.newBuilder()
                    .setCity(ByteString.copyFrom(weatherStruct.getCityName().getBytes()))
                    .setNow(now)
                    .setToday(today);
        }

        weather = builder.build();

        setData(weather.toByteArray());
    }


    private BtNx2Weather.weather_info.Builder getWeatherInfoBuilder(String info) {
        return BtNx2Weather.weather_info.newBuilder().setStr(ByteString.copyFrom(info.getBytes()));
    }

    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }
}
