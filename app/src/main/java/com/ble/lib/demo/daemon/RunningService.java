package com.ble.lib.demo.daemon;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.ble.lib.demo.BuildConfig;
import com.ble.lib.demo.R;

import java.util.concurrent.TimeUnit;

public class RunningService extends Service {
    private static final String TAG = "BongRunningService";

    private boolean mShowNotify = true;

    private int notifyId = 200;
    private Notification.Builder mNotificationBuilder;
    private NotificationManager mNotificationManager;
    private Handler mHandler;

    private static final long CONNECTION_CHECK_INTERVAL = TimeUnit.SECONDS.toMillis(5);

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate() called");

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationBuilder = new Notification.Builder(this);
        mNotificationBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(getString(R.string.app_name))
                .setOngoing(true)
                .setContentText("任务service")
                .setPriority(Notification.PRIORITY_MAX);


        PackageManager manager = getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(BuildConfig.APPLICATION_ID);
        if (i != null) {
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            PendingIntent activity = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

            mNotificationBuilder.setContentIntent(activity);
        }


        mHandler = new Handler(Looper.getMainLooper());


        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: " + DateUtil.formatDateDefault(System.currentTimeMillis()));
                showToast("同步数据~ "+ DateUtil.formatDateDefault(System.currentTimeMillis()));
                mHandler.postDelayed(this, CONNECTION_CHECK_INTERVAL);

                mNotificationBuilder.setContentText("同步数据【"+DateUtil.formatDateDefault(System.currentTimeMillis())+"】");

                if (mShowNotify) {
                    mNotificationManager.notify(notifyId, mNotificationBuilder.build());
                }
            }
        });
        Log.d(TAG, "start check interval ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (mShowNotify) {
            startForeground(notifyId, mNotificationBuilder.build());
        } else {
            stopForeground(true);
        }
        Log.d(TAG, "onStartCommand() called with: intent = [" + intent + "], flags = [" + flags + "], startId = [" + startId + "]");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
