package com.example.guillaume.colortouch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameModesActivity extends AppCompatActivity {

    private Button endlessButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_modes);

        endlessButton = (Button) findViewById(R.id.endless);
        endlessButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(GameModesActivity.this, PlayActivity.class);
                GameModesActivity.this.startActivity(myIntent);
            }
        });

        backButton = (Button) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(GameModesActivity.this, MainActivity.class);
                GameModesActivity.this.startActivity(myIntent);
            }
        });
    }
}
