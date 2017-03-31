package com.ble.lib.x.request;

/**
 * @author hackill
 * @date 1/20/16.
 */
public interface XPerReadResponse extends XResponse {
    void onReceive(byte[] rsp);

}
