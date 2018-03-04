package com.example.lchen.catmemory.presentation.util;

import com.example.lchen.catmemory.domain.model.Card;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lei Chen on 2018/3/3.
 */

public class ImageDownloadTask extends AsyncTask<String, Void, List<Bitmap>> {

    private final String TAG = "ImageDownloadTask";

    private List<Card> cards;

    public ImageDownloadTask(List<Card> cards){
        this.cards = cards;
    }

    @Override
    protected List<Bitmap> doInBackground(String... strings) {
        List<Bitmap> images = new ArrayList<>();

        for(String url : strings){
            HttpURLConnection connection = null;
            try {
                URL currentURL = new URL(url);
                connection = (HttpURLConnection) currentURL.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();

                Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                images.add(bmp);
            }catch(IOException e){
                Log.e(TAG, "Download failed", e);
            }finally{
                connection.disconnect();
            }
        }
        return images;
    }

    protected void onPostExecute(List<Bitmap> result) {
        for(int i = 0; i  < cards.size(); i++){
            cards.get(i).setOnlineImage(result.get(i));
        }
    }
}
