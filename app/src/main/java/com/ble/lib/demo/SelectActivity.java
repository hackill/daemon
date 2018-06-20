package com.ble.lib.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ginshell.ble.scan.BleDevice;
import com.ginshell.ble.scan.BleScanCallback;
import com.ginshell.ble.scan.BleScanner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;


/**
 * 搜索蓝牙设备
 * <p>
 * 支持同时搜索多种设备，只需要在搜索前设置filter即可，未设置则默认搜索所有蓝牙设备
 */
public class SelectActivity extends AppCompatActivity {
    private static final String TAG = "SelectActivity";

    private Set<BleDevice> mBleDeviceHashSet = Collections.synchronizedSet(new HashSet<BleDevice>(10));

    private SelectAdapter mSelectAdapter;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private BleScanner mBleScanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select);

        initView();

        mBleScanner = new BleScanner(this.getApplicationContext());
        mBleScanner.filter("VENZ-ECHO");

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mBleScanner != null) {
            mBleScanner.stopLeScan();
        }
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSelectAdapter = new SelectAdapter(new SelectAdapter.OnBleClickListener() {
            @Override
            public void onBleClick(BleDevice device) {
                Intent intent = new Intent();

                intent.putExtra("ble_mac", device.mac);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        recyclerView.setAdapter(mSelectAdapter);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void startScan(View v) {

        mBleScanner.startLeScan(SelectActivity.this, new BleScanCallback() {
            @Override
            public void onScanResult(BleDevice device) {
                mBleDeviceHashSet.add(device);
            }

            @Override
            public void onError(Exception e) {
                showToast("请检查蓝牙是否打开");
                Log.e(TAG, "onError: ", e);
            }
        });


        mHandler.removeCallbacksAndMessages(null);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                BleDevice[] bleDevices = new BleDevice[mBleDeviceHashSet.size()];
                bleDevices = mBleDeviceHashSet.toArray(bleDevices);
                Arrays.sort(bleDevices, new Comparator<BleDevice>() {
                    @Override
                    public int compare(BleDevice o1, BleDevice o2) {
                        return -o1.rssi + o2.rssi;
                    }
                });

                mSelectAdapter.setBleDevices(bleDevices);

                mHandler.postDelayed(this, 1000);
            }
        }, 500);

    }

    public void stopScan(View v) {
        if (mBleScanner != null) {
            mBleScanner.stopLeScan();
        }
        mHandler.removeCallbacksAndMessages(null);
    }
}
