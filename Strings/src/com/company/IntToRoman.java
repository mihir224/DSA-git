package com.company;
//https://leetcode.com/problems/integer-to-roman/
import java.util.HashMap;
import java.util.Map;

public class IntToRoman {
    public String intToRoman(int num) {
        int[] arr={1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] str={"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<arr.length;i++){
            while(num>=arr[i]){
                sb.append(str[i]);
                num-=arr[i];
            }
        }
        return sb.toString();
    }


}
