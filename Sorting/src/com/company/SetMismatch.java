package com.company;
//https://leetcode.com/problems/set-mismatch/
public class SetMismatch {
        public int[] findErrorNums(int[] nums) {
            int i=0;
            while(i<nums.length){
                int correctIndex=nums[i]-1;
                if(nums[i]!=nums[correctIndex]){
                    int temp=nums[i];
                    nums[i]=nums[correctIndex];
                    nums[correctIndex]=temp;
                }
                else{
                    i++;
                }
            }
            for(int index=0;index<nums.length;index++){
                if(nums[index]!=index+1){
                    return new int[]{nums[index], index+1};
                }
            }
            return new int[]{-1};
        }
    }

