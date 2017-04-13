package com.ble.lib.demo.daemon;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * @author hackill
 */
public class ServiceUtil {

    private static final String TAG = "ServiceUtil";

    public synchronized static void checkServices(Context c) {
        Context context = c.getApplicationContext();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(Integer.MAX_VALUE);

        String running = RunningService.class.getName();

        boolean runOk = false;

        if (runningServices != null) {
            for (ActivityManager.RunningServiceInfo rsi : runningServices) {
                if (!runOk && TextUtils.equals(rsi.service.getClassName(), running)) {
                    runOk = true;
                }
            }
        }

        Log.i(TAG, "checkServices: isOk = " + runOk + ", running = " + running);

        if (!runOk) {
            Intent intent = new Intent(context, RunningService.class);
            context.startService(intent);
        }
    }
}
