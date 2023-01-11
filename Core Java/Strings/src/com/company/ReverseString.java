package com.company;

public class ReverseString {
    public static void main(String[] args) {
        String str="mihir";



    }
//    static boolean isRotated(String str){
//        StringBuilder sb=new StringBuilder(reverse(str));
//
//    }
    static String reverse(String str){
            String rev="";
        for(int i=str.length()-1;i>=0;i--){
            rev=rev+str.charAt(i);
        }
return rev;
    }
}
