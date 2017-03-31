package com.ble.lib;

/**
 * @author hackill
 */
public interface BleManager {
    /**
     * 连接蓝牙
     *
     * @param address
     * @param callback
     */
    void connect(String address, BLEInitCallback callback);

    /**
     * 释放蓝牙
     */
    void close();

    /**
     * 重新连接
     *
     * @param callback
     */
    void reconnect(BLEInitCallback callback);

    /**
     * 断开连接
     */
    void disconnect();

    /**
     * 添加 请求 并执行
     *
     * @param request 请求
     * @param tag     标签，用于cancel
     */
    void addRequest(BaseRequest request, String tag);


    /**
     * 添加请求
     *
     * @param request
     */
    void addRequest(BaseRequest request);

    /**
     * 取消请求
     *
     * @param tag 在addRequest 中添加的tag ，null 清空所有请求
     */
    void cancel(String tag);

    /**
     * 取消所有请求
     */
    void cancelAll();

    /**
     * 退出 并释放队列
     */
    void quit();


    /**
     * 判断蓝牙是否连接
     *
     * @return
     */
    boolean isConnected();

    /**
     * 读取蓝牙信号
     *
     * @param callback
     * @return
     */
    boolean readRssi(BleRssiCallback callback);

    /**
     * 读取蓝牙名称
     *
     * @return
     */
    String readDeviceName();
}
