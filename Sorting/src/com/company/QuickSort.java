package com.company;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
    int[] arr={4,1,3,9,7};
        quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
    static void quickSort(int[] arr, int low, int hi){
        if(low>=hi){
            return;
        }
        int start=low;
        int end=hi;
        int mid=start+(end-start)/2;
        int pivot= (int) Math.random()*9;
        while(start<=end){
            //if already sorted it won't swap
            while(arr[start]<pivot){
                start++;
            }while(arr[end]>pivot){
                end--;
            }
            if(start<=end){
                int temp=arr[start];
                arr[start]=arr[end];
                arr[end]=temp;
                start++;
                end--;
            }
        }

        //pivot is now at its correct position
        quickSort(arr,low, end);
        quickSort(arr,start,hi);
    }
}
