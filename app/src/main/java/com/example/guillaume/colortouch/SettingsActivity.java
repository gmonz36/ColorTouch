package com.example.guillaume.colortouch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private Button backButton;
    private Button saveButton;
    private RadioGroup volume;
    private RadioGroup gameSpeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();

        final SharedPreferences settingsPrefs = getSharedPreferences("settings", 0);
        final SharedPreferences.Editor mEditor = settingsPrefs.edit();

        String selectedVolume = settingsPrefs.getString("volume", "medium");
        String selectedSpeed = settingsPrefs.getString("speed", "normal");

        volume = (RadioGroup) findViewById(R.id.volume);
        gameSpeed = (RadioGroup) findViewById(R.id.speed);

        switch (selectedVolume) {
            case "mute":
                volume.check(R.id.mute);
                break;
            case "quiet":
                volume.check(R.id.quiet);
                break;
            case "medium":
                volume.check(R.id.medium);
                break;
            case "loud":
                volume.check(R.id.loud);
                break;
        }

        switch (selectedSpeed) {
            case "slow":
                gameSpeed.check(R.id.slow);
                break;
            case "normal":
                gameSpeed.check(R.id.normal);
                break;
            case "fast":
                gameSpeed.check(R.id.fast);
                break;
        }

        //When saved button is clicked all parameters selected in the radioGroups are pushed to the sharedPreferences
        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (volume.getCheckedRadioButtonId()) {
                    case R.id.mute:
                        mEditor.putString("volume", "mute").commit();
                        break;
                    case R.id.quiet:
                        mEditor.putString("volume", "quiet").commit();
                        break;
                    case R.id.medium:
                        mEditor.putString("volume", "medium").commit();
                        break;
                    case R.id.loud:
                        mEditor.putString("volume", "loud").commit();
                        break;
                }

                switch (gameSpeed.getCheckedRadioButtonId()) {
                    case R.id.slow:
                        mEditor.putString("speed", "slow").commit();
                        break;
                    case R.id.normal:
                        mEditor.putString("speed", "normal").commit();
                        break;
                    case R.id.fast:
                        mEditor.putString("speed", "fast").commit();
                        break;
                }

                Toast.makeText(SettingsActivity.this, "settings saved", Toast.LENGTH_SHORT).show();
            }
        });

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(SettingsActivity.this, MainActivity.class);
                SettingsActivity.this.startActivity(myIntent);
            }
        });
    }
}
