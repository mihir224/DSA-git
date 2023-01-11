package com.company;
//MIHIR SAINI 9920102054 E2
public class Question3 {
    public void roots(double a,double b,double c){
        double m=Math.sqrt(b*b-4*a*c);
        double p=(-b+m)/(2*a);
        double q=(-b-m)/(2*a);
        if(m<0){
            m=m*(-1);
            System.out.println("Roots are imaginary: " + p + " , " + q + " i ");
        }
        else if(m==0){
            System.out.println("Roots are real and equal: " + p);
        }
        else System.out.println("Roots are real and different: " + p + " , " + q);

    }
}
