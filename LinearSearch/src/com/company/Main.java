package com.company;

public class Main {

    public static void main(String[] args) {
	int[] arr={44,55,2,5,321,6,890,65}; //By declaring an array like this, we don't need to use the new keyword as the array object is created automatically
        int index=linearSearch(arr, 55);
        System.out.println(index);
    }
    public static int linearSearch(int[] arr, int target){
        if(arr.length==0){
            return -1;
        }
        for(int i=0;i<arr.length;i++){
            if(arr[i]==target){
                return i;
            }

        }
        return -1;
    }
}
