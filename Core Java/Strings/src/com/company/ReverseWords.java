package com.company;

//https://leetcode.com/problems/reverse-words-in-a-string/submissions/
import java.util.ArrayList;
import java.util.Arrays;

public class ReverseWords {
    public static void main(String[] args) {
        String str="  hello world ";
        System.out.println(reverseWordS(str));

    }
    static String reverseWordS(String str){

//        String ans=" ";
//        int i=0;
//        int n=str.length();
//        while(i<n){
//            while(i<n&&str.charAt(i)==' ')i++;
//            if(i>=n){
//                break;
//            }
//
//            int j=i+1;
//            while(j<n&&str.charAt(j)!=' ')j++;
//            String temp=str.substring(i, j-i);
//            if(ans.isEmpty()){
//                ans=temp;
//            }
//            else {
//                ans=temp + " " + ans;
//
//            }
//            i=j+1;
//
//        }
//        return ans;

        StringBuilder ans=new StringBuilder();
        int i=str.length()-1;
         while(i>=0){
             if(i<0){
                 break;
             }
              while(i>=0&&str.charAt(i)==' ')i--;
                int j=i;
              while(i>=0&&str.charAt(i)!=' ')i--;
              if(ans.length()==0){
                  ans=ans.append(str.substring(i+1, j+1));
              }
              else{
                  ans=ans.append(" " + str.substring(i+1, j+1));
              }
         }
         return ans.toString();
    }

}
