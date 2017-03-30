package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.STM32CRC;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtFile;

/**
 * 获取手表文件信息，为了支持断点续传
 *
 * @author hackill
 * @date 11/14/15.
 */
public class FileInfoRequest extends Request<BtFile.bt_file_req, BtFile.bt_file_rsp> {

    public FileInfoRequest(BtFile.FILE_NAME file_name, byte[] file, @NonNull Response<BtFile.bt_file_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.FILE_REQ_VALUE, BtDataReq.DATA_TYPE.FILE_RSP_VALUE);


        STM32CRC crc = new STM32CRC();

        crc.update(file);

        BtFile.FILE_INFO info = BtFile.FILE_INFO.newBuilder()
                .setFileSize(file.length)
                .setFileCrc(crc.getValue())
                .build();


        BtFile.bt_file_req req = BtFile.bt_file_req.newBuilder()
                .setName(file_name)
                .setInfo(info)
                .build();

        setData(req.toByteArray());

    }

    @Override
    public BtFile.bt_file_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtFile.bt_file_rsp.parseFrom(raw);
    }
}
