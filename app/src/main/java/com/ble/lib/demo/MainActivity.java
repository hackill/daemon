package com.ble.lib.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ble.lib.BLEInitCallback;
import com.ble.lib.BleManager;
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

                } else {
                    showToast("先选择设备");
                }
                break;
            case R.id.btn_disconnect:
                if (!TextUtils.isEmpty(mCurMac)) {

                } else {
                    showToast("先选择设备");
                }
                break;
        }
    }

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


    private void connext() {


        /**
         * bleManager最好使用单例模式
         */
        if (mBleManager == null)
            mBleManager = new XBleManager(this.getApplication());

        mBleManager.connect(mCurMac, new BLEInitCallback() {
            @Override
            public void onSuccess() {
                showToast("连接成功");
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

    }
}
