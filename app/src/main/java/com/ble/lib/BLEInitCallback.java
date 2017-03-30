package com.ble.lib;

/**
 * @author hackill
 * @date 11/12/15.
 */
public interface BLEInitCallback {
    void onSuccess();

    boolean onFailure(int error);
}
