package com.company;
//MIHIR SAINI 9920102054 E2
public class Question4 {
    public static int product1toN(int N){
        if(N==0){
            return 1;
        }
        else return (N*product1toN(N-1));
    }
}
