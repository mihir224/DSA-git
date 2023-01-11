package com.company;
import java.util.Scanner;
//MIHIR SAINI 9920102054 E2
public class SquarePattern {
        public static Scanner sc=new Scanner(System.in);
        public static void main(String[] args)
        {
            int side, i, j;
            System.out.print(" Enter any side of the square: ");
            side = sc.nextInt();
            for(i=1; i<= side;i++)
            {
                for(j=1; j<=side; j++)
                {
                    System.out.print("*");
                }
                System.out.print("\n");
            }
        }
    }

