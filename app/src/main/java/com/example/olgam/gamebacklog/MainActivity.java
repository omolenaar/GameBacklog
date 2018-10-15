package com.example.olgam.gamebacklog;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Game> mGames;
    private static final String Tag = "TAG";
    private RecyclerView mRecyclerView;
    private GameAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGames = new ArrayList<Game>();
        mRecyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager((mLayoutManager));

        initializeData();

        GameAdapter mAdapter = new GameAdapter(this, mGames);
        mRecyclerView.setAdapter(mAdapter);
        Log.e(Tag, "mGamesContents:"+mGames.get(1).getTitle().toString()+mGames.get(2).getTitle().toString()+"mGames size ="+mGames.size());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGame.class);
                startActivityForResult(intent,1111);
                Log.e(Tag, ("Starting activity with code 1111"));
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Check if the result code is the right one
        if (resultCode == RESULT_OK) {
            //Check if the request code is correct
            if (requestCode == 1111) {
                addGame(data);
                updateUI();
            }
        } else {
            Log.e(Tag, "Resultcode not OK");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void updateUI() {
        if (mAdapter == null) {
            Log.e(Tag, "mAdapter was null, creating new PortalAdapter");
            mAdapter = new GameAdapter(this, mGames);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initializeData(){
        mGames.add(new Game("Doom II", "Very old but enjoyable", "31-8-1994", "DROPPED"));
        mGames.add(new Game("Rogue Squadron", "Best Star Wars game ever", "20-12-1998", "PLAY"));
        mGames.add(new Game("Fortnight", "No idea", "1-11-2017", "WANTED"));
        mGames.add(new Game("World of Warcraft", "Addicitve", "5-4-2008", "STALLED"));
    }

    private void addGame(Intent data){
        Bundle extras = data.getExtras();
        String newTitle = extras.getString("Title");
        String newDate = data.getStringExtra("Date");
        String newPlatform = data.getStringExtra("Platform");
        String newNotes = data.getStringExtra("Notes");
        Game newGame = new Game(newTitle,newNotes, newDate, newPlatform);
        mGames.add(newGame);
    }

}
