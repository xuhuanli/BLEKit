package com.ehooworld.blekit;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.receiver.listener.BluetoothBondListener;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.response.SearchResponse;

/**
 * Created by xuhuanli on 2017/12/4.
 */

public class BluetoothCmpl implements IBluetooth {
    private BluetoothClient mClient = MyApp.getBluetoothClient();

    @Override
    public SearchRequest newSearcher(int durationBLE, int timesBLE, int durationClassic, int timesClassic) {
        return new SearchRequest.Builder()
                .searchBluetoothLeDevice(durationBLE, timesBLE)
                .searchBluetoothClassicDevice(durationClassic,timesClassic)
                .searchBluetoothLeDevice(durationBLE, timesBLE)
                .build();
    }

    @Override
    public SearchRequest newSearcher() {
        return new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 3)
                .searchBluetoothClassicDevice(5000)
                .searchBluetoothLeDevice(2000, 2)
                .build();
    }

    @Override
    public boolean isOpenBT() {
        return mClient.isBluetoothOpened();
    }

    @Override
    public boolean openBT() {

        return mClient.openBluetooth();
    }

    @Override
    public boolean closeBT() {

        return mClient.closeBluetooth();
    }

    @Override
    public BluetoothClient getClient() {
        return mClient;
    }

    @Override
    public void search(final SearchRequest request, final SearchResponse response) {
        MyApp.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
//                Log.d("MainActivity",Process.myTid()+"");
                mClient.search(request,response);
            }
        });


    }

    @Override
    public void stopSearch() {
        mClient.stopSearch();

    }

    @Override
    public void registerBluetoothStateListener(BluetoothStateListener listener) {
        mClient.registerBluetoothStateListener(listener);
    }

    @Override
    public void unregisterBluetoothStateListener(BluetoothStateListener listener) {
        mClient.unregisterBluetoothStateListener(listener);
    }

    @Override
    public void registerBluetoothBondListener(BluetoothBondListener listener) {
        mClient.registerBluetoothBondListener(listener);
    }

    @Override
    public void unregisterBluetoothBondListener(BluetoothBondListener listener) {
        mClient.unregisterBluetoothBondListener(listener);
    }

}
