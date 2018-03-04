package com.example.lchen.catmemory.presentation.ui.GameActivity;

import com.example.lchen.catmemory.data.CardsRepository;
import com.example.lchen.catmemory.data.GameRecordRepository;
import com.example.lchen.catmemory.domain.GameLogic;
import com.example.lchen.catmemory.domain.GameLogicImpl;
import com.example.lchen.catmemory.domain.model.Card;
import com.example.lchen.catmemory.domain.model.Difficulty;

import java.util.List;

/**
 * Created by Lei Chen on 2018/2/25.
 */

public class GameActivityPresenter implements GameContract.Presenter {

    private GameContract.View mView;

    private GameLogic mGameLogic;

    private Difficulty difficulty;


    private boolean isGameStarted;

    public GameActivityPresenter(GameContract.View mView, Difficulty difficulty, CardsRepository cardsRepository, GameRecordRepository gameRecordRepository) {
        this.mView = mView;
        this.difficulty = difficulty;
        this.mGameLogic = new GameLogicImpl(cardsRepository.getCards(), difficulty, this, gameRecordRepository);
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
        loadCards();
    }

    @Override
    public void endGame() {
        isGameStarted = false;
        mGameLogic.onEndGame();
        mView.showFinalResult(mGameLogic.getWinner());
        updateUserScore(1, 0);
        updateUserScore(2, 0);
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
