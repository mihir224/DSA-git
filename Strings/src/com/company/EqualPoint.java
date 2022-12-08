package com.company;

public class EqualPoint {
    public static void main(String[] args) {

    }
    static long countSub(String str){
        int left=0;
        int right=0;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='('){
                left++;
            }
        }
        if(left==0){
            return str.length();
        }
        for(int i=str.length()-1;i>=0;i--){
            if(str.charAt(i)=='('){
                left--;
            }
            if(str.charAt(i)==')'){
                right--;
            }
            if(left==right){
                return i;
            }
        }
        return -1;
    }
}
