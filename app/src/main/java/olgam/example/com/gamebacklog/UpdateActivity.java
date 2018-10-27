package olgam.example.com.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {

    EditText titleInput;
    EditText statusInput;
    EditText platformInput;
    EditText notesInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleInput = findViewById(R.id.editTitle);
        platformInput = findViewById(R.id.editPlatform);
        notesInput = findViewById(R.id.editNotes);
        statusInput = findViewById(R.id.editStatus);

        Bundle data = getIntent().getExtras();
        final int index = data.getInt("Index");

        if (data != null) {
            Game inputGame = (Game) data.getParcelable("Game");

            if (inputGame != null) {
                //set text in EditText boxes ; the values of the game that came with the Intent
                titleInput.setText(inputGame.getTitle());
                platformInput.setText(inputGame.getPlatform());
                notesInput.setText(inputGame.getNotes());
                statusInput.setText(inputGame.getStatus());
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set values entered by user
                Game updateGame = new Game("","","","", "");
                updateGame.setTitle(titleInput.getText().toString());
                updateGame.setPlatform(platformInput.getText().toString());
                updateGame.setNotes(notesInput.getText().toString());
                updateGame.setStatus(statusInput.getText().toString());

                Intent result = new Intent();
                //Put game object as extra in Intent
                Bundle extras = new Bundle();
                extras.putParcelable("Game", updateGame);
                extras.putInt("Index", index);
                result.putExtras(extras);
                //Send the result back to the activity
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });
    }

}
