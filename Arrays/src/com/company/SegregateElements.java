package com.company;

import java.util.Arrays;
//https://practice.geeksforgeeks.org/problems/move-all-negative-elements-to-end1813/1
class SegregateElements {
    public static void main(String[] args) {
        int[] arr={-3,2,-1,9,5};
        segregateElements(arr, arr.length);
        System.out.println(Arrays.toString(arr));
    }
    public static void segregateElements(int arr[], int n)
    {
        int[] nums=new int[n];
        int index=0;
        for(int i=0;i<n;i++){
            if(arr[i]>=0){ //adding all positive numbers first
                nums[index]=arr[i];
                index++;
            }
        }
        for(int i=0;i<n;i++){
            if(arr[i]<0){
                nums[index]=arr[i];
                index++;
            }
        }
        int i=0;
        for(int el:nums){ //This for loop states that for every element el in the array nums..
            arr[i]=el;
            i++;
        }
    }
}