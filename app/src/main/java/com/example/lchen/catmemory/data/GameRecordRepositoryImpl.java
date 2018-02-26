package com.example.lchen.catmemory.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.SimpleArrayMap;
import android.util.ArrayMap;

import com.example.lchen.catmemory.R;
import com.example.lchen.catmemory.model.Difficulty;

/**
 * Created by Lei Chen on 2018/2/25.
 */

public class GameRecordRepositoryImpl implements GameRecordRepository {

    private Context context;
    SharedPreferences sharedPref;
    SimpleArrayMap<String, Integer> topScoreRecord;

    public GameRecordRepositoryImpl(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), Context.MODE_PRIVATE);

        initializeScoreRecord();
    }

    private void initializeScoreRecord() {
        topScoreRecord = new SimpleArrayMap<>();
        topScoreRecord.put(Difficulty.DIFFICULTY_EASY, sharedPref.getInt(Difficulty.DIFFICULTY_EASY, 0));
        topScoreRecord.put(Difficulty.DIFFICULTY_MIDDLE, sharedPref.getInt(Difficulty.DIFFICULTY_MIDDLE, 0));
        topScoreRecord.put(Difficulty.DIFFICULTY_HARD, sharedPref.getInt(Difficulty.DIFFICULTY_HARD, 0));
    }

    @Override
    public void tryToWriteTopScore(int score, Difficulty difficulty) {
        if(topScoreRecord.get(difficulty.getName()) < score){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(difficulty.getName(), score);
            editor.commit();
        }
    }

    @Override
    public int getTopScore(String difficultyName){
        return sharedPref.getInt(difficultyName, 0);
    }
}
