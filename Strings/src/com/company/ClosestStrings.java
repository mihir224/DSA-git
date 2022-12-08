package com.company;
//https://practice.geeksforgeeks.org/problems/closest-strings0611/1

import java.util.ArrayList;

public class ClosestStrings {
    public static void main(String[] args) {
        ArrayList<String> list=new ArrayList<>();
        list.add("put");
        list.add("get");
        list.add("since");
        System.out.println(shortestDistance(list, "put","since"));
    }
static int shortestDistance(ArrayList<String> list, String word1, String word2){
    if(word1.equals(word2)){
        return 0;
    }
    int index1=0;
    int index2=0;
    int index=0;
    int ans=Integer.MAX_VALUE;
    for(String str:list){
        if(str.equals(word1)){
            index1=index;
        }
        else if(str.equals(word2)) {
            index2 = index;
        }
        if(index1!=Integer.MAX_VALUE&&index2!=Integer.MAX_VALUE){
        ans=Math.abs(index1-index2);
        }
        index++;

    }
    return ans;
}

}
