package com.example.lchen.catmemory.domain.model;


/**
 * Created by Lei Chen on 2018/2/24.
 */

public final class Card {

    private Object onlineImage;
    private int frontImageId;
    private int backImageId;

    private boolean shouldBeFlipped;

    private boolean isCatVisible;

    private CardActionExecutor cardActionExecutor;
    private boolean found;

    private int pairId;

    public Card(){}

    public Card(Card card){
        if(card.onlineImage != null){
            this.onlineImage = card.onlineImage;
        }
        this.frontImageId = card.frontImageId;
        this.backImageId = card.backImageId;
    }

    public Card(int frontImageId, int backImageId) {
        this.frontImageId = frontImageId;
        this.backImageId = backImageId;
    }

    public void setFrontImageId(int frontImageId) {
        this.frontImageId = frontImageId;
    }

    public void setBackImageId(int backImageId) {
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

    public void setOnlineImage(Object onlineImage) {
        this.onlineImage = onlineImage;
    }

    public Object getOnlineImage() {
        return onlineImage;
    }

    public int getPairId() {
        return pairId;
    }

    public void setPairId(int pairId) {
        this.pairId = pairId;
    }

    public interface CardActionExecutor {
        void flipCard(Card card);
        void setCardInvisible(Card card);
    }


}
