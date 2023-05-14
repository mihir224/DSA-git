import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

class DynamicProgramming{
    //0-1 knapsack
    //https://practice.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1

    //recursive
    static int knapSack(int W, int wt[], int val[], int n)
    {
        //base condition
        if(n==0||W==0){
            return 0;
        }
        //choice diagram
        if(wt[n-1]<=W){
            return Math.max(val[n-1]+knapSack(W-wt[n-1],wt,val,n-1),knapSack(W,wt,val,n-1));
        }
        return knapSack(W,wt,val,n-1); //W<w[n-1]
    }

    //memoization
    static int knapSack2(int W, int wt[], int val[], int n)
    {
        int[][] dp=new int[n+1][W+1];
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        return memoHelpKnap(W,n,wt,val,dp);
    }
    static int memoHelpKnap(int w,int n,int[] wt,int [] val,int[][] dp){
        if(n==0||w==0){
            return 0;
        }
        if(dp[n][w]!=-1){ //current cell has already been evaluated
            return dp[n][w];
        }
        if(wt[n-1]<=w){
            return dp[n][w]=Math.max(val[n-1]+memoHelpKnap(w-wt[n-1],n-1,wt,val,dp),memoHelpKnap(w,n-1,wt,val,dp));
        }
        return dp[n][w]=memoHelpKnap(w,n-1,wt,val,dp);
    }

    //tabulation (bottom up)
    static int knapSack3(int w, int wt[], int val[], int n)
    {
        int[][] dp=new int[n+1][w+1];
        for(int i=0;i<=n;i++){
            for(int j=0;j<=w;j++){
                if(i==0||j==0){
                    dp[i][j]=0;
                }
            }
        }
        for(int i=1;i<=n;i++){ //i varies wrt n
            for(int j=1;j<=w;j++){ //j varies wrt w
                if(wt[i-1]<=j){
                    dp[i][j]=Math.max(val[i-1]+dp[i-1][j-wt[i-1]],dp[i-1][j]);
                }
                else {
                    dp[i][j]=dp[i-1][j];
                }
            }
        }
        return dp[n][w];
    }

    //equal sum partition
    //https://leetcode.com/problems/partition-equal-subset-sum/
    public boolean canPartition(int[] nums) {
        int sum=0;
        for(int i:nums){
            sum+=i;
        }
        if(sum%2!=0){ //sum is odd
            return false;
        }
        return isSubsetSum(nums,nums.length,sum/2);
    }
    public boolean isSubsetSum(int[] nums, int n, int sum){
        boolean[][] dp=new boolean[n+1][sum+1];
        for(int i=0;i<n+1;i++){
            for(int j=0;j<sum+1;j++){
                if(i==0){
                    dp[i][j]=false;
                }
                if(j==0){
                    dp[i][j]=true;
                }
            }
        }
        for(int i=1;i<n+1;i++){
            for(int j=1;j<sum+1;j++){
                if(nums[i-1]<=j){
                    dp[i][j]=dp[i-1][j-nums[i-1]]||dp[i-1][j];
                }
                else{
                    dp[i][j]=dp[i-1][j];
                }
            }
        }
        return dp[n][sum];
    }

    //number of senior citizens
    public int countSeniors(String[] details) {
        int count=0;
        for(String s:details){
            String a="";
            a+=s.charAt(11);
            a+=s.charAt(12);
            if(Integer.parseInt(a)>60){
                count++;
            }
        }
        return count;
    }

    //max product sub array
    //https://leetcode.com/problems/maximum-product-subarray
    //O(N)

    //this is not a dp approach IG
    //we simply iterate over the given array, in each iteration, first we check if prefix or suffix products are 0 (meaning
    // that they encountered a zero while travelling), if they are, we make them 1 so that we can start fresh from elements
    // ahead of 0. Then, we calculate the prefix and suffix product and get the max of them and compare it with the max yet.
    // We basically try moving forward and backward through the array and in each iteration we multiply the current element
    // to the product in both directions till now. This ensures that if we encounter a negative element then the product in
    // that iteration is ignored (since we are always taking the max product)

    public int maxProduct(int[] nums) {
        int pref=1;
        int suff=1;
        int max=Integer.MIN_VALUE;
        for(int i=0;i<nums.length;i++){
            if(pref==0){
                pref=1;
            }
            if(suff==0){
                suff=1;
            }
            pref=pref*nums[i];
            suff=suff*nums[nums.length-i-1];
            max=Math.max(max,Math.max(pref,suff));
        }
        return max;
    }

