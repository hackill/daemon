package com.ble.lib.x2;

import android.util.Log;

import java.util.concurrent.BlockingQueue;

import cn.ginshell.bong.ble.BLEInitCallback;
import cn.ginshell.bong.ble.BaseRequest;
import cn.ginshell.bong.ble.BleManager;
import cn.ginshell.bong.ble.BleRssiCallback;
import cn.ginshell.bong.ble.x2.request.Request;

/**
 * @author hackill
 * @date 11/10/15.
 */
public class X2BleManager implements BleManager {
    public final static String TAG = X2BleManager.class.getSimpleName();

    X2BleController mController;

    BlockingQueue<Request> mRequestQueue;

    BleWorker[] mWorkers;

    public X2BleManager(X2BleController mController, BlockingQueue<Request> mRequestQueue, BleWorker[] bleWorkers) {
        this.mController = mController;
        this.mRequestQueue = mRequestQueue;
        mWorkers = bleWorkers;
    }

    @Override
    public void connect(String address, BLEInitCallback callback) {
        mController.connectTo(address, callback);
    }

    @Override
    public void close() {
        mController.disconnect();
    }

    @Override
    public void reconnect(BLEInitCallback callback) {
        mController.reconnect(callback);
    }

    @Override
    public void disconnect() {
        mController.disconnect();
    }

    @Override
    public synchronized void addRequest(final BaseRequest request, String tag) {
        Log.d(TAG, "addRequest() called with: " + "request = [" + request + "], tag = [" + tag + "]");

        request.setTag(tag);

        try {
            mRequestQueue.add((Request) request);
        } catch (IllegalStateException e) {

            Log.e(TAG, "mRequest queue is full ");

        }


    }

    /**
     * copy of  addRequest(final BaseRequest request, String tag)  tag == null
     *
     * @param request request
     */
    @Override
    public void addRequest(BaseRequest request) {
        addRequest(request, null);
    }

    @Override
    public void cancel(String tag) {


        if (tag == null) {
            mRequestQueue.clear();
        } else {
            mRequestQueue.remove(tag);
        }

        for (BleWorker worker : mWorkers) {
            worker.cancel(tag);
        }


    }

    /**
     * not impl ,this method is emptys
     */
    @Override
    public void cancelAll() {
        cancel(null);
    }

    @Override
    public void quit() {
        Log.i(TAG, "quit ");
        mController.quit();
    }

    @Override
    public boolean isConnected() {

        return mController.isConnected();
    }


    @Override
    public boolean readRssi(BleRssiCallback callback) {
        throw new UnsupportedOperationException("read rssi not support");
    }

    @Override
    public String readDeviceName() {
        return mController.getDeviceName();
    }
}
