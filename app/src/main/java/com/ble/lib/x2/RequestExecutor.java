package com.ble.lib.x2;

import android.os.DeadObjectException;
import android.os.Process;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import cn.ginshell.bong.BuildConfig;
import cn.ginshell.bong.ble.BleConnectionException;
import cn.ginshell.bong.ble.BleExecuteException;
import cn.ginshell.bong.ble.BleGeneratePackageException;
import cn.ginshell.bong.ble.CancelException;
import cn.ginshell.bong.ble.x2.frame.EndFrame;
import cn.ginshell.bong.ble.x2.frame.Frame;
import cn.ginshell.bong.ble.x2.frame.FrameCtl;
import cn.ginshell.bong.ble.x2.frame.PerFrame;
import cn.ginshell.bong.ble.x2.frame.StartFrame;
import cn.ginshell.bong.ble.x2.proto.BtAck;
import cn.ginshell.bong.ble.x2.request.Request;
import cn.ginshell.bong.utils.ParserUtils;

/**
 * 执行
 *
 * @author hackill
 * @date 11/9/15.
 */
public class RequestExecutor extends BleWorker {
    public final static String TAG = RequestExecutor.class.getSimpleName();

    public final static int MAX_RETRY_TIMES = 3;

    private BlePackage mUnfinishedPackage = null;

    protected BlockingQueue<Request> mQueue;


    public RequestExecutor(X2BleController mBleController, int mChannel, BlockingQueue<Request> mQueue) {
        super(mBleController, mChannel);
        this.mQueue = mQueue;
    }


    @Override
    public void receiveFrame(Frame frame) {
        Log.v(TAG, "receiveFrame() called with: channel=[" + mChannel + "] frame = [" + frame.getFrameCtl().getFrameType() + "] bufferSize:" + mFrameBuffer.size());

        //  12/5/15 手表发送数据过多是，清空缓存，防止新frame 无法进入
        if (mFrameBuffer.remainingCapacity() == 0) {
            Log.e(TAG, "receiveFrame frame buffer full , clear buffer");
            if (BuildConfig.DEBUG) {

                while (mFrameBuffer.size() != 0) {
                    Frame f = mFrameBuffer.poll();

                    if (f == null)
                        break;

                    Log.e(TAG, "receiveFrame drop frame : channel = [" + mChannel + "]frame = [" + f.getFrameCtl().getFrameType() + "], raw = [" + ParserUtils.parse(frame.getRaw()) + "] bufferSize:" + mFrameBuffer.size());
                }
            }
            mFrameBuffer.clear();

        }

        mFrameBuffer.add(frame);

    }

