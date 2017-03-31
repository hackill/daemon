package com.ble.lib.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ble.lib.BLEInitCallback;
import com.ble.lib.BleManager;
import com.ble.lib.GattState;
import com.ble.lib.x.BongHexUtils;
import com.ble.lib.x.BongUtil;
import com.ble.lib.x.XBleManager;
import com.ble.lib.x.request.XPerReadRequest;
import com.ble.lib.x.request.XPerReadResponse;
import com.ble.lib.x.request.XReadRequest;
import com.ble.lib.x.request.XReadResponse;
import com.ble.lib.x.request.XResponse;
import com.ble.lib.x.request.XWriteRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MainActivity";

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
        findViewById(R.id.btn_cmd).setOnClickListener(this);

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

            case R.id.btn_cmd:
//                cmdDemo();
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

    /**
     * 三种命令
     * a、只写指令 没有返回
     * b、写指令+ 一条返回
     * c、写指令 + 多条返回
     */
    private void cmdDemo() {

        if (mBleManager == null) return;

        //a 类型例子  发送一个震动命令 震动命令是 向设备发送 十六进制指令 260000002001
        //这类指令 只发送指令，不接受结果
        byte[] cmdVibrate = BongUtil.hexStringToBytes("260000002001");

        mBleManager.addRequest(new XWriteRequest(cmdVibrate, new XResponse() {
            @Override
            public void onError(Exception e) {
                //发送失败
            }

            @Override
            public void onCommandSuccess() {
                //发送指令成功
            }
        }));

        // b 类型例子
        //类型二 案例：读取电量 . 2600000010是读取电量指令 ，改指令发送后，有一条结果
        byte[] cmdBattery = BongUtil.hexStringToBytes("2600000010");
        mBleManager.addRequest(new XPerReadRequest(cmdBattery, new XPerReadResponse() {
            @Override
            public void onReceive(byte[] bytes) {
                //解析回调结果 具体如何解析 请查看指令文档
                int bu;
                if (bytes != null && bytes.length > 10) {
                    bu = (bytes[10] & 0x000000ff);
                } else {
                    bu = -1;
                }

                Log.i(TAG, "电量值为：" + bu);
            }

            @Override
            public void onError(Exception e) {
                //发送失败
                // 或者发送成功 但是在接收指令返回值超时

            }

            @Override
            public void onCommandSuccess() {

            }
        }));

        long endTime = System.currentTimeMillis();
        long startTime = endTime - TimeUnit.HOURS.toMillis(6);
        byte[] cmdSync = BongUtil.hexStringToBytes(getSyncDataCmd(startTime, endTime));
        //类型三  案例：同步最近6小时数据 同步数据 发送指令后，会返回一大堆数据，直到接收到终止符
        //
        mBleManager.addRequest(new XReadRequest(cmdSync, new XReadResponse() {
            @Override
            public void onReceive(List<byte[]> list) {
                //总结果 byte list
            }

            @Override
            public void onReceivePerFrame(byte[] bytes) {
                //每条 byte

            }

            @Override
            public void onError(Exception e) {
                //出错

            }

            @Override
            public void onCommandSuccess() {

            }
        }));


    }


    private static String getSyncDataCmd(long beginTime, long endTime) {
        // 这里的规则是使用我们与硬件预定好的时间规则
        return "2000000013" + BongHexUtils.getStrTimeForHex(beginTime) + BongHexUtils.getStrTimeForHex(endTime);
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
