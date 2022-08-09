package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;
import androidx.appcompat.app.AlertDialog;

public class MainActivity extends AppCompatActivity {
    // Represents the internal state of the game
    private TicTacToeGame mGame;
    // Buttons making up the board
    private Button mBoardButtons[];
    // Various text displayed
    private TextView mInfoTextView;
    // Restart Button
    private Button startButton,bgm, bgm2, StopBGM,clearAndRestart,aboutButton;
    // Game Over
    Boolean mGameOver;
    // show Current Level
    private TextView currentLevel,yourWinTime,androidWinTime,TieTime;

    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;
    private int winner;
    private String lastBboard;
    private int lastLevel;
    private int lastTieTime;
    private int lastComputerWinTime;
    private int lastYourWinTime;
    private int lastWinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGame = new TicTacToeGame();
        mBoardButtons = new Button[mGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.button0);
        mBoardButtons[1] = (Button) findViewById(R.id.button1);
        mBoardButtons[2] = (Button) findViewById(R.id.button2);
        mBoardButtons[3] = (Button) findViewById(R.id.button3);
        mBoardButtons[4] = (Button) findViewById(R.id.button4);
        mBoardButtons[5] = (Button) findViewById(R.id.button5);
        mBoardButtons[6] = (Button) findViewById(R.id.button6);
        mBoardButtons[7] = (Button) findViewById(R.id.button7);
        mBoardButtons[8] = (Button) findViewById(R.id.button8);
        mInfoTextView = (TextView) findViewById(R.id.information);
        currentLevel = (TextView) findViewById(R.id.levelShow);
        yourWinTime = (TextView) findViewById(R.id.yourWinTime);
        androidWinTime = (TextView) findViewById(R.id.androidWinTime);
        TieTime = (TextView) findViewById(R.id.TieTime);
        startButton = (Button) findViewById(R.id.button_restart);
        bgm = (Button) findViewById(R.id.bgm);
        StopBGM = (Button) findViewById(R.id.StopBGM);
        clearAndRestart = (Button)findViewById(R.id.clear);
        aboutButton = (Button)findViewById(R.id.about);



