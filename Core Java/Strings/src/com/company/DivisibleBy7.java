package com.company;

public class DivisibleBy7 {
    public static void main(String[] args) {
       String str="8955795758";

        System.out.println(isDivisible(str));
    }
    static int isDivisible(String str){
        int rem=0;
        for(int i=0;i<str.length();i++){

            int temp =str.charAt(i)-'0';
            rem=((rem*10)+temp)%7;
        }
        if(rem==0){
            return 1;
        }
        else{
            return 0;
        }
    }
}
