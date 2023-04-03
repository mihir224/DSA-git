import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecursionBacktracking {
    //subset sum
    //https://leetcode.com/problems/subsets/
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans=new ArrayList<>();
        List<Integer> list=new ArrayList<>();
        helper(0,nums,list,ans);
        return ans;
    }
    public void helper(int i, int[] nums, List<Integer> list,List<List<Integer>> ans){
        if(i>=nums.length) {
            ans.add(new ArrayList<>(list));
            return;
        }
        list.add(nums[i]);
        helper(i+1,nums,list,ans); //pick
        list.remove(list.size()-1);
        helper(i+1,nums,list,ans); //not pick
    }

    //combination sum
    //https://leetcode.com/problems/combination-sum/

    //O((2^t)*k) - where t is target and k is complexity for the insertions and deletions in a list of size k
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans=new ArrayList<>();
        List<Integer> list=new ArrayList<>();
        solve(0,target,candidates,list,ans);
        return ans;
    }
    public void solve(int i, int target,int[] candidates,List<Integer> list,List<List<Integer>> ans){
        if(i==candidates.length){
            if(target==0){
                ans.add(new ArrayList<>(list));
            }
            return;
        }
        if(candidates[i]<=target){
            list.add(candidates[i]);
            solve(i,target-candidates[i],candidates,list,ans);
            list.remove(list.size()-1);
        }
        solve(i+1,target,candidates,list,ans);
    }

    //combination sum-II
    //https://leetcode.com/problems/combination-sum-ii/
    //O((2^t)*k) - where t is target and k is complexity for the insertions and deletions in a list of size k
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans=new ArrayList<>();
        Arrays.sort(candidates);
        solve2(0,target,candidates,new ArrayList<Integer>(),ans);
        return ans;
    }
    public void solve2(int i,int target,int[] candidates,List<Integer> list,List<List<Integer>> ans){
        if(target==0){
            ans.add(new ArrayList<>(list));
            return;
        }
        for(int it=i;it<candidates.length;it++){
            if(it>i&&candidates[it]==candidates[it-1]){
                continue;
            }
            if(candidates[it]>target){
                break;
            }
            list.add(candidates[it]);
            solve2(it+1,target-candidates[it],candidates,list,ans);
            list.remove(list.size()-1);
        }
    }
}
