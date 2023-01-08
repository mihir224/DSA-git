import java.util.ArrayList;
import java.util.List;

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

}
