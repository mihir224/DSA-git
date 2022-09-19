package com.company;
//https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/
import java.util.ArrayList;
import java.util.List;

class DisappearingElements {
    public List<Integer> findDisappearedNumbers(int[] nums) {
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
        List<Integer> list=new ArrayList<>();
        for (int index=0;index<nums.length;index++){
            if(nums[index]!=index+1){
                list.add(index+1);
            }
        }
        return list;
    }
}