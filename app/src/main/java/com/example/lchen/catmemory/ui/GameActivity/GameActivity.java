package com.example.lchen.catmemory.ui.GameActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lchen.catmemory.MainActivity;
import com.example.lchen.catmemory.MyApplication;
import com.example.lchen.catmemory.R;
import com.example.lchen.catmemory.model.Card;
import com.example.lchen.catmemory.model.Difficulty;
import com.example.lchen.catmemory.model.User;
import com.example.lchen.catmemory.ui.BasicGame.BasicGameContract;
import com.example.lchen.catmemory.ui.BasicGame.CardListener;
import com.example.lchen.catmemory.ui.BasicGame.CardsAdapter;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements BasicGameContract.View{

    private boolean mIsStarted = false;

    private List<Card> mAllCards;

    private BasicGameContract.Presenter mPresenter;

    private CardListener mCardListener;
    private CardsAdapter mCardsAdapter;

    private Difficulty difficulty;
    private int gridColumnNumber;

    private RatingBar user1Progress;
    private RatingBar user2Progress;

    RecyclerView mRecyclerView;
    private PopupWindow mPopupWindow;
    private View mPopUpView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        difficulty = new Difficulty(intent.getStringExtra(MainActivity.EXTRA_DIFFICULTY));
        gridColumnNumber = intent.getIntExtra(MainActivity.EXTRA_GRID_COLUMN_NUMBER, 4);

        initializeAllCards();

        mPresenter = new GameActivityPresenter(this, difficulty, mAllCards, MyApplication.getGameRecordRepository());

        mCardListener = new CardListener() {
            @Override
            public void onCardClick(Card clickedCard) {
                mPresenter.applyGameLogic(clickedCard);
            }
        };
        mCardsAdapter = new CardsAdapter(new ArrayList<Card>(0), mCardListener, this);

        mPresenter.startGame();

        mRecyclerView = findViewById(R.id.cards_grid);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnNumber));
        mRecyclerView.setAdapter(mCardsAdapter);

        user1Progress = findViewById(R.id.user1_progress);
        user2Progress = findViewById(R.id.user2_progress);

    }

    private void initializeAllCards() {
        mAllCards = new ArrayList<>();
        int backIconId = this.getResources().getIdentifier("cat_back", "drawable", this.getPackageName());
        String drawableName;
        int catResId;
        for(int i = 1; i <= 20; i++){
            drawableName = "cat" + i;
            catResId = this.getResources().getIdentifier(drawableName, "drawable", this.getPackageName());
            mAllCards.add(new Card(catResId, backIconId));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadCards();
    }

    @Override
    public void showCards(List<Card> cards) {
        mCardsAdapter.replaceData(cards);
    }

    @Override
    public void showFinalResult(User winner) {
        showFinishDialog(winner);
    }

    private void showPopUp(User winner){
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
        mPopUpView = layoutInflater.inflate(R.layout.pop_up_layout, null);

        mPopupWindow = new PopupWindow(mPopUpView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mPopupWindow.showAtLocation(mRecyclerView, Gravity.CENTER, 0, 0);
        TextView textview = findViewById(R.id.pop_up_text);
        textview.setText("User" + winner.getIndex() + " win!");
    }

    private void showFinishDialog(User winner){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("User" + winner.getIndex() + " win!");
        builder.setPositiveButton(R.string.restart, ((dialog, which) -> {
            startActivity(getIntent());
        }));
        builder.setNegativeButton(R.string.back, ((dialog, which) -> {
            finish();
        }));
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
