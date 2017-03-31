package com.ble.lib.x.request;

/**
 * @author hackill
 * @date 1/18/16.
 */
public interface XResponse {

    void onError(Exception e);

    void onCommandSuccess();
}
