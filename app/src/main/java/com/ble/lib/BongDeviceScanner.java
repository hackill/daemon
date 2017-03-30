package com.ble.lib;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import cn.ginshell.bong.common.BONG;
import cn.ginshell.bong.model.NewBindItemModel;
import cn.ginshell.bong.ui.fragment.device.BindMessage;
import cn.ginshell.bong.utils.BleUtil;

/**
 * 搜索周边的bong设备
 */
public class BongDeviceScanner {

    private static final String TAG = "BongDeviceScanner";

    private BluetoothAdapter mBleAdapter;
    private Handler mHandler;
    private BongScanCallback mBongScanCallback;

    private ScanType mScanType = ScanType.BOTH;

    public BongDeviceScanner() {
        mBleAdapter = BluetoothAdapter.getDefaultAdapter();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void setScanType(ScanType scanType) {
        this.mScanType = scanType;
    }

    public void startScan(long returnTime, long overTime, final BongScanCallback callback) {

        mHandler.removeCallbacksAndMessages(null);

        mBleAdapter.startLeScan(mScanCallback);
        Log.d(TAG, "startScan startLeScan  " + mScanCallback);

        mBongScanCallback = callback;

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mBongScanCallback != null) {
                    mBongScanCallback.onFirstScanReturn();
                }
            }
        }, returnTime);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mBongScanCallback != null) {
                    mBongScanCallback.onScanTimeOver();
                }
            }
        }, overTime);
    }

    public void closeScanner() {
        mHandler.removeCallbacksAndMessages(null);
        mBleAdapter.stopLeScan(mScanCallback);
        mBongScanCallback = null;
        Log.d(TAG, "closeScanner stopLeScan");
    }


    public void onPause() {
        mBleAdapter.stopLeScan(mScanCallback);
        Log.d(TAG, "onPause stopLeScan");

    }

    public void onResume() {
        mBleAdapter.startLeScan(mScanCallback);
        Log.d(TAG, "onResume startLeScan " + mScanCallback);

    }

    BluetoothAdapter.LeScanCallback mScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

            if (device != null) {
                String deviceName = device.getName();
                if (TextUtils.isEmpty(deviceName)) {
                    deviceName = BleUtil.decodeName(scanRecord);
                    if (TextUtils.isEmpty(deviceName)) {
//                        Log.i(TAG, "onLeScan: 解析仍然失败了 deviceName = null , mac =  " + device.getAddress());
                    } else {
//                        Log.e(TAG, "解析成功 decode " + deviceName + ", mac =  " + device.getAddress());
                    }
                }
                final NewBindItemModel itemModel = parseDeviceMac(device, deviceName, rssi);
                if (mBongScanCallback != null && itemModel != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mBongScanCallback.onResult(itemModel);
                        }
                    });
                }
            }
        }
    };

    private NewBindItemModel parseDeviceMac(BluetoothDevice device, String deviceName, int rssi) {

        if (TextUtils.isEmpty(deviceName)) {
            return null;
        }

        NewBindItemModel model = new NewBindItemModel();
        model.setChecking(false);
        model.setBind(false);
        model.setMac(device.getAddress());
        model.setRssi(rssi);

        if (mScanType != ScanType.ONLY_FIT) {
            if (TextUtils.equals(BONG.BONG_2S.toString(), deviceName)) {
                model.setBong(BONG.BONG_2S);
            } else if (TextUtils.equals(BONG.BONG_2S_DFU.toString(), deviceName)) {
                model.setMsg(BindMessage.DFU_MODEL);
                model.setBong(BONG.BONG_2S_DFU);
            } else if (TextUtils.equals(BONG.BONG_2P.toString(), deviceName)) {
                model.setBong(BONG.BONG_2P);
            } else if (TextUtils.equals(BONG.BONG_2P_DFU.toString(), deviceName)) {
                model.setMsg(BindMessage.DFU_MODEL);
                model.setBong(BONG.BONG_2P_DFU);
            } else if (TextUtils.equals(BONG.BONG_2PH.toString(), deviceName)) {
                model.setBong(BONG.BONG_2PH);
            } else if (TextUtils.equals(BONG.BONG_2PH_DFU.toString(), deviceName)) {
                model.setMsg(BindMessage.DFU_MODEL);
                model.setBong(BONG.BONG_2PH_DFU);
            } else if (TextUtils.equals(BONG.BONG_3HR.toString(), deviceName)) {
                model.setBong(BONG.BONG_3HR);
            } else if (TextUtils.equals(BONG.BONG_3HR_DFU.toString(), deviceName)) {
                model.setMsg(BindMessage.DFU_MODEL);
                model.setBong(BONG.BONG_3HR_DFU);
            } else if (TextUtils.equals(BONG.BONG_X2.toString(), deviceName)) {
                model.setBong(BONG.BONG_X2);
            } else if (TextUtils.equals(BONG.BONG_NX2.toString(), deviceName)) {
                model.setBong(BONG.BONG_NX2);
            } else if (TextUtils.equals(BONG.BONG_NX2_DFU.toString(), deviceName)) {
                model.setMsg(BindMessage.DFU_MODEL);
                model.setBong(BONG.BONG_NX2_DFU);
            } else if (equal(BONG.BONG_XX.toString(), deviceName)) {
                model.setMsg(BindMessage.UN_SUPPORT_DEVICE);
                model.setBong(BONG.BONG_XX);
            } else if (equal(BONG.BONG_X.toString(), deviceName)) {
                model.setMsg(BindMessage.UN_SUPPORT_DEVICE);
                model.setBong(BONG.BONG_X);
            } else if (equal(BONG.BONG_II.toString(), deviceName)) {
                model.setMsg(BindMessage.UN_SUPPORT_DEVICE);
                model.setBong(BONG.BONG_II);
            } else if (equal(BONG.BONG_4.toString(), deviceName)) {
                model.setBong(BONG.BONG_4);
            } else if (equal(BONG.BONG_4_DFU.toString(), deviceName)) {
                model.setBong(BONG.BONG_4_DFU);
                model.setMsg(BindMessage.DFU_MODEL);
            }
        }
        if (mScanType != ScanType.ONLY_BONG) {
            if (equal(BONG.BONG_FIT.toString(), deviceName)) {
                model.setBong(BONG.BONG_FIT);
            } else if (equal(BONG.BONG_FIT_DFU.toString(), deviceName)) {
                model.setMsg(BindMessage.DFU_MODEL);
                model.setBong(BONG.BONG_FIT_DFU);
            }
        }

        if (model.getBong() == null) {
            return null;
        }

        return model;
    }


    private boolean equal(String one, String two) {
        return TextUtils.equals(one, two);
    }

    public interface BongScanCallback {

        void onScanTimeOver();

        void onFirstScanReturn();

        void onResult(NewBindItemModel itemModel);
    }


    public enum ScanType {
        ONLY_BONG, ONLY_FIT, BOTH
    }

}
