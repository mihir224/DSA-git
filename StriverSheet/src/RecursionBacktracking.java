import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecursionBacktracking {
    //subset sum
    //https://leetcode.com/problems/subsets/
    //O(2^n=number of subsets)
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

    //subset sum-II
    //https://leetcode.com/problems/subsets-ii/
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        helper2(0, nums, new ArrayList<>(), ans);
        return ans;
    }
    public void helper2(int currentIndex, int[] nums, List<Integer> list, List<List<Integer>> ans) {
        ans.add(new ArrayList<>(list));
        for (int i = currentIndex; i < nums.length; i++) { //this for loop will take care of base condition ie when i exceeds n
            if (i > currentIndex && nums[i] == nums[i - 1]) { //skipping duplicates
                continue;
            }
            list.add(nums[i]);
            helper(i + 1, nums, list, ans);
            list.remove(nums[i]);
        }
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

    //palindrome partitioning
    //https://leetcode.com/problems/palindrome-partitioning/
    public List<List<String>> partition(String s) {
        List<List<String>> ans=new ArrayList<>();
        helper3(0,s,new ArrayList<>(),ans);
        return ans;
    }
    public void helper3(int currentIndex,String s,List<String> list,List<List<String>> ans){
        if(currentIndex==s.length()){ //partition at the end
            ans.add(new ArrayList<>(list));
            return;
        }
        for(int i=currentIndex;i<s.length();i++){
            if(isPalindrome(currentIndex,i,s)){
                list.add(s.substring(currentIndex,i+1));
                helper3(currentIndex+1,s,list,ans);
                list.remove(list.size()-1);
            }
        }
    }
    public boolean isPalindrome(int i, int j, String s){
        while(i<j){
            if(s.charAt(i)==s.charAt(j)){
                i++;
                j--;
            }
            else{
                return false;
            }
        }
        return true;
    }

    //kth permutation sequence
    //https://leetcode.com/problems/permutation-sequence/
    //(O(N2) - To calculate all n digits of the kth permutation sequence and for each digit we are reducing the list
    // size where each deletion operation takes O(N) tine)

    public String getPermutation(int n, int k) {
        String ans="";
        List<Integer> list=new ArrayList<>();
        int fact=1;
        for(int i=1;i<n;i++){
            fact*=i;
            list.add(i);
        }
        list.add(n);
        k=k-1; //k=kth index
        while(true){
            ans+=list.get(k/fact);
            list.remove(k/fact); //removing the 1st element of the permutation sequence from the list
            if(list.size()==0){ //all elements of kth permutation sequence have been obtained
                break;
            }
            k=k%fact;
            fact=fact/list.size();
        }
        return ans;
    }

    //print all permutations
    //https://leetcode.com/problems/permutations/
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans=new ArrayList<>();
        boolean[] vis=new boolean[nums.length];
        helper4(new ArrayList<>(),ans,vis,nums);
        return ans;
    }
    public void helper4(List<Integer> list, List<List<Integer>> ans,boolean[] vis,int[] nums){
        if(list.size()==nums.length){
            ans.add(new ArrayList<>(list));
            return;
        }
        for(int i=0;i<nums.length;i++){
            if(!vis[i]){
                vis[i]=true;
                list.add(nums[i]);
                helper4(list,ans,vis,nums);
                list.remove(list.size()-1);
                vis[i]=false;
            }
        }
    }

    //N-Queens
    //https://leetcode.com/problems/n-queens/
    //O(N factorial * N - since we are traversing n columns and for each col we can place the queen at N position in N factorial ways)tc
    //O(N2)sc
    public List<List<String>> solveNQueens(int n) {
        char[][] chessB=new char[n][n];
        List<List<String>> ans=new ArrayList<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                chessB[i][j]='.';
            }
        }
        helperQueen(0,chessB,ans);
        return ans;
    }
    public void helperQueen(int col,char[][] chessB,List<List<String>> ans){
        if(col==chessB.length){
            ans.add(new ArrayList<>(charToString(chessB)));
            return;
        }
        for(int i=0;i<chessB.length;i++){
            if(isPossible(i,col,chessB)){
                chessB[i][col]='Q';
                helperQueen(col+1,chessB,ans);
                chessB[i][col]='.';
            }
        }
    }
    public boolean isPossible(int row, int col, char[][] chessB){
        int rowCopy=row;
        int colCopy=col;
        while(row>=0&&col>=0){
            if(chessB[row][col]=='Q'){
                return false;
            }
            row--;
            col--;
        }
        row=rowCopy;
        col=colCopy;
        while(col>=0){
            if(chessB[row][col]=='Q'){
                return false;
            }
            col--;
        }
        col=colCopy;
        while(row<chessB.length&&col>=0){
            if(chessB[row][col]=='Q'){
                return false;
            }
            row++;
            col--;
        }
        return true;
    }
    public List<String> charToString(char[][] chessB) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < chessB.length; i++) {
            String s = new String(chessB[i]);
            list.add(s);
        }
        return list;
    }

    //sudoku solver
    //https://leetcode.com/problems/sudoku-solver/
    //O(9^N^2 - since for each cell we check 9 digits to place in that cell)
    public void solveSudoku(char[][] board) {
        helperSudoku(board);
    }
    public boolean helperSudoku(char[][] chessB){
        for(int i=0;i<chessB.length;i++){
            for(int j=0;j<chessB[0].length;j++){
                if(chessB[i][j]=='.'){
                    for(char ch='1';ch<='9';ch++){
                        if(isValid(i,j,ch,chessB)){
                            chessB[i][j]=ch;
                            if(helperSudoku(chessB)){
                                return true;
                            }
                            else{
                                chessB[i][j]='.';
                            }
                        }
                    }
                    return false; //cannot fill the cell with any number
                }
            }
        }
        return true;
    }
    public boolean isValid(int row,int col,char ch,char[][] chessB){
        for(int i=0;i<9;i++){
            if(chessB[row][i]==ch){ //checking row
                return false;
            }
            if(chessB[i][col]==ch){ //checking col
                return false;
            }
            if(chessB[3*(row/3)+i/3][3*(col/3)+i%3]==ch){ //checking grid
                return false;
            }
        }
        return true;
    }

    //M-Coloring problem
    //https://practice.geeksforgeeks.org/problems/m-coloring-problem-1587115620/1#
    public boolean graphColoring(boolean graph[][], int m, int n) {
        //forming the adj list;
        List<List<Integer>> adj=new ArrayList<>();
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<graph[i].length;j++){
                if(graph[i][j]) {
                    adj.get(i).add(j);
                    adj.get(j).add(i);
                }
            }
        }
        int[] color=new int[n];
        return solveM(0,color,adj,n,m);
    }
    public boolean solveM(int currentNode, int[] color, List<List<Integer>> adj, int n,int m){
        if(currentNode==n){ //reached a possible solution
            return true;
        }
        for(int i=1;i<=m;i++){
            if(isPossibleM(i,currentNode,color,adj)){
                color[currentNode]=i;
                if(solveM(currentNode+1,color,adj,m,n)){
                    return true;
                }
            }
            else{
                color[currentNode]=0; //not possible to color current node
            }
        }
        return false; //not possible to color the current node with alt color
    }
    public boolean isPossibleM(int clr, int currentNode,int[] color,List<List<Integer>> adj){
        for(int i:adj.get(currentNode)){
            if(color[i]==clr){ //ie an adj node has same color
                return false;
            }
        }
        return true;
    }
}
