package com.example.lchen.catmemory.domain;

import com.example.lchen.catmemory.model.Card;
import com.example.lchen.catmemory.model.User;

import java.util.List;

/**
 * Created by Lei Chen on 2018/2/25.
 */

public interface GameLogic {
    void onStartGame();
    void onEndGame();

    void onTakeTurn();
    void onPairFound();
    void onNotAPairWith(Card clickCard);

    List<Card> getStartingCards();

    void apply(Card clickedCard);

    User getWinner();

    public interface GameLogicListener{
        void startGame();
        void endGame();
        void takeTurn();

        boolean isGameStarted();

        void applyGameLogic(Card clickedCard);

        void updateUserScore(int index, float progress);
    }
}
