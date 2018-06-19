package com.ble.lib.demo.daemon;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;

import static com.ble.lib.demo.daemon.ServiceUtil.checkServices;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DaemonService extends JobService {

    private static final String TAG = "DaemonService";

    public static final int JOB_ID = 111123231;

    private Handler mHandler = new Handler(Looper.getMainLooper(),
            new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    Log.i(TAG, "handleMessage: ...");
                    checkServices(DaemonService.this);
                    jobFinished((JobParameters) msg.obj, false);
                    return true;
                }
            });


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: .. params = " + params.getJobId());
        mHandler.sendMessage(Message.obtain(mHandler, 1, params));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob: .....");

        mHandler.removeMessages(1);

        return false;
    }


}
