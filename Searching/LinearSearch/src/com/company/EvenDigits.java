package com.company;

public class EvenDigits {
    public static void main(String[] args) {
        int[] nums={555,901,482,1771};
        System.out.println(findNumbers(nums));
    }
    public static boolean even(int num){
        int count=findDigits(num);
        if(count%2==0){
            return true;
        }
        return false;
    }
    public static int findDigits(int num){
        int count=0;
        while(num>0){
            count++;
            num=num/10;
        }
        return count;
    }
    public static int findNumbers(int[] nums) {
        int count=0;
        for (int i=0;i<nums.length;i++){
            if(even(nums[i])){
                count++;
            }
        }
        return count;
    }
}
