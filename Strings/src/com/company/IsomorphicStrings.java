package com.company;

import java.util.HashMap;
import java.util.Map;

public class IsomorphicStrings {
    public static void main(String[] args) {
        String str1="foo";
        String str2="bar";
        System.out.println(areIsomorphic(str1,str2));
    }
    static boolean areIsomorphic(String str1, String str2){
        //initialising two empty(all elements have zero value) arrays to store the frequency of each character
        Map<Character, Character> map=new HashMap<>();
        if(str1.length()!=str2.length()){
            return false;

        }
        for(int i=0;i< str1.length();i++){
            char ch1=str1.charAt(i);
            char ch2=str2.charAt(i);
            if(map.containsKey(ch1)){
                if(map.get(ch1)!=ch2){
                    return false;
                }
            }
            else if(map.containsValue(ch2)){
                return false;
            }
            else{
                map.put(ch1, ch2);
            }
        }
        return true;
    }
}
