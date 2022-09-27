package com.company;

import java.util.ArrayList;
import java.util.Collections;
public class BucketSort {
    static void bucketSort(float arr[], int n) {
        if(n<=0){
            return;
        }
        ArrayList<Float> [] buckets=new ArrayList[n]; //creating n empty buckets
        for(int i=0;i<n;i++){
            buckets[i]=new ArrayList<Float>(); //initialising an empty list in each bucket
        }
        for(int i=0;i<n;i++){
            float index=arr[i]*n; //we insert each element of arr at the floor of this index in the bucket list
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


