package com.ehooworld.blekit;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleReadResponse;
import com.inuker.bluetooth.library.connect.response.BleReadRssiResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;

import java.util.UUID;

/**
 * Created by xuhuanli on 2017/12/4.
 */

public class BLuetoothConnectCmpl implements IBLuetoothConnect {
    private BluetoothClient mClient = MyApp.getBluetoothClient();

    @Override
    public void connectDevice(String MAC, BleConnectResponse response) {
        mClient.connect(MAC, response);
    }

    @Override
    public void connectDevice(String MAC, BleConnectOptions options, BleConnectResponse response) {
        mClient.connect(MAC, options, response);
    }

    @Override
    public BleConnectOptions newConnectConfig() {
        return newConnectConfig(3, 30000, 2, 20000);
    }

    @Override
    public BleConnectOptions newConnectConfig(int retry, int timeout, int retryService, int timeoutService) {
        return new BleConnectOptions.Builder()
                .setConnectRetry(retry)   // 连接如果失败重试3次
                .setConnectTimeout(timeout)   // 连接超时30s
                .setServiceDiscoverRetry(retryService)  // 发现服务如果失败重试3次
                .setServiceDiscoverTimeout(timeoutService)  // 发现服务超时20s
                .build();
    }

    @Override
    public void registerConnectStatusListener(String MAC, BleConnectStatusListener listener) {
        mClient.registerConnectStatusListener(MAC, listener);
    }

    @Override
    public void unregisterConnectStatusListener(String MAC, BleConnectStatusListener listener) {
        mClient.unregisterConnectStatusListener(MAC, listener);
    }

    @Override
    public int getConnectStatus(String MAC) {
        return mClient.getConnectStatus(MAC);
    }

    @Override
    public void disconnect(String MAC) {
        mClient.disconnect(MAC);
    }

    @Override
    public void writeData(String MAC, UUID service, UUID character, byte[] value, BleWriteResponse response) {
        mClient.write(MAC, service, character, value, response);
    }

    @Override
    public void readData(String MAC, UUID service, UUID character, BleReadResponse response) {
        mClient.read(MAC, service, character, response);
    }

    @Override
    public void readDescriptor(String MAC, UUID service, UUID character, UUID descriptor, BleReadResponse response) {
        mClient.readDescriptor(MAC, service, character, descriptor, response);
    }

    @Override
    public void writeDescriptor(String MAC, UUID service, UUID character, UUID descriptor, byte[] value, BleWriteResponse response) {
        mClient.writeDescriptor(MAC, service, character, descriptor, value, response);
    }

    @Override
    public void openNotify(String MAC, UUID service, UUID character, BleNotifyResponse response) {
        mClient.notify(MAC, service, character, response);
    }

    @Override
    public void closeNotify(String MAC, UUID service, UUID character, BleUnnotifyResponse response) {
        mClient.unnotify(MAC, service, character, response);
    }

    @Override
    public void openIndicate(String MAC, UUID service, UUID character, BleNotifyResponse response) {
        mClient.indicate(MAC, service, character, response);
    }

    @Override
    public void closeIndicate(String MAC, UUID service, UUID character, BleUnnotifyResponse response) {
        mClient.unindicate(MAC, service, character, response);
    }

    @Override
    public void readRssi(String MAC, BleReadRssiResponse response) {
        mClient.readRssi(MAC, response);
    }

    @Override
    public void refreshCache(String MAC) {
        mClient.refreshCache(MAC);
    }

    @Override
    public void clearRequest(String MAC, int clearType) {
        mClient.clearRequest(MAC, clearType);
    }

    @Override
    public Beacon getBeacon(byte[] scanRecord) {
        return new Beacon(scanRecord);
    }
}
