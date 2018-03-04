package com.example.lchen.catmemory.presentation.ui.GameActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lchen.catmemory.R;
import com.example.lchen.catmemory.domain.model.Card;

import java.util.List;

/**
 * Created by Lei Chen on 2018/2/24.
 */

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder>{

    private List<Card> mCards;
    private CardListener mCardListener;
    private Context context;



    public CardsAdapter(List<Card> mCards, CardListener mCardListener, Context context) {
        this.mCards = mCards;
        this.mCardListener = mCardListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cardView = inflater.inflate(R.layout.card, parent, false);

        return new ViewHolder(cardView, mCardListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Card card = mCards.get(position);
        card.setCardActionExecutor(viewHolder);

        Drawable frontImage;
        if(card.getOnlineImage() == null){
            frontImage = getDrawable(card.getFrontImageId());
        }else{
            frontImage = new BitmapDrawable(context.getResources(), (Bitmap) card.getOnlineImage());
        }
        viewHolder.cardFrontImage.setImageDrawable(frontImage);

        Drawable[] layers = new Drawable[2];
        layers[0] = getDrawable(R.drawable.rectangle);
        layers[1] = getDrawable(card.getBackImageId());
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        viewHolder.cardBackImage.setImageDrawable(layerDrawable);
    }

    private Drawable getDrawable(@DrawableRes int resId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(resId, null);
        }else{
            return context.getResources().getDrawable(resId);
        }
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    public Card getItem(int position) {
        return mCards.get(position);
    }

    public void replaceData(List<Card> cards) {
        mCards = cards;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Card.CardActionExecutor {

        public ImageView cardFrontImage;
        public ImageView cardBackImage;

        private CardListener mCardListener;

        private AnimatorSet mSetLeftIn;
        private AnimatorSet mSetLeftOut;
        private AnimatorSet mSetRightIn;
        private AnimatorSet mSetRightOut;

        public ViewHolder(View itemView, CardListener listener) {
            super(itemView);
            mCardListener = listener;
            cardFrontImage = itemView.findViewById(R.id.card_front_image);
            cardBackImage = itemView.findViewById(R.id.card_back_image);
            itemView.setOnClickListener(this);
            loadAnimations(itemView.getContext());
        }

        private void loadAnimations(Context context) {
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.left_in);
            mSetLeftOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.left_out);
            mSetRightIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.right_in);
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.right_out);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Card card = getItem(position);
            mCardListener.onCardClick(card);
        }

        @Override
        public void flipCard(Card card) {
            if (card.isCatVisible()) {
                //flipOut
                flip(mSetRightOut, cardFrontImage, 10000, mSetLeftIn, cardBackImage, 1000);
                card.setIsCatVisible(false);
            } else {
                //flipIn
                flip(mSetLeftOut, cardBackImage, 0, mSetRightIn, cardFrontImage, 0);
                card.setIsCatVisible(true);
            }
        }

        private void flip(Animator outAnimator, View outView, int outDelay, Animator inAnimator, View inView, int inDelay) {
            outAnimator.setTarget(outView);
            inAnimator.setTarget(inView);
            outAnimator.setStartDelay(outDelay);
            inAnimator.setStartDelay(inDelay);
            outAnimator.start();
            inAnimator.start();
        }

        @Override
        public void setCardInvisible(Card card) {
            cardFrontImage.postDelayed(() -> cardFrontImage.setVisibility(View.INVISIBLE), 1000);
            cardBackImage.postDelayed(() -> cardBackImage.setVisibility(View.INVISIBLE), 1000);
        }
    }
}