    @Override
    protected BlePackage receivePackage() throws InterruptedException {
        Log.v(TAG, "receivePackage() called with: " + "");


        BlePackage blePackage;
        //get first frame
        Frame frame = pollFrame();

        if (frame == null) {
            return null;
        }

        if (mUnfinishedPackage == null) {
            //不存在未接收完的package
            if (frame instanceof StartFrame) {
                Log.v(TAG, "receivePackage init ble package");
                blePackage = new BlePackage(mChannel, ((StartFrame) frame).getOptType(), (((StartFrame) frame).getPackageFlag() & StartFrame.FLAG_END_PACKAGE) == 1);

            } else {
                Log.v(TAG, "receivePackage frame type error " + frame.getClass().getSimpleName());
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
            blePackage = mUnfinishedPackage;
            mUnfinishedPackage = null;

            while (frame.getFrameCtl().getSequence() != (blePackage.getFrameList().size() % FrameCtl.MAX_FRAME_SEQUENCE)) {
                frame = pollFrame();
                Log.d(TAG, "receivePackage skip frame sequence:" + frame.getFrameCtl().getSequence());
                if (frame == null) {
                    Log.d(TAG, "receivePackage find uncompleted package");
                    blePackage.setIsPackageReceiveComplete(false);
                    return blePackage;
                }
            }

            Log.d(TAG, "receivePackage found unfinished frame");

            blePackage.setIsPackageReceiveComplete(true);

        }


        while (true) {
            if (frame == null || !blePackage.addFrameAndCheckSequence(frame)) {
                blePackage.setIsPackageReceiveComplete(false);
                return blePackage;
            }


            if (frame instanceof EndFrame) {
                Log.v(TAG, "receivePackage end frame return package");
                return blePackage;
            }

            frame = pollFrame();

        }
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        while (true) {

            // release previous mRequest object to avoid leaking mRequest object when mQueue is drained.
            mRequest = null;
            try {

                // Take a mRequest from the queue.
                Log.i(TAG, "waiting new request c:" + mChannel);


                mRequest = mQueue.take();

                mBleController.checkStateLock();

                if (mRequest.isCanceled()) {
                    continue;
                }
                

                mFrameBuffer.clear();

                Log.d(TAG, "take one mRequest c:" + mChannel + " mRequest:" + mRequest.getClass().getSimpleName());

                BlePackage responsePackage = sendRequest(mRequest);

                if (mQuit) {
                    return;
                }

                Log.d(TAG, "start receive");
                receiveResponse(mRequest, responsePackage);
                Log.i(TAG, "end receive");

            } catch (CancelException e) {
                Log.e(TAG, "x2 cancel ", e);
                mRequest = null;
                mFrameBuffer.clear();
                mUnfinishedPackage = null;

            } catch (Exception e) {

                if (e instanceof DeadObjectException) {
                    // TODO: 1/19/16 不知是否能解决这个蓝牙内部异常
                    X2BleController xbc = mBleController;
                    if (xbc != null) {
                        xbc.disconnect();
                    }
                }

                // We may have been interrupted because it was time to quit.
                Log.e(TAG, "x2 run ", e);
                if (mRequest != null) {
                    mRequest.deliverError(e);
                    mRequest = null;
                }

                mFrameBuffer.clear();

                mUnfinishedPackage = null;

                if (mQuit) {
                    Log.e(TAG, "run channel:" + mChannel + " stop");
                    return;
                }


                if (onErrorNotified) {
                    BleConnectionException bce = new BleConnectionException("ble connection break");
                    while ((mRequest = mQueue.poll()) != null) {
                        mRequest.deliverError(bce);
                    }

                    onErrorNotified = false;
                }
            }
        }

    }

    //  11/12/15 如果返回本是ack 但是由于某种原因没有收到，此处会当作response 处理
    private void receiveResponse(Request request, BlePackage firstPackage) throws Exception {
        //receive response

        int retry;
        BlePackage responsePackage = firstPackage;

        Log.d(TAG, "run start receive");


        while (true) {

            retry = 0;

            if (request.isCanceled()) {
                //当request 取消时 抛出取消异常
                throw new CancelException();
            }

            while (retry < MAX_RETRY_TIMES && !handleResponsePackage(request, responsePackage)) {
                Log.e(TAG, "receiveResponse retry ");

                if (request.isCanceled()) {
                    //当request 取消时 抛出取消异常
                    throw new CancelException();
                }

                retry++;

                responsePackage = receivePackage();

            }

            if (retry >= MAX_RETRY_TIMES || responsePackage == null) {
                //失败，直接跳出
                throw new BleExecuteException("receive response failure");
            } else if (responsePackage.isEndPackage()) {
                Log.i(TAG, "receiveResponse response receive success");
                request.deliverAllResponse();
                return;
            } else {
                Log.d(TAG, "receiveResponse receive next package c:" + mChannel);
                responsePackage = receivePackage();
            }
        }
    }

    /**
     * 处理接收到的 响应包 ，依次检查 空值，完整性，crc，类型一致
     *
     * @param request         mRequest
     * @param responsePackage response package
     * @return passed all test return true ,other wise return false
     * @throws Exception sendAck fail,response package opt type not equals mRequest response opt type,or be interrupted
     */
    private boolean handleResponsePackage(Request request, BlePackage responsePackage) throws Exception {
        BtAck.bt_ack ack;


        if (responsePackage == null) {
            Log.e(TAG, "handleResponsePackage not receive");
            //未接收到
            ack = BtAck.bt_ack.newBuilder()
                    .setRetryFrameSeq(0)
                    .setReturnCode(BtAck._return.frame_error)
                    .build();

            if (!sendAck(ack)) {
                throw new BleExecuteException("send ack failure");
            }

            return false;
        } else if (!responsePackage.isPackageReceiveComplete()) {
            Log.e(TAG, "handleResponsePackage receive not complete");
            //未接收完
            ack = BtAck.bt_ack.newBuilder()
                    .setReturnCode(BtAck._return.frame_error)
                    .setRetryFrameSeq(responsePackage.getFrameList().size())
                    .build();

            mUnfinishedPackage = responsePackage;

            if (!sendAck(ack)) {
                throw new BleExecuteException("send ack failure");
            }

            return false;
        } else if (!responsePackage.prepareAndCheckData()) {
            Log.e(TAG, "handleResponsePackage crc check failure");
            //未接收完
            ack = BtAck.bt_ack.newBuilder()
                    .setReturnCode(BtAck._return.frame_error)
                    .setRetryFrameSeq(0)
                    .build();

//            mUnfinishedPackage = responsePackage;

            if (!sendAck(ack)) {
                throw new BleExecuteException("send ack failure !");
            }

            //crc check failure
            return false;
        } else if (responsePackage.getOptType() != request.getResponseOptType()) {
            //响应包类型与请求类型不一致
            throw new BleExecuteException("Received package opt type not equals Request wanted opt type:" + request.getResponseOptType() + ",receive type =" + responsePackage.getOptType());
        } else {
            Log.v(TAG, "handleResponsePackage receive success");
            //成功接收
            ack = BtAck.bt_ack.newBuilder()
                    .setReturnCode(BtAck._return.success)
                    .build();

            if (!sendAck(ack)) {
                throw new BleExecuteException("send ack failure");
            }


            try {
                request.deliverPerResponse(responsePackage);
            } catch (InvalidProtocolBufferException e) {
                //这里的结果经过滤空，完整性检验，crc检验。可以保障此处接收数据与手表发送数据一致
                Log.e(TAG, "deliverPerResponse pares data Error ", e);
            }

            return true;
        }
    }

    /**
     * send mRequest
     *
     * @param request mRequest to send
     * @return if send package and receive ack ok , and last response is not ack,return true ; otherwise return false
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private BlePackage sendRequest(Request request) throws Exception {
        if (!request.generatePackage(mChannel)) {
            //当request 取消时 抛出取消异常
            throw new BleGeneratePackageException();
        }

        List<BlePackage> pbl = request.getPackageList();
        BlePackage responseBlePackage;
        BtAck.bt_ack ack;

        int retry;
        // send mRequest

        for (int i = 0; i < pbl.size(); i++) {

            BlePackage bp = pbl.get(i);

            ReturnType rt = ReturnType.RETRY;

            retry = -1;
            while (retry < MAX_RETRY_TIMES && rt == ReturnType.RETRY) {

                if (request.isCanceled()) {
                    Log.e(TAG, "sendRequest canceled");
                    throw new CancelException();
                }

                retry++;
//                  test
//                if (retry == -1) {
//                    bp.getFrameList().get(0).getFrameCtl().setSequence(1);
//                } else {
//                    bp.getFrameList().get(0).getFrameCtl().setSequence(0);
//                }

                rt = sendPackageAndReceiveAck(bp, i != pbl.size() - 1);


                if (rt == ReturnType.SUCCESS && i == pbl.size() - 1) {
                    Log.d(TAG, "sendRequest last package check");
                    //如果为最后一帧，检查是否是ack
                    responseBlePackage = receivePackage();
                    if (responseBlePackage == null || responseBlePackage.getOptType() != AckPackage.getAckOptCode()) {
                        Log.d(TAG, "sendRequest last package not ack");
                        return responseBlePackage;
                    }
                    //是ack ，解析并处理ack

                    if (responseBlePackage.isPackageReceiveComplete() && responseBlePackage.prepareAndCheckData()) {
                        ack = BtAck.bt_ack.parseFrom(responseBlePackage.getData());
                    } else {
                        //  一般情况下 ack 只有一帧，只存在收得到，和收不到两种，不存在解析错误，
                        Log.e(TAG, "sendRequest ack decode error");
                        return null;
                    }

                    if (ack.getReturnCode() == BtAck._return.frame_error) {
                        Log.e(TAG, "sendRequest last package receive frame error seq:" + ack.getRetryFrameSeq());
                        bp.setStartPackage(ack.getRetryFrameSeq());
                        rt = ReturnType.RETRY;
                    } else if (ack.getReturnCode() == BtAck._return.busy) {
                        throw new BleExecuteException("Device busy");
                    } else if (ack.getReturnCode() == BtAck._return.other) {
                        throw new BleExecuteException("Other Error");
                    }
                }
            }

            Log.d(TAG, "sendRequest after rt:" + rt);

            if (rt != ReturnType.SUCCESS) {
                //多次重试依然不成功断开原有ble 连接
                X2BleController xbc = mBleController;
                if (xbc != null) {
                    xbc.disconnect();
                }

                throw new BleExecuteException("send mRequest failure");
            } else {
                Log.d(TAG, "sendRequest send mRequest success");
                request.deliverPerRequestSend(bp);
            }

        }


        //代码不应该运行到这个地方
        Log.e(TAG, "sendRequest code error , app should not run here");
        return null;
    }

//    boolean test = true;

    private ReturnType sendPackageAndReceiveAck(BlePackage blePackage, boolean needAck) throws InterruptedException {
        if (!sendPackage(blePackage)) {
            Log.e(TAG, "sendPackageAndReceiveAck send package failure");
            return ReturnType.RETRY;
        }

        if (!needAck) {
            return ReturnType.SUCCESS;
        }


        AckPackage rspAck = receiveAck();

        if (rspAck == null) {
            Log.e(TAG, "sendPackageAndReceiveAck receive ack failure");
            return ReturnType.RETRY;
        } else if (!rspAck.isPackageReceiveComplete() || !rspAck.prepareAndCheckData()) {
            Log.d(TAG, "sendPackageAndReceiveAck ack package error");
            return ReturnType.ABORT;
        }

        BtAck.bt_ack ack;
        try {
            ack = rspAck.getAck();
        } catch (InvalidProtocolBufferException e) {
            Log.e(TAG, "sendPackageAndReceiveAck ", e);
            return ReturnType.RETRY;
        }

        if (ack.getReturnCode() == BtAck._return.success) {
            Log.v(TAG, "Receive Ack Success");
            return ReturnType.SUCCESS;
        } else if (ack.getReturnCode() == BtAck._return.frame_error) {
            Log.e(TAG, "sendPackageAndReceiveAck Receive ack tell frame_error and retry frame:" + ack.getRetryFrameSeq());
            blePackage.setStartPackage(ack.getRetryFrameSeq());
            return ReturnType.RETRY;
        } else {
            Log.e(TAG, "sendPackageAndReceiveAck Receive ack Abort");
            return ReturnType.ABORT;
        }
    }


}
