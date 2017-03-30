package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtReadFlash;

/**
 * @author hackill
 * @date 12/17/15.
 */
public class ReadFlashRequest extends Request<BtReadFlash.bt_read_flash_req, BtReadFlash.bt_read_flash_rsp> {
    public ReadFlashRequest(int start, int end, @NonNull Response<BtReadFlash.bt_read_flash_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.READ_FLASH_REQ_VALUE, BtDataReq.DATA_TYPE.READ_FLASH_RSP_VALUE);

        BtReadFlash.bt_read_flash_req req = BtReadFlash.bt_read_flash_req.newBuilder()
                .setStartAddr(start)
                .setEndAddr(end)
                .build();

        setData(req.toByteArray());

    }

    @Override
    public BtReadFlash.bt_read_flash_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtReadFlash.bt_read_flash_rsp.parseFrom(raw);
    }
}
