package olgam.example.com.gamebacklog;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameAdapter.GameClickListener {

    private ArrayList<Game> mGames;
    private RecyclerView mRecyclerView;
    private GameAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGames = new ArrayList<Game>();
        initializeData();

        mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager((mLayoutManager));

        final GameAdapter mAdapter = new GameAdapter(mGames, this, this);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGame.class);
                startActivityForResult(intent, 1111);
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
                        Log.e("Main", "Swiped detected");
                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());
                        //db.gameDao().deleteGames(mGames.get(position));
                        mGames.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        updateUI();
                        //mAdapter.notifyItemRangeRemoved(position, 1);
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
        startActivityForResult(intent, 2222);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Check if the result code is the right one
        if (resultCode == RESULT_OK) {
            //Check if the request code is correct
            if (requestCode == 1111) {
                addGame(data);
                updateUI();
            } else if (requestCode == 2222) {
                updateGame(data);
                updateUI();
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

    private void initializeData(){

        mGames.add(new Game("1 Doom II", "PC", "DROPPED", "Old and ugly", "23-4-1994"));
        mGames.add(new Game("2 Rogue Squadron", "PC", "PLAYING","Best Star Wars game ever", "20-12-1998"));
        mGames.add(new Game("3 Fortnite", "PC, Mobile", "WANTED", "No idea", "1-11-2017"));
        mGames.add(new Game("4 World of Warcraft", "PC", "PLAYING", "Addicitive", "20-4-2012"));
    }
    private void updateUI() {
        if (mAdapter == null) {
            Log.e("UpdateUI", "mAdapter was null, creating new PortalAdapter");
            mAdapter = new GameAdapter(mGames, this, this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            //mAdapter.swapList(mGames);

        } else {
            //List<Game> mGamesList = db.gameDao().getAllGames();
            //tmp = new ArrayList<Game>(db.gameDao().getAllGames());
            //ArrayList mGames = tmp;
            mAdapter.notifyDataSetChanged();
            //mAdapter.swapList(mGames);
        }
    }
    private void addGame(Intent result){
        Bundle data = result.getExtras();
        Game newGame = (Game) data.getParcelable("Game");
        mGames.add(newGame);
    }
    private void updateGame(Intent result) {
        Bundle data = result.getExtras();
        Integer i = data.getInt("Index");
        Game newValues = data.getParcelable("Game");
        Game updatedGame = mGames.get(i);
        mGames.set(i,newValues);
    }
}
