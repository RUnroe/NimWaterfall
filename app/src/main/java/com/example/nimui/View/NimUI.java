package com.example.nimui.View;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nimui.Controller.Nim;
import com.example.nimui.Model.Difficulty;
import com.example.nimui.R;

public class NimUI extends AppCompatActivity {

    //Setting up the UI to connect with the backend. Called raw due to it's type.
    TextView settingsDisplayRaw;

    //Changing the EditText contents and turning them to String to display new information, at least these will be used to do so.
    String settingsDisplay;

    //Settings strings.
    String playerSettingsToggle = "Player V Player : ";
    String difficultyToggle = "Easy Difficulty";


    private Nim nim = new Nim(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        //set initial settings text
        ((TextView) findViewById(R.id.txtViewSettings)).setText(playerSettingsToggle + difficultyToggle);
    }


    /**
     * Buttons used to get back and forth between pages, between Game, Instructions, and Settings; which you start on.
     * @param v is just used to make the view do what's needed.
     */
    public void goToGame (View v){
        nim.createGame();
        setContentView(R.layout.game);
        updateGameBoard(nim.getGame().getBoard());
        updateScores(0,0);
        setOutput("It is now player ONE's turn");

    }

    public void goToInstructions (View v){
        setContentView(R.layout.intructions);

    }

    public void goToSettings (View v){
        setContentView(R.layout.settings);

        //Set default settings
        playerSettingsToggle = "Player V Player : ";
        difficultyToggle = "Easy Difficulty";
        nim.setDifficulty(Difficulty.EASY);
        nim.setSecondPlayerComputer(false);
        ((TextView) findViewById(R.id.txtViewSettings)).setText(playerSettingsToggle + difficultyToggle);
    }

    /**
     * PVP and PVC buttons used to set Player Two(Either a computer or an actual player.
     * @param v is just used to make the view do what's needed.
     */
    public void setPVP (View v){
        settingsDisplayRaw = findViewById(R.id.txtViewSettings);
        playerSettingsToggle = "Player V Player : ";
        settingsDisplay = playerSettingsToggle + difficultyToggle;
        settingsDisplayRaw.setText(settingsDisplay);
        nim.setSecondPlayerComputer(false);
    }

    public void setPVC (View v){
        settingsDisplayRaw = findViewById(R.id.txtViewSettings);
        playerSettingsToggle = "Player V Computer: ";
        settingsDisplay = playerSettingsToggle + difficultyToggle;
        settingsDisplayRaw.setText(settingsDisplay);
        nim.setSecondPlayerComputer(true);
    }

    /**
     * Difficulty buttons meant to change the playing board of the game.
     * @param v is just used to make the view do what's needed.
     */
    public void setEasy (View v){
        settingsDisplayRaw = findViewById(R.id.txtViewSettings);
        difficultyToggle = "Easy Difficulty";
        settingsDisplay = playerSettingsToggle + difficultyToggle;
        settingsDisplayRaw.setText(settingsDisplay);
        nim.setDifficulty(Difficulty.EASY);
    }

    public void setMedium (View v){
        settingsDisplayRaw = findViewById(R.id.txtViewSettings);
        difficultyToggle = "Medium Difficulty";
        settingsDisplay = playerSettingsToggle + difficultyToggle;
        settingsDisplayRaw.setText(settingsDisplay);
        nim.setDifficulty(Difficulty.MEDIUM);

    }

    public void setHard (View v){
        settingsDisplayRaw = findViewById(R.id.txtViewSettings);
        difficultyToggle = "Hard Difficulty";
        settingsDisplay = playerSettingsToggle + difficultyToggle;
        settingsDisplayRaw.setText(settingsDisplay);
        nim.setDifficulty(Difficulty.HARD);
    }

    /**
     * Buttons meant to restart games, end turns and exit the game entirely.
     * @param v is just used to make the view do what's needed.
     */
    public void exitGame (View v){

        this.finishAffinity();
    }

    public void restartGame (View v){
        nim.getGame().resetGame();

    }

    public void endTurn (View v){
        nim.getGame().endTurn();

    }

    /**
     * Row buttons meant to work as functioning match sticks. May make the other's none clickable when one row already is pressed.
     * @param v is just used to make the view do what's needed.
     */
    public void rowOne (View v){
        removeFromRow(0);
    }

    public void rowTwo (View v){
        removeFromRow(1);
    }

    public void rowThree (View v){
        removeFromRow(2);
    }

    public void rowFour (View v){
        removeFromRow(3);
    }

    /**
     * Takes in row and removes one match from that row. Then updates the game board on the UI.
     * @param row the index of the board array to remove matches from
     */
    private void removeFromRow(int row) {
        nim.getGame().playerRemoveMatchesFromRow(row, 1);
        updateGameBoard(nim.getGame().getBoard());
    }

    /**
     * Updates the visual representation of the game board.
     * @param board current state of the game board
     */
    public void updateGameBoard(int[] board) {
        //loop through board and create "matches" for each row
        for(int i = 0; i < board.length; i++) {
            String matches = "";
            for(int j = 0; j < board[i]; j++) {
                matches +=" |";
            }
            //Sets the text of the correct button/row based on index i in the for loop
            int temp = getResources().getIdentifier("btnRow" + i, "id", getPackageName());
            ((Button) findViewById(temp)).setText(matches);
        }
    }

    /**
     * Sets the text of the output TextView.
     * @param text string to be displayed on the output TextView.
     */
    public void setOutput(String text) {
        ((TextView) findViewById(R.id.txtGameOutPut)).setText(text);
    }

    /**
     * Updates the score display of the players. Uses different names for player two based on
     * whether it is an ai or a user.
     * @param playerOneScore integer value of player one's current score
     * @param playerTwoScore integer value of player two's current score
     */
    public void updateScores(int playerOneScore, int playerTwoScore) {
        ((TextView) findViewById(R.id.txtPlayerOneScore)).setText("Player One: " + playerOneScore);

        String secondPlayerScoreText = nim.isSecondPlayerComputer() ? "Computer: " : "Player Two: ";
        ((TextView) findViewById(R.id.txtPlayerTwoScore)).setText(secondPlayerScoreText + playerTwoScore);
    }

    /**
     * Changes the visual representation of which player is currently active. The active player will
     * have their name in bold, black text.
     * @param isNowPlayer1Turn boolean value to determine which player's turn is is.
     */
    public void changeActivePlayer(boolean isNowPlayer1Turn) {
        TextView player1Text = findViewById(R.id.txtPlayerOneScore);
        TextView player2Text = findViewById(R.id.txtPlayerTwoScore);
        //Make player 1 name bold and black. Make player 2 name gray and regular font weight
        if(isNowPlayer1Turn) {
            player1Text.setTextColor(0xFF000000);
            player1Text.setTypeface(Typeface.DEFAULT_BOLD);
            player2Text.setTextColor(0xFF666666);
            player2Text.setTypeface(Typeface.DEFAULT);

        }
        //Make player 2 name bold and black. Make player 1 name gray and regular font weight
        else {
            player1Text.setTextColor(0xFF666666);
            player1Text.setTypeface(Typeface.DEFAULT);
            player2Text.setTextColor(0xFF000000);
            player2Text.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }
}
