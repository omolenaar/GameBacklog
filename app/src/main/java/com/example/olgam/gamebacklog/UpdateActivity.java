package com.example.olgam.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateActivity extends AppCompatActivity {

    EditText titleEditText;
    EditText dateInput;
    EditText platformInput;
    EditText notesInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //get text so that it can be displayed and possibly edited
        Bundle bundle = getIntent().getExtras();
        String oldTitle = bundle.getString("Title");
        String oldDate = bundle.getString("Date");
        String oldPlatform = bundle.getString("Platform");
        String oldNotes = bundle.getString("Notes");

        //initialize the local vars
        //titleInput = (EditText) findViewById(R.id.editTitle);
        titleEditText = findViewById(R.id.editTitle);
        dateInput = findViewById(R.id.editDate);
        platformInput = findViewById(R.id.editPlatform);
        notesInput = findViewById(R.id.editNotes);

        //set text in EditText boxes
        titleEditText.setText(oldTitle);
        dateInput.setText(oldDate);
        platformInput.setText(oldPlatform);
        notesInput.setText(oldNotes);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newTitle = titleEditText.getText().toString();
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
