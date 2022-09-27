package com.company;

public class CountSort {
    public static void main(String[] args) {
        int[] arr={9,6,3,5,3,4,3,9,6,4,6,5,8,9,9};
        countSort(arr,9,3);
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]);
        }
    }
    public static void countSort(int[] arr, int max, int min){
        int range=max+min-1;
        int[] freqArray=new int[range];
        int[] ans=new int[arr.length];
        for(int i=0;i<arr.length;i++){
            int index=arr[i]-min; //to find the index of each element in the frequency array
            freqArray[index]++; //updating frequency by 1 whenever we encounter an element
        }
        for(int i=1;i<freqArray.length;i++){
            freqArray[i]=freqArray[i]+freqArray[i-1]; //converting freq array to prefix sum array
        }
        for(int i=arr.length-1;i>=0;i--){
            int val=arr[i];
            int position=freqArray[val-min]; //to obtain the position of each element from prefix sum
            int index=position-1; //index of each element in the sorted array=position-1
            ans[index]=val;
            freqArray[val-min]--; //decrementing position of an element by 1 whenever we encounter it
        }
        for(int i=0;i<arr.length;i++){
            arr[i]=ans[i];
        }
    }

}
