import java.util.*;

public class Questions {
    //combination sum
    //https://leetcode.com/problems/combination-sum/
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans=new ArrayList<>();
        helper(0,candidates,target,ans,new ArrayList<>());
        return ans;
    }
    public void helper(int i, int[] candidates, int target, List<List<Integer>> ans, List<Integer> list){
        if(i==candidates.length){
            if(target==0){
                ans.add(new ArrayList<>(list));
            }
            return;
        }
        if(candidates[i]<=target){
            list.add(candidates[i]);
            helper(i,candidates,target-candidates[i],ans,list);
            list.remove(list.size()-1);
        }
        helper(i+1,candidates,target,ans,list);
    }

    //combination sum 2
    //https://leetcode.com/problems/combination-sum-ii/
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<Integer> list=new ArrayList<>();
        List<List<Integer>> ans=new ArrayList<>();
        Arrays.sort(candidates);
        helper2(0,candidates,target,list,ans);
        return ans;
    }
    public void helper2(int currentIndex, int[] candidates, int target, List<Integer> list, List<List<Integer>> ans){
        if(target==0){
            ans.add(new ArrayList<>(list));
            return;
        }
        for(int i=0;i<candidates.length;i++){
            //case 1
            if(i>currentIndex&&candidates[i]==candidates[i-1]){ //check for duplicates
                continue;
            }
            //case 2
            if(candidates[i]>target){
                break;
            }
            //case 3 (the one that leads us to possible solution)
            list.add(candidates[i]);
            helper2(i+1,candidates,target-candidates[i],list,ans);
            list.remove(list.size()-1);
        }
    }

    //subset sum
    //https://practice.geeksforgeeks.org/problems/subset-sums2234/1
    ArrayList<Integer> subsetSums(ArrayList<Integer> arr, int N){
        ArrayList<Integer> ans=new ArrayList<>();
        helper3(0,0,arr,N,ans);
        Collections.sort(ans);
        return ans;
    }
    public void helper3(int i, int sum, ArrayList<Integer> arr, int n, ArrayList<Integer> ans){
        if(i==n){ //found a sum
            ans.add(sum);
        }
        helper3(i+1,sum+arr.get(i),arr,n,ans); //picked
        helper3(i+1,sum,arr,n,ans); //not picked
    }

    //subsets
    //https://leetcode.com/problems/subsets/
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans=new ArrayList<>();
        List<Integer> list=new ArrayList<>();
        helper4(0,list,nums,ans);
        return ans;
    }
    public void helper4(int i,List<Integer> list, int[] nums,List<List<Integer>> ans){
        if(i>= nums.length){
            ans.add(new ArrayList<>(list));
            return;
        }
        list.add(nums[i]); //pick
        helper4(i+1,list,nums,ans);
        list.remove(list.size()-1); //not pick
        helper4(i+1,list,nums,ans);
    }
}
