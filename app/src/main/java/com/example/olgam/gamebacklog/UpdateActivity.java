package com.example.olgam.gamebacklog;

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
    EditText dateInput;
    EditText platformInput;
    EditText notesInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleInput = findViewById(R.id.editTitle);
        dateInput = findViewById(R.id.editDate);
        platformInput = findViewById(R.id.editPlatform);
        notesInput = findViewById(R.id.editNotes);

        final Game updateGame = getIntent().getParcelableExtra("Game");

        //get text so that it can be displayed (set) and possibly edited
        titleInput.setText(updateGame.getTitle());
        dateInput.setText(updateGame.getDate());
        platformInput.setText(updateGame.getPlatform());
        notesInput.setText(updateGame.getNotes());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newTitle = titleInput.getText().toString();
                String newDate = dateInput.getText().toString();
                String newPlatform = platformInput.getText().toString();
                String newNotes = notesInput.getText().toString();

                Intent data = new Intent();
                data.putExtra("Title", newTitle);
                data.putExtra("Date", newDate);
                data.putExtra("Platform", newPlatform);
                data.putExtra("Notes", newNotes);

                //Send the result back to the activity
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }

}
