package com.company;

import java.util.Arrays;

//MIHIR SAINI 9920102054 E2
public class ReversedString {
    public static void main(String[] args) {
        String str="I AM MIHIR SAINI";
        String[] s=str.split(" "); //splits the string str at points where there is an empty space
        String ans="";
        for(int i=s.length-1;i>=0;i--){
            ans+=s[i] + " " ;
        }
        System.out.println(ans);
    }

}
