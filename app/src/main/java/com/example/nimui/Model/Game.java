package com.example.nimui.Model;



public class Game {

    public Game(int[] initBoardState, boolean player2IsAI) {
        board = initBoardState;
        this.initBoardState = initBoardState;
        player2IsAI = player2IsAI;
    }

    private int[] initBoardState;
    private int[] board;
    private int selectedRow;
    private boolean player2AI;

    private int player1Score = 0;
    private int player2Score = 0;

    private AI ai;

    private boolean isPlayer1Turn;

    public boolean aiTurn() {
        boolean valid = false;

        if(player2AI == true){
            do {
                int[] aiMove = ai.determineMove(board);
                valid = isMoveValid(aiMove[0], aiMove[1]);
            } while(!valid);
        }
        return valid;
    }

    private boolean isMoveValid(int row, int amount) {
        boolean valid = false;

        if(row > 0 && row <= board.length && amount <= board[row-1] && amount > 0) {
            do {
                valid = removeMatchesFromRow(row, amount);
            } while(!valid);
        }
        return valid;
    }

    public boolean removeMatchesFromRow(int row, int amount) {
        board[row-1] = board[row-1] - amount;

        boolean winCheck = checkForWin();
        if(winCheck == true) {
            if(isPlayer1Turn == true) {
                player1Score++;
            } else {
                player2Score++;
            }
        }
        return true;
    }

    public boolean endTurn() {
        if(checkForWin() == false) {
            isPlayer1Turn = !isPlayer1Turn;
            return true;
        }
    }

    private boolean checkForWin() {
        int count = 0;
        boolean win;

        for(int i = 0; i < board.length; i++) {
            if(board[i] == 0) count++;
        }

        if(count == board.length) {
            win = true;
        } else {
            win = false;
        }

        return win;
    }

    public void resetGame() {


    }

}
