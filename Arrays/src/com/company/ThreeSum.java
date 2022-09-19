package com.company;
//https://leetcode.com/problems/3sum/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> list=new ArrayList<List<Integer>>();
        for(int i=0;i<nums.length-2;i++){
            if(i==0||(i>0&&nums[i]!=nums[i-1])){
                int low=i+1;
                int hi=nums.length-1;
                int sum=0-nums[i];
                while(low<hi){
                    if(nums[low]+nums[hi]==sum){
                        list.add(List.of(nums[i],nums[low],nums[hi]));
                        while(low<hi&&nums[low]==nums[low+1]){
                            low++;
                        }
                        while(low<hi&&nums[hi]==nums[hi-1]){
                            hi--;
                        }
                        low++;
                        hi--;
                    }
                    else if(nums[low]+nums[hi]<sum)
                    {
                        low++;
                    }
                    else{
                        hi--;
                    }
                }

            }
        }
        return list;
    }
}