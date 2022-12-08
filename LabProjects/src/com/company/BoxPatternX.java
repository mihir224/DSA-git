package com.company;
  import java.util.Scanner;
  //MIHIR SAINI 9920102054 E2
public class BoxPatternX {
        public static void main(String[] args)
        {
            int n;
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the value of n: ");
            n=sc.nextInt();

            System.out.println("(a)");
            for(int i=1;i<=n;i++)
            {
                for(int j=1;j<=n;j++)
                {
                    if(i==1 || i==n || j==1 || j==n)
                    {
                        System.out.print("#");
                    }
                    else
                    {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
            System.out.println("(b)");


            for (int row = 0; row < n; row++) {
                for (int column = 0; column < n; column++) {
                    if (row == 0 || row == n - 1 || column == n - 1 - row) {
                        System.out.print("# ");
                    }
                    else {
                        System.out.print("  ");
                    }
                }
                System.out.println("");
            }
            System.out.println("(c)");
            for(int i=1;i<=n;i++)
            {
                for(int j=1;j<=n;j++)
                {
                    if(i==1 || i==n || j==1 || j==n || i==j || i==n-j+1)
                    {
                        System.out.print("#");
                    }
                    else
                    {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        }
    }

