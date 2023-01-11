package com.company;

public class Main {

    public static void main(String[] args) {
        boolean gameOver=true;
        int score=800;
        int levelCompleted=5;
        int bonus=100;
        if(gameOver)
        {
            int finalScore=score+(levelCompleted*bonus);
            System.out.println("FINAL SCORE: "+finalScore);
        }
        boolean newGameOver=true;
        int newScore=10000;
        int newLevelCompleted=8;
        int newBonus=200;
        if(gameOver)
        {
            int finalScore=newScore+(newLevelCompleted*newBonus);
            System.out.println("NEW FINAL SCORE: "+finalScore);
        }
    }
}
