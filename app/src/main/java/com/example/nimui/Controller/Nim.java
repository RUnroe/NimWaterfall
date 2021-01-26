package com.example.nimui.Controller;


import android.renderscript.ScriptIntrinsicYuvToRGB;

import com.example.nimui.Model.Difficulty;
import com.example.nimui.Model.Game;
import com.example.nimui.View.NimUI;


public class Nim {


    private boolean isSecondPlayerComputer = false;
    private Difficulty difficulty = Difficulty.EASY;
    private Game game;
    private NimUI nimUI;

    /**
     * Custom constructor that takes in the instance of nimUI. This allows children of the UI class
     * to access and call necessary methods.
     * @param nimUI The instance of the nimUI activity
     */
    public Nim(NimUI nimUI) {
        this.nimUI = nimUI;
    }

    /**
     * Simple getter for the private boolean variable isSecondPlayerComputer.
     * @return isSecondPlayerComputer variable
     */
    public boolean isSecondPlayerComputer() {
        return isSecondPlayerComputer;
    }

    /**
     * Simple setter for the private boolean variable "isSecondPlayerComputer".
     * @param secondPlayerComputer boolean value based on whether the second player is an AI or a player
     */
    public void setSecondPlayerComputer(boolean secondPlayerComputer) {
        isSecondPlayerComputer = secondPlayerComputer;
    }

    /**
     * Simple setter for the private Difficulty variable "difficulty".
     * @param difficulty Difficulty enum value chosen through settings page
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    /**
     * Takes in the selected difficulty and returns the initial board state for the respective
     * difficulty.
     * @param difficulty difficulty chosen by player through gui
     * @return initial board state as an int array
     */
    private int[] getDifficulty(Difficulty difficulty) {
        //Return easy game by default
        switch(difficulty) {
            case MEDIUM:
                return new int[] {0, 2, 5, 7};
            case HARD:
                return new int[] {2, 3, 8, 9};
            default:
                return new int[] {0, 3, 3, 0};
        }

    }


    /**
     * Instantiates the game class with the settings chosen.
     */
    public void createGame() {
        game = new Game(nimUI, getDifficulty(difficulty), isSecondPlayerComputer );
    }

    /**
     * Simple getter that returns the Game instance.
     * @return instance of Game class.
     */
   public Game getGame() {
        return this.game;
   }
}

