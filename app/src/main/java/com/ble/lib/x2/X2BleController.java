package com.ble.lib.x2;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.UUID;

import cn.ginshell.bong.ble.BaseBleController;
import cn.ginshell.bong.ble.x2.frame.Frame;
import cn.ginshell.bong.utils.ParserUtils;

/**
 * 负责蓝牙连接与蓝牙IO操作
 *
 * @author hackill
 * @date 11/9/15.
 */
public class X2BleController extends BaseBleController {

    private static final String TAG = "X2BleController";

    public X2BleController(Context appContext) {
        super(appContext);
    }


    public final static UUID NOTIFY_CHARACTERISTIC = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca1e");
    public final static UUID WRITE_CHARACTERISTIC = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca1e");
    private final static UUID BONG_SERVICE = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca1e");

    private NotificationDispatcher mNotificationDispatcher;


    /**
     * do not touch this instance after quit
     */
    public void quit() {
        super.quit();

        NotificationDispatcher nd = mNotificationDispatcher;
        if (nd != null)
            nd.close();


        mNotificationDispatcher = null;
    }


    @Override
    protected UUID getNotifyCharacteristicUUID() {
        return NOTIFY_CHARACTERISTIC;
    }

    @Override
    protected UUID getWriteCharacteristicUUID() {
        return WRITE_CHARACTERISTIC;
    }

    @Override
    protected UUID getServiceUUID() {
        return BONG_SERVICE;
    }

    @Override
    protected void notifyWorkerConnectionError() {
        Log.i(TAG, "notifyWorkerConnectionError: ");
        
        mNotificationDispatcher.notifyError();
    }


    @Override
    protected void handleCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (status == 0x85) {
            disconnect();
        }
    }

    @Override
    protected void handleCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        NotificationDispatcher nd = mNotificationDispatcher;
        if (nd != null)
            nd.deliveryFrame(characteristic.getValue());
    }


    /**
     * set to enable and set null to disable
     *
     * @param notificationDispatcher
     */
    public void enableNotificationDispatcher(NotificationDispatcher notificationDispatcher) {
        mNotificationDispatcher = notificationDispatcher;
    }

    public synchronized boolean writePackage(BlePackage blePackage) throws InterruptedException {


        BluetoothGattCharacteristic write = mWrite;
        BluetoothGatt gatt = mBluetoothGatt;

        if (write == null || gatt == null) {
            return false;
        }

        write.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);

        int retry = 0;

        List<Frame> fl = blePackage.getFrameList();
        Frame frame;

        for (int i = blePackage.getStartPackage(); i < fl.size(); i++) {

            frame = fl.get(i);

            Log.v(TAG, "writePackage type:" + frame.getClass().getSimpleName() + " raw:" + ParserUtils.parse(frame.getRaw()));

            write.setValue(frame.getRaw());
            retry = 0;
            while (retry < 3 && !gatt.writeCharacteristic(write)) {
                //写入可能由于设备忙造成写入失败，等待5毫秒
                Log.e(TAG, "writePackage ble busy");
                Thread.sleep(10);
                retry++;
            }

            Thread.sleep(10);

            if (retry == 3) {
                Log.e(TAG, "write failure");
                return false;
            }
        }

        return true;
    }
}
