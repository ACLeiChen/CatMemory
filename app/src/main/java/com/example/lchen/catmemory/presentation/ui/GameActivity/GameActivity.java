package com.example.lchen.catmemory.presentation.ui.GameActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;

import com.example.lchen.catmemory.presentation.ui.MainActivity.MainActivity;
import com.example.lchen.catmemory.MyApplication;
import com.example.lchen.catmemory.R;
import com.example.lchen.catmemory.data.ImageUrls;
import com.example.lchen.catmemory.domain.model.Card;
import com.example.lchen.catmemory.domain.model.Difficulty;
import com.example.lchen.catmemory.domain.model.User;
import com.example.lchen.catmemory.presentation.util.ImageDownloadTask;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements GameContract.View{

    private GameContract.Presenter mPresenter;

    private CardListener mCardListener;
    private CardsAdapter mCardsAdapter;

    private Difficulty difficulty;
    private int gridColumnNumber;

    private RatingBar user1Progress;
    private RatingBar user2Progress;

    RecyclerView mRecyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        difficulty = new Difficulty(intent.getStringExtra(MainActivity.EXTRA_DIFFICULTY));
        gridColumnNumber = intent.getIntExtra(MainActivity.EXTRA_GRID_COLUMN_NUMBER, 4);

        mPresenter = new GameActivityPresenter(this, difficulty, MyApplication.getCardsRepositoryImpl(), MyApplication.getGameRecordRepository());

        mCardListener = new CardListener() {
            @Override
            public void onCardClick(Card clickedCard) {
                mPresenter.applyGameLogic(clickedCard);
            }
        };
        mCardsAdapter = new CardsAdapter(new ArrayList<Card>(0), mCardListener, this);

        mRecyclerView = findViewById(R.id.cards_grid);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnNumber));
        mRecyclerView.setAdapter(mCardsAdapter);

        user1Progress = findViewById(R.id.user1_progress);
        user2Progress = findViewById(R.id.user2_progress);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!MyApplication.getCardsRepositoryImpl().isImageDownloadingFinished()){
            MyApplication.getCardsRepositoryImpl().setFrontImagesFromLocalRes();
        }
        mPresenter.startGame();
    }

    @Override
    public void showCards(List<Card> cards) {
        mCardsAdapter.replaceData(cards);
    }

    @Override
    public void showFinalResult(User winner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String message = "User" + winner.getIndex() + " win!";
        if (winner.getIndex() == 0){
            message = "Draw game.";
        }
        builder.setMessage(message);
        builder.setPositiveButton(R.string.restart, (dialog, which) -> {
            startActivity(getIntent());
        });
        builder.setNegativeButton(R.string.back, (dialog, which) -> {
            finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        mCardsAdapter = new CardsAdapter(new ArrayList<Card>(0), mCardListener, this);
        mRecyclerView.setAdapter(mCardsAdapter);
        mPresenter.startGame();
    }

    @Override
    public void updateUserScore(int index, float progress) {
        RatingBar currentUserProgress = (index == 1) ? user1Progress : user2Progress;
        currentUserProgress.setRating(progress * 4);
    }
}
