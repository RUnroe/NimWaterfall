package com.example.nimui.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nimui.R;

public class NimUI extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }
    //Setting up the UI to connect with the backend. Called raw due to it's type.
    TextView settingsDisplayRaw;
    TextView instructionsRaw;
    TextView playerOneScoreRaw;
    TextView playerTwoScoreRaw;
    TextView outputGameRaw;

    //Changing the EditText contents and turning them to String to display new information, at least these will be used to do so.
    String settingsDisplay;
    String instructions;
    String playerOneScore;
    String playerTwoScore;
    String outputGame;

    //Error possibly when they try to start the game without pressing any buttons.
    String error = "You cannot continue until you select the settings for your game.";

    //Settings strings.
    String playerSettingsToggle = "";
    String difficultyToggle= "";


    /**
     * Buttons used to get back and forth between pages, between Game, Instructions, and Settings; which you start on.
     * @param v is just used to make the view do what's needed.
     */
    public void goToGame (View v){

        setContentView(R.layout.game);
    }

    public void goToInstructions (View v){

        setContentView(R.layout.intructions);
    }

    public void goToSettings (View v){

        setContentView(R.layout.settings);
    }

    /**
     * PVP and PVC buttons used to set Player Two(Either a computer or an actual player.
     * @param v is just used to make the view do what's needed.
     */
    public void setPVP (View v){


    }

    public void setPVC (View v){


    }

    /**
     * Difficulty buttons meant to change the playing board of the game.
     * @param v is just used to make the view do what's needed.
     */
    public void setEasy (View v){


    }

    public void setMedium (View v){


    }

    public void setHard (View v){


    }

    /**
     * Buttons meant to restart games, end turns and exit the game entirely.
     * @param v is just used to make the view do what's needed.
     */
    public void exitGame (View v){

        this.finishAffinity();
    }

    public void restartGame (View v){


    }

    public void endTurn (View v){


    }

    /**
     * Row buttons meant to work as functioning match sticks. May make the other's none clickable when one row already is pressed.
     * @param v is just used to make the view do what's needed.
     */
    public void rowOne (View v){


    }

    public void rowTwo (View v){


    }

    public void rowThree (View v){


    }

    public void rowFour (View v){


    }
}
