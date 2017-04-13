package com.ble.lib.scan;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.ble.lib.BlePermission;
import com.ble.lib.utils.BleUtil;

import java.util.List;


/**
 * hackill
 *
 * @date 31/03/2017
 */
public class BleScanner {

    private ScanCallback mScanCallback;

    private BluetoothAdapter.LeScanCallback mLeScanCallback;

    private BleScanCallback mCallback;

    private String[] mBleFilter;

    private List<String> mAddressList;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * @param bleName
     */
    public void filter(String... bleName) {
        mBleFilter = bleName;
    }

    private Context mContext;

    public BleScanner(Context context) {
        this.mContext = context;

    }

    /**
     * 线程不安全
     *
     * @param context  context
     * @param callback 搜索callback
     */
    public void startLeScan(@NonNull Context context, @NonNull BleScanCallback callback) {

        if (!BlePermission.checkBlePermission(context)) {
            callback.onError(new RuntimeException("Ble Permission not granted check permissions:\n" +
                    "\t\t\t" + Manifest.permission.BLUETOOTH + "\n" +
                    "\t\t\t" + Manifest.permission.BLUETOOTH_ADMIN + "\n" +
                    "\t\t\t" + Manifest.permission.ACCESS_FINE_LOCATION + "\n"
            ));

            return;
        }


        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();

        if (defaultAdapter == null || !defaultAdapter.isEnabled()) {
            callback.onError(new RuntimeException("bluetooth not open"));
            return;
        }

        mCallback = callback;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            BluetoothLeScanner bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();

            if (mScanCallback != null) {
                bluetoothLeScanner.stopScan(mScanCallback);
                mScanCallback = null;
            }

            mScanCallback = new ScanCallback() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onScanResult(int callbackType, ScanResult result) {

                    if (result == null) return;

                    if (result.getScanRecord() == null) {
                        callback(result.getDevice(), result.getRssi(), null);
                    } else {
                        callback(result.getDevice(), result.getRssi(), result.getScanRecord().getBytes());
                    }
                }
            };
            bluetoothLeScanner.startScan(mScanCallback);
        } else {
            if (mLeScanCallback != null) {
                defaultAdapter.stopLeScan(mLeScanCallback);
                mLeScanCallback = null;
            }


            mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                    callback(device, rssi, scanRecord);
                }
            };


            defaultAdapter.startLeScan(mLeScanCallback);
        }

        /**
         * 把已经连接的设备 通知出去 这类设备直接扫描不到
         */
        getDeviceConnected();
    }


    public void scanDeviceByAddress(List<String> address, long scanTime, final BleScanTimeCallback bleScanCallback) {

        this.mAddressList = address;
        startLeScan(mContext, bleScanCallback);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopLeScan();
                bleScanCallback.onScanTimeOut();
            }
        }, scanTime);
    }

    private synchronized void callback(BluetoothDevice device, int rssi, byte[] scanRecord) {
        BleDevice d = new BleDevice();
        d.mac = device.getAddress();
        d.name = device.getName();

        if (TextUtils.isEmpty(d.name) && scanRecord != null) {
            d.name = BleUtil.decodeName(scanRecord);
        }

        d.rssi = rssi;

        onResultCallBack(d);
    }

    private void onResultCallBack(final BleDevice device) {

        if (mCallback instanceof BleScanTimeCallback) {
            //时间回调
            for (String address : mAddressList) {
                if (TextUtils.equals(address, device.mac)) {
                    stopLeScan();
                    mCallback.onScanResult(device);
                    break;
                }
            }
        } else {
            onNormalCallBack(device);
        }
    }

    private void onNormalCallBack(BleDevice device) {
        // 过滤 filter
        if (mBleFilter == null || mBleFilter.length == 0) {
            mCallback.onScanResult(device);
        } else {
            for (String bleName : mBleFilter) {
                if (TextUtils.equals(bleName, device.name)) {
                    mCallback.onScanResult(device);
                    break;
                }
            }
        }
    }


    private void getDeviceConnected() {
        BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);

        List<BluetoothDevice> connectedDevices = bluetoothManager.getConnectedDevices(BluetoothProfile.GATT);

        if (connectedDevices != null) {
            for (BluetoothDevice device : connectedDevices) {
                callback(device, -1, null);
            }
        }
    }

    public void stopLeScan() {

        mHandler.removeCallbacksAndMessages(null);

        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();

        if (defaultAdapter == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mScanCallback != null) {
                BluetoothLeScanner bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();
                bluetoothLeScanner.stopScan(mScanCallback);
                mScanCallback = null;
            }
        } else {
            if (mLeScanCallback != null) {
                defaultAdapter.stopLeScan(mLeScanCallback);
                mLeScanCallback = null;
            }
        }


    }
}
