package com.ble.lib.x2;

import android.util.Log;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import cn.ginshell.bong.ble.x2.frame.Frame;
import cn.ginshell.bong.ble.x2.frame.FrameCtl;
import cn.ginshell.bong.ble.x2.frame.FrameHelper;
import cn.ginshell.bong.ble.x2.frame.StartFrame;
import cn.ginshell.bong.utils.ParserUtils;

/**
 * @author hackill
 * @date 11/8/15.
 */
public class BlePackage {
    private static final String TAG = "BlePackage";

    public final static int MAX_PACKAGE_DATA_SIZE = 160;

    protected ArrayList<Frame> mFrameList;
    protected byte[] mData;

    protected boolean isEndPackage = false;

    protected int mStartPackage = 0;

    protected boolean isPackageReceiveComplete = true;

    /**
     * channel to execute package
     */
    protected int mChannel;

    /**
     * command type
     */
    protected int mType;

    public BlePackage(int mChannel, int mType, boolean isEndPackage) {
        this.mChannel = mChannel;
        this.mType = mType;
        this.isEndPackage = isEndPackage;
    }

    public int getOptType() {
        return mType;
    }

    public ArrayList<Frame> getFrameList() {
        return mFrameList;
    }

    public void setFrameList(ArrayList<Frame> mFrameList) {
        this.mFrameList = mFrameList;
    }

    public byte[] getData() {
        return mData;
    }

    public void setData(byte[] data) {
        if (data.length > MAX_PACKAGE_DATA_SIZE)
            throw new IllegalArgumentException("data length is large than MAX_PACKAGE_DATA_SIZE = " + MAX_PACKAGE_DATA_SIZE + " current:" + data.length);

        this.mData = data;
    }

    public boolean prepareFrameList() {
        if (mData == null || mData.length >= MAX_PACKAGE_DATA_SIZE)
            return false;

        ByteBuffer bb = ByteBuffer.wrap(mData);
        int frameCount = 0;


        mFrameList = new ArrayList<>();
        STM32CRC crc = new STM32CRC();

        //first frame
        crc.reset();
        crc.update(mData);

        Frame frame = FrameHelper.decode(FrameHelper.encodeStartOrPer(mChannel, frameCount, mType, mData.length, crc.getValue(), isEndPackage ? 1 : 0, bb));
        mFrameList.add(frame);

        frameCount++;

        //normal or end frame
        while (bb.remaining() > 0) {
            frame = FrameHelper.decode(FrameHelper.encodeNormalOrEnd(mChannel, frameCount++, bb));
            mFrameList.add(frame);
        }

        return true;
    }

    public boolean prepareAndCheckData() {
        if (mFrameList == null || mFrameList.size() < 1) {
            Log.e(TAG, "Frame list is not ready ");
            return false;
        }

        Frame f = mFrameList.get(0);
        if (!(f instanceof StartFrame)) {
            Log.e(TAG, "Don't have start frame ");
            return false;
        }

        StartFrame sf = (StartFrame) f;
        if (sf.getLen() < 0) {
            Log.e(TAG, "Package length error");
            return false;
        }
        ByteBuffer bb = ByteBuffer.allocate(sf.getLen());

        Log.d(TAG, "prepareAndCheckData data len:" + sf.getLen());


        try {
            for (Frame frame : mFrameList) {
                bb.put(frame.getData());
            }
        } catch (BufferOverflowException e) {
            Log.e(TAG, "Error overflow, frames carry more data than length defined in package ", e);
            return false;
        }

        if (bb.remaining() == 0) {
            mData = bb.array();
            STM32CRC crc = new STM32CRC();
            crc.reset();
            crc.update(mData);
            if (crc.getValue() == sf.getCrc()) {
                return true;
            } else {
                Log.e(TAG, "Data Crc check failure");
                return false;
            }
        } else {
            Log.e(TAG, "Data length is less than length defined in package ");
            return false;
        }

    }


    public boolean isPackageReceiveComplete() {
        return isPackageReceiveComplete;
    }

    public void setIsPackageReceiveComplete(boolean isPackageReceiveComplete) {
        this.isPackageReceiveComplete = isPackageReceiveComplete;
    }

    public boolean isEndPackage() {
        return isEndPackage;
    }


    /**
     * add frame to package and check sequence
     *
     * @param frame
     * @return success return true ; sequence check failure return false
     */
    public boolean addFrameAndCheckSequence(Frame frame) {

        if (mFrameList == null)
            mFrameList = new ArrayList<>();
        //check sequence

        if ((mFrameList.size()) % FrameCtl.MAX_FRAME_SEQUENCE == frame.getFrameCtl().getSequence()) {
            //pass
            mFrameList.add(frame);

            Log.v(TAG, "addFrameAndCheckSequence sequence check success w:" + frame.getFrameCtl().getSequence());

            return true;
        } else {
            Log.e(TAG, "addFrameAndCheckSequence Frame:" + ParserUtils.parse(frame.getRaw()) + " type:" + frame.getClass().getSimpleName());
            Log.e(TAG, "addFrameAndCheckSequence sequence check failure w:" + ((mFrameList.size()) % FrameCtl.MAX_FRAME_SEQUENCE) + " r:" + frame.getFrameCtl().getSequence());
            return false;
        }
    }

    public int getStartPackage() {
        return mStartPackage;
    }

    public void setStartPackage(int s) {
        mStartPackage = s;
    }


}
