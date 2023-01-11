package com.company;
//MIHIR SAINI 9920102054 E2
public class Question4 {
    public double EMI(double p, double r, int n){
        double t=Math.pow((1+r),n);
        double m=p*(r*t/(t-1));
        return m;
    }
}
