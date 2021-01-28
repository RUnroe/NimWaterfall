package com.example.nimui.Model;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class AITest {
    AI ai = new AI();
    @Test
    public void determineMove_isCorrect() {
        //Test that the ai will take the last piece
        Assert.assertArrayEquals(new int[]{0,1}, ai.determineMove(new int[]{1,0,0,0}));

        //Test that the ai will leave the player with one piece left
        Assert.assertArrayEquals(new int[]{1,6}, ai.determineMove(new int[]{1,6,0,0}));
        Assert.assertArrayEquals(new int[]{1,5}, ai.determineMove(new int[]{0,6,0,0}));

        //Test that the ai will make valid moves
        Assert.assertArrayEquals(new int[]{2,3}, ai.determineMove(new int[]{1,2,4,0}));
        Assert.assertArrayEquals(new int[]{3,7}, ai.determineMove(new int[]{1,2,4,8}));
        Assert.assertArrayEquals(new int[]{2,2}, ai.determineMove(new int[]{1,4,7,0}));
        Assert.assertArrayEquals(new int[]{3,2}, ai.determineMove(new int[]{0,2,0,8}));

    }

}