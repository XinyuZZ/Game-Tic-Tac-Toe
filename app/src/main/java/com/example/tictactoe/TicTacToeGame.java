package com.example.tictactoe;

/* TicTacToeConsole.java
 * By Frank McCown (Harding University)
 *
 * This is a tic-tac-toe game that runs in the console window.  The human
 * is X and the computer is O.
 */


import java.util.Arrays;
import java.util.Random;


public class TicTacToeGame {

    // Characters used to represent the human, computer, and open spots
    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT = ' ';
    public static final int BOARD_SIZE = 9;
    public char mBoard[] = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    public int level = 0;
    public int computerWinTime = 0;
    public int yourWinTime = 0;
    public int TieTime = 0;

    private Random mRand = new Random();
    char turn = HUMAN_PLAYER; // Human starts first
    int win = 0; // Set to 1, 2, or 3 when game is over
    public void TicTacToeModel(){

    }



    /** Clear the board of all X's and O's. */
    public void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i] = OPEN_SPOT;
        } }

    /** Set the given player at the given location on the game board * */
    public void setMove(char player, int location) {
        mBoard[location] = player;
    }
    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel(){
        if(level == 0){
            return R.string.Easy;
        }else if(level == 1){
            return R.string.Hard;
        }else{
            return R.string.Expert;
        }
    }

    // Save the game result
    public void setComputerWinTime(){
        computerWinTime++ ;
    }
    public String getComputerWinTime(){
        return Integer.toString(computerWinTime);
    }
    public void setYourWinTime(){
        yourWinTime++ ;
    }
    public String getYourWinTime(){
        return Integer.toString(yourWinTime);
    }
    public void setTieTime(){
        TieTime++ ;
    }
    public String getTieTime(){
        return Integer.toString(TieTime);
    }




    // Define the type of who wins the game

    // 0---Nobody wins yet
    // 1---Tie
    // 2---X won
    // 3---O won

    public int WinnerCheck() {

        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3)	{
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+1] == HUMAN_PLAYER &&
                    mBoard[i+2]== HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i+1]== COMPUTER_PLAYER &&
                    mBoard[i+2] == COMPUTER_PLAYER)
                return 3;
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+3] == HUMAN_PLAYER &&
                    mBoard[i+6]== HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i+3] == COMPUTER_PLAYER &&
                    mBoard[i+6]== COMPUTER_PLAYER)
                return 3;
        }

        // Check for diagonal wins
        if ((mBoard[0] == HUMAN_PLAYER &&
                mBoard[4] == HUMAN_PLAYER &&
                mBoard[8] == HUMAN_PLAYER) ||
                (mBoard[2] == HUMAN_PLAYER &&
                        mBoard[4] == HUMAN_PLAYER &&
                        mBoard[6] == HUMAN_PLAYER))
            return 2;
        if ((mBoard[0] == COMPUTER_PLAYER &&
                mBoard[4] == COMPUTER_PLAYER &&
                mBoard[8] == COMPUTER_PLAYER) ||
                (mBoard[2] == COMPUTER_PLAYER &&
                        mBoard[4] == COMPUTER_PLAYER &&
                        mBoard[6] == COMPUTER_PLAYER))
            return 3;

        // Check for Tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER)
                return 0;
        }

        // If we make it through the previous loop, all places are taken, so it's a Tie
        return 1;
    }



    public int getComputerMove()
    {
        int move;
        if(level == 0){
            // Generate random move
            do
            {
                move = mRand.nextInt(BOARD_SIZE);
            } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER);
            mBoard[move] = COMPUTER_PLAYER;
            return move;
        }
        else if(level == 1){
            // First see if there's a move O can make to win
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                    char curr = mBoard[i];
                    mBoard[i] = COMPUTER_PLAYER;
                    if (WinnerCheck() == 3) {
                        System.out.println("Computer is moving to " + (i + 1));
                        return i;
                    }
                    else
                        mBoard[i] = curr;
                }
            }
            // See if there's a move O can make to block X from winning
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                    char curr = mBoard[i]; // Save the current number
                    mBoard[i] = HUMAN_PLAYER;
                    if (WinnerCheck() == 2) {
                        mBoard[i] = COMPUTER_PLAYER;
                        System.out.println("Computer is moving to " + (i + 1));
                        return i;
                    }
                    else
                        mBoard[i] = curr;
                }
            }
            // Generate random move
            do
            {
                move = mRand.nextInt(BOARD_SIZE);
            } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER);
            mBoard[move] = COMPUTER_PLAYER;
            return move;
        }
        else {
            // Expert level
            // First see if there's a move O can make to win
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                    char curr = mBoard[i];
                    mBoard[i] = COMPUTER_PLAYER;
                    if (WinnerCheck() == 3) {
                        System.out.println("Computer is moving to " + (i + 1));
                        return i;
                    }
                    else
                        mBoard[i] = curr;
                }
            }
            // See if there's a move O can make to block X from winning
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                    char curr = mBoard[i]; // Save the current number
                    mBoard[i] = HUMAN_PLAYER;
                    if (WinnerCheck() == 2) {
                        mBoard[i] = COMPUTER_PLAYER;
                        System.out.println("Computer is moving to " + (i + 1));
                        return i;
                    }
                    else
                        mBoard[i] = curr;
                }
            }
            if(mBoard[0] == HUMAN_PLAYER ||mBoard[2] == HUMAN_PLAYER
                    ||mBoard[6] == HUMAN_PLAYER||mBoard[8] == HUMAN_PLAYER){
                if(mBoard[4] != HUMAN_PLAYER && mBoard[4] != COMPUTER_PLAYER){
                    mBoard[4] = COMPUTER_PLAYER;
                    return  4;
                }
            }else{
                if(mBoard[0] != HUMAN_PLAYER && mBoard[0] != COMPUTER_PLAYER){
                    mBoard[0] = COMPUTER_PLAYER;
                    return 0;
                }
                else if(mBoard[2] != HUMAN_PLAYER && mBoard[0] != COMPUTER_PLAYER){
                    mBoard[2] = COMPUTER_PLAYER;
                    return  2;
                }
                else if(mBoard[8] != HUMAN_PLAYER && mBoard[0] != COMPUTER_PLAYER){
                    mBoard[8] = COMPUTER_PLAYER;
                    return  8;
                }
                else if(mBoard[6] != HUMAN_PLAYER && mBoard[0] != COMPUTER_PLAYER){
                    mBoard[6] = COMPUTER_PLAYER;
                    return  6;
                }
            }
            // Generate random move
            do
            {
                move = mRand.nextInt(BOARD_SIZE);
            } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER);
            mBoard[move] = COMPUTER_PLAYER;
            return move;
        }
    }



}