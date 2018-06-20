package com.ginshell.ble.scan;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ginshell.ble.BlePermission;

import java.util.List;

public class BleScanner {

//    private ScanCallback mScanCallback;

    private BluetoothAdapter.LeScanCallback mLeScanCallback;

    private BleScanCallback mCallback;

    private String[] mBleFilter;

    private Context context;

    public BleScanner(Context context) {
        this.context = context;
    }

    /**
     * @param bleName
     */
    public void filter(String... bleName) {
        mBleFilter = bleName;
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

        stopLeScan();

        mCallback = callback;

        getDeviceConnected();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            BluetoothLeScanner bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();
//
//            mScanCallback = new ScanCallback() {
//                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                @Override
//                public void onScanResult(int callbackType, ScanResult result) {
//
//                    if (result == null) return;
//
//                    if (result.getScanRecord() == null) {
//                        callback(result.getDevice(), result.getRssi(), null);
//                    } else {
//                        callback(result.getDevice(), result.getRssi(), result.getScanRecord().getBytes());
//                    }
//                }
//            };
//
//            bluetoothLeScanner.startScan(mScanCallback);
//        } else {
            mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                    callback(device, rssi, scanRecord);
                }
            };

            defaultAdapter.startLeScan(mLeScanCallback);
//        }
    }

    private void getDeviceConnected() {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);

        List<BluetoothDevice> connectedDevices = bluetoothManager.getConnectedDevices(BluetoothProfile.GATT);

        if (connectedDevices != null) {
            for (BluetoothDevice device : connectedDevices) {
                callback(device, -1, null);
            }
        }
    }

    private synchronized void callback(BluetoothDevice device, int rssi, byte[] scanRecord) {
        BleDevice d = new BleDevice();
        d.mac = device.getAddress();

        d.name = device.getName();

        if (TextUtils.isEmpty(d.name) && scanRecord != null) {
            d.name = BleUtil.decodeName(scanRecord);
        }

        d.rssi = rssi;

        // 过滤 filter
        if (mBleFilter == null || mBleFilter.length == 0) {
            mCallback.onScanResult(d);
        } else {
            synchronized (this) {
                for (String bleName : mBleFilter) {
                    if (TextUtils.equals(bleName, d.name)) {
                        mCallback.onScanResult(d);
                        break;
                    }
                }
            }
        }
    }

    public void stopLeScan() {

        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) return;

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            BluetoothLeScanner bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();
//
//            if (bluetoothLeScanner != null && mScanCallback != null)
//                bluetoothLeScanner.stopScan(mScanCallback);
//
//        } else {
            if (mLeScanCallback != null) {
                defaultAdapter.stopLeScan(mLeScanCallback);
            }
//        }


    }
}
