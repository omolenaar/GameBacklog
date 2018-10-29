package olgam.example.com.gamebacklog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import java.util.List;

import static olgam.example.com.gamebacklog.MainActivity.TASK_DELETE_ALL;
import static olgam.example.com.gamebacklog.MainActivity.TASK_DELETE_GAME;
import static olgam.example.com.gamebacklog.MainActivity.db;

public class MainActivity extends AppCompatActivity implements GameAdapter.GameClickListener {

    private ArrayList<Game> mGames;
    private ArrayList<Game> tmp;
    private RecyclerView mRecyclerView;
    private GameAdapter mAdapter;

    public final static int TASK_GET_ALL_GAMES = 0;
    public final static int TASK_DELETE_GAME = 1;
    public final static int TASK_DELETE_ALL = 2;
    public final static int TASK_UPDATE_GAME = 3;
    public final static int TASK_INSERT_GAME = 4;

    public final static int UPDATE_REQUEST_CODE = 1111;
    public final static int ADD_REQUEST_CODE = 2222;

    static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = AppDatabase.getInstance(this);

        mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager((mLayoutManager));

        final GameAdapter mAdapter = new GameAdapter(mGames, this, this);
        mRecyclerView.setAdapter(mAdapter);

        mGames = new ArrayList<Game>();
        initializeData();
        new GameAsyncTask(TASK_GET_ALL_GAMES).execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGame.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });

        //DELETE
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
                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());
                        Game deleteGame = mGames.get(position);
                        db.gameDao().deleteGames(deleteGame);
                        mGames.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        new GameAsyncTask(TASK_GET_ALL_GAMES).execute();
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    //UPDATE
    public void gameOnClick (int i) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        Log.e("MAIN", "mGames title = "+mGames.get(i).getTitle().toString());
        Game mGame = mGames.get(i);
        Bundle extras = new Bundle();
        extras.putParcelable("Game", mGame);
        extras.putInt("Index", i);
        intent.putExtras(extras);
        startActivityForResult(intent, UPDATE_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Check if the result code is the right one
        if (resultCode == RESULT_OK) {
            //Check if the request code is correct
            if (requestCode == ADD_REQUEST_CODE) {
                addGame(data);
                new GameAsyncTask(TASK_GET_ALL_GAMES).execute();
            } else if (requestCode == UPDATE_REQUEST_CODE) {
                updateGame(data);
                new GameAsyncTask(TASK_GET_ALL_GAMES).execute();
            }
        }  else {
            Log.e("MAIN", "ResultCode not OK");
        }
    }

    //MENU
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

    private void initializeData() {
        if (db.gameDao().getAllGames().size() == 0) {
            Log.e("initializeData", "Adding games since i equals 0");
            Game new1 = new Game("Doom II", "PC", "Dropped", "Old and ugly", "23-4-1994");
            db.gameDao().insertGames(new1);
            Game new2 = new Game("Rogue Squadron", "PC", "Playing", "Best Star Wars game ever", "20-12-1998");
            db.gameDao().insertGames(new2);
            Game new3 = new Game("Fortnite", "PC, Mobile", "Want to play", "No idea", "1-11-2017");
            db.gameDao().insertGames(new3);
            Game new4 = new Game("World of Warcraft", "PC", "Playing", "Addictive", "20-4-2012");
            db.gameDao().insertGames(new4);
        }
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new GameAdapter(mGames, this, this);
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.swapList(mGames);
    }


    private void addGame(Intent result){
        Bundle data = result.getExtras();
        Game newGame = (Game) data.getParcelable("Game");
        new GameAsyncTask(TASK_INSERT_GAME).execute(newGame);
    }
    private void updateGame(Intent result) {
        Bundle data = result.getExtras();
        Integer i = data.getInt("Index");
        Game newValues = data.getParcelable("Game");
        //mGames.set(i,newValues);
        new GameAsyncTask(TASK_UPDATE_GAME).execute(newValues);
    }

    public class GameAsyncTask extends AsyncTask<Game, Void, List> {

        private int taskCode;

        public GameAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }
        @Override
        protected List doInBackground(Game... games) {
            switch (taskCode){
                case TASK_DELETE_GAME:
                    db.gameDao().deleteGames(games[0]);
                    break;
                case TASK_DELETE_ALL:
                    db.gameDao().deleteAll();
                    break;
                case TASK_UPDATE_GAME:
                    db.gameDao().updateGames(games[0]);
                    break;
                case TASK_INSERT_GAME:
                    db.gameDao().insertGames(games[0]);
                    break;
            }
            //To return a new list with the updated data, we get all the data from the database again.
            return db.gameDao().getAllGames();
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            onGameDbUpdated(list);
        }
    }
    public void onGameDbUpdated(List list) {
        mGames = (ArrayList<Game>) list;
        updateUI();
    }
}
