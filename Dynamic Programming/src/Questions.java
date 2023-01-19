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
            for(int j=0;j<N+1;j++){
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

}
