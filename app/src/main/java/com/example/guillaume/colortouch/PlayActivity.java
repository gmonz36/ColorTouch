package com.example.guillaume.colortouch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayActivity extends AppCompatActivity {

    private Button play;

    private Button redButton;
    private Button yellowButton;
    private Button greenButton;
    private Button blueButton;

    private GameController game;

    private Boolean playerTurn = false;
    private Boolean lost = false;

    private Handler handler;
    private Runnable task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        final MediaPlayer redSound = MediaPlayer.create(PlayActivity.this,R.raw.red_beep_short);
        redSound.setAudioStreamType(AudioManager.STREAM_MUSIC);
        final MediaPlayer blueSound = MediaPlayer.create(PlayActivity.this,R.raw.blue_beep_short);
        blueSound.setAudioStreamType(AudioManager.STREAM_MUSIC);
        final MediaPlayer greenSound = MediaPlayer.create(PlayActivity.this,R.raw.green_beep_short);
        greenSound.setAudioStreamType(AudioManager.STREAM_MUSIC);
        final MediaPlayer yellowSound = MediaPlayer.create(PlayActivity.this,R.raw.yellow_beep_short);
        yellowSound.setAudioStreamType(AudioManager.STREAM_MUSIC);



        //initialize game
        game = new GameController();

        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                game = new GameController();
                playSequence();
            }
        });


            redButton = (Button) findViewById(R.id.redButton);
            redButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clearClickedView();
                    setClickedView(v);
                    redSound.start();
                    System.out.println("red!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    if (playerTurn) {
                        lost = game.selectColor(0);
                        if (game.completeSequence()) {
                            game.resetPlayerSequence();
                            playSequence();
                        }
                        if (lost) {
                            playerTurn = false;
                            lostGame();
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
                    System.out.println("blue!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    if (playerTurn) {
                        lost = game.selectColor(1);
                        if (game.completeSequence()) {
                            game.resetPlayerSequence();
                            playSequence();
                        }
                        if (lost) {
                            playerTurn = false;
                            lostGame();
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
                    System.out.println("green!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    if (playerTurn) {
                        lost = game.selectColor(2);
                        if (game.completeSequence()) {
                            game.resetPlayerSequence();
                            playSequence();
                        }
                        if (lost) {
                            playerTurn = false;
                            lostGame();
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
                    System.out.println("yellow!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    if (playerTurn) {
                        lost = game.selectColor(3);
                        if (game.completeSequence()) {
                            game.resetPlayerSequence();
                            playSequence();
                        }
                        if (lost) {
                            playerTurn = false;
                            lostGame();
                        }
                    }
                }
            });
    }

    private void playSequence() {

        System.out.println("listen carefully to this sequence!");
        playerTurn = false;
        game.incSequence();
        ArrayList<Integer> colorList = game.getList();
        displayDelay();

        for(int i = 0; i<colorList.size(); i++) {
            playNext(i);
        }
        clearClickedView();
        System.out.println("Now replay the sequence!");
    }

    private void lostGame() {
        Toast.makeText(PlayActivity.this, "Wrong Sequence!", Toast.LENGTH_LONG).show();
        System.out.println("You lost, lets start again!");
        System.out.println("-----------------------------------");
        game = new GameController();
    }

    private void setClickedView(View v) {
        Button view = (Button) v;
        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        v.invalidate();
    }

    private void clearClickedView() {
        Button view;
        view = (Button) findViewById(R.id.redButton);
        view.getBackground().clearColorFilter();
        view = (Button) findViewById(R.id.blueButton);
        view.getBackground().clearColorFilter();
        view = (Button) findViewById(R.id.greenButton);
        view.getBackground().clearColorFilter();
        view = (Button) findViewById(R.id.yellowButton);
        view.getBackground().clearColorFilter();
        view.invalidate();
    }

    private void playNext(int n) {
        final ArrayList<Integer> colorList = game.getList();
        Handler handler1 = new Handler();
        final int next = n;
        handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    {
                        switch (colorList.get(next)) {
                            case 0:
                                redButton.callOnClick();
                                break;
                            case 1:
                                blueButton.callOnClick();
                                break;
                            case 2:
                                greenButton.callOnClick();
                                break;
                            case 3:
                                yellowButton.callOnClick();
                                break;
                        }
                    }
                    if(next==colorList.size()-1) {
                        playerTurn = true;
                        displayDelay();
                    }

                }
            }, 1000 * n);

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
}