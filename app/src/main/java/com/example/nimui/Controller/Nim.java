package com.example.nimui.Controller;


import com.example.nimui.Model.Game;

enum Difficulty {
    EASY,
    MEDIUM,
    HARD
}


public class Nim {

    private boolean isSecondPlayerComputer = false;
    private Difficulty difficulty = Difficulty.EASY;
    private Game game;


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
    public void playGame() {
        //game = new Game(getDifficulty(difficulty), isSecondPlayerComputer );
    }


    /**
     *
     */
    public void newGame() {

    }
}

