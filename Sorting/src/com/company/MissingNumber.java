package com.company;
//https://leetcode.com/problems/missing-number/
public class MissingNumber {
    public static void main(String[] args) {
        int[] arr={4,0,1,2};
        System.out.println(missingNum(arr));
    }
    public static int missingNum(int[] arr){
        int i=0;
        while(i<arr.length){
            int correctIndex=arr[i];
            if(arr[i]<arr.length&&arr[i]!=arr[correctIndex]){
                int temp=arr[i];
                arr[i]=arr[correctIndex];
                arr[correctIndex]=temp;
            }
            else{
                i++;
            }
        }
        for(int index=0; index<=arr.length;index++){
            if(arr[index]!=index){
                return index;
            }
        }
        return arr.length;

    }
}
