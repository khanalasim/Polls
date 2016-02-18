package com.asimkhanal.polls;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CommonActivity extends AppCompatActivity {

    private String username;
    private static int NUMBER = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        setTitle("Polls");
        final Intent intent = new Intent(this, PollActivity.class);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            username = extras.getString("username");
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Polls");
        query.setLimit(5);
        final String[] toTell = new String[1];
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> poleList, com.parse.ParseException e) {
                if (e == null) {
                    toTell[0] = "Retrieved " + poleList.size() + " scores";
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
        LinearLayout myLayout = (LinearLayout)findViewById(R.id.my_layout);
        for (int i =0; i<NUMBER; i++){
            TextView poll = new TextView(this);
            poll.setText(toTell[0]);
            myLayout.addView(poll);
        }
        intent.putExtra("username",username);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Create new poll!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(intent);
            }
        });

    }
}
