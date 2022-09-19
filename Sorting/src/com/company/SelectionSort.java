package com.company;

import java.util.Arrays;

public class SelectionSort {
    public static void main(String[] args) {
        int[] arr={3,1,5,4,2};
        sortArray(arr);
        System.out.println(Arrays.toString(arr));
    }
    public static void sortArray(int[] arr){
        for(int i=0;i<arr.length;i++) {
            int last = arr.length - i - 1;
            int maxIndex=getMaxIndex(arr, 0, last); //index of largest element
            swap(arr, maxIndex, last);
        }
    }
    public static int getMaxIndex(int[] arr, int start, int end){
        int max=start;
        for(int i=start;i<=end;i++){
            if(arr[max]<arr[i]){
                max=i; //To obtain the index of the largest element
            }
        }
        return max;
    }
    public static void swap(int[] arr, int first, int second){
            int temp=arr[first];
            arr[first]=arr[second];
            arr[second]=temp;
    }
}
