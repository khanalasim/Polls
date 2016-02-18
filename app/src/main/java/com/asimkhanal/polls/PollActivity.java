package com.asimkhanal.polls;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseObject;

import org.json.JSONArray;

public class PollActivity extends AppCompatActivity {

    private EditText questionView;
    private EditText optionsView;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        setTitle("Create Poll");
        questionView = (EditText)findViewById(R.id.questionText);
        optionsView = (EditText)findViewById(R.id.optionsText);
        final Intent intent = new Intent(this,CommonActivity.class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Make!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                String question = questionView.getText().toString();
                String[] options = optionsView.getText().toString().split(";");
                JSONArray optionArray = new JSONArray();
                JSONArray numberChoice = new JSONArray();
                for (int i=0; i<options.length;i++){
                    optionArray.put(options[i]);
                    numberChoice.put(0);
                }
                ParseObject newPoll = new ParseObject("Polls");
                newPoll.put("Question",question);
                newPoll.put("Choices", optionArray);
                newPoll.put("numberChoice",numberChoice);

                //get the username
                Bundle extras = getIntent().getExtras();
                if (extras != null){
                    username = extras.getString("username");
                }
                newPoll.put("User",username);
                newPoll.saveInBackground();
                startActivity(intent);

            }
        });
    }

}
