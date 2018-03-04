package com.example.lchen.catmemory;

import android.app.Application;

import com.example.lchen.catmemory.data.CardsRepository;
import com.example.lchen.catmemory.data.CardsRepositoryImpl;
import com.example.lchen.catmemory.data.GameRecordRepository;
import com.example.lchen.catmemory.data.GameRecordRepositoryImpl;

/**
 * Created by Lei Chen on 2018/2/26.
 */

public class MyApplication extends Application {

    private static GameRecordRepository mGameRecordRepository;

    private static CardsRepository mCardsRepositoryImpl;

    @Override
    public void onCreate() {
        super.onCreate();
        mGameRecordRepository = new GameRecordRepositoryImpl(this);
        mCardsRepositoryImpl = new CardsRepositoryImpl(this);
    }

    public static GameRecordRepository getGameRecordRepository() {
        return mGameRecordRepository;
    }

    public static CardsRepository getCardsRepositoryImpl() {
        return mCardsRepositoryImpl;
    }
}
