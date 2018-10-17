package com.example.olgam.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

        //get text so that it can be displayed and possibly edited
        Bundle bundle = getIntent().getExtras();
        String oldTitle = bundle.getString("Title");
        String oldPlatform = bundle.getString("Platform");
        //String oldStatus = bundle.getString("Status");
        String oldDate = bundle.getString("Date");
        String oldNotes = bundle.getString("Notes");
        final int i = bundle.getInt("Index");

        //initialize the local vars
        titleInput = findViewById(R.id.editTitle);
        platformInput = findViewById(R.id.editPlatform);
        //statusInput = findViewById(R.id.editStatus);
        dateInput = findViewById(R.id.editDate);
        notesInput = findViewById(R.id.editNotes);

        //set text in EditText boxes
        //replace Null values with empty strings
        //if (oldTitle != null && titleInput != null)
            titleInput.setText(oldTitle);
        //if (oldDate != null && dateInput != null)
            dateInput.setText(oldDate);
        //if (oldPlatform != null && platformInput != null)
            platformInput.setText(oldPlatform);
        //if (oldNotes != null && notesInput != null)
            notesInput.setText(oldNotes);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newTitle = titleInput.getText().toString();
                String newDate = dateInput.getText().toString();
                String newPlatform = platformInput.getText().toString();
                String newNotes = notesInput.getText().toString();

                Intent data = new Intent();
                data.putExtra("Index", i);
                data.putExtra("Title", newTitle);
                data.putExtra("Platform", newPlatform);
                data.putExtra("Date", newDate);
                data.putExtra("Notes", newNotes);

                //Send the result back to the activity
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }
}
