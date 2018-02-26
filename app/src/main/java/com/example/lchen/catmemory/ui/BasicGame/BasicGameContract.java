package com.example.lchen.catmemory.ui.BasicGame;

import com.example.lchen.catmemory.domain.GameLogic;
import com.example.lchen.catmemory.model.Card;
import com.example.lchen.catmemory.model.User;

import java.util.List;

/**
 * Created by L.Chen on 20/02/2018.
 */

public interface BasicGameContract {

    interface View {
        void showCards(List<Card> cards);

        void showFinalResult(User winner);

        void updateUserScore(int index, float progress);
    }

    interface Presenter extends GameLogic.GameLogicListener {

        void loadCards();

        List<Card> getStartingCards();
    }

}