    //Unbounded Knapsack

    //Rod cutting problem

    public int cutRod(int price[], int n) {
        int[] length=new int[n];
        int[][] dp=new int[n+1][n+1];
        //length array
        for(int i=1;i<n+1;i++){
            length[i-1]=i;
        }
        //dp initialization
        for(int i=0;i<n+1;i++){
            for(int j=0;j<n+1;j++){
                if(i==0||j==0){
                    dp[i][j]=0;
                }
            }
        }

        for(int i=1;i<n+1;i++){
            for(int j=1;j<n+1;j++){
                if(length[i-1]<=j){
                    dp[i][j]=Math.max(price[i-1]+dp[i][j-length[i-1]],dp[i-1][j]);
                }
                else{
                    dp[i][j]=dp[i-1][j];
                }
            }
        }
        return dp[n][n];
    }


    //min cost to cut a stick
    //https://leetcode.com/problems/minimum-cost-to-cut-a-stick/

    // we first have to sort the given array so that whenever we do a partition at a particular cut, we can solve all the
    // cuts before and after the current cut (left and right sub problems) separately without any relation between them
    // and return the optimal ans

    //memoization solution
    public int minCost(int n, int[] cuts) {
        ArrayList<Integer> list=new ArrayList<>();
        int w=cuts.length;
        for(int i:cuts){
            list.add(i);
        }
        list.add(n);
        list.add(0,0);
        Collections.sort(list);
        int[][] dp=new int[w+1][w+1];
        for(int[] row:dp){
            Arrays.fill(row,-1);
        }
        return helper(1,w,list,dp);
    }
    public int helper(int i,int j, ArrayList<Integer> cuts,int[][] dp){
        if(i>j){
            return 0;
        }
        if(dp[i][j]!=-1){
            return dp[i][j];
        }
        int min=Integer.MAX_VALUE;
        for(int index=i;index<=j;index++){
            int cost=cuts.get(j+1)-cuts.get(i-1)+helper(i,index-1,cuts,dp)+helper(index+1,j,cuts,dp);
            min=Math.min(min,cost);
        }
        return dp[i][j]=min;
    }

    //coin change: number of ways
    //https://practice.geeksforgeeks.org/problems/coin-change2448/1
    public long count(int coins[], int N, int sum) {
        long[][] dp=new long[N+1][sum+1];
        for(int i=0;i<N+1;i++){
            for(int j=0;j<sum+1;j++){
                if(j==0){
                    dp[i][j]=0;
                }
                if(i==0){
                    dp[i][j]=1;
                }
            }
        }
        for(int i=1;i<N+1;i++){
            for(int j=1;j<sum+1;j++){
                if(coins[i-1]<=j){
                    dp[i][j]=Math.max(dp[i][j-coins[i-1]],dp[i-1][j]);
                }
                else{
                    dp[i][j]=dp[i-1][j];
                }
            }
        }
        return dp[N][sum];
    }

    //coin change: min number of coins req to form sum
    //https://leetcode.com/problems/coin-change/

    //The following step in the initialisation process is not necessary and it only increases the time. Thus we can OMIT it.
    // Just read for educational purposes:
    //verma sir told us to consider row i==1 also in the initialization. For i=1, we have one element in the array such that,
    // if its remainder with sum is 0, then we can add that element multiple times to form the sum and thus, j/coins[0] would
    // give us our ans. Otherwise, we initialize the element to INT_MIN-1

    public int coinChange(int[] coins, int amount) {
        int n=coins.length;
        int[][] dp=new int[n+1][amount+1];
        for(int i=0;i<n+1;i++){
            for(int j=0;j<amount+1;j++){
                if(i==0){
                    dp[i][j]=Integer.MAX_VALUE-1;
                }
                else{
                    dp[i][j]=0;
                }
            }
        }
        for(int i=1;i<n+1;i++){
            for(int j=1;j<amount+1;j++){
                if(coins[i-1]<=j){
                    dp[i][j]=Math.min(1+dp[i][j-coins[i-1]],dp[i-1][j]);
                }
                else{
                    dp[i][j]=dp[i-1][j];
                }
            }
        }
        return dp[n][amount]==Integer.MAX_VALUE-1?-1:dp[n][amount];
    }
}