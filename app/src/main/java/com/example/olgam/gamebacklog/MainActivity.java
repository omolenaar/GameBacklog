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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GameAdapter.GameClickListener {

    private ArrayList<Game> mGames;
    private static final String Tag = "TAG";
    private RecyclerView mRecyclerView;
    private GameAdapter mAdapter;

    //Constants used when calling the update activity
    public static final String EXTRA_GAME = "Game";
    public static final int ADDREQUESTCODE = 1111;
    public static final int UPDATEREQUESTCODE = 2222;
    private int mModifyPosition;

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

        final GameAdapter mAdapter = new GameAdapter(this, mGames, this);
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

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                    target) {
                return false;
            }
            //Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir){
                Log.e(Tag, "Swiped detected");
                //Get the index corresponding to the selected position
                int position = (viewHolder.getAdapterPosition());
                mGames.remove(position);
                mAdapter.notifyItemRangeRemoved(position, 1);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Check if the result code is the right one
        if (resultCode == RESULT_OK) {
            //Check if the request code is correct
            if (requestCode == ADDREQUESTCODE) {
                addGame(data);
                updateUI();
            }
        } else {
            Log.e(Tag, "ResultCode not OK");
        }
    }

    @Override
    public void gameOnClick (int i) {
        //Toast.makeText(this, "Test "+i, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        mModifyPosition = i;
        intent.putExtra(EXTRA_GAME, mGames.get(i));
        startActivityForResult(intent, UPDATEREQUESTCODE);
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
            mAdapter = new GameAdapter(this, mGames, this);
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
