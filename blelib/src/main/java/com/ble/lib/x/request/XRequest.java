package com.ble.lib.x.request;

import android.os.Handler;
import android.os.Looper;

import com.ble.lib.BaseRequest;

import java.util.ArrayList;
import java.util.List;


/**
 * @author hackill
 * @date 1/18/16.
 */
public class XRequest extends BaseRequest {

    private byte[][] mCommand;
    protected XResponse mResponse;
    protected List<byte[]> mResultList = new ArrayList<>();

    protected Handler mHandler = new Handler(Looper.getMainLooper());

    public XRequest(byte[][] mCommand, XResponse mResponse) {
        this.mCommand = mCommand;
        this.mResponse = mResponse;
    }

    @Override
    public void discardResult() {
        mResponse = null;
    }

    @Override
    public boolean isCanceled() {
        return mResponse == null;
    }

    public byte[][] getCommand() {
        return mCommand;
    }

    @Override
    public void deliverError(final Exception e) {
        final XResponse r = mResponse;

        if (r == null)
            return;

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                r.onError(e);
            }
        });
    }

    public void deliverCommandSuccess() {
        final XResponse r = mResponse;

        if (r == null)
            return;

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                r.onCommandSuccess();
            }
        });
    }

}
