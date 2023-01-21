import java.util.ArrayList;
import java.util.Arrays;

public class Questions {

    //0/1 knapsack
    //https://practice.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1

    //memoization
    static int knapSack(int W, int wt[], int val[], int n) {
        int[][] dp = new int[n + 1][W + 1];
        //initialization
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < W + 1; j++) {
                dp[i][j] = -1;
            }
        }
        return helperMem(wt, val, W, n, dp);
    }

    static int helperMem(int[] wt, int[] val, int W, int n, int[][] dp) {
        if (n == 0 || W == 0) {
            return 0;
        }
        if (dp[n][W] != -1) {
            return dp[n][W];
        }
        if (wt[n - 1] <= W) {
            return dp[n][W] = Math.max(val[n - 1] + helperMem(wt, val, W - wt[n - 1], n - 1, dp), helperMem(wt, val, W, n - 1, dp));
        } else
            return dp[n][W] = helperMem(wt, val, W, n - 1, dp);
    }

    //tabulation
    static int knapSack2(int W, int wt[], int val[], int n)
    {
        int[][] dp=new int[n+1][W+1];
        //initialization
        for(int i=0;i<n+1;i++){
            for(int j=0;j<W+1;j++){
                if(i==0||j==0){
                    dp[i][j]=0;
                }
            }
        }
        for(int i=1;i<n+1;i++){
            for(int j=1;j<W+1;j++){
                if(wt[i-1]<=j){
                    dp[i][j]=Math.max(val[i-1]+dp[i-1][j-wt[i-1]],dp[i-1][j]);
                }
                else{
                    dp[i][j]=dp[i-1][j];
                }
            }
        }
        return dp[n][W];
    }

    //subset sum problem
    //https://practice.geeksforgeeks.org/problems/subset-sum-problem-1611555638/1
    static Boolean isSubsetSum(int N, int arr[], int sum){
        boolean[][] dp=new boolean[N+1][sum+1];
        for(int i=0;i<N+1;i++){
            for(int j=0;j<sum+1;j++){
                if(i==0){
                    dp[i][j]=false; //initialising row n=0 as false initially
                }
                if(j==0){
                    dp[i][j]=true; //initialising col sum=0 as true, overwriting (0,0)
                }
            }
        }
        for(int i=1;i<N+1;i++){
            for(int j=1;j<sum+1;j++){
                if(arr[i-1]<=j){
                    dp[i][j]=dp[i-1][j-arr[i-1]]||dp[i-1][j];
                }
                else
                    dp[i][j]=dp[i-1][j];
            }
        }
        return dp[N][sum];
    }

    //equal sum partition
    //https://leetcode.com/problems/partition-equal-subset-sum/
    public boolean canPartition(int[] nums) {
        int sum=0;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
        }
        if(sum%2!=0){
            return false;
        }
        return isSubsetSum(nums.length, nums, sum/2);
    }

    //count subsets with given sum
    //https://practice.geeksforgeeks.org/problems/perfect-sum-problem5633/1
    public int perfectSum(int arr[],int N, int sum)
    {
        int[][] dp=new int[N+1][sum+1];
        int mod=(int)1e9+7;
        //initialisation
        for(int i=0;i<N+1;i++){
            for(int j=0;j<sum+1;j++){
                if(i==0){
                    dp[i][j]=0;
                }
                if(j==0){
                    dp[i][j]=1;
                }
            }
        }
        for(int i=1;i<N+1;i++){
            for(int j=0;j<sum+1;j++){
                if(arr[i-1]<=j){
                    dp[i][j]=(dp[i-1][j-arr[i-1]]+dp[i-1][j])%mod;
                }
                else
                    dp[i][j]=dp[i-1][j]%mod;
            }
        }
        return dp[N][sum]%mod;
    }

    //minimum subset difference
    //https://practice.geeksforgeeks.org/problems/minimum-sum-partition3317/1
    //leetcode link- https://leetcode.com/problems/last-stone-weight-ii/
    public int minDifference(int arr[], int n)
    {
        int sum=0;
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<arr.length;i++){
            sum+=arr[i];
        }
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
                if(arr[i-1]<=j){
                    dp[i][j]=dp[i-1][j-arr[i-1]]||dp[i-1][j];
                }
                else
                    dp[i][j]=dp[i-1][j];
            }
        }
        for(int j=0;j<(sum/2)+1;j++){
            if(dp[n][j]==true){
                list.add(j); //filtered list of possible elements
            }
        }

        int min=Integer.MAX_VALUE;
        for(int i:list){
            if((sum-2*i)<0){ //incase diff becomes negative
                break;
            }
            min=Math.abs(Math.min(min,sum-2*i));
        }
        return min;
    }

    //goldmine problem
    //https://practice.geeksforgeeks.org/problems/gold-mine-problem2608/1
    static int maxGold(int n, int m, int M[][])
    {
        int[][] dp=new int[n][m];
        int[] delRow={-1,0,1};
        int[] delCol={1,1,1};
        for(int i=0;i<n;i++){
            Arrays.fill(dp[i],-1);
        }
        int ans=-1;
        for(int i=0;i<n;i++){
            int sum=helper1(i,0,delRow,delCol,dp,n,m,M);
            ans=Math.max(ans,sum);
        }
        return ans;
    }
    public static int helper1(int row,int col, int[] delRow, int[] delCol, int[][] dp, int n, int m, int[][] M){
        if(row>=n||col>=m||row<0||col<0){
            return 0;
        }
        if(dp[row][col]!=-1){
            return dp[row][col];
        }
        int ans=-1;
        for(int i=0;i<3;i++){
            int sum=M[row][col];
            int nrow=row+delRow[i];
            int ncol=col+delCol[i];
            sum=sum+helper1(nrow,ncol,delRow,delCol,dp,n,m,M);
            ans=Math.max(ans,sum);
        }
        dp[row][col]=ans;
        return dp[row][col];
    }

    //count number of subset pairs with given difference
    //https://practice.geeksforgeeks.org/problems/partitions-with-given-difference/1
    public int countPartitions(int N, int d, int arr[]){
        int range=0;
        if((d+range)%2!=0||range<Math.abs(d)){ //edge cases
            return 0;
        }
        for(int i:arr){
            range+=i;
        }
        int sum=(d+range)/2;
        return perfectSum(arr,arr.length,sum);
    }

    //target sum
    //https://leetcode.com/problems/target-sum/
    public int findTargetSumWays(int[] arr, int d) {
        int range=0;
        for(int i:arr){
            range+=i;
        }
        if((d+range)%2!=0){ //edge cases
            return 0;
        }
        int sum1=(d+range)/2;
        if(sum1<0){ //case when d+range is negative
            return 0;
        }
        return perfectSum(arr,arr.length,sum1);
    }



}
