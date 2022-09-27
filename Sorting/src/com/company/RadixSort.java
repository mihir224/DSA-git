package com.company;

public class RadixSort {
    public static void main(String[] args) {
        int[] arr={267,358,294};
        radixSort(arr);
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]);
        }
    }
    public static void radixSort(int[] arr){
        int max=Integer.MIN_VALUE; //to store the max value in the array
        for(int val:arr){
            if(val>max){
                max=val;
            }
        }
        int exp=1; //exponent for handling powers of 10 while calculating number of digits
        while(exp<=max){ //sorting till we reach the max value in the array
            countSort(arr,exp);
            exp=exp*10;
        }
    }
    public static void countSort(int[] arr, int exp){
        int range=10; //since each digit has a range of 0 to 9
        int[] freqArray=new int[range];
        int[] ans=new int[arr.length];
        for(int i=0;i<arr.length;i++){
            int index=arr[i]; //to find the index of each element in the frequency array
            freqArray[(index/exp)%10]++; //updating frequency by 1 whenever we encounter an element
        }
        for(int i=1;i<freqArray.length;i++){
            freqArray[i]=freqArray[i]+freqArray[i-1]; //converting freq array to prefix sum array
        }
        for(int i=arr.length-1;i>=0;i--){
            int val=arr[i];
            int position=freqArray[(arr[i]/exp)%10]; //to obtain the position of each element from prefix sum
            int index=position-1; //index of each element in the sorted array=position-1
            ans[index]=val;
            freqArray[(arr[i]/exp)%10]--; //decrementing position of an element by 1 whenever we encounter it
        }
        for(int i=0;i<arr.length;i++){
            arr[i]=ans[i];
        }
    }
}
