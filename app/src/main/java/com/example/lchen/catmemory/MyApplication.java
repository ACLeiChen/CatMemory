package com.example.lchen.catmemory;

import android.app.Application;

import com.example.lchen.catmemory.data.GameRecordRepository;
import com.example.lchen.catmemory.data.GameRecordRepositoryImpl;

/**
 * Created by Lei Chen on 2018/2/26.
 */

public class MyApplication extends Application {

    public static GameRecordRepository mGameRecordRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        mGameRecordRepository = new GameRecordRepositoryImpl(this);
    }

    public static GameRecordRepository getGameRecordRepository() {
        return mGameRecordRepository;
    }
}
