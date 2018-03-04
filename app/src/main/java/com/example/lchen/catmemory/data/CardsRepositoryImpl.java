package com.example.lchen.catmemory.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.lchen.catmemory.domain.model.Card;
import com.example.lchen.catmemory.presentation.util.ImageDownloadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lei Chen on 2018/3/4.
 */

public class CardsRepositoryImpl implements CardsRepository {

    private final Context context;

    List<Card> mAllCards;

    private AsyncTask<String, Void, List<Bitmap>> mImageDownloadTask;

    public CardsRepositoryImpl(Context context){
        this.context = context;
        initializeAllCards();
    }

    private void initializeAllCards() {
        initializeCardsWithEmptyImages();
        setBackImages();
        setFrontImagesFromInternet(ImageUrls.get());
    }

    @Override
    public List<Card> getCards() {
        return mAllCards;
    }

    private void initializeCardsWithEmptyImages() {
        mAllCards = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            mAllCards.add(new Card());
        }
    }

    private void setBackImages() {
        int backIconId = context.getResources().getIdentifier("cat_back", "drawable", context.getPackageName());
        for(Card card : mAllCards){
            card.setBackImageId(backIconId);
        }
    }

    private void setFrontImagesFromInternet(String[] urls) {
        mImageDownloadTask = new ImageDownloadTask(mAllCards).execute(urls);
    }

    @Override
    public boolean isImageDownloadingFinished() {
        return mImageDownloadTask.getStatus() == AsyncTask.Status.FINISHED;
    }

    @Override
    public void setFrontImagesFromLocalRes() {
        mImageDownloadTask.cancel(true);
        String drawableName;
        int catResId;
        for(int i = 1; i <= 12; i++){
            drawableName = "cat" + i;
            catResId = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
            mAllCards.get(i - 1).setFrontImageId(catResId);
        }
    }
}
