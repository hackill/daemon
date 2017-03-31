package com.ble.lib;

/**
 * @author hackill
 *         初始化蓝牙回调
 * @date 11/12/15.
 */
public interface BLEInitCallback {
    void onSuccess();

    boolean onFailure(int error);
}
