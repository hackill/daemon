package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtCommand;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;

/**
 * @author hackill
 * @date 11/13/15.
 */
public class CommandRequest extends Request<BtCommand.bt_command_req, BtCommand.bt_command_rsp> {

    public CommandRequest(BtCommand._command command, @NonNull Response<BtCommand.bt_command_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.COMMAND_REQ_VALUE, BtDataReq.DATA_TYPE.COMMAND_RSP_VALUE);


        BtCommand.bt_command_req req = BtCommand.bt_command_req.newBuilder()
                .setCommandId(command)
                .build();

        setData(req.toByteArray());
    }

    @Override
    public BtCommand.bt_command_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtCommand.bt_command_rsp.parseFrom(raw);
    }
}
