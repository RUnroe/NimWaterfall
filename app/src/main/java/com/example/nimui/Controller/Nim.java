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

    public Nim(NimUI nimUI) {
        this.nimUI = nimUI;
    }


    public boolean isSecondPlayerComputer() {
        return isSecondPlayerComputer;
    }
    public void setSecondPlayerComputer(boolean secondPlayerComputer) {
        isSecondPlayerComputer = secondPlayerComputer;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    /**
     * Takes in the selected difficulty and returns the initial board state for the respective difficulty.
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
     *
     */
    public void createGame() {
        game = new Game(nimUI, getDifficulty(difficulty), isSecondPlayerComputer );
    }


   public Game getGame() {
        return this.game;
   }
}

