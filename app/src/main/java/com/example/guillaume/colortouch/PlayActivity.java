package com.example.guillaume.colortouch;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class PlayActivity extends AppCompatActivity {

    TextView currentScore;
    Integer score = 0;
    private Button play;
    private Button back;
    private Button help;
    private Button redButton;
    private Button yellowButton;
    private Button greenButton;
    private Button blueButton;
    private GameController game;
    private Boolean playerTurn = false;
    private Boolean lost = false;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();

        info = (TextView) findViewById(R.id.info);

        help = (Button) findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(PlayActivity.this);

                dlgAlert.setMessage("There's four buttons(blue, red, green and yellow). You have to watch the sequence " +
                        "as shown to you with in the exact same order and repeat it, if you don't remember the sequence, you can click on" +
                        "replay sequence. You will have your score displayed at the bottom of the game and a text message that will guide " +
                        "throughout the game. Have fun !! ");
                dlgAlert.setTitle("Game general description");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(false);
                dlgAlert.create().show();

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            }
        });

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(PlayActivity.this, GameModesActivity.class);
                PlayActivity.this.startActivity(myIntent);
            }
        });
        //TODO add a sharedpreference for the game high score and options
        SharedPreferences mPrefs = getSharedPreferences("settings", 0);
        String sound = mPrefs.getString("normalScore", "medium");
        String speed = mPrefs.getString("endlessScore", "normal");

        currentScore = (TextView) findViewById(R.id.score);
        currentScore.setText("Score : " + score);


        final MediaPlayer redSound = MediaPlayer.create(PlayActivity.this, R.raw.red_beep_short);
        redSound.setAudioStreamType(AudioManager.STREAM_MUSIC);
        final MediaPlayer blueSound = MediaPlayer.create(PlayActivity.this, R.raw.blue_beep_short);
        blueSound.setAudioStreamType(AudioManager.STREAM_MUSIC);
        final MediaPlayer greenSound = MediaPlayer.create(PlayActivity.this, R.raw.green_beep_short);
        greenSound.setAudioStreamType(AudioManager.STREAM_MUSIC);
        final MediaPlayer yellowSound = MediaPlayer.create(PlayActivity.this, R.raw.yellow_beep_short);
        yellowSound.setAudioStreamType(AudioManager.STREAM_MUSIC);


        //initialize game
        game = new GameController();

        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                game = new GameController();
                score = 0;
                updateScoreView();
                playSequence();
            }
        });


        redButton = (Button) findViewById(R.id.redButton);
        redButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearClickedView();
                setClickedView(v);
                redSound.start();
                if (playerTurn) {
                    playerDisplayDelay();
                    lost = game.selectColor(0);
                    if (lost) {
                        //playerTurn = false;
                        lostGame();
                        return;
                    }
                    if (game.completeSequence()) {
                        game.resetPlayerSequence();
                        playSequence();
                    }
                }
            }
        });

        blueButton = (Button) findViewById(R.id.blueButton);
        blueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearClickedView();
                setClickedView(v);
                blueSound.start();
                if (playerTurn) {
                    playerDisplayDelay();
                    lost = game.selectColor(1);
                    if (lost) {
                        //playerTurn = false;
                        lostGame();
                        return;
                    }
                    if (game.completeSequence()) {
                        game.resetPlayerSequence();
                        playSequence();
                    }
                }
            }
        });
        greenButton = (Button) findViewById(R.id.greenButton);
        greenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearClickedView();
                setClickedView(v);
                greenSound.start();
                if (playerTurn) {
                    playerDisplayDelay();
                    lost = game.selectColor(2);
                    if (lost) {
                        //playerTurn = false;
                        lostGame();
                        return;
                    }
                    if (game.completeSequence()) {
                        game.resetPlayerSequence();
                        playSequence();
                    }
                }
            }
        });

        yellowButton = (Button) findViewById(R.id.yellowButton);
        yellowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearClickedView();
                setClickedView(v);
                yellowSound.start();
                if (playerTurn) {
                    playerDisplayDelay();
                    lost = game.selectColor(3);
                    if (lost) {
                        //playerTurn = false;
                        lostGame();
                        return;
                    }
                    if (game.completeSequence()) {
                        game.resetPlayerSequence();
                        playSequence();
                    }

                }
            }
        });
    }

    private void playSequence() {
        info.setText("Watch and do the same sequence");
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                {
                    clearClickedView();
                    playerTurn = false;
                    game.incSequence();
                    ArrayList<Integer> colorList = game.getList();
                    //displayDelay();

                    for (int i = 0; i < colorList.size(); i++) {
                        playNext(i);
                    }
                    clearClickedView();
                    score++;
                    updateScoreView();
                }
            }
        }, 1000);

    }

    private void lostGame() {
        redButton.setEnabled(false);
        blueButton.setEnabled(false);
        greenButton.setEnabled(false);
        yellowButton.setEnabled(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("That was the wrong sequence.").setTitle("You lost the game!");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Intent myIntent = new Intent(PlayActivity.this, GameModesActivity.class);
                //PlayActivity.this.startActivity(myIntent);
            }
        });
        builder.show();
        info.setText("Press \"new game\" to start again");
        game = new GameController();
        score = 0;
        updateScoreView();
    }

    private void setClickedView(View v) {
        Button view = (Button) v;
        view.setBackgroundResource(R.drawable.clicked_button);
        //view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        v.invalidate();
    }

    private void clearClickedView() {
        Button view;
        view = (Button) findViewById(R.id.redButton);
        view.setBackgroundResource(R.drawable.new_red_button);
        //view.getBackground().clearColorFilter();
        view = (Button) findViewById(R.id.blueButton);
        view.setBackgroundResource(R.drawable.new_blue_button);
        //view.getBackground().clearColorFilter();
        view = (Button) findViewById(R.id.greenButton);
        view.setBackgroundResource(R.drawable.new_green_button);
        //view.getBackground().clearColorFilter();
        view = (Button) findViewById(R.id.yellowButton);
        view.setBackgroundResource(R.drawable.new_yellow_button);
        //view.getBackground().clearColorFilter();
        view.invalidate();
    }

    private void playNext(int n) {
        redButton.setEnabled(false);
        blueButton.setEnabled(false);
        greenButton.setEnabled(false);
        yellowButton.setEnabled(false);
        play.setEnabled(false);
        final ArrayList<Integer> colorList = game.getList();
        Handler handler1 = new Handler();
        final int next = n;
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                {
                    switch (colorList.get(next)) {
                        case 0:
                            redButton.setEnabled(true);
                            redButton.callOnClick();
                            redButton.setEnabled(false);
                            break;
                        case 1:
                            blueButton.setEnabled(true);
                            blueButton.callOnClick();
                            blueButton.setEnabled(false);
                            break;
                        case 2:
                            greenButton.setEnabled(true);
                            greenButton.callOnClick();
                            greenButton.setEnabled(false);
                            break;
                        case 3:
                            yellowButton.setEnabled(true);
                            yellowButton.callOnClick();
                            yellowButton.setEnabled(false);
                            break;
                    }
                }
                displayDelay();
                if (next == colorList.size() - 1) {
                    playerTurn = true;
                    displayDelay();
                    redButton.setEnabled(true);
                    blueButton.setEnabled(true);
                    greenButton.setEnabled(true);
                    yellowButton.setEnabled(true);
                    play.setEnabled(true);
                }

            }
        }, 1250 * n);

    }

    private void displayDelay() {
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                {
                    clearClickedView();
                }
            }
        }, 500);
    }

    private void updateScoreView() {
        currentScore.setText("Score : " + score);
    }

    private void playerDisplayDelay() {
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                {
                    clearClickedView();
                }
            }
        }, 200);
    }

    public int getMaxScore(){
        return score;
    }
}