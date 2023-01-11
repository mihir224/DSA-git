package com.company;

public class LongestCommonPrefix {
    public static void main(String[] args) {
        String[] str={"MIH","MI","MIHIR"};
        String ans=longestCommonPrefix(str,str.length);
        if(ans.isEmpty()){
            System.out.println(-1);
        }
        else{
            System.out.println(ans);
        }

    }
    static int findMinLen(String[] str, int n)
    {
        int min=str[0].length();
        for(int i=0;i<str.length;i++){
            if(str[i].length()<min){
                min=str[i].length();
            }
        }
        return min;
    }
    static String longestCommonPrefix(String[] str, int n){
        int minLen=findMinLen(str, n);
        String result="";
        char current;
        for(int i=0;i<minLen;i++){
            current=str[0].charAt(i);
            for(int j=0;j<n;j++){
                if(str[j].charAt(i)!=current){
                    return result;
                }
            }
            result=result+current;
        }
        return result;
    }
}
