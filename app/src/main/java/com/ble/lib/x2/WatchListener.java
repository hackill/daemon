package com.ble.lib.x2;

import android.os.Process;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.ginshell.bong.ble.BleExecuteException;
import cn.ginshell.bong.ble.BleManager;
import cn.ginshell.bong.ble.x2.frame.EndFrame;
import cn.ginshell.bong.ble.x2.frame.Frame;
import cn.ginshell.bong.ble.x2.frame.PerFrame;
import cn.ginshell.bong.ble.x2.frame.StartFrame;
import cn.ginshell.bong.ble.x2.proto.BtAck;

/**
 * 监听从手表向手机发送的请求，并执行
 *
 * @author hackill
 * @date 11/9/15.
 */
public class WatchListener extends BleWorker {
    private static final String TAG = "WatchListener";

    private BlePackage mUnfinishedPackage = null;
    List<BlePackage> packageList = null;
    BleManager mManager;


    public WatchListener(X2BleController mBleController, int mChannel, BleManager manager) {
        super(mBleController, mChannel);
        mManager = manager;
    }

    @Override
    public void receiveFrame(Frame frame) {
        super.receiveFrame(frame);
    }

    @Override
    protected BlePackage receivePackage() throws InterruptedException {
        BlePackage blePackage = null;
        //get first frame
        Frame frame;

        if (mUnfinishedPackage == null) {
            //不存在未接收完的package
            frame = mFrameBuffer.take();
            // TODO: 11/12/15 take() not return null ?

            if (frame instanceof StartFrame) {
                Log.d(TAG, "receivePackage init ble package");
                blePackage = new BlePackage(mChannel, ((StartFrame) frame).getOptType(), (((StartFrame) frame).getPackageFlag() & StartFrame.FLAG_END_PACKAGE) == 1);
            } else {
                Log.d(TAG, "receivePackage frame type error " + frame.getClass().getSimpleName());
                return null;
            }

            //单帧结束
            if (frame instanceof PerFrame) {

                if (!blePackage.addFrameAndCheckSequence(frame)) {
                    return null;
                }

                return blePackage;

            }

        } else {
            //存在未接收完的package
            frame = pollFrame();

            blePackage = mUnfinishedPackage;
            mUnfinishedPackage = null;

            blePackage.setIsPackageReceiveComplete(true);

        }


        while (true) {
            if (frame == null || !blePackage.addFrameAndCheckSequence(frame)) {
                blePackage.setIsPackageReceiveComplete(false);
                return blePackage;
            }

            if (frame instanceof EndFrame) {
                Log.d(TAG, "receivePackage end frame return package");

                return blePackage;
            }

            frame = pollFrame();


        }
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        BtAck.bt_ack ack;
        while (true) {
            try {

                BlePackage blepackage = receivePackage();
                if (blepackage == null)
                    continue;


                if (!blepackage.isPackageReceiveComplete()) {
                    ack = BtAck.bt_ack.newBuilder()
                            .setRetryFrameSeq(blepackage.getFrameList().size())
                            .setReturnCode(BtAck._return.frame_error)
                            .build();

                    if (!sendAck(ack)) {
                        throw new BleExecuteException("send ack failure");
                    }
                } else if (!blepackage.prepareAndCheckData()) {
                    ack = BtAck.bt_ack.newBuilder()
                            .setRetryFrameSeq(0)
                            .setReturnCode(BtAck._return.frame_error)
                            .build();

                    if (!sendAck(ack)) {
                        throw new BleExecuteException("send ack failure");
                    }
                } else {
                }
            } catch (Exception e) {
                Log.e(TAG, "watch listener ", e);

                if (mQuit) {
                    return;
                }
            }
        }
    }

    private void handlePackage(BlePackage blePackage) {
        if (packageList == null)
            packageList = new ArrayList<>();

        packageList.add(blePackage);

        if (blePackage.isEndPackage) {

        }
    }

    private void handleRequest() {
    }
}
