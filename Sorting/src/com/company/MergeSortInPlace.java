package com.company;

import java.util.ArrayList;
import java.util.List;

public class MergeSortInPlace{
    void mergeSort(int arr[], int start, int end)
    {
        if(start>=end){
            return;
        }
        int mid=(start+end)/2;
        mergeSort(arr,start,mid);
        mergeSort(arr,mid+1,end);
        merge(arr,start,mid,end);
    }
    void merge(int arr[], int start, int mid, int end)
    {
        int left=start;
        int right=mid+1;
        List<Integer> mergedList=new ArrayList<>();
        while(left<=mid&&right<=end){
            if(arr[left]<arr[right]){
                mergedList.add(arr[left]);
                left++;
            }
            else{
                mergedList.add(arr[right]);
                right++;
            }
        }
        while(left<=mid){
            mergedList.add(arr[left]);
            left++;
        }
        while(right<=end){
            mergedList.add(arr[right]);
            right++;
        }
        for(int i=start;i<=end;i++){
            arr[i]=mergedList.get(i-start);
        }
    }

}