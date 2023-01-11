package com.company;

public class MaxWealth {
    public static void main(String[] args) {
        int[][] arr={
                {1,3,4},
                {3,1,6},
                {4,2,6}
        };
        System.out.println("The maximum wealth is: " + maxWealth(arr));
    }
    public static int maxWealth(int[][] arr){
        int temp=Integer.MIN_VALUE;
        for(int row=0;row<arr.length;row++) {
            int sum=0;
            for (int col = 0; col < arr[row].length; col++) {
                sum += arr[row][col];
            }
            if(sum>temp){
                temp=sum;
            }
        }
        return temp;
    }
}
