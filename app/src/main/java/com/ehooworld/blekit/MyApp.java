package com.ehooworld.blekit;

import android.app.Application;

import com.inuker.bluetooth.library.BluetoothClient;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by xuhuanli on 2017/12/4.
 */

public class MyApp extends Application {

    private static BluetoothClient mClient;
    private static ExecutorService mThreadPool;

    @Override
    public void onCreate() {
        super.onCreate();
        mClient = new BluetoothClient(this);
        mThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public static BluetoothClient getBluetoothClient(){
        return mClient;
    }

    public static ExecutorService getThreadPool(){
        return mThreadPool;
    }




}
