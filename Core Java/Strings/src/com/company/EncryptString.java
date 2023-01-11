package com.company;

import java.util.HashMap;
import java.util.Map;

//https://practice.geeksforgeeks.org/problems/encrypt-the-string-21117/1
public class EncryptString {
    public static void main(String[] args) {
        String str="aaaa";
        System.out.println(encryptString(str));
    }
    static String encryptString(String str){

        String ans="";
        char ch=str.charAt(0);

        int count=0;
        for(int i=1;i<str.length();i++){
            if(str.charAt(i)==str.charAt(i-1)){

                count++;
            }
            else{

            ans+=Integer.toHexString(count);
            }
        }

return ans;
    }
}
