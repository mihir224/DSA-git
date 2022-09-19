package com.company;
//9920102054 MIHIR SAINI E2
import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] arr1={1,2,3,2,5,6};

        System.out.println(Arrays.toString(MergeSort(arr1)));
    }
    static int[] MergeSort(int[] arr){ //to sort parts of the array we use copy of the two parts of array
        if(arr.length==1){
            return arr;
        }
        int mid=arr.length/2;
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
    static void mergeSortInPlace(int[] arr, int start, int end){ //merge sort without dividing the array in two parts
        if(end-start==1){ //base condition
            return;
        }
        int mid=(start+end)/2;
        mergeSortInPlace(arr,start,mid); //to sort parts of the array we use indices like start and end
        mergeSortInPlace(arr,mid, end);
        mergeInPlace(arr,start,mid,end);
    }
    private static void mergeInPlace(int[] arr, int start, int mid, int end){
        int [] mergedArray=new int[end-start];
        int i=start;
        int j=mid;
        int k=0;
        while(i<mid&&j<end){
            if(arr[i]<arr[j]){
                mergedArray[k]=arr[i];
                i++;
            }
            else{
               mergedArray[k]=arr[j];
               j++;
            }
            k++;
        }
        while (i < mid) {
            mergedArray[k] =arr[i]; //copying the remaining elements as it is
            i++;
            k++;
            ;
        }
        while (j < end) {
            mergedArray[k] = arr[j];
            j++;
            k++;
        }
        for(int l=0;l<mergedArray.length;l++){
            arr[start+l]=mergedArray[l];  //this puts the sorted elements from wherever the start index is
        }
    }

}
