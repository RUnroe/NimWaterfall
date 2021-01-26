package com.example.nimui.Model;


import com.example.nimui.Controller.Nim;
import com.example.nimui.View.NimUI;

import java.util.Arrays;

public class Game {

    public Game(NimUI nimUI, int[] initBoardState, boolean player2IsAI) {
        this.nimUI = nimUI;
        //Save original board state
        this.initBoardState = initBoardState;
        //Make copy of board state for game play
        this.board = Arrays.copyOf(initBoardState, initBoardState.length);
        this.player2IsAI = player2IsAI;
        //Set the first output text to who's turn it is
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


    public int[] getBoard() {
        return board;
    }

    public boolean aiTurn() {
        int[] aiMove;
        do {
            aiMove = ai.determineMove(board);

        } while(!removeMatchesFromRow(aiMove[0], aiMove[1]));
        endTurn();
        nimUI.updateGameBoard(board);
        return true;
    }

    private boolean isMoveValid(int row, int amount) {
        return (row >= 0 && row < board.length && amount <= board[row] && amount > 0);
    }

    public void playerRemoveMatchesFromRow(int row, int amount) {
        if (!(!isPlayer1Turn && player2IsAI)) removeMatchesFromRow(row, amount);
    }
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

    public void endTurn() {
        if(!checkForWin() && selectedRow != -1) {
            nimUI.setOutput("It is now player " + (isPlayer1Turn ? "TWO" : "ONE") + "'s turn");
            isPlayer1Turn = !isPlayer1Turn;
            nimUI.changeActivePlayer(isPlayer1Turn);
            selectedRow = -1;
            if(!isPlayer1Turn && player2IsAI) aiTurn();
        }

    }

    private boolean checkForWin() {
        int count = 0;
        boolean win;

        for(int i = 0; i < board.length; i++) {
            if(board[i] == 0) count++;
        }
        win = (count == board.length);
        if(win && isGamePlaying) {
            isGamePlaying = false;
            if(!isPlayer1Turn) {
                nimUI.setOutput("Player 1 won!");
                player1Score++;
            } else {
                nimUI.setOutput(player2IsAI? "Computer won!" : "Player 2 won!");
                player2Score++;
            }
            nimUI.updateScores(player1Score, player2Score);
        }
        return win;
    }

    public void resetGame() {
        selectedRow = -1;
        isPlayer1Turn = true;
        isGamePlaying = true;
        nimUI.changeActivePlayer(isPlayer1Turn);
        board = Arrays.copyOf(initBoardState, initBoardState.length);
        nimUI.updateGameBoard(board);
        nimUI.setOutput("The game has been reset!");
    }

}
