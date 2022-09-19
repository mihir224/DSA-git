package com.company;
//https://leetcode.com/problems/first-missing-positive/
class FirstMissingPositive {
    public int firstMissingPositive(int[] nums) {
        int i=0;
        while(i<nums.length){
            int correctIndex=nums[i]-1;
            if(nums[i]>0&&nums[i]<=nums.length&&nums[i]!=nums[correctIndex]){ //since range is not given and we want a
                //positive number
                int temp=nums[i];
                nums[i]=nums[correctIndex];
                nums[correctIndex]=temp;
            }
            else {
                i++;
            }
        }
        for (int index=0;index<nums.length;index++){
            if(nums[index]!=index+1){
                return index+1;
            }

        }
        return nums.length+1;
    }
}