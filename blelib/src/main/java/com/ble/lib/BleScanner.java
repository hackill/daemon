package com.ble.lib;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.ble.lib.utils.BleUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 搜索蓝牙设备
 *
 * @author hackill
 * @date 11/12/15.
 */
public class BleScanner {
    private static final String TAG = "BongScanner";

    private BluetoothAdapter mBleAdapter;
    private Handler mHandler;
    private Map<BluetoothDevice, Integer> mDevicesMap;

    private Context mContext;
    private String mFilterName;

    public BleScanner(Context context, String filterName) {
        this.mContext = context;
        this.mFilterName = filterName;
        mBleAdapter = BluetoothAdapter.getDefaultAdapter();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 开始扫描附近bong 设备
     *
     * @param scanPeriod 扫描时长
     * @param timeUnit   时长单位
     * @param callback   回调函数，通知扫描完成
     */
    public void startScan(int scanPeriod, TimeUnit timeUnit, final BongScanCallback callback) {
        final long timeout = timeUnit.toMillis(scanPeriod);
        mDevicesMap = new HashMap<>();
        mBleAdapter.startLeScan(mScanCallback);

        BluetoothDevice deviceConnected = getDeviceConnected();

        if (deviceConnected != null)
            mDevicesMap.put(deviceConnected, -1);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBleAdapter.stopLeScan(mScanCallback);
                if (callback != null) {
                    callback.onScanFinished();
                }
            }
        }, timeout);
    }


    private BluetoothDevice getDeviceConnected() {
        BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);

        List<BluetoothDevice> connectedDevices = bluetoothManager.getConnectedDevices(BluetoothProfile.GATT);

        for (BluetoothDevice device : connectedDevices) {

            if (TextUtils.equals(device.getName(), mFilterName)) {
                return device;
            }
        }

        return null;
    }

    /**
     * 获取距离你最近的一个设备，获取后从列表中移除此设备
     *
     * @return 最近的设备，null 附近没有满足条件的设备，活着未开始扫描
     */
    public BluetoothDevice getNextDevice() {
        BluetoothDevice mostNear = null;
        int maxRssi = -127;

        if (mDevicesMap == null)
            return null;

        for (BluetoothDevice device : mDevicesMap.keySet()) {
            int rssi = mDevicesMap.get(device);
            if (rssi < 0 && rssi > -127 && rssi > maxRssi) {
                maxRssi = rssi;
                mostNear = device;
            }
        }

        return mostNear;
    }

    public Map<BluetoothDevice, Integer> getDeviceList() {
        if (mDevicesMap == null) {
            mDevicesMap = new HashMap<>();
        }
        return mDevicesMap;
    }

    BluetoothAdapter.LeScanCallback mScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Log.d(TAG, "onLeScan address:[" + device.getAddress() + "] rssi:[" + rssi + "] name:[" + device.getName() + "]");

            String deviceName = device.getName();
            if (TextUtils.isEmpty(deviceName)) {
                deviceName = BleUtil.decodeName(scanRecord);
            }
            // 正常的搜索
            if (TextUtils.equals(deviceName, mFilterName)) {
                Log.i(TAG, "onLeScan " + device.getAddress() + " rssi:" + rssi);
                mDevicesMap.put(device, rssi);
            }
        }
    };


    public interface BongScanCallback {
        void onScanFinished();
    }


    public void closeScanner() {
        mBleAdapter.stopLeScan(mScanCallback);
    }

}
