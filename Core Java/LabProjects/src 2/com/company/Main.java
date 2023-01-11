package com.company;
//MIHIR SAINI 9920102054 E2
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //code for ques1
//        Question1 q1=new Question1();
//        Scanner scanner=new Scanner(System.in);
//        System.out.println("Enter the temperature in fahrenheit: ");
//        double f=scanner.nextDouble();
//        System.out.println("The temperature in Celsius is: " + q1.fahrenToCel(f));
        //code for ques2
//        Question2 q2=new Question2();
//        q2.factors(20);
        //code for ques3
//        Question3 q3=new Question3();
//        Scanner scanner=new Scanner(System.in);
//        System.out.println("Enter the values of a, b & c: ");
//        double a=scanner.nextDouble();
//        double b=scanner.nextDouble();
//        double c=scanner.nextDouble();
//        q3.roots(a,b,c);
        Question4 q4=new Question4();
        double emi=q4.EMI(100000.0,0.01167,36);
        System.out.println(emi);
    }
}
