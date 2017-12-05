package com.ehooworld.blekit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.ByteUtils;

import java.util.UUID;

import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;

public class MainActivity extends AppCompatActivity implements IMainView {

    private static final String TAG = "MainActivity";
    private IBluetooth mBluetooth;
    private boolean isOpen = false;
    private final BluetoothStateListener mBluetoothStateListener = new BluetoothStateListener() {
        @Override
        public void onBluetoothStateChanged(boolean openOrClosed) {
            isOpen = openOrClosed;
            Log.d(TAG, openOrClosed+"");
        }

    };
    private final BleConnectStatusListener mBleConnectStatusListener = new BleConnectStatusListener() {

        @Override
        public void onConnectStatusChanged(String mac, int status) {
            if (status == STATUS_CONNECTED) {

            } else if (status == STATUS_DISCONNECTED) {

            }
        }
    };
    private IBLuetoothConnect mConnectCmpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetooth = new BluetoothCmpl();
        mBluetooth.registerBluetoothStateListener(mBluetoothStateListener);

        //connect devices
        mConnectCmpl = new BLuetoothConnectCmpl();
        mConnectCmpl.registerConnectStatusListener("08:7C:BE:96:27:3F",mBleConnectStatusListener);

    }


    public void detectedBT(View view) {
        if (mBluetooth.isOpenBT()) {
            Log.d(TAG, "蓝牙已打开");
            isOpen = true;

        } else {
            mBluetooth.openBT();
            //蓝牙打开或关闭需要一段时间，可以注册回调监听状态，回调的参数如果是true表示蓝牙已打开，false表示蓝牙关闭
        }
    }

    public void startSearch(View view) {
        if (!isOpen) {
            return;
        }
        SearchRequest searchRequest = mBluetooth.newSearcher();
        mBluetooth.search(searchRequest, new SearchResponse() {
            @Override
            public void onSearchStarted() {
                Log.d(TAG, "onSearchStarted excuted");
            }

            @Override
            public void onDeviceFounded(SearchResult device) {
                Log.d(TAG, "onDeviceFounded excuted : " +
                        device.getAddress()+"\t" +
                        device.getName());
                Beacon beacon = new Beacon(device.scanRecord);
                BluetoothLog.d(String.format("beacon for %s\n%s", device.getAddress(), beacon.toString()));
            }

            @Override
            public void onSearchStopped() {
                Log.d(TAG, "onSearchStopped excuted");
            }

            @Override
            public void onSearchCanceled() {
                Log.d(TAG, "onSearchCanceled excuted");
            }
        });
    }

    public void connectD(View view){
//        mConnectCmpl.registerConnectStatusListener("08:7C:BE:96:27:3F",mBleConnectStatusListener);
        mConnectCmpl.connectDevice("08:7C:BE:96:27:3F", new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile data) {
                if (code == Constants.REQUEST_SUCCESS) {
                    Log.d(TAG,"connect success");
                }
            }
        });


    }

    public void writeData(View view){
        writeValue(new byte[]{}, new BleWriteResponse() {
            @Override
            public void onResponse(int code) {
                if (code == Constants.REQUEST_SUCCESS) {
                    Log.d(TAG,"writeData success");
                }
            }
        });
    }

    private void writeValue(byte[] value, BleWriteResponse response) {
        mConnectCmpl.writeData("08:7C:BE:96:27:3F", UUIDUtils.makeUUID(0xFEE0), UUIDUtils.makeUUID(0xFEE2), ByteUtils.stringToBytes("F006010301000106"),response);
    }

    public void readData(View view){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBluetooth.stopSearch();
        if (mBluetooth.isOpenBT()) {
            mBluetooth.closeBT();
        }

        mBluetooth.unregisterBluetoothStateListener(mBluetoothStateListener);
        mConnectCmpl.unregisterConnectStatusListener("94:65:2D:9E:3C:77",mBleConnectStatusListener);

//        MyApp.getThreadPool().shutdown();  //显示调用存在些问题
    }
}
