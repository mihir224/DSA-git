package com.company;

import java.util.Scanner;
//MIHIR SAINI 9920102054 E2
public class ReverseNumber {
        public static void main(String[] args) {

            int num=0;
            int rev=0;
            Scanner scanner=new Scanner(System.in);
            num=scanner.nextInt();
            while(num != 0) {
                int digit = num % 10;
                rev = rev * 10 + digit;

                num /= 10;
            }
            System.out.println("Entered Number: " + num);
            System.out.println("Reversed Number: " + rev);
        }
    }
