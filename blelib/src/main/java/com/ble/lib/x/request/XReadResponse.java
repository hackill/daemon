package com.ble.lib.x.request;

import java.util.List;

/**
 * @author hackill
 * @date 1/18/16.
 */
public interface XReadResponse extends XResponse {
    void onReceive(List<byte[]> rsp);

    void onReceivePerFrame(byte[] perFrame);
}
