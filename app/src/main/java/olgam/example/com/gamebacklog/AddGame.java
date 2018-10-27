package olgam.example.com.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class AddGame extends AppCompatActivity {
    EditText titleNew;
    EditText platformNew;
    EditText notesNew;
    Spinner statusNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleNew = findViewById(R.id.editTitle);
        platformNew = findViewById(R.id.editPlatform);
        notesNew = findViewById(R.id.editNotes);
        //statusNew = findViewById(R.id.editStatus);
        statusNew = findViewById(R.id.statusSpinner1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set values entered by user
                Game updateGame = new Game("","","","", "");
                updateGame.setTitle(titleNew.getText().toString());
                updateGame.setPlatform(platformNew.getText().toString());
                updateGame.setNotes(notesNew.getText().toString());
                updateGame.setStatus(String.valueOf(statusNew.getSelectedItem()));

                Intent result = new Intent();
                //Put game object as extra in Intent
                Bundle extras = new Bundle();
                extras.putParcelable("Game", updateGame);
                result.putExtras(extras);
                //Send the result back to the activity
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });
    }
}
