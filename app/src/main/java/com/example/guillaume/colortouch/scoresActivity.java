package com.example.guillaume.colortouch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class scoresActivity extends AppCompatActivity {

    private Button backButton;
    private TextView endlessTopScore;
    private TextView normalTopScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();

        SharedPreferences mPrefs = getSharedPreferences("BestScores", 0);
        String normalScore = mPrefs.getString("normalScore", "0");
        String endlessScore = mPrefs.getString("endlessScore", "0");

        endlessTopScore = (TextView) findViewById(R.id.endlessScore);
        endlessTopScore.setText(endlessScore);

        normalTopScore = (TextView) findViewById(R.id.normalScore);
        normalTopScore.setText(normalScore);


        backButton = (Button) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(scoresActivity.this, MainActivity.class);
                scoresActivity.this.startActivity(myIntent);
            }
        });
    }
}
