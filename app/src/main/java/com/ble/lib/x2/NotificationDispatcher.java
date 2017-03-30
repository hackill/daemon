package com.ble.lib.x2;

import cn.ginshell.bong.ble.x2.frame.Frame;
import cn.ginshell.bong.ble.x2.frame.FrameHelper;

/**
 * 分发 notification 到对应的通道线程
 *
 * @author hackill
 * @date 11/9/15.
 */
public class NotificationDispatcher {
    BleWorker[] mWorkers;

    public NotificationDispatcher(BleWorker[] mWorkers) {
        this.mWorkers = mWorkers;
    }

    public boolean checkFrame(byte[] rawFrame) {
        return !(rawFrame == null || rawFrame.length > Frame.FRAME_SIZE_LIMIT_MAX || rawFrame.length < Frame.FRAME_SIZE_LIMIT_MIN);
    }

    /**
     * 如果贞错误，不做任何处理，让等待线程超时
     *
     * @param rawFrame
     */
    public void deliveryFrame(byte[] rawFrame) {
//        Log.v(TAG, "deliveryFrame() called with: " + "rawFrame = [" + ParserUtils.parse(rawFrame) + "]");
        if (checkFrame(rawFrame)) {
            Frame frame = FrameHelper.decode(rawFrame);
            int channel = frame.getFrameCtl().getChannel();
            if (channel >= 0 && channel < mWorkers.length) {
                mWorkers[channel].receiveFrame(frame);
            }
        }

    }

    public void notifyError() {

        for (BleWorker mWorker : mWorkers) {
            mWorker.setOnErrorNotified();

            mWorker.interrupt();
        }
    }

    public void close() {
//        for (BleWorker w : mWorkers)
//            w.quit();
        for (int i = 0; i < mWorkers.length; i++) {
            mWorkers[i].quit();
            mWorkers[i] = null;
        }


    }
}
