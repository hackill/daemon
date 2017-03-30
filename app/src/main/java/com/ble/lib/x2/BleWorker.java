package com.ble.lib.x2;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import cn.ginshell.bong.ble.x2.frame.Frame;
import cn.ginshell.bong.ble.x2.frame.PerFrame;
import cn.ginshell.bong.ble.x2.proto.BtAck;
import cn.ginshell.bong.ble.x2.request.Request;
import cn.ginshell.bong.utils.ParserUtils;

/**
 * @author hackill
 * @date 11/9/15.
 */
public abstract class BleWorker extends Thread {
    private static final String TAG = "BleWorker";

    public final static int TIMEOUT_PERIOD_MS = 4000;


    protected Request mRequest;

    protected X2BleController mBleController;
    protected boolean mQuit = false;
    protected int mChannel;

    protected boolean onErrorNotified = false;


    public BleWorker(X2BleController mBleController, int mChannel) {
        super("bleworker-" + mChannel);
        this.mChannel = mChannel;
        this.mBleController = mBleController;
    }

    protected BlockingQueue<Frame> mFrameBuffer = new ArrayBlockingQueue<>(BlePackage.MAX_PACKAGE_DATA_SIZE / Frame.FRAME_SIZE_LIMIT_MAX + 2);

    public void receiveFrame(Frame frame) {
        mFrameBuffer.add(frame);
    }

    protected Frame pollFrame() throws InterruptedException {
        Frame frame = mFrameBuffer.poll(TIMEOUT_PERIOD_MS, TimeUnit.MILLISECONDS);
        if (frame != null)
            Log.v(TAG, "pollFrame type:" + frame.getFrameCtl().getFrameType() + " rawFrame = [" + ParserUtils.parse(frame.getRaw()) + "]");
        return frame;
    }


    public void quit() {
        mBleController = null;
        mQuit = true;
        interrupt();
    }

    public void cancel(String tag) {

        Request r = mRequest;
        if (r == null) {
            Log.i(TAG, "cancel request null");
        } else {
            Log.i(TAG, "cancel request tag:" + r.getTag());
        }

        if (r != null && r.sameTag(tag)) {
            Log.i(TAG, "cancel tag:" + tag + " thread:" + Thread.currentThread().getId());
            r.discardResult();
        } else {
            Log.i(TAG, "cancel not found tag tag:" + tag + " thread:" + Thread.currentThread().getId());
        }
    }


    protected boolean sendPackage(BlePackage blePackage) throws InterruptedException {
        Log.d(TAG, "sendPackage() called with: " + "blePackage = [" + blePackage + "]");

        if (mFrameBuffer.size() > 0) {
            Log.e(TAG, "sendAck incorrect frame state, size:" + mFrameBuffer.size());
            mFrameBuffer.clear();
        }

        X2BleController bc = mBleController;

        return bc != null && bc.writePackage(blePackage);

    }

    protected boolean sendAck(BtAck.bt_ack ack) throws InterruptedException {
        Log.v(TAG, "sendAck() called with: " + "ack = [" + ack.getReturnCode() + " rq:" + ack.getRetryFrameSeq() + "]");

        AckPackage ackPackage = new AckPackage(mChannel);

        ackPackage.setData(ack.toByteArray());
        ackPackage.prepareFrameList();


        if (mFrameBuffer.size() > 0) {
            Log.e(TAG, "sendAck incorrect frame state, size:" + mFrameBuffer.size());
//            mFrameBuffer.clear();
            Frame f;
            while ((f = mFrameBuffer.poll()) != null) {
                Log.v(TAG, "redundancy type:" + f.getFrameCtl().getFrameType() + " rawFrame = [" + ParserUtils.parse(f.getRaw()) + "]");

            }

        }


        X2BleController bc = mBleController;

        return bc != null && bc.writePackage(ackPackage);
    }

    protected AckPackage receiveAck() throws InterruptedException {
        Frame frame = pollFrame();


        // ack is per frame

        AckPackage ackPackage = null;
        if (frame != null && frame instanceof PerFrame && ((PerFrame) frame).getOptType() == AckPackage.getAckOptCode()) {
            ackPackage = new AckPackage(mChannel);
            ackPackage.addFrameAndCheckSequence(frame);
        } else {
            Log.e(TAG, "receiveAck not take frame or ack type error ");
        }

        return ackPackage;
    }

    protected abstract BlePackage receivePackage() throws InterruptedException;

    public void setOnErrorNotified() {
        this.onErrorNotified = true;
    }

}
