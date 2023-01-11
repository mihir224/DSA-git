package com.company;

public class MinNumber {
    public static void main(String[] args) {
        int[] arr={4,5,3,1,2,5,7,19};
        System.out.println("The minimum number in the array is: " + minNumber(arr));
    }
    public static int minNumber(int[] arr)
    {
        int temp=arr[0];
        for(int i=1;i<arr.length;i++){
            if(arr[i]<temp){
                temp=arr[i];
            }
        }
        return temp;
    }
}
