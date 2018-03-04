package com.example.lchen.catmemory.data;

import com.example.lchen.catmemory.domain.model.Card;

import java.util.List;

/**
 * Created by Lei Chen on 2018/3/4.
 */

public interface CardsRepository {
    List<Card> getCards();
    boolean isImageDownloadingFinished();
    void setFrontImagesFromLocalRes();
}
