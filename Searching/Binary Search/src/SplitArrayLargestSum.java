//https://leetcode.com/problems/split-array-largest-sum/

public class SplitArrayLargestSum {
   static int splitArray(int[] nums, int m){
       int start=0;
       int end=0;
       for(int i=0;i<nums.length;i++){
           start=Math.max(start, nums[i]); //this will contain the max item from the array
           end=end+nums[i];
           while(start<end){
               //try for middle as potential ans
               int mid=start+(end-start)/2;
               //calculating no of pieces we can divide the array into with mid as the max sum
               int sum=0;
               int pieces=1;
               for(int num:nums){
                   if(sum+num>mid){
                       //we can't add this in our sub array
                       sum= num;
                       pieces++;
                   }
                   else{
                       sum+=num;
                   }
               }
               if(pieces>m){
                   start=mid+1;
               }
                else{
                    end=mid;
               }
           }
       }
       return end; //here start==end
   }
}
