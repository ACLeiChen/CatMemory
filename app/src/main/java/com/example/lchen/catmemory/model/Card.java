package com.example.lchen.catmemory.model;

/**
 * Created by Lei Chen on 2018/2/24.
 */

public class Card {

    private int frontImageId;

    private int backImageId;

    private boolean shouldBeFlipped;

    private boolean isCatVisible;

    private CardActionExecutor cardActionExecutor;
    private boolean found;

    public Card(int frontImageId, int backImageId) {
        this.frontImageId = frontImageId;
        this.backImageId = backImageId;
    }

    public int getFrontImageId() {
        return frontImageId;
    }

    public int getBackImageId() {
        return backImageId;
    }

    public boolean shouldBeFlipped() {
        return shouldBeFlipped;
    }

    public void setShouldBeFlipped(boolean shouldBeFlipped) {
        this.shouldBeFlipped = shouldBeFlipped;
    }

    public boolean isCatVisible() {
        return isCatVisible;
    }

    public void setIsCatVisible(boolean catVisible) {
        isCatVisible = catVisible;
    }

    public void setCardActionExecutor(CardActionExecutor cardActionExecutor) {
        this.cardActionExecutor = cardActionExecutor;
    }

    public void flipCard(){
        cardActionExecutor.flipCard(this);
    }

    public void setInvisible(){
        cardActionExecutor.setCardInvisible(this);
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public interface CardActionExecutor {
        void flipCard(Card card);
        void setCardInvisible(Card card);
    }
}
