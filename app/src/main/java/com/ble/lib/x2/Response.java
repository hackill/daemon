package com.ble.lib.x2;

import java.util.List;

/**
 * @author hackill
 * @date 11/9/15.
 */
public interface Response<T> {
    void onReceive(List<T> rsp);

    void onReceivePerPackage(T perRsp);

    void onSendPerPackage(int index, int total);

    void onError(Exception e);
}
