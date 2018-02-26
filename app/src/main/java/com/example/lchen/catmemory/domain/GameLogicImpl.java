package com.example.lchen.catmemory.domain;

import com.example.lchen.catmemory.data.GameRecordRepository;
import com.example.lchen.catmemory.model.Card;
import com.example.lchen.catmemory.model.Difficulty;
import com.example.lchen.catmemory.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lei Chen on 2018/2/25.
 */

public class GameLogicImpl implements GameLogic {

    private List<Card> mAllCards;
    private List<Card> mStartingCards;
    private Card[] mPairOfCards;

    private Difficulty difficulty;
    private boolean isDraw;

    private User user1;
    private User user2;
    private User currentUser;
    private User winner;

    private GameLogicListener mGameLogicListener;

    private GameRecordRepository mGameRecordRepository;
    private int pairsFound;

    public GameLogicImpl(List<Card> mAllCards, Difficulty difficulty, GameLogicListener mGameLogicListener, GameRecordRepository mGameRecordRepository) {
        this.mAllCards = mAllCards;
        this.difficulty = difficulty;
        this.mGameLogicListener = mGameLogicListener;
        this.mGameRecordRepository = mGameRecordRepository;
    }

    @Override
    public void onStartGame() {
        user1 = new User(1);
        user2 = new User(2);
        currentUser = user1;
        initilizeCards();
    }

    private void initilizeCards() {
        Collections.shuffle(mAllCards);
        generateNewStartingCards(difficulty.getNumberOfCards());
        cleanCachedPair();
    }

    private void cleanCachedPair(){
        mPairOfCards = new Card[2];
    }

    private void generateNewStartingCards(int num){
        mStartingCards = new ArrayList<>();
        int frontImageId;
        int backImageId;

        for(int i = 0; i < num; i++){
            frontImageId = mAllCards.get(i).getFrontImageId();
            backImageId = mAllCards.get(i).getBackImageId();
            mStartingCards.add(new Card(frontImageId, backImageId));
            mStartingCards.add(new Card(frontImageId, backImageId));
        }
        Collections.shuffle(mStartingCards);
    }

    @Override
    public void onEndGame() {
        setWinner();
        updateTopScore(winner.getScore(), difficulty);
    }

    private void setWinner() {
        if(user1.getScore() == user2.getScore()){
            isDraw = true;
            winner = new Nobody();
        }else{
            isDraw = false;
            winner = (user1.getScore() > user2.getScore()) ? user1 : user2;
        }
    }

    private void updateTopScore(int score, Difficulty difficulty) {
        mGameRecordRepository.tryToWriteTopScore(score, difficulty);
    }

    @Override
    public void onTakeTurn() {
        currentUser = (currentUser == user1) ? user2 : user1;
        cleanCachedPair();
    }

    @Override
    public void onPairFound() {
        pairsFound++;
        currentUser.increaseScore();
        currentUser.updateProgress((float) currentUser.getScore() / difficulty.getNumberOfCards());
        mGameLogicListener.updateUserScore(currentUser.getIndex(), currentUser.getProgress());
        markCardsAsFound();
        cleanCachedPair();
    }

    private void markCardsAsFound() {
        for(Card card : mPairOfCards){
            card.setFound(true);
            card.setInvisible();
            //mStartingCards.remove(card);
        }
    }

    @Override
    public void onNotAPairWith(Card clickedCard) {
        mPairOfCards[0].flipCard();
        clickedCard.flipCard();
        mGameLogicListener.takeTurn();
    }

    @Override
    public List<Card> getStartingCards() {
        return mStartingCards;
    }

    @Override
    public void apply(Card clickedCard) {
        if(clickedCard.isFound()){return;}
        else{
            if(clickedCard.isCatVisible()){
                return;
            }else{
                clickedCard.flipCard();
                if(isFirstCard()){
                    mPairOfCards[0] = clickedCard;
                    return;
                }else{
                    if(isPairFoundWith(clickedCard)) {
                        mPairOfCards[1] = clickedCard;
                        onPairFound();
                        endGameIfNecessary();
                    }
                    else{
                        onNotAPairWith(clickedCard);
                    }
                }
            }
        }
    }

    private boolean isFirstCard() {
        return mPairOfCards[0] == null;
    }

    private boolean isPairFoundWith(Card clickedCard) {
        return mPairOfCards[0].getFrontImageId() == clickedCard.getFrontImageId();
    }

    private void endGameIfNecessary() {
        if(pairsFound == difficulty.getNumberOfCards()){
            mGameLogicListener.endGame();
        }
    }

    @Override
    public User getWinner() {
        return winner;
    }


    public class Nobody extends User {

        public Nobody() {
            super(0);
        }

        @Override
        public int getScore(){
            return 0;
        }
    }
}
