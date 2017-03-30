package com.ble.lib.x2.request;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

import cn.ginshell.bong.BongApp;
import cn.ginshell.bong.ble.BaseRequest;
import cn.ginshell.bong.ble.x2.BlePackage;
import cn.ginshell.bong.ble.x2.Response;

/**
 * 管理 操作id ，req合成类型，返回结果解析类型
 *
 * @author hackill
 * @date 11/9/15.
 */
public abstract class Request<T extends GeneratedMessageLite, R extends GeneratedMessageLite> extends BaseRequest {
    private static final String TAG = "Request";


    protected Response<R> mResponse;

    protected byte[] mData;

    protected int mRequestOptType, mResponseOptType;

    protected List<BlePackage> mPackageList = new ArrayList<>();

    private List<R> mResultList = new ArrayList<>();

    protected int mDataLength, mSendDataCount;

    protected Handler mHandler = BongApp.getBongComponent().getHandler();

    /**
     * 断点续传
     */
    private int mStartPackage = 0;

    public Request(@NonNull Response<R> mResponse, int requestOptType, int responseOptType) {
        this.mResponse = mResponse;
        mRequestOptType = requestOptType;
        mResponseOptType = responseOptType;
    }

    /**
     * this method can not be reserve after set false
     */
    @Override
    public void discardResult() {
        Log.d(TAG, "discardResult() called with: " + "");
        mResponse = null;
    }

    @Override
    public boolean isCanceled() {
        return mResponse == null;
    }

    public void deliverPerResponse(BlePackage blePackage) throws InvalidProtocolBufferException {
        final Response<R> r = mResponse;

        if (r == null)
            return;

        final R msg = parseFrom(blePackage.getData());
        mResultList.add(msg);


        r.onReceivePerPackage(msg);
    }

    public void deliverAllResponse() {
        final Response<R> r = mResponse;

        if (r == null)
            return;

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                r.onReceive(mResultList);
            }
        });
    }

    public void deliverPerRequestSend(BlePackage blePackage) {
        final Response<R> r = mResponse;

        if (r == null)
            return;

        mSendDataCount += SendFileRequest.MAX_FILE_SECTION_SIZE;

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                r.onSendPerPackage(mSendDataCount, mDataLength);
            }
        });
    }

    @Override
    public void deliverError(final Exception e) {
        final Response<R> r = mResponse;

        if (r == null)
            return;

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                r.onError(e);

            }
        });
    }

    public int getStartPackage() {
        return mStartPackage;
    }

    public void setStartPackage(int mStartPackage) {
        this.mStartPackage = mStartPackage;
    }


    public void setResponse(Response<R> mResponse) {
        this.mResponse = mResponse;
    }

    public byte[] getData() {
        return mData;
    }

    public void setData(byte[] mData) {
        this.mData = mData;
    }

    public int getRequestOptType() {
        return mRequestOptType;
    }

    public int getResponseOptType() {
        return mResponseOptType;
    }

    public List<BlePackage> getPackageList() {
        return mPackageList;
    }

    public abstract R parseFrom(byte[] raw) throws InvalidProtocolBufferException;


    public boolean generatePackage(int channel) {
        if (mData == null) {
            Log.e(TAG, "generatePackage not set request data");
            return false;
        }

        mDataLength = mData.length;
        mSendDataCount = 0;


        BlePackage blePackage = new BlePackage(channel, mRequestOptType, true);

        blePackage.setData(mData);

        if (!blePackage.prepareFrameList()) {
            return false;
        }

//        mDataLength += blePackage.getFrameList().size();
        mPackageList.add(blePackage);

//        while (bb.remaining() > 0) {
//            byte[] tmp = new byte[bb.remaining() > BlePackage.MAX_PACKAGE_DATA_SIZE ? BlePackage.MAX_PACKAGE_DATA_SIZE : bb.remaining()];
//            bb.get(tmp);
//            BlePackage blePackage = new BlePackage(channel, mRequestOptType, bb.remaining() > 0);
//
//            blePackage.setData(tmp);
//
//            if (!blePackage.prepareFrameList()) {
//                return false;
//            }
//
//            mDataLength += blePackage.getFrameList().size();
//            mPackageList.add(blePackage);
//        }

        return true;
    }

}
