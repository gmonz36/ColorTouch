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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        setPrefs = getSharedPreferences("settings", 0);
        String Mute = setPrefs.getString("Mute", "mute");
        String Quiet = setPrefs.getString("Quiet", "quiet");
        String Medium = setPrefs.getString("Medium", "medium");
        String Loud = setPrefs.getString("Loud", "loud");

        String Slow = setPrefs.getString("Slow", "slow");
        String Normal = setPrefs.getString("Normal", "normal");
        String Fast = setPrefs.getString("Fast", "fast");

        final MediaPlayer redSound = MediaPlayer.create(PlayActivity.this, R.raw.red_beep_short);
        final MediaPlayer blueSound = MediaPlayer.create(PlayActivity.this, R.raw.blue_beep_short);
        final MediaPlayer greenSound = MediaPlayer.create(PlayActivity.this, R.raw.green_beep_short);
        final MediaPlayer yellowSound = MediaPlayer.create(PlayActivity.this, R.raw.yellow_beep_short);

        if (setPrefs.contains(Medium)) {
            redSound.setVolume(1.5f,1.5f);
            blueSound.setVolume(1.5f,1.5f);
            greenSound.setVolume(1.5f,1.5f);
            yellowSound.setVolume(1.5f,1.5f);
        }

        else if (setPrefs.contains(Mute)) {
            redSound.setVolume(0,0);
            blueSound.setVolume(0,0);
            greenSound.setVolume(0,0);
            yellowSound.setVolume(0,0);
        }

        else if (setPrefs.contains(Loud)){
            redSound.setVolume(1.75f,1.75f);
            blueSound.setVolume(1.75f,1.75f);
            greenSound.setVolume(1.75f,1.75f);
            yellowSound.setVolume(1.75f,1.75f);
        }

        else if (setPrefs.contains(Quiet)){
            redSound.setVolume(1,1);
            blueSound.setVolume(1,1);
            greenSound.setVolume(1,1);
            yellowSound.setVolume(1,1);
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
                        " replay sequence. You will have your score displayed at the top of the screen and a text message that will guide " +
                        "you throughout the game. Have fun !! ");
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

        //TODO add a sharedpreference for the game high score and options
        mPrefs = getSharedPreferences("settings", 0);
        String sound = mPrefs.getString("normalScore", "medium");
        String speed = mPrefs.getString("endlessScore", "normal");

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
                        //playerTurn = false;
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
                        //playerTurn = false;
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
                        //playerTurn = false;
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
                        //playerTurn = false;
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
                    //displayDelay();

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
        info.setText("Watch and do the same sequence");
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
            if (score == 0){
                score = 0;
            }
            else{
                score++;
            }
            mEditor.putString("endlessScore", score.toString()).commit();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Congratulation!").setTitle("New high score!");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //Intent myIntent = new Intent(PlayActivity.this, GameModesActivity.class);
                    //PlayActivity.this.startActivity(myIntent);
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
                    //Intent myIntent = new Intent(PlayActivity.this, GameModesActivity.class);
                    //PlayActivity.this.startActivity(myIntent);
                }
            });
            builder.show();
        }
        info.setText("Press \"new game\" to start again");
        game = new GameController();
        score = 0;
        updateScoreView();
    }

    private void setClickedView(View v) {
        Button view = (Button) v;
        view.setBackgroundResource(R.drawable.darkb);
        //view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        v.invalidate();
    }

    private void clearClickedView() {
        Button view;
        view = (Button) findViewById(R.id.redButton);
        view.setBackgroundResource(R.drawable.rb);
        //view.getBackground().clearColorFilter();
        view = (Button) findViewById(R.id.blueButton);
        view.setBackgroundResource(R.drawable.bb);
        //view.getBackground().clearColorFilter();
        view = (Button) findViewById(R.id.greenButton);
        view.setBackgroundResource(R.drawable.gb);
        //view.getBackground().clearColorFilter();
        view = (Button) findViewById(R.id.yellowButton);
        view.setBackgroundResource(R.drawable.yb);
        //view.getBackground().clearColorFilter();
        view.invalidate();
    }

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