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
    private PlayNormalActivity normActivity = new PlayNormalActivity();
    private PlayActivity activity = new PlayActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
<<<<<<< HEAD
=======

        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();

>>>>>>> 27c29d86b8ac046ddd081d2e7a02fbd8947879f8
        SharedPreferences mPrefs = getSharedPreferences("BestScores", 0);
        String normalScore = mPrefs.getString("normalScore", Integer.toString(normActivity.getMaxScore()));
        String endlessScore = mPrefs.getString("endlessScore", Integer.toString(activity.getMaxScore()));

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
