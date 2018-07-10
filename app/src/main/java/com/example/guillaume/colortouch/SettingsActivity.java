package com.example.guillaume.colortouch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    private Button backButton;
    private RadioGroup volume;
    private RadioGroup gameSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences mPrefs = getSharedPreferences("settings", 0);
        String sound = mPrefs.getString("normalScore", "medium");
        String speed = mPrefs.getString("endlessScore", "normal");

        volume = (RadioGroup) findViewById(R.id.volume);
        volume.check(R.id.medium);

        gameSpeed = (RadioGroup) findViewById(R.id.speed);
        gameSpeed.check(R.id.normal);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(SettingsActivity.this, MainActivity.class);
                SettingsActivity.this.startActivity(myIntent);
            }
        });
    }
}
