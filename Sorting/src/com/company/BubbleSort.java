package com.company;

import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
        int [] arr={5,4,3,2,1};
        System.out.println(Arrays.toString(sortArray(arr)));
    }
    public static int[] sortArray(int[] arr){
        boolean flag=true;
        while(flag){
            flag=false;
            for(int i=0;i<arr.length;i++){
                for(int j=1;j<arr.length-i;j++){
                    if(arr[j]<arr[j-1]){
                        int temp=arr[j];
                        arr[j]=arr[j-1];
                        arr[j-1]=temp;
                        flag=true;
                    }
                }
            }
        }
        return arr;
    }
}
