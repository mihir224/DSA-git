package com.company;
//https://leetcode.com/problems/find-the-duplicate-number/
import java.util.ArrayList;
import java.util.List;

class DuplicateElement {
    public static void main(String[] args) {
        int[] arr={5,3,1,2,6,9,4,9};
        System.out.println(findDuplicate(arr));
    }
    public static int findDuplicate(int[] nums) {
        int i=0;
        ArrayList<Integer> list=new ArrayList<>();
        while(i<nums.length){
            int correctIndex=nums[i]-1;
            if(nums[i]!=i+1){  //extra check
                if(nums[i]<nums.length&&nums[i]!=nums[correctIndex]){
                    int temp=nums[i];
                    nums[i]=nums[correctIndex];
                    nums[correctIndex]=temp;
                }
                else{ //case when nums[i]==nums[correctIndex] but nums[i]!=i+1
                   return nums[i];

                }
            }
            else{
                i++;
            }
        }
        return -1;
    }
}