package com.company;

public class SearchInRange {
    public static void main(String[] args) {
        int[] arr={3,5,3,21,6,78,7323,466,643};
        System.out.println("The index of given target is: " + SearchInRange(arr, 78, 3, 8));
    }
    public static int SearchInRange(int[] arr, int target, int start, int end){
        if(arr.length==0){
            return -1;
        }
        for(int i=start;i<end;i++){
            if(arr[i]==target){
                return i;
            }
        }
        return -1;
    }
}
