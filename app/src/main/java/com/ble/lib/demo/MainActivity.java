package com.ble.lib.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ble.lib.BLEInitCallback;
import com.ble.lib.BleManager;
import com.ble.lib.GattState;
import com.ble.lib.x.XBleManager;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private String mCurMac = "E4:FE:C6:1A:11:48";
    private TextView mMacTextView, mStatusTextView;

    private BleManager mBleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mMacTextView = (TextView) findViewById(R.id.tv_mac);
        mStatusTextView = (TextView) findViewById(R.id.tv_status);

        findViewById(R.id.btn_select).setOnClickListener(this);
        findViewById(R.id.btn_connect).setOnClickListener(this);
        findViewById(R.id.btn_disconnect).setOnClickListener(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mBleStateReceiver, new IntentFilter(GattState.BLE_STATE_CHANGE));

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btn_select:
                Intent intent = new Intent(this, SelectActivity.class);
                startActivityForResult(intent, 10);
                break;
            case R.id.btn_connect:
                if (!TextUtils.isEmpty(mCurMac)) {
                    connect();
                } else {
                    showToast("先选择设备");
                }
                break;
            case R.id.btn_disconnect:
                if (!TextUtils.isEmpty(mCurMac)) {
                    disconnect();
                } else {
                    showToast("先选择设备");
                }
                break;
        }
    }

    /**
     * 连接状态会以广播的形式通知出来
     * 蓝牙连接state
     */
    private BroadcastReceiver mBleStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String state = intent.getStringExtra("state");
            mStatusTextView.setText("连接状态：" + state);
            if (TextUtils.equals(state, "CONNECTED")) {
                showToast("连接成功");
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            mCurMac = data.getStringExtra("ble_mac");
            mMacTextView.setText("当前设备：" + mCurMac);
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private void connect() {


        /**
         * bleManager最好使用单例模式
         */
        if (mBleManager == null)
            mBleManager = new XBleManager(this.getApplication());

        mBleManager.connect(mCurMac, new BLEInitCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public boolean onFailure(int error) {
                return false;
            }
        });

    }


    private void disconnect() {
        if (mBleManager != null) {
            mBleManager.cancelAll();
            mBleManager.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBleManager != null) {
            mBleManager.cancelAll();
            mBleManager.disconnect();
            mBleManager.quit();
            mBleManager = null;
        }

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBleStateReceiver);

    }
}
