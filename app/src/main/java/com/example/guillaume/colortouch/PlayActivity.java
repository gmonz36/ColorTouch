package com.example.guillaume.colortouch;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Iterator;

public class PlayActivity extends AppCompatActivity {

    private Button menuButton;
    private Button play;

    private Button redButton;
    private Button yellowButton;
    private Button greenButton;
    private Button blueButton;

    private GameController game;

    private Boolean playerTurn;
    private Boolean lost = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        wait(5000);

        final MediaPlayer redSound = MediaPlayer.create(PlayActivity.this,R.raw.red_beep);
        final MediaPlayer blueSound = MediaPlayer.create(PlayActivity.this,R.raw.blue_beep);
        final MediaPlayer greenSound = MediaPlayer.create(PlayActivity.this,R.raw.green_beep);
        final MediaPlayer yellowSound = MediaPlayer.create(PlayActivity.this,R.raw.yellow_beep);



        //initialize game
        game = new GameController();

        menuButton = (Button) findViewById(R.id.BackToMenu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(PlayActivity.this, MainActivity.class);
                PlayActivity.this.startActivity(myIntent);
            }
        });

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
                redSound.start();
                clearClickedView();
                setClickedView(v);
                System.out.println("red");
                if(playerTurn) {
                    lost = game.selectColor(0);
                    if(game.completeSequence()) {
                        game.resetPlayerSequence();
                        playSequence();
                    }
                    if (lost) {
                        lostGame();
                    }
                }
            }
        });

        blueButton = (Button) findViewById(R.id.blueButton);
        blueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                blueSound.start();
                clearClickedView();
                setClickedView(v);
                System.out.println("blue");
                if(playerTurn) {
                    lost = game.selectColor(1);
                    if(game.completeSequence()) {
                        game.resetPlayerSequence();
                        playSequence();
                    }
                    if (lost) {
                        lostGame();
                    }
                }
            }
        });
        greenButton = (Button) findViewById(R.id.greenButton);
        greenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                greenSound.start();
                clearClickedView();
                setClickedView(v);
                System.out.println("green");
                if(playerTurn) {
                    lost = game.selectColor(2);
                    if(game.completeSequence()) {
                        game.resetPlayerSequence();
                        playSequence();
                    }
                    if (lost) {
                        lostGame();
                    }
                }
            }
        });

        yellowButton = (Button) findViewById(R.id.yellowButton);
        yellowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                yellowSound.start();
                clearClickedView();
                setClickedView(v);
                System.out.println("yellow");
                if(playerTurn) {
                    lost = game.selectColor(3);
                    if(game.completeSequence()) {
                        game.resetPlayerSequence();
                        playSequence();
                    }
                    if (lost) {
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
        Iterator<Integer> iter = game.getSequenceIterator();
        clearClickedView();
        while(iter.hasNext()) {
            PlayActivity.this.wait(1500);
            clearClickedView();
            switch (iter.next()) {
                case 0: redButton.callOnClick();
                        break;
                case 1: blueButton.callOnClick();
                        break;
                case 2: greenButton.callOnClick();
                        break;
                case 3: yellowButton.callOnClick();
                        break;
            }
        }
        //clearClickedView();
        System.out.println("Now replay the sequence!");
        playerTurn = true;
    }

    private void lostGame() {
        Toast.makeText(PlayActivity.this, "Wrong Sequence!", Toast.LENGTH_LONG).show();
        System.out.println("You lost, lets start again!");
        System.out.println("-----------------------------------");
        game = new GameController();
        playSequence();
    }

    private void wait(int time) {
        try {
            Thread.sleep(time);
        }
        catch (Exception e) {
            e.getStackTrace();
        }
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
}
