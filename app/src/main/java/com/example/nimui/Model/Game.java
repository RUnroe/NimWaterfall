package com.example.nimui.Model;


import com.example.nimui.Controller.Nim;
import com.example.nimui.View.NimUI;

import java.util.Arrays;

public class Game {

    /**
     * Custom constructor that takes in the instance of nimUI and the settings chosen by the user.
     * @param nimUI The instance of the nimUI activity
     * @param initBoardState int[] of the initial board state
     * @param player2IsAI boolean determining whether the second player is an ai or not.
     */
    public Game(NimUI nimUI, int[] initBoardState, boolean player2IsAI) {
        this.nimUI = nimUI;
        //Save original board state
        this.initBoardState = initBoardState;
        //Make copy of board state for game play
        this.board = Arrays.copyOf(initBoardState, initBoardState.length);
        this.player2IsAI = player2IsAI;
    }

    private NimUI nimUI;
    private int[] initBoardState;
    private int[] board;
    private int selectedRow = -1;
    private boolean player2IsAI;

    private int player1Score = 0;
    private int player2Score = 0;

    private AI ai = new AI();

    private boolean isGamePlaying = true;
    private boolean isPlayer1Turn = true;


    /**
     * Simple getter to return the current board state.
     * @return current board state represented as an int array
     */
    public int[] getBoard() {
        return board;
    }

    /**
     * Gets valid move from the instance of the ai class, removes matches, ends turn, then updates
     * the game board.
     */
    public void aiTurn() {
        int[] aiMove;
        do {
            aiMove = ai.determineMove(board);

        } while(!removeMatchesFromRow(aiMove[0], aiMove[1]));
        endTurn();
        nimUI.updateGameBoard(board);
    }

    /**
     * Takes in the row and amount to remove from the row and determines if the move is valid or
     * invalid.
     * @param row index of board int array
     * @param amount amount of matches to remove from the specified row
     * @return boolean value expressing whether the move is valid or not
     */
    private boolean isMoveValid(int row, int amount) {
        return (row >= 0 && row < board.length && amount <= board[row] && amount > 0);
    }

    /**
     * Only calls the removeMatchesFromRow method if the AI is not currently making a turn. This
     * prevents the user from removing matches when it is not their turn.
     * @param row index of board int array
     * @param amount amount of matches to remove from the specified row
     */
    public void playerRemoveMatchesFromRow(int row, int amount) {
        if (!(!isPlayer1Turn && player2IsAI)) removeMatchesFromRow(row, amount);
    }

    /**
     * If the move is valid, the specified amount of matches will be removed from the specified row.
     * This method also will check if the game is complete.
     * @param row index of board int array
     * @param amount amount of matches to remove from the specified row
     * @return boolean value expressing whether the move was valid or not
     */
    public boolean removeMatchesFromRow(int row, int amount) {
        if(isMoveValid(row, amount)) {
            if(selectedRow == -1) selectedRow = row;
            if(row == selectedRow) {
                board[row] = board[row] - amount;
                checkForWin();
                return true;
            }
        }
        nimUI.setOutput("Invalid move!");
        return false;
    }

    /**
     * Ends the players turn by switching which players turn it is and checking if the game was left
     * in a complete/winning state. A player's turn is only allowed to end if the player has removed
     * at least one match from the board.
     */
    public void endTurn() {
        if(!checkForWin() && selectedRow != -1) {
            nimUI.setOutput("It is now player " + (isPlayer1Turn ? "TWO" : "ONE") + "'s turn");
            isPlayer1Turn = !isPlayer1Turn;
            nimUI.changeActivePlayer(isPlayer1Turn);
            selectedRow = -1;
            if(!isPlayer1Turn && player2IsAI) aiTurn();
        }

    }

    /**
     * Checks if the game is complete, meaning there are no matches left on the board. If the game
     * is over, output which player won and add to the score accordingly.
     * @return boolean value expressing whether the game was won or not
     */
    private boolean checkForWin() {
        int count = 0;
        boolean win;

        //Loop through the rows to check if there are any matches left
        for(int i = 0; i < board.length; i++) {
            if(board[i] == 0) count++;
        }
        //Determine if the game was won or not
        win = (count == board.length);
        //if the game was won, add to the players score and stop the game from continuing.
        if(win && isGamePlaying) {
            isGamePlaying = false;
            if(!isPlayer1Turn) {
                nimUI.setOutput("Player 1 won!");
                player1Score++;
            } else {
                nimUI.setOutput(player2IsAI? "Computer won!" : "Player 2 won!");
                player2Score++;
            }
            //Update the scores on the UI
            nimUI.updateScores(player1Score, player2Score);
        }
        return win;
    }

    /**
     * Reset the game to the original state, keeping the score the same
     */
    public void resetGame() {
        selectedRow = -1;
        isPlayer1Turn = true;
        isGamePlaying = true;
        nimUI.changeActivePlayer(isPlayer1Turn);
        //Create new copy of the initial board state
        board = Arrays.copyOf(initBoardState, initBoardState.length);
        nimUI.updateGameBoard(board);
        nimUI.setOutput("The game has been reset!");
    }

}
