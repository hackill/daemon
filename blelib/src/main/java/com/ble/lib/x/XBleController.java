package com.ble.lib.x;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ble.lib.BaseBleController;
import com.ble.lib.utils.ParserUtils;

import java.util.UUID;


/**
 * @author hackill
 * @date 1/18/16.
 */

public class XBleController extends BaseBleController {

    private static final String TAG = "XBleController";

    public final static UUID NOTIFY_CHARACTERISTIC = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca1e");
    public final static UUID WRITE_CHARACTERISTIC = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca1e");
    private final static UUID UUID_SERVICE = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca1e");

    private XWorkerThread mWorkerThread;
    private LocalBroadcastManager mLBM;

    public XBleController(Context appContext) {
        super(appContext);
        mLBM = LocalBroadcastManager.getInstance(appContext);

    }


    @Override
    protected UUID getNotifyCharacteristicUUID() {
        return UUID.fromString("0003cbb1-0000-1000-8000-00805f9b0131");
//        return NOTIFY_CHARACTERISTIC;
    }

    @Override
    protected UUID getWriteCharacteristicUUID() {
        return UUID.fromString("0003cbb1-0000-1000-8000-00805f9b0131");
//        return WRITE_CHARACTERISTIC;
    }

    @Override
    protected UUID getServiceUUID() {

        return UUID.fromString("0003cbbb-0000-1000-8000-00805f9b0131");
//        return UUID_SERVICE;
    }

    @Override
    protected void notifyWorkerConnectionError() {
        Log.i(TAG, "notifyWorkerConnectionError: ");
        if (mWorkerThread != null) {
            mWorkerThread.setOnErrorNotified();
            mWorkerThread.interrupt();
        }
    }

    /**
     * do not touch this instance after quit
     */
    @Override
    public void quit() {
        super.quit();

        XWorkerThread xt = mWorkerThread;
        if (xt != null)
            xt.quit();

        mWorkerThread = null;
    }

    @Override
    protected void handleCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (characteristic.getUuid().compareTo(WRITE_CHARACTERISTIC) == 0) {
            XWorkerThread xt = mWorkerThread;

            if (status == BluetoothGatt.GATT_SUCCESS) {

                if (xt != null)
                    mWorkerThread.deliverCommandSuccess(true);
            } else {

                if (xt != null)
                    mWorkerThread.deliverCommandSuccess(false);

                if (status == 0x85) {
                    disconnect();
                }
            }
        } else {
            Log.e(TAG, "onCharacteristicWrite unhandled uuid:" + characteristic.getUuid().toString());
        }
    }

    @Override
    protected void handleCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        byte[] value = characteristic.getValue();

        if (value != null) {
            XWorkerThread xt = mWorkerThread;
            if (xt != null)
                xt.receiveFrame(value);
        }
    }


    /**
     * set to enable and set null to disable
     *
     * @param thread worker thread
     */
    public void attachWorkerThread(XWorkerThread thread) {
        mWorkerThread = thread;
    }

    public boolean writeFrame(byte[] frame) throws InterruptedException {

        Log.v(TAG, "writeFrame frame =[" + ParserUtils.parse(frame) + "]");

        BluetoothGattCharacteristic write = mWrite;
        BluetoothGatt gatt = mBluetoothGatt;

        if (write == null || gatt == null)
            return false;

        write.setValue(frame);

        int retry = 0;
        while (retry < 3 && !gatt.writeCharacteristic(write)) {
            //写入可能由于设备忙造成写入失败，等待10毫秒
            Log.e(TAG, "writePackage ble busy");

            Thread.sleep(10);

            retry++;
        }

        if (retry == 3) {
            Log.e(TAG, "write failure");
            return false;
        }

        return true;
    }


}
