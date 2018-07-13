package com.example.guillaume.colortouch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private Button backButton;
    private Button saveButton;
    private RadioGroup volume;
    private RadioGroup gameSpeed;
    private RadioButton mute;
    private RadioButton quiet;
    private RadioButton medium;
    private RadioButton loud;
    private RadioButton slow;
    private RadioButton normal;
    private RadioButton fast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();

        final SharedPreferences settingsPrefs = getSharedPreferences("settings", 0);
        /*final String Mute = settingsPrefs.getString("Mute", "mute");
        final String Quiet = settingsPrefs.getString("Quiet", "quiet");
        final String Medium = settingsPrefs.getString("Medium", "medium");
        final String Loud = settingsPrefs.getString("Loud", "loud");

        final String Slow = settingsPrefs.getString("Slow", "slow");
        final String Normal = settingsPrefs.getString("Normal", "normal");
        final String Fast = settingsPrefs.getString("Fast", "fast");*/

        final SharedPreferences.Editor mEditor = settingsPrefs.edit();



        volume = (RadioGroup) findViewById(R.id.volume);
        gameSpeed = (RadioGroup) findViewById(R.id.speed);
        mute = (RadioButton)findViewById(R.id.mute);
        medium = (RadioButton)findViewById(R.id.medium);
        quiet = (RadioButton)findViewById(R.id.quiet);
        loud = (RadioButton)findViewById(R.id.loud);
        slow = (RadioButton)findViewById(R.id.slow);
        normal = (RadioButton)findViewById(R.id.normal);
        fast = (RadioButton)findViewById(R.id.fast);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                volume.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                       if(mute.isChecked())
                        {
                            volume.check(R.id.mute);
                            String Mute = "mute";
                            mEditor.putString("Mute", Mute).commit();
                        }
                        else if (medium.isChecked())
                        {
                            volume.check(R.id.medium);
                            String Medium = "medium";
                            mEditor.putString("Medium", Medium).commit();
                        }
                        else if (loud.isChecked())
                        {
                            volume.check(R.id.loud);
                            String Loud = "Loud";
                            mEditor.putString("Loud", Loud).commit();
                        }

                        else{
                            volume.check(R.id.quiet);
                            String Quiet = "quiet";
                           mEditor.putString("Quiet", Quiet).commit();
                        }


                    }
                });

                gameSpeed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {



                        if(slow.isChecked())
                        {
                            gameSpeed.check(R.id.slow);
                            String Slow = "slow";
                            mEditor.putString("Mute", Slow).commit();
                        }
                        else if (fast.isChecked())
                        {
                            gameSpeed.check(R.id.fast);
                            String Fast = "fast";
                            mEditor.putString("Mute", Fast).commit();
                        }

                        else{
                            gameSpeed.check(R.id.normal);
                            String Normal = "normal";
                            mEditor.putString("Mute", Normal).commit();
                        }


                    }
                });
                Toast.makeText(SettingsActivity.this, "Settings chosen saved !!", Toast.LENGTH_SHORT).show();
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
