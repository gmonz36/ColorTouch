package com.example.guillaume.colortouch;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private Boolean playerTurn = true;
    private Boolean lost = false;
    private TextView info;
    private Button replay;
    SharedPreferences mPrefs;
    Integer topScore;
    private SharedPreferences setPrefs;
    private int waitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        setPrefs = getSharedPreferences("settings", 0);
        String volume = setPrefs.getString("volume", "medium");
        String speed = setPrefs.getString("speed", "normal");
        switch (speed) {
            case "fast":
                waitTime = 750;
                break;
            case "normal":
                waitTime = 1000;
                break;
            case "slow":
                waitTime = 1250;
                break;
        }


        final MediaPlayer redSound = MediaPlayer.create(PlayActivity.this, R.raw.red_beep_short);
        final MediaPlayer blueSound = MediaPlayer.create(PlayActivity.this, R.raw.blue_beep_short);
        final MediaPlayer greenSound = MediaPlayer.create(PlayActivity.this, R.raw.green_beep_short);
        final MediaPlayer yellowSound = MediaPlayer.create(PlayActivity.this, R.raw.yellow_beep_short);

        switch (volume) {
            case "mute":
                redSound.setVolume(0.0f,0.0f);
                blueSound.setVolume(0.0f,0.0f);
                greenSound.setVolume(0.0f,0.0f);
                yellowSound.setVolume(0.0f,0.0f);
                break;
            case "quiet":
                redSound.setVolume(0.2f,0.2f);
                blueSound.setVolume(0.2f,0.2f);
                greenSound.setVolume(0.2f,0.2f);
                yellowSound.setVolume(0.2f,0.2f);
                break;
            case "medium":
                redSound.setVolume(0.5f,0.5f);
                blueSound.setVolume(0.5f,0.5f);
                greenSound.setVolume(0.5f,0.5f);
                yellowSound.setVolume(0.5f,0.5f);
                break;
            case "loud":
                redSound.setVolume(1.0f,1.0f);
                blueSound.setVolume(1.0f,1.0f);
                greenSound.setVolume(1.0f,1.0f);
                yellowSound.setVolume(1.0f,1.0f);
                break;
        }

        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();

        info = (TextView) findViewById(R.id.info);

        help = (Button) findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(PlayActivity.this);

                dlgAlert.setMessage("There's four buttons(blue, red, green and yellow). You have to watch the sequence " +
                        "as shown to you and repeat it in the exact same order, if you don't remember the sequence, you can click on" +
                        " replay sequence. You will have your score displayed at the top of the screen to see your progress.");
                dlgAlert.setTitle("Game general description");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(false);
                dlgAlert.create().show();
            }
        });

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //the line below is to stop the sound from playing after the activity is left if the game was playing a sequence
                if(!playerTurn) System.exit(0);
                Intent myIntent = new Intent(PlayActivity.this, GameModesActivity.class);
                PlayActivity.this.startActivity(myIntent);
            }
        });

        replay = (Button) findViewById(R.id.replay);
        replay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                replaySequence();
            }
        });

        replay.setEnabled(false);

        mPrefs = getSharedPreferences("BestScores", 0);
        String bestScore = mPrefs.getString("endlessScore", "0");
        topScore = Integer.parseInt(bestScore);

        currentScore = (TextView) findViewById(R.id.score);
        currentScore.setText("Score : " + score);

        //initialize game
        game = new GameController();

        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(PlayActivity.this, "Watch the sequence!", Toast.LENGTH_SHORT).show();
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
                        lostGame();
                        return;
                    }
                    if (game.completeSequence()) {
                        game.resetPlayerSequence();
                        score++;
                        updateScoreView();
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
                        lostGame();
                        return;
                    }
                    if (game.completeSequence()) {
                        game.resetPlayerSequence();
                        score++;
                        updateScoreView();
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
                        lostGame();
                        return;
                    }
                    if (game.completeSequence()) {
                        game.resetPlayerSequence();
                        score++;
                        updateScoreView();
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
                        lostGame();
                        return;
                    }
                    if (game.completeSequence()) {
                        game.resetPlayerSequence();
                        score++;
                        updateScoreView();
                        playSequence();
                    }

                }
            }
        });

        redButton.setEnabled(false);
        blueButton.setEnabled(false);
        greenButton.setEnabled(false);
        yellowButton.setEnabled(false);
    }

    private void playSequence() {
        playerTurn = false;
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

                    for (int i = 0; i < colorList.size(); i++) {
                        playNext(i);
                    }
                    clearClickedView();
                }
            }
        }, 1000);
    }

    private void replaySequence() {
        playerTurn = false;
        info.setText("Watch then do the same sequence");
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                {
                    clearClickedView();
                    playerTurn = false;
                    ArrayList<Integer> colorList = game.getList();

                    for (int i = 0; i < colorList.size(); i++) {
                        playNext(i);
                    }
                    clearClickedView();
                }
            }
        }, 1000);

    }

    private void lostGame() {
        redButton.setEnabled(false);
        blueButton.setEnabled(false);
        greenButton.setEnabled(false);
        yellowButton.setEnabled(false);
        replay.setEnabled(false);
        if(score > topScore) {
            SharedPreferences.Editor mEditor = mPrefs.edit();
            mEditor.putString("endlessScore", score.toString()).commit();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Congratulation!").setTitle("New high score! You missed the sequence but you still reached your highest score.");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("That was the wrong sequence.").setTitle("You lost the game!");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.show();
        }
        info.setText("Press \"new game\" to start again");
        game = new GameController();
        score = 0;
        updateScoreView();
    }

    /**
     * make a pressed button animation when the button is clicked
     * @param v is the button to be clicked
     */
    private void setClickedView(View v) {
        Button view = (Button) v;
        view.setBackgroundResource(R.drawable.darkb);
        v.invalidate();
    }

    /**
     * remove the pressed button animation
     */
    private void clearClickedView() {
        Button view;
        view = (Button) findViewById(R.id.redButton);
        view.setBackgroundResource(R.drawable.rb);
        view = (Button) findViewById(R.id.blueButton);
        view.setBackgroundResource(R.drawable.bb);
        view = (Button) findViewById(R.id.greenButton);
        view.setBackgroundResource(R.drawable.gb);
        view = (Button) findViewById(R.id.yellowButton);
        view.setBackgroundResource(R.drawable.yb);
        view.invalidate();
    }

    /**
     * This method select the next button to be pressed in the game sequence
     * Handler is used to delay the time before a button is pressed
     * @param n is how many button have previously been pressed in the sequence
     */
    private void playNext(int n) {
        redButton.setEnabled(false);
        blueButton.setEnabled(false);
        greenButton.setEnabled(false);
        yellowButton.setEnabled(false);
        play.setEnabled(false);
        replay.setEnabled(false);
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
                    replay.setEnabled(true);
                }

            }
        }, waitTime * n);

    }

    /**
     * Keeps the button pressed  for half a second when the game press a button
     */
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

    /**
     * This method keeps the pressed button animation for a fifth of a second after the player press the button.
     */
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
}