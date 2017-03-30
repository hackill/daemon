package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtGetDataRsp;
import cn.ginshell.bong.ble.x2.proto.BtUserInfo;
import cn.ginshell.bong.model.User;
import cn.ginshell.bong.utils.NumberUtil;

/**
 * Created by hackill on 15/12/3.
 */
public class UserInfoRequest extends Request<BtUserInfo.bt_user_info, BtGetDataRsp.bt_get_data_rsp> {

    public UserInfoRequest(@NonNull Response<BtGetDataRsp.bt_get_data_rsp> mResponse, User user) {
        super(mResponse, BtDataReq.DATA_TYPE.USER_INFO_VALUE, BtDataReq.DATA_TYPE.GET_DATA_RSP_VALUE);

        BtUserInfo.GENDER sex;
        if ("1".equals(user.getGender())) {
            sex = BtUserInfo.GENDER.MALE;
        } else {
            sex = BtUserInfo.GENDER.FEMALE;
        }

        BtUserInfo.WEAR_POSITION position;
        if (user.getWearPosition() == 0) {
            position = BtUserInfo.WEAR_POSITION.LEFT;
        } else {
            position = BtUserInfo.WEAR_POSITION.RIGHT;
        }

        BtUserInfo.bt_user_info userInfo = BtUserInfo.bt_user_info.newBuilder()
                .setHeight(user.getHeight()) //cm
                .setWeight(NumberUtil.parseToInt(user.getWeight() * 1000)) //g
                .setBirthday(user.getBirthday())
                .setWearPosition(position)
                .setGender(sex)
                .build();
        setData(userInfo.toByteArray());
    }

    @Override
    public BtGetDataRsp.bt_get_data_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtGetDataRsp.bt_get_data_rsp.parseFrom(raw);
    }
}
