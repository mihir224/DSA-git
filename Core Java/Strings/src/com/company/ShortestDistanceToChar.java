package com.company;

import java.util.Arrays;

public class ShortestDistanceToChar {
    public static void main(String[] args) {


    }
    static int[] shortestToChar(String str, char ch){
        int n=str.length();
        int[] ans=new int[n];
        int[] movingRight=new int[n];
        int[] movingLeft=new int[n];
        Arrays.fill(movingRight,Integer.MAX_VALUE); //fills all the elements of the array with the specified value
        Arrays.fill(movingLeft, Integer.MAX_VALUE);
        int index=Integer.MAX_VALUE;
        for(int i=0;i<n;i++){ //moving left
            if(str.charAt(i)==ch){
                index=0;
                movingRight[i]=index;
            }
            else{
                if(index!=Integer.MAX_VALUE){ //to make sure that value of index has been changed  else it will give an error
                    index++;
                    movingRight[i]=index;
                }
            }
        }
        for(int i=n-1;i>=0;i--){
            if(str.charAt(i)==ch){
                index=0;
                movingLeft[i]=index;
            }
            else{
                if(index!=Integer.MAX_VALUE){
                    index++;
                    movingLeft[i]=index;
                }
            }
        }
        for(int i=0;i<n;i++){
            ans[i]=Math.min(movingRight[i],movingLeft[i]);
        }
        return ans;
    }
}
