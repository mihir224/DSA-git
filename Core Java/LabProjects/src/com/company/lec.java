package com.company;

import java.util.Scanner;
class ImaginaryException extends Exception{
    public ImaginaryException(String s){
        super(s);
    }
}
public class lec {
   private double a;
   private double b;
   private double c;

    public lec(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.println("Enter the coefficients of the quadratic equation: ");
        double a=scanner.nextInt();
        double b=scanner.nextInt();
        double c=scanner.nextInt();
        lec l=new lec(a,b,c);
        l.printEq();


    }

    public void printEq()
    {
        double d=Math.pow(this.b,2)-(4*this.a*this.c);
        if(d>=0){
            double root1=(-this.b+Math.sqrt(d))/(2*a);
            double root2=(-this.b-Math.sqrt(d))/(2*a);
            System.out.println("Root 1: " + root1);
            System.out.println("Root 2: " + root2 );
        }
        else{
            try{
                throw new ImaginaryException("The solution is not real");
            }
            catch(ImaginaryException e){
                System.out.println(e.getMessage());
            }
        }
    }



}
