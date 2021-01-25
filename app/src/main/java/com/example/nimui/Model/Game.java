package com.example.nimui.Model;


import java.util.Arrays;

public class Game {

    public Game(int[] initBoardState, boolean player2IsAI) {
        this.board = initBoardState;
        this.initBoardState = initBoardState;
        this.player2IsAI = player2IsAI;
    }

    private int[] initBoardState;
    private int[] board;
    private int selectedRow;
    private boolean player2IsAI;

    private int player1Score = 0;
    private int player2Score = 0;

    private AI ai = new AI();

    private boolean isPlayer1Turn = true;

    public boolean aiTurn() {
        boolean valid = false;
        do {
            int[] aiMove = ai.determineMove(board);
            valid = isMoveValid(aiMove[0], aiMove[1]);
        } while(!valid);
        return valid;
    }

    private boolean isMoveValid(int row, int amount) {
        boolean valid = false;

        if(row >= 0 && row < board.length && amount <= board[row] && amount > 0) {
            do {
                valid = removeMatchesFromRow(row, amount);
            } while(!valid);
        }
        return valid;
    }

    public boolean removeMatchesFromRow(int row, int amount) {
        if(selectedRow == -1) selectedRow = row;
        if(selectedRow == row) {
            board[row] = board[row] - amount;
            checkForWin();
            return true;
        }
        return false;
    }

    public void endTurn() {
        if(!checkForWin() && selectedRow != -1) {
            isPlayer1Turn = !isPlayer1Turn;
            selectedRow = -1;
        }
        if(!isPlayer1Turn && player2IsAI) aiTurn();
    }

    private boolean checkForWin() {
        int count = 0;
        boolean win;

        for(int i = 0; i < board.length; i++) {
            if(board[i] == 0) count++;
        }
        win = (count == board.length);
        if(win) {
            if(isPlayer1Turn) {
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
    }

}
