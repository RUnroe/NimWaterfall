package com.example.nimui.Model;


import com.example.nimui.Controller.Nim;
import com.example.nimui.View.NimUI;

import java.util.Arrays;

public class Game {

    public Game(NimUI nimUI, int[] initBoardState, boolean player2IsAI) {
        this.nimUI = nimUI;
        this.initBoardState = initBoardState;
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

    private boolean isPlayer1Turn = true;


    public int[] getBoard() {
        return board;
    }

    public boolean aiTurn() {
        int[] aiMove = ai.determineMove(board);
        removeMatchesFromRow(aiMove[0], aiMove[1]);
        endTurn();
        nimUI.updateGameBoard(board);
        return true;
    }

    private boolean isMoveValid(int row, int amount) {
        return (row >= 0 && row < board.length && amount <= board[row] && amount > 0);
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
        if(win) {
            if(!isPlayer1Turn) {
                nimUI.setOutput("Player 1 won!");
                player1Score++;
            } else {
                player2Score++;
            }
        }
        return win;
    }

    public void resetGame() {
        isPlayer1Turn = true;
        board = Arrays.copyOf(initBoardState, initBoardState.length);
        nimUI.updateGameBoard(board);
        nimUI.setOutput("The game has been reset!");
    }

}
