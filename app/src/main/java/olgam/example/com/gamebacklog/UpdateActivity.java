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

import java.util.Arrays;

public class UpdateActivity extends AppCompatActivity {

    private EditText titleInput;
    private EditText platformInput;
    private EditText notesInput;
    private Spinner statusInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleInput = findViewById(R.id.editTitle);
        platformInput = findViewById(R.id.editPlatform);
        notesInput = findViewById(R.id.editNotes);
        statusInput = findViewById(R.id.statusSpinner2);

        Bundle data = getIntent().getExtras();
        final int index = data.getInt("Index");
        int position;

        final Game updateGame = (Game) data.getParcelable("Game");
        String[] statuses= getResources().getStringArray(R.array.statusNames);
        position = Arrays.asList(statuses).indexOf(updateGame.getStatus());

        titleInput.setText(updateGame.getTitle());
        platformInput.setText(updateGame.getPlatform());
        notesInput.setText(updateGame.getNotes());
        statusInput.setSelection(position);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set values entered by user
                updateGame.setTitle(titleInput.getText().toString());
                updateGame.setPlatform(platformInput.getText().toString());
                updateGame.setNotes(notesInput.getText().toString());
                updateGame.setStatus(String.valueOf(statusInput.getSelectedItem()));

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
