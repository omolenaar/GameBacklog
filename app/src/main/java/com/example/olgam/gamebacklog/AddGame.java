package com.example.olgam.gamebacklog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddGame extends AppCompatActivity {

    private static final int Result_OK = -1;
    private static final String Tag = "TAG";

    EditText newTitle;
    EditText newPlatform;
    EditText newNotes;
    EditText newDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newTitle = findViewById(R.id.editTitle);
        newDate = findViewById(R.id.editDate);
        newPlatform = findViewById(R.id.editPlatform);
        newNotes = findViewById(R.id.editNotes);
        //TODO: Status


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String newTitleString = newTitle.getText().toString();
                final String newPlatformString = newPlatform.getText().toString();
                final String newNotesString = newNotes.getText().toString();
                final String newDateString = newDate.getText().toString();
                //TODO: Status
                Bundle extras = new Bundle();
                extras.putString("Title", newTitleString);
                extras.putString("Platform", newPlatformString);
                extras.putString("Notes", newNotesString);
                extras.putString("Date", newDateString);
                Intent result = new Intent();
                result.putExtras(extras);
                setResult(Result_OK, result);
                Log.e(Tag, "sending back new game "+newTitleString);
                finish();
            }
        });
    }

}

