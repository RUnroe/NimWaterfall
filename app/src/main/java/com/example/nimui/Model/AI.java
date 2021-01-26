package com.example.nimui.Model;


public class AI {
    /**
     * This method determines what kind of move to make based on the board state. If there is a special case, it will
     * make a move based on that first. If not, it will determine if it can get in a winning state (nim sum = 0). If it
     * can, it will make that move, but if it cannot, it will make a random move.
     * @param boardState the current state of the board in an int array. Index is the row and value is the amount of
     *                   matches in that row
     * @return move to make as int[]. The first index contains the row to remove from, and the second index is the
     * amount to remove.
     */
    public int[] determineMove(int[] boardState) {
        int[] specialCaseMove = checkSpecialCases(boardState);
        if( specialCaseMove == null) {
            int nimSum = findNimSum(boardState);
            if(nimSum > 0) {
                return genMove(boardState, nimSum);
            }
            else {
                return genRandomMove(boardState);
            }
        }
        else {
            return specialCaseMove;
        }
    }

    /**
     * A method to find the nim sum of the current board state. The nim sum is found by performing XOR on all of the
     * values on the game board. The resulting number is known as the num sum.
     * @param board the current state of the board in an int array. Index is the row and value is the amount of matches
     *             in that row
     * @return nim sum
     */
    private int findNimSum(int[] board) {
        int nimSum = 0;
        for (int row: board) {
            nimSum = nimSum ^ row;
        }
        return nimSum;
    }

    /**
     * Generates the best move to make based on the current board state and the nim sum of that state. This method
     * calculated how much to remove from a row based on the nim sum, and then removes it from the largest row.
     * @param board the current state of the board in an int array. Index is the row and value is the amount of matches
     *              in that row
     * @param nimSum an integer value determined from the current board state.
     * @return move to make as int[]. The first index contains the row to remove from, and the second index is the
     * amount to remove.
     */
    private int[] genMove(int[] board, int nimSum) {
        int amountToRemove; // amount to remove from a row.
        int highestAmountOnBoard = 0;
        int indexOfHighestAmount = 0;

        //Loop through the board and find the row with the highest amount in it
        for (int i = 0; i < board.length; i++) {
            if(board[i] >= highestAmountOnBoard) {
                highestAmountOnBoard = board[i];
                indexOfHighestAmount = i;
            }
        }
        //Nim sum can be greater than the amount in a row
        amountToRemove = nimSum;
        if(nimSum != highestAmountOnBoard) amountToRemove = nimSum % highestAmountOnBoard;
        //Return move to make
        System.out.println(indexOfHighestAmount + ": -" + amountToRemove);
        return new int[] {indexOfHighestAmount, amountToRemove};

    }

    /**
     * Generates a random, valid move. This is used when the AI cannot get itself into a winning state.
     * @param board the current state of the board in an int array. Index is the row and value is the amount of matches
     *              in that row
     * @return move to make as int[]. The first index contains the row to remove from, and the second index is the
     * amount to remove
     */
    private int[] genRandomMove(int[] board) {
        int selectedRow;
        int amountToRemove = 1; // amount to remove from a row.

        // To take from a row, it has to have at least one match in it.
        // This loop will keep running until it randomly selects a row with matches in it.
        do {
            selectedRow = board[(int) Math.floor(Math.random() * (board.length))];
        } while(board[selectedRow] == 0);

        //Return move to make
        System.out.println(selectedRow + ": -" + amountToRemove);
        return new int[] {selectedRow, amountToRemove};
    }

    /**
     * Checks to see if there are any special cases (specific to misere version) that need to be dealt with. If there
     * are, this method will return the proper move to make. If not, the method will return null.
     * @param board the current state of the board in an int array. Index is the row and value is the amount of matches
     *              in that row
     * @return either a move to make as an int[] or null.
     */
    private int[] checkSpecialCases(int[] board) {
        //Get number of populated rows
        int numOfPopulatedRows = 0;
        for (int row: board) {
            if(row > 0) numOfPopulatedRows++;
        }

        //Check if board has only one row
        if(numOfPopulatedRows == 1) {
            int indexOfRow = 0;
            int amountOfMatchesInRow = 0;
            //Get index and number of matches in the one populated row
            for (int i = 0; i < board.length; i++) {
                if(board[i] > 0) {
                    indexOfRow = i;
                    amountOfMatchesInRow = board[i];
                    //Found info, do not need to look at other rows
                    break;
                }
            }
            if(amountOfMatchesInRow == 1) {
                return new int[] {indexOfRow, 1}; //accept defeat
            }
            else {
                return new int[] {indexOfRow, amountOfMatchesInRow - 1}; //Leave other player with only one match
            }
        }
        //Check if there are 2 rows, and one has only one match
        else if(numOfPopulatedRows == 2) {
            //Determine if one row has only one match
            boolean aRowHasOnlyOneMatch = false;
            for (int row: board) {
                if(row == 1) {
                    aRowHasOnlyOneMatch = true;
                    break;
                }
            }
            if(aRowHasOnlyOneMatch) {
                int indexOfRow = 0;
                int amountOfMatchesInRow = 0;
                //Get index and number of matches in the other populated row
                for (int i = 0; i < board.length; i++) {
                    if(board[i] > 1) {
                        indexOfRow = i;
                        amountOfMatchesInRow = board[i];
                        //Found info, do not need to look at other rows
                        break;
                    }
                }
                //Remove all matches from more populated row, leaving just a row of 1
                return new int[] {indexOfRow, amountOfMatchesInRow};
            }
        }
        //If none of the special cases are met, return null
        return null;
    }

}
