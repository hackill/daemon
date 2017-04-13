package com.ble.lib.x;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import com.ble.lib.BLEInitCallback;
import com.ble.lib.BLEScanInitCallback;
import com.ble.lib.BaseRequest;
import com.ble.lib.BleExecuteException;
import com.ble.lib.BleManager;
import com.ble.lib.BleRssiCallback;
import com.ble.lib.scan.BleDevice;
import com.ble.lib.scan.BleScanTimeCallback;
import com.ble.lib.scan.BleScanner;
import com.ble.lib.x.request.XRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author hackill
 * @date 1/19/16.
 */
public class XBleManager implements BleManager {

    private static final String TAG = "XBleManager";

    private static final long SCAN_TIME = TimeUnit.SECONDS.toMillis(20);

    private XBleController mController;
    private XWorkerThread mWorkerThread;
    private BleScanner mBleScanner;

    public XBleManager(Application app) {
        XBleController bleController = new XBleController(app);
        XWorkerThread workerThread = new XWorkerThread(bleController);
        bleController.attachWorkerThread(workerThread);
        workerThread.start();

        mBleScanner = new BleScanner(app);

        mController = bleController;
        mWorkerThread = workerThread;
    }

    @Override
    public void connect(List<String> addressList, final BLEScanInitCallback callback) {
        mBleScanner.scanDeviceByAddress(addressList, SCAN_TIME, new BleScanTimeCallback() {
            @Override
            public void onScanTimeOut() {
                callback.onScanTimeOut();
            }

            @Override
            public void onScanResult(BleDevice device) {
                Log.i(TAG, "onScanResult: " + device.mac + device.name);
                mController.connectTo(device.mac, callback);
            }

            @Override
            public void onError(Exception e) {
                callback.onFailure(0);
            }
        });

    }

    @Override
    public void connect(String address, final BLEInitCallback callback) {
        mController.connectTo(address, callback);
    }

    @Override
    public void close() {
        mController.quit();
    }

    @Override
    public void reconnect(BLEInitCallback callback) {
        mController.reconnect(callback);
    }

    @Override
    public void disconnect() {
        mController.disconnect();
    }


    private void addRequestWhileConnected(XRequest request) {
        if (!mWorkerThread.addRequest(request)) {
            request.deliverError(new BleExecuteException("add mRequest failure"));
        }
    }

    @Override
    public void addRequest(BaseRequest request, String tag) {

        try {
            BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mAdapter == null || !mAdapter.isEnabled()) {
                request.deliverError(new BleExecuteException("not open bluetooth"));
                return;
            }
        } catch (Exception e) {
            Log.w(TAG, "addRequest: ", e);
        }

        request.setTag(tag);

        if (request instanceof XRequest) {
            addRequestWhileConnected((XRequest) request);

        } else {
            request.deliverError(new BleExecuteException("mRequest type error"));
        }


    }


    @Override
    public void addRequest(BaseRequest request) {
        addRequest(request, null);
    }

    @Override
    public void cancel(String tag) {
    }

    @Override
    public void cancelAll() {
        mWorkerThread.cancelAllRequest();
    }

    @Override
    public void quit() {
        if (mController != null)
            mController.quit();
    }

    @Override
    public boolean isConnected() {
        return mController.isConnected();
    }

    public void setDeviceAddress(String address) {
        mController.setAddress(address);
    }

    @Override
    public boolean readRssi(BleRssiCallback callback) {
        if (isConnected()) {
            return mController.readRssi(callback);
        } else {
            return false;
        }
    }

    @Override
    public String readDeviceName() {
        if (isConnected()) {
            return mController.getDeviceName();
        }
        return null;
    }
}
