package com.company;
//https://practice.geeksforgeeks.org/problems/reverse-words-in-a-given-string5459/1
public class ReverseWordsGFG {
    public static void main(String[] args) {
    String str="mihir.saini";

        System.out.println(reverseWords(str));
    }
    static String reverseWords(String str){

        int j=str.length()-1;
        String ans="";
        for(int i=str.length()-1;i>=0;i--){
            if(str.charAt(i)=='.'){
            ans=ans.concat(str.substring(i+1,j+1));
            ans=ans.concat(".");
            j=i-1;
            }
            else if(i==0){
                ans=ans.concat(str.substring(i, j+1));
            }
        }
        return ans;
    }

}
