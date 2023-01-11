package com.company;

public class Main {

    public static void main(String[] args) {
        boolean gameOver=true;
        int score=800;
        int levelCompleted=5;
        int bonus=100;

        int highScore= calculateScore(gameOver, score, levelCompleted, bonus);//assigning a variable to a method
        System.out.println("The final score is "+highScore);
        score=10000;
        levelCompleted=8;
        bonus=500;
        calculateScore(gameOver, score, levelCompleted, bonus);

    }
    public static int calculateScore(boolean gameOver, int score, int levelCompleted, int bonus)//method calculateScore declared
    {
        if(gameOver)
        {
            int finalScore=score+(levelCompleted*bonus);
            finalScore+=1000;
            //System.out.println("The final score is "+finalScore);
            return finalScore;
        }
        return -1;
    }

}
