package com.ehooworld.blekit;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.receiver.listener.BluetoothBondListener;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.response.SearchResponse;

/**
 * Created by xuhuanli on 2017/12/4.
 */

/**
 * 蓝牙设备的扫描/开关/配对
 */
public interface IBluetooth {
    /**
     * 创建一个扫描器
     * 支持经典蓝牙和BLE设备混合扫描，可自定义扫描策略。每次扫描都要创建新的SearchRequest，不能复用。
     *
     * @return SearchRequest对象
     */
    SearchRequest newSearcher(int durationBLE, int timesBLE, int durationClassic, int timesClassic);

    /**
     * 默认扫描器
     * 先扫BLE设备3次，每次3s 再扫经典蓝牙5s 再扫BLE设备2s
     *
     * @return SearchRequest对象
     */
    SearchRequest newSearcher();

    /**
     * 判断蓝牙是否打开
     *
     * @return
     */
    boolean isOpenBT();

    /**
     * 打开蓝牙
     *
     * @return
     */
    boolean openBT();

    /**
     * 关闭蓝牙
     *
     * @return
     */
    boolean closeBT();

    /**
     * 获取一个全局的蓝牙管理器
     * 建议作为一个全局单例，管理所有BLE设备的连接。
     * 所有接口都通过BluetoothClient调用，涉及的常量如回调的错误码都在Constants类中。
     *
     * @return
     */
    BluetoothClient getClient();

    /**
     * 扫描
     *
     * @param request
     * @param response
     */
    void search(SearchRequest request, SearchResponse response);

    /**
     * 停止扫描
     */
    void stopSearch();

    /**
     * 蓝牙打开或关闭需要一段时间，可以注册回调监听状态，回调的参数如果是true表示蓝牙已打开，false表示蓝牙关闭
     *
     * @param listener
     */
    void registerBluetoothStateListener(BluetoothStateListener listener);

    /**
     * 取消蓝牙状态注册回调监听
     *
     * @param listener
     */
    void unregisterBluetoothStateListener(BluetoothStateListener listener);

    /**
     * 监听设备配对状态变化
     * @param listener
     */
    void registerBluetoothBondListener(BluetoothBondListener listener);

    /**
     * 取消监听设备配对状态变化
     * @param listener
     */
    void unregisterBluetoothBondListener(BluetoothBondListener listener);
}
