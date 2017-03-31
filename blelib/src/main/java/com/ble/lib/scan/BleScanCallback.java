package com.ble.lib.scan;

/**
 * hackill
 *
 * @date 31/03/2017
 */
public interface BleScanCallback {
    void onScanResult(BleDevice device);
    void onError(Exception e);
}
