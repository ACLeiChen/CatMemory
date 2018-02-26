package com.example.lchen.catmemory.ui.GameActivity;

import com.example.lchen.catmemory.data.GameRecordRepository;
import com.example.lchen.catmemory.domain.GameLogic;
import com.example.lchen.catmemory.domain.GameLogicImpl;
import com.example.lchen.catmemory.model.Card;
import com.example.lchen.catmemory.model.Difficulty;
import com.example.lchen.catmemory.ui.BasicGame.BasicGameContract;

import java.util.List;

/**
 * Created by Lei Chen on 2018/2/25.
 */

public class GameActivityPresenter implements BasicGameContract.Presenter {

    private BasicGameContract.View mView;

    private GameLogic mGameLogic;

    private Difficulty difficulty;

    private List<Card> mAllCards;

    private GameRecordRepository mGameRecordRepository;

    private boolean isGameStarted;

    public GameActivityPresenter(BasicGameContract.View mView, Difficulty difficulty, List<Card> mAllCards, GameRecordRepository mGameRecordRepository) {
        this.mView = mView;
        this.difficulty = difficulty;
        this.mAllCards = mAllCards;
        this.mGameRecordRepository = mGameRecordRepository;
        this.mGameLogic = new GameLogicImpl(mAllCards, difficulty, this, mGameRecordRepository);
    }

    @Override
    public void loadCards() {
        mView.showCards(getStartingCards());
    }

    @Override
    public List<Card> getStartingCards() {
        return mGameLogic.getStartingCards();
    }

    @Override
    public void startGame() {
        isGameStarted = true;
        mGameLogic.onStartGame();
        //loadCards();
    }

    @Override
    public void endGame() {
        isGameStarted = false;
        mGameLogic.onEndGame();
        mView.showFinalResult(mGameLogic.getWinner());
    }

    @Override
    public void takeTurn() {
        mGameLogic.onTakeTurn();
    }

    @Override
    public boolean isGameStarted() {
        return isGameStarted;
    }

    @Override
    public void applyGameLogic(Card clickedCard) {
        mGameLogic.apply(clickedCard);
    }

    @Override
    public void updateUserScore(int index, float progress) {
        mView.updateUserScore(index, progress);
    }
}
