package com.company;

import java.util.Arrays;

public class SearchingIn2dArray {
    public static void main(String[] args) {
        int[][] arr={
                {1,4,5},
                {9,2,6,55,132},
                {7,6,14,13}
        };
        System.out.println("The target element is at index position: " + Arrays.toString(search1(arr, 55)));
        System.out.println("The maximum element of the array is " + search2(arr));
    }
    public static int[] search1(int[][] arr, int target){
        for(int row=0;row<arr.length;row++){
            for(int col=0;col<arr[row].length;col++){ //arr[row] returns the inner array at the specified position. For eg: for row=0, arr[row] returns the row at arr[0]
               if(arr[row][col]==target){
                   return new int[] {row, col};
               }
            }
        }
        return new int[] {-1,-1};
    }
    public static int search2(int[][] arr){ //for finding max element in a 2d array
        int max=Integer.MIN_VALUE;
        for(int row=0;row<arr.length;row++){
            for(int col=0;col<arr[row].length;col++){ //arr[row] returns the inner array at the specified position. For eg: for row=0, arr[row] returns the row at arr[0]
                if(arr[row][col]>max){
                    max=arr[row][col];
                }
            }
        }
        return max;
    }
}
