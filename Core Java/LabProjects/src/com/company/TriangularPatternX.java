package com.company;

import java.util.Scanner;

//MIHIR SAINI 9920102054 E2
public class TriangularPatternX {
    public static void main(String[] args)
    {
        int n;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the value of n");
        n=sc.nextInt();

        System.out.println("(a)");
        for(int i=0;i<=n;i++)
        {
            for(int j=0;j<i;j++)
            {
                System.out.print("#");
            }
            System.out.println();
        }
        System.out.println("(b)");
        for(int i=n;i>=0;i--)
        {
            for(int j=i;j>0;j--)
            {
                System.out.print("#");
            }
            System.out.println();
        }

        System.out.println("(c)");
        for(int i=0;i<=n;i++)
        {
            for(int j=0;j<i;j++)
            {
                System.out.print(" ");
            }
            for(int j=n;j>=i;j--)
            {
                System.out.print("#");
            }
            System.out.println();
        }
        System.out.println("(d)");
        for(int i=n;i>=0;i--)
        {
            for(int j=i;j>0;j--)
            {
                System.out.print(" ");
            }
            for(int j=n;j>=i;j--)
            {
                System.out.print("#");
            }
            System.out.println();
        }
    }
}
