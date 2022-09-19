package com.company;

import java.util.Arrays;

public class CyclicSort {
    public static void main(String[] args) {
        int[] arr={5,4,3,2,1};
        sortArray(arr);
        System.out.println(Arrays.toString(arr));
    }
    public static void sortArray(int[]arr){
        int i=0;
        while(i<arr.length){
            int correctIndex=arr[i]-1;
            if(arr[i]!=arr[correctIndex]){
                swap(arr, i, correctIndex);
            }
            else {
                i++;
            }
        }
    }
    public static void swap(int[] arr, int firstNum, int secondNum){
        int temp=arr[firstNum];
        arr[firstNum]=arr[secondNum];
        arr[secondNum]=temp;
    }
}
