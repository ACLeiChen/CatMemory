package com.example.lchen.catmemory.presentation.ui.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lchen.catmemory.R;
import com.example.lchen.catmemory.domain.model.Difficulty;
import com.example.lchen.catmemory.presentation.ui.GameActivity.GameActivity;

import static com.example.lchen.catmemory.MyApplication.getGameRecordRepository;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_DIFFICULTY = "DIFFICULTY";
    public static final String EXTRA_GRID_COLUMN_NUMBER = "GRID_COLUMN_NUMBER";
    public static final int GRID_COLUMN_NUMBER = 4;

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        updateMenuTitle();

    }

    public void updateMenuTitle(){
        int scoreForEasy = getGameRecordRepository().getTopScore(Difficulty.DIFFICULTY_EASY);
        int scoreForMiddle = getGameRecordRepository().getTopScore(Difficulty.DIFFICULTY_MIDDLE);
        int scoreForHard = getGameRecordRepository().getTopScore(Difficulty.DIFFICULTY_HARD);
        navigationView.getMenu().getItem(0).setTitle(getString(R.string.EASY_MODE) + " : " + scoreForEasy);
        navigationView.getMenu().getItem(1).setTitle(getString(R.string.MIDDLE_MODE) + " : " + scoreForMiddle);
        navigationView.getMenu().getItem(2).setTitle(getString(R.string.HARD_MODE) + " : " + scoreForHard);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void startEasyMode(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(EXTRA_DIFFICULTY, Difficulty.DIFFICULTY_EASY);
        intent.putExtra(EXTRA_GRID_COLUMN_NUMBER, GRID_COLUMN_NUMBER);
        startActivity(intent);
    }
    public void startMiddleMode(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(EXTRA_DIFFICULTY, Difficulty.DIFFICULTY_MIDDLE);
        intent.putExtra(EXTRA_GRID_COLUMN_NUMBER, GRID_COLUMN_NUMBER);
        startActivity(intent);
    }
    public void startHardMode(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(EXTRA_DIFFICULTY, Difficulty.DIFFICULTY_HARD);
        intent.putExtra(EXTRA_GRID_COLUMN_NUMBER, GRID_COLUMN_NUMBER);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.easy_mode) {
            // Handle the camera action
        } else if (id == R.id.middle_mode) {

        } else if (id == R.id.hard_mode) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
