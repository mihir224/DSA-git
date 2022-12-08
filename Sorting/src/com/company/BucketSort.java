package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
public class BucketSort {
    public static void main(String[] args) {
        double[] arr={0.7,0.2,0.3,0.1,0.6,0.4,0.5};
        bucketSort(arr,arr.length);
        System.out.println(Arrays.toString(arr));
    }
    static void bucketSort(double arr[], int n) {
        if(n<=0){
            return;
        }
        ArrayList<Double> [] buckets=new ArrayList[n]; //creating n empty buckets
        for(int i=0;i<n;i++){
            buckets[i]=new ArrayList<Double>(); //initialising an empty list in each bucket
        }
        for(int i=0;i<n;i++){
            double index=arr[i]*n; //we insert each element of arr at the floor of this index in the bucket list
            buckets[(int) index].add(arr[i]);
        }
        for(int i=0;i<n;i++){
            Collections.sort(buckets[i]); //sorting individual elements of all buckets
        }
        int index=0; //index of original array
        for(int i=0;i<n;i++){ //inserting each element of every bucket into the original array
            for(int j=0;j<buckets[i].size();j++) {
                arr[index]=buckets[i].get(j);
                index++;
            }
        }
    }
}


