package com.ehooworld.blekit;

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

/**
 * 蓝牙连接与通信相关接口
 */
public interface IBLuetoothConnect {
    /**
     * 连接
     * 连接过程包括了普通的连接(connectGatt)和发现服务(discoverServices)
     * 这里收到回调时表明服务发现已完成。回调参数BleGattProfile包括了所有的service和characteristic的uuid。
     * 返回的code表示操作状态，包括成功，失败或超时等，所有常量都在Constants类中。
     *
     * @param MAC
     * @param response
     */
    void connectDevice(String MAC, BleConnectResponse response);

    /**
     * 带配置参数的连接
     *
     * @param MAC
     * @param options  配置参数
     * @param response
     */
    void connectDevice(String MAC, BleConnectOptions options, BleConnectResponse response);

    /**
     * 默认配置参数
     *
     * @return 参数对象
     */
    BleConnectOptions newConnectConfig();

    /**
     * 配置参数
     *
     * @return 参数对象
     */
    BleConnectOptions newConnectConfig(int retry, int timeout, int retryService, int timeoutService);

    /**
     * 注册连接状态的监听
     *
     * @param MAC
     * @param listener
     */
    void registerConnectStatusListener(String MAC, BleConnectStatusListener listener);

    /**
     * 取消注册连接状态的监听
     *
     * @param MAC
     * @param listener
     */
    void unregisterConnectStatusListener(String MAC, BleConnectStatusListener listener);

    /**
     * 主动获取连接状态
     *
     * @param MAC
     * @return // Constants.STATUS_UNKNOWN
     * // Constants.STATUS_DEVICE_CONNECTED
     * // Constants.STATUS_DEVICE_CONNECTING
     * // Constants.STATUS_DEVICE_DISCONNECTING
     * // Constants.STATUS_DEVICE_DISCONNECTED
     */
    int getConnectStatus(String MAC);

    /**
     * 断开连接
     *
     * @param MAC
     */
    void disconnect(String MAC);

    /**
     * 写Characteristic
     *
     * @param MAC
     * @param service
     * @param character
     * @param value     要注意这里写的byte[]不能超过20字节，如果超过了需要自己分成几次写。建议的办法是第一个byte放剩余要写的字节的长度。
     * @param response
     */
    void writeData(String MAC, UUID service, UUID character, byte[] value, BleWriteResponse response);

    /**
     * 读Characteristic
     *
     * @param MAC
     * @param service
     * @param character
     * @param response
     */
    void readData(String MAC, UUID service, UUID character, BleReadResponse response);

    /**
     * 读Descriptor
     *
     * @param MAC
     * @param service
     * @param character
     * @param descriptor
     * @param response
     */
    void readDescriptor(String MAC, UUID service, UUID character, UUID descriptor, BleReadResponse response);

    /**
     * 写Descriptor
     *
     * @param MAC
     * @param service
     * @param character
     * @param descriptor
     * @param value
     * @param response
     */
    void writeDescriptor(String MAC, UUID service, UUID character, UUID descriptor, byte[] value, BleWriteResponse response);

    /**
     * 打开通知
     * 简介 Notify和Indicate : Notify无需client端确认,client直接收到信息 Indicate需要client端确认,在client确认下，才会收到信息
     *
     * @param MAC
     * @param service
     * @param character
     * @param response
     */
    void openNotify(String MAC, UUID service, UUID character, BleNotifyResponse response);

    /**
     * 关闭通知
     *
     * @param MAC
     * @param service
     * @param character
     * @param response
     */
    void closeNotify(String MAC, UUID service, UUID character, BleUnnotifyResponse response);

    /**
     * 打开指示
     *
     * @param MAC
     * @param service
     * @param character
     * @param response
     */
    void openIndicate(String MAC, UUID service, UUID character, BleNotifyResponse response);

    /**
     * 关闭指示
     *
     * @param MAC
     * @param service
     * @param character
     * @param response
     */
    void closeIndicate(String MAC, UUID service, UUID character, BleUnnotifyResponse response);

    /**
     * 读取信号强度
     *
     * @param MAC
     * @param response
     */
    void readRssi(String MAC, BleReadRssiResponse response);

    /**
     * 刷新缓存
     *
     * @param MAC
     */
    void refreshCache(String MAC);

    /**
     * 清理请求队列
     * 如果发送给设备的请求设备来不及处理，则这些请求会保存到队列中，如果在某些场景下需要清除这些请求
     *
     * @param MAC
     * @param clearType // Constants.REQUEST_READ，所有读请求
     *                  // Constants.REQUEST_WRITE，所有写请求
     *                  // Constants.REQUEST_NOTIFY，所有通知相关的请求
     *                  // Constants.REQUEST_RSSI，所有读信号强度的请求
     */
    void clearRequest(String MAC, int clearType);

    /**
     * 获取Beacon
     * 可以在广播中携带设备的自定义数据，用于设备识别，数据广播，事件通知等，这样手机端无需连接设备就可以获取设备推送的数据。
     * 扫描到的beacon数据为byte[]，在SearchResult的scanRecord中
     * @param scanRecord
     * @return
     */
    Beacon getBeacon(byte[] scanRecord);
}
