package com.company;

import java.util.ArrayList;
import java.util.Iterator;

public class Palindrome {
    public static void main(String[] args) {
        String str="cbbcc";
        System.out.println(validPalindrome(str));
    }
    static boolean validPalindrome(String str){
        int i=0;
        int j=str.length()-1;
        while(i<j){
            if(str.charAt(i)==str.charAt(j)){
                i++;
                j--;
            }
            else{
                return isPalindrome(str, i+1, j)||isPalindrome(str,i,j-1);
            }
        }
        return true;
    }
    static boolean isPalindrome(String str, int i, int j){
        while(i<j){
            if(str.charAt(i)==str.charAt(j)){
                i++;
                j--;
            }
            else{
                return false;
            }
        }
        return true;
    }
//    static boolean isPalindrome(String str){
//        ArrayList<Character> list=new ArrayList<>();
//        for(int i=0;i<str.length();i++){
//            list.add(str.charAt(i));
//        }
//       for(int i=0;i<list.size()/2;i++){
//           char start= list.get(i);
//           char end=list.get(list.size()-i-1);
//           if(start!=end){
//               return false;
//           }
//       }
//       return true;
//    }
//
//    static boolean validPalindrome(String str){
//        ArrayList<Character> list=new ArrayList<>();
//        for(int i=0;i<str.length();i++){
//            list.add(str.charAt(i));
//        }
//        for(int i=0;i< list.size();i++){
//            if(!isPalindrome(str)){
//                list.remove(i);
//            }
//            else{
//                return true;
//            }
//        }
//        return false;
//    }
//    static void reverse(int[] arr, int x, int y){
//        int low=x;
//        int high=y;
//        while(low<high){
//            int temp=arr[low];
//            arr[low]=arr[high];
//            arr[high]=temp;
//        }
//    }
//    public static boolean isPalindrome(String str){
//        for(int i=0;i<str.length()/2;i++){
//            char start=str.charAt(i);
//            char end=str.charAt(str.length()-i-1);
//            if(start!=end){
//                return false;
//            }
//        }
//        return true;
//    }
}
