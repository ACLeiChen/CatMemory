package com.example.lchen.catmemory.data;

import com.example.lchen.catmemory.domain.model.Difficulty;

/**
 * Created by Lei Chen on 2018/2/25.
 */

public interface GameRecordRepository {
    int getTopScore(String difficultyName);
    void tryToWriteTopScore(int score, Difficulty difficulty);
}
