package com.ble.lib.x.request;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import cn.ginshell.bong.BongApp;
import cn.ginshell.bong.ble.BaseRequest;

/**
 * @author hackill
 * @date 1/18/16.
 */
public class XRequest extends BaseRequest {

    private byte[][] mCommand;
    private boolean errorHasSync = true;

    protected XResponse mResponse;
    protected List<byte[]> mResultList = new ArrayList<>();

    protected Handler mHandler = BongApp.getBongComponent().getHandler();

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

    /**
     * 是否设置了前面有数据同步则报错退出
     *
     * @return
     */
    public boolean isErrorHasSync() {
        return errorHasSync;
    }

    /**
     * 是否设置了前面有数据同步则报错退出
     * default is true
     *
     * @return
     */
    public void setErrorHasSync(boolean errorHasSync) {
        this.errorHasSync = errorHasSync;
    }
}
