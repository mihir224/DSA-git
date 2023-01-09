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

}
