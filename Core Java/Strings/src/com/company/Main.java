package com.company;

public class Main {

    public static void main(String[] args) {
	//byte
        //short
        //int
        //char
        //boolean
        //long
        //double
        //float
        String[] str=new String[20];




    }
//    static int test(String str, String target){
//        int[] arr=new int[26];
//        for(char ch:str.toCharArray()){
//            ++arr[ch-'a'];
//        }
//    }
    public static int test(String s, String target) {
        int ans=s.length();
        int[] strCount=new int[26];
        int[] targetCount=new int[26];
        for(char ch:s.toCharArray()){
            ++strCount[ch-'a'];
        }
        for(char ch:target.toCharArray()){
            ++targetCount[ch-'a'];
        }
        for(char ch:target.toCharArray()){
            ans=Math.min(ans, strCount[ch-'a']/targetCount[ch-'a']);
        }
        return ans;
    }
}
