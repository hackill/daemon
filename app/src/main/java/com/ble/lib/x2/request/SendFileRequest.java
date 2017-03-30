package com.ble.lib.x2.request;

import android.support.annotation.NonNull;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import java.nio.ByteBuffer;

import cn.ginshell.bong.ble.x2.BlePackage;
import cn.ginshell.bong.ble.x2.Response;
import cn.ginshell.bong.ble.x2.proto.BtDataReq;
import cn.ginshell.bong.ble.x2.proto.BtFile;

/**
 * 从断点发送文件
 *
 * @author hackill
 * @date 11/14/15.
 */
public class SendFileRequest extends Request<BtFile.bt_file_req, BtFile.bt_file_rsp> {

    public final static int MAX_FILE_SECTION_SIZE = 76;

    private int mStartIndex;
    private BtFile.FILE_NAME mFileName;

    /**
     * 发送文件
     *
     * @param file_name  文件类型，千万不能写错
     * @param file       文件内容
     * @param startIndex 起始文件偏移
     * @param mResponse  响应回调函数
     */
    public SendFileRequest(BtFile.FILE_NAME file_name, byte[] file, int startIndex, @NonNull Response<BtFile.bt_file_rsp> mResponse) {
        super(mResponse, BtDataReq.DATA_TYPE.FILE_REQ_VALUE, BtDataReq.DATA_TYPE.FILE_RSP_VALUE);

        mStartIndex = startIndex;
        mFileName = file_name;


        setData(file);

    }

    @Override
    public BtFile.bt_file_rsp parseFrom(byte[] raw) throws InvalidProtocolBufferException {
        return BtFile.bt_file_rsp.parseFrom(raw);
    }

    @Override
    public boolean generatePackage(int channel) {
        if (mData == null)
            return false;

        mDataLength = mData.length;
        mSendDataCount = mStartIndex;

        ByteBuffer bb = ByteBuffer.wrap(mData);

        //断点续传用
        bb.position(mStartIndex);
        byte[] tmp;
        int index = 0;
        BtFile.bt_file_req req;
        BtFile.FILE_SECTION section;
        while (bb.remaining() > 0) {
            tmp = new byte[bb.remaining() > MAX_FILE_SECTION_SIZE ? MAX_FILE_SECTION_SIZE : bb.remaining()];


            bb.get(tmp);
            BlePackage blePackage = new BlePackage(channel, mRequestOptType, bb.remaining() == 0);

            section = BtFile.FILE_SECTION.newBuilder()
                    .setDataTransferedLen(index)
                    .setData(ByteString.copyFrom(tmp))
                    .build();

            req = BtFile.bt_file_req.newBuilder()
                    .setName(mFileName)
                    .setSection(section)
                    .build();

            blePackage.setData(req.toByteArray());

            index += tmp.length;

            if (!blePackage.prepareFrameList()) {
                return false;
            }

//            mDataLength += blePackage.getFrameList().size();
            mPackageList.add(blePackage);
        }

        return true;
    }
}
