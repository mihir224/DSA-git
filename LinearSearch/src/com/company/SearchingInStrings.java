package com.company;

import java.util.Arrays;

public class SearchingInStrings{
    public static void main(String[] args) {
        String str="MIHIR SAINI";
        System.out.println(stringSearch1(str, 'A'));
        System.out.println(stringSearch2(str, 'M'));
        System.out.println(Arrays.toString(str.toCharArray())); //Arrays.toString is used to print the elements of the char array inside square brackets separated by commas

    }
    public static boolean stringSearch1(String str, char target){ //method 1 for searching through a string: Using a normal for loop iteration
        if(str.length()==0) {
            return false;
        }
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)==target){
                return true;
            }
        }return false;
    }
    public static boolean stringSearch2(String str, char target){ //method 2 for searching through a string: Using a for-each loop iteration
        if(str.length()==0) {
            return false;
        }
        for(char ch : str.toCharArray()){ //example of a for-each loop in which we run an iteration of a char variable ch through a char array of strings
            if(ch==target) {
                return true;
            }
        }
       return false;
    }
}