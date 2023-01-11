package com.company;

import java.util.Arrays;

public class TwoPointers {
    public static void main(String[] args) {
        int[] arr={4,2,1,3,9};
        Arrays.sort(arr);

    }
     static boolean twoSum(int[] arr, int target){
        int i=0;
        int j=arr.length-1;
        while(i<j){
            if(arr[i]+arr[j]<target){ //two sum
                i++;
            }
            else if(arr[i]+arr[j]>target){
                j--;
            }
            else {return true;}
        }
        return false;
     }
}