        // Start Button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
                // get a random start
                Random random = new Random();
                if(random.nextInt(2)==1){
                    mInfoTextView.setText(R.string.AndroidTurn);
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
                    mInfoTextView.setText(R.string.YourTurn);
                    savePreferences();
                }
            }


        });
        // Start The BGM
        bgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer == null) {
                    Uri uri = Uri.parse("android.resource://"
                            + getPackageName() + "/" + R.raw.bgm);
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                    mediaPlayer.start();
                }else{
                    mediaPlayer.start();
                }
            }
        }


        );
        // Stop The BGM
        StopBGM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer2 == null) {
                    Uri uri = Uri.parse("android.resource://"
                            + getPackageName() + "/" + R.raw.bgm2);
                    mediaPlayer2 = MediaPlayer.create(getApplicationContext(), uri);
                    mediaPlayer2.start();
                }else{
                    mediaPlayer2.start();
                }
            }

        });

        // Reset
        clearAndRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGame.level = 0;
                currentLevel.setText(mGame.getLevel());
                yourWinTime.setText(Integer.valueOf(0).toString());
                mGame.yourWinTime = 0;
                TieTime.setText(Integer.valueOf(0).toString());
                mGame.TieTime = 0;
                androidWinTime.setText(Integer.valueOf(0).toString());
                mGame.computerWinTime = 0;
                startNewGame();
                savePreferences();
            }

        });
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.about_msg);
                builder.setMessage(R.string.about_msg2);
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            { }
                        });
                builder.create();
                builder.show();
            }
        });
        startNewGame();

    }

    // Data loading and switching
    @Override
    protected void onStart() {
        super.onStart();
        loadPreferences();
        mGame.level = lastLevel;
        currentLevel.setText(mGame.getLevel());
        yourWinTime.setText(Integer.valueOf(lastYourWinTime).toString());
        mGame.yourWinTime = lastYourWinTime;
        TieTime.setText(Integer.valueOf(lastTieTime).toString());
        mGame.TieTime = lastTieTime;
        androidWinTime.setText(Integer.valueOf(lastComputerWinTime).toString());
        mGame.computerWinTime = lastComputerWinTime;
        if(lastWinner == 0){
            mGame.mBoard = lastBboard.toCharArray();
            for (int i = 0; i < mBoardButtons.length; i++) {
                if(lastBboard.charAt(i)==TicTacToeGame.HUMAN_PLAYER){
                    mBoardButtons[i].setText(String.valueOf(TicTacToeGame.HUMAN_PLAYER));
                    mBoardButtons[i].setEnabled(false);
                    mBoardButtons[i].setTextColor(Color.rgb(0, 200, 0));
                }else if(lastBboard.charAt(i)==TicTacToeGame.COMPUTER_PLAYER){
                    mBoardButtons[i].setText(String.valueOf(TicTacToeGame.COMPUTER_PLAYER));
                    mBoardButtons[i].setEnabled(false);
                    mBoardButtons[i].setTextColor(Color.rgb(200, 0, 0));
                }
            }
        }else{

        }


    }

    // BGM will shut down upon user closes the App
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
        super.onDestroy();
        if(mediaPlayer2 != null){
            mediaPlayer2.stop();
        }

    }


    // Option menu definition
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the aaction bar if it is present
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true; }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.level0:
                mGame.setLevel(0);
                startNewGame();
                return true;
            case R.id.level1:
                mGame.setLevel(1);
                startNewGame();
                return true;
            case R.id.level2:
                mGame.setLevel(2);
                startNewGame();
                return true;
            case R.id.menu_Exit:
                finish();
                return true;
        }
        return false; }



    //--- Set up the game board.
    private void startNewGame() {
        mGameOver = false;
        mGame.clearBoard();
        currentLevel.setText(mGame.getLevel());
        //---Reset all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));

        }
        //---Human goes first
        mInfoTextView.setText(R.string.YouPlayFirst);

    }
    //---Handles clicks on the game board buttons
    private class ButtonClickListener implements View.OnClickListener {
        int location;
        public ButtonClickListener(int location) {
            this.location = location;
        }
        @Override
        public void onClick(View v) {
            if (mGameOver == false) {
                if (mBoardButtons[location].isEnabled()) {
                    setMove(TicTacToeGame.HUMAN_PLAYER, location);
                    //--- If no winner yet, let the computer make a move
                    winner = mGame.WinnerCheck();
                    if (winner == 0) {
                        mInfoTextView.setText(R.string.AndroidTurn);
                        int move = mGame.getComputerMove();
                        setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                        winner = mGame.WinnerCheck();
                    }
                    if (winner == 0) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
                        mInfoTextView.setText(R.string.YourTurn);
                        savePreferences();
                    } else if (winner == 1) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 200));
                        mInfoTextView.setText(R.string.Tie);
                        mGame.setTieTime();
                        TieTime.setText(mGame.getTieTime());
                        mGameOver = true;
                        savePreferences();
                    } else if (winner == 2) {
                        mInfoTextView.setTextColor(Color.rgb(0, 200, 0));
                        mInfoTextView.setText(R.string.YouWon);
                        mInfoTextView.animate().scaleX(1.5f).scaleY(1.5f).setDuration(2000);
                        mGame.setYourWinTime();
                        yourWinTime.setText(mGame.getYourWinTime());
                        mGameOver = true;
                        savePreferences();
                    } else {
                        mInfoTextView.setTextColor(Color.rgb(200, 0, 0));
                        mInfoTextView.setText(R.string.AndroidWon);
                        mInfoTextView.animate().scaleX(0.5f).scaleY(0.5f).setDuration(2000);
                        mGame.setComputerWinTime();
                        androidWinTime.setText(mGame.getComputerWinTime());
                        mGameOver = true;
                        savePreferences();
                    }
                }
            }
        } }
    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
    }
    // Data saving
    public void savePreferences() {
        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        pref.edit().putString("mBoard", String.valueOf(mGame.mBoard)).apply();
        pref.edit().putInt("level", mGame.level).apply();
        pref.edit().putInt("TieTime", mGame.TieTime).apply();
        pref.edit().putInt("computerWinTime", mGame.computerWinTime).apply();
        pref.edit().putInt("yourWinTime", mGame.yourWinTime).apply();
        pref.edit().putInt("winner", winner).apply();
    }
    // Data loading
    public void loadPreferences() {
        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        lastBboard          = pref.getString("mBoard", "[ , , , , , , , , ]");
        lastLevel           = pref.getInt("level",0);
        lastTieTime         = pref.getInt("TieTime",0);
        lastComputerWinTime = pref.getInt("computerWinTime",0);
        lastYourWinTime     = pref.getInt("yourWinTime",0);
        lastWinner          = pref.getInt("winner",0);
    }




}