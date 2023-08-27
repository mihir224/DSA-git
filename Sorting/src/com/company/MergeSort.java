package com.company;
import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] arr1={1,7,3,2,5,6};
        System.out.println(Arrays.toString(MergeSort(arr1)));
    }

    //using external arrays to store the divided left and right arrays
    static int[] MergeSort(int[] arr){ //to sort parts of the array we use copy of the two parts of array
        if(arr.length==1){
            return arr;
        }
        int mid=arr.length/2;
        //here we divide the array from start to mid-1 and mid to end. But in the inplace solution we do it from start till
        // mid and from mid+1 till end.
        int[] left=MergeSort(Arrays.copyOfRange(arr, 0, mid)); //Arrays.copyOfRange() copies the contents of an existing array into a
        //new array within the specified range
        int[] right=MergeSort(Arrays.copyOfRange(arr, mid, arr.length));
        return merge(left, right);
    }
   private static int[] merge(int[] left, int[] right) {
        int[] mergedArray = new int[left.length + right.length];
        int i = 0;
        int j = 0;
        int k = 0;
        int count=0;
        while (i < left.length && j < right.length) {
            if(left[i]==right[j]){
                count++;
            }
            if (left[i] < right[j]) {
                mergedArray[k] = left[i];
                i++;
            } else {
                mergedArray[k] = right[j];
                j++;
            }
            k++;
        }
        //case when one of the array is out of bounds and the other one is still running
        while (i < left.length) {
            mergedArray[k] = left[i]; //copying the remaining elements as it is
            i++;
            k++;

        }
        while (j < right.length) {
            mergedArray[k] = right[j];
            j++;
            k++;
        }
        return mergedArray;
    }

}
