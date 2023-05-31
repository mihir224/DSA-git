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


    //longest increasing subsequence
    //https://leetcode.com/problems/longest-increasing-subsequence/o

    //approach - We start from the first element of the array, and for each element, we maintain a previous element such
    // that if the current element is greater than the previous element, then we can pick that element for our LIS. Thus,
    // we move forward, making the current element as prev for next element. If however, the current element is less than
    // the prev element, then we can not pick that element in our LIS, and thus we move onto the next element, not changing
    // the prev element. Now if we pick an element, we take the max of what we got from not picking the element and from
    // what we got from picking that element. For this reason,we calculate the result from not picking up the element beforehand,
    // and then we calculate the max from this result and the result from picking up the element.`

    //memoization (O(N2)) - check tabulation also for this
    public int lengthOfLIS(int[] nums) {
        int n=nums.length;
        //we always take prev_index + 1 in the dp table because for prev_index=-1, it would give out of bounds error (this is called coordinate shift)
        // and for this  reason we make dp table of size n*n+1
        int [][] dp=new int[n][n+1];
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        return helperLIS(0,-1,dp, nums, n);
    }
    public int helperLIS(int index, int prev_index, int[][] dp, int[] nums, int n){
        if(index==n){ //bc, traversed all elements
            return 0;
        }
        if(dp[index][prev_index+1]!=-1){
            return dp[index][prev_index+1];
        }
        int len=0+helperLIS(index+1,prev_index,dp,nums,n); //not pick
        if(prev_index==-1||nums[index]>nums[prev_index]){ //pick
            len=Math.max(len,1+helperLIS(index+1,index,dp,nums,n)); //we didn't directly put 0+helperLis(index+1,
            // prev_index,dp,nums,n) here instead of len and calculated it before hand because if we didn't do that,
            // 0+helperLis(index+1,prev_index,dp,nums,n) would be called only for the cases when current element>prev
        }
        return dp[index][prev_index+1]=len;
    }

    //tabulataion
    public int lengthOfLIS2(int[] nums) {
        int n=nums.length;
        int [][] dp=new int[n+1][n+1]; //since we start index from n-1 and in each iteration we check dp[index+1][...]
        //here we don't need to intialize dp table for base case ie for i==n, return 0. This is because each element of
        // the table is 0 by default
        //Now since we moved from start till end in recursive and memoized solutions, we do the exact opposite here. ie
        // we move from end till start.
        for(int index=n-1;index>=0;index--){
            for(int prev_index=index-1;prev_index>=-1;prev_index--){
                int len=0+dp[index+1][prev_index+1];
                if(prev_index==-1||nums[index]>nums[prev_index]){
                    len=Math.max(len,1+dp[index+1][index+1]);
                }
                dp[index][prev_index+1]=len;
            }
        }
        return dp[0][-1 +1];
    }

    //there's one more way to solve this problem, with same tc: O(N2) but much lesser sc: O(N). Now if we have to print
    // the LIS, it is important to understand the obtainment of LIS from this method, so that we can trace back from it
    // in order to print the LIS

    //here we take a 1d dp[] of size n, where dp[i] stores the maximum length of an increasing subsequence that ends with nums[i].
    // Then, we can simply iterate over this array and find the max value to obtain the ans. To fill this dp, we first
    // initialize all elements with 1, as in the worst case, length of an increasing subsequence can be 1 (if there's no
    // element before the current element that is less than it). Then, we iterate over the nums array and for each element
    // we check if there's a valid smaller element before it. If there is, we set the current element's value in the dp array
    // as max of its current value, and the value obtained after adding 1(picking current element) to the dp value of the valid prev element ie (the max
    // length of an increasing subsequence that ends with that prev element).

    //This way, we can add the length of any increasing subsequence that ends with a valid prev element to the length of
    // the increasing subsequence that ends with current element without having to calculate the length by picking all
    // valid previous elements individually
    public int lengthOfLIS3(int[] nums) {
        int n=nums.length;
        int[] dp=new int[n];
        int max=Integer.MIN_VALUE;
        Arrays.fill(dp,1); //initialization
        for(int i=0;i<n;i++){
            for(int prev=0;prev<=i-1;prev++){
                if(nums[i]>nums[prev]){
                    dp[i]=Math.max(dp[i],1+dp[prev]);
                }
            }
            max=Math.max(max,dp[i]);
        }
        return max;
    }

    // now to print LIS, we make use of a hash array (each element initialised with their index) . This hash will store the index of the prev element for each element
    // after the dp array has been formed to help us identify where the current element came from in forming the IS which
    // ended with that element. Then, we can access the prev of the element that gave us the LIS ie the one with the max
    // value in the dp array through the hash array and then we can back track to find all elements involved in forming
    // the LIS

    public String lengthOfLIS4(int[] nums) {
        int n=nums.length;
        int[] dp=new int[n];
        int max=Integer.MIN_VALUE;
        int[] hash=new int[n];
        int lastIndex=0;
        Arrays.fill(dp,1); //initialization
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<n;i++){
            hash[i]=i;
        }
        for(int i=0;i<n;i++){
            for(int prev=0;prev<=i-1;prev++){
                if(nums[i]>nums[prev]){
                    dp[i]=Math.max(dp[i],1+dp[prev]);
                    hash[i]=prev;
                }
            }
            if(dp[i]>max){
                max=dp[i];
                lastIndex=i;
            }
        }
        while(hash[lastIndex]!=lastIndex){
            sb.append(dp[lastIndex]);
            lastIndex=hash[lastIndex];
        }
        sb.append(hash[lastIndex]);
        return sb.reverse().toString();
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

    //longest common subsequence
    //https://leetcode.com/problems/longest-common-subsequence/

    public int longestCommonSubsequence(String a, String b) {
        int m=a.length();
        int n=b.length();
        int[][] dp=new int[m+1][n+1];
        for(int i=0;i<m+1;i++){
            for(int j=0;j<n+1;j++){
                if(i==0||j==0){
                    dp[i][j]=0;
                }
            }
        }
        for(int i=1;i<m+1;i++){
            for(int j=1;j<n+1;j++){
                if(a.charAt(i-1)==b.charAt(j-1)){
                    dp[i][j]=1+dp[i-1][j-1];
                }
                else{
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        //print lcs
//        StringBuilder sb =new StringBuilder();
//        int i=m;
//        int j=n;
//        while(i!=0&&j!=0){
//            if(a.charAt(i)==b.charAt(j)){
//                sb.append(a.charAt(i));
//                i--;
//                j--;
//            }
//            else if(dp[i-1][j]>dp[i][j-1]){
//                i--;
//            }
//            else{
//                j--;
//            }
//        }
//        sb=sb.reverse();
        return dp[m][n];
    }

    //longest common substring
    //https://practice.geeksforgeeks.org/problems/longest-common-substring1452/1
    int longestCommonSubstr(String a, String b, int m, int n){
        int[][] dp=new int[m+1][n+1];
        int length=0;
        for(int i=0;i<m+1;i++){
            for(int j=0;j<n+1;j++){
                if(i==0||j==0){
                    dp[i][j]=0;
                }
            }
        }
        for(int i=1;i<m+1;i++){
            for(int j=1;j<n+1;j++){
                if(a.charAt(i-1)==b.charAt(j-1)){
                    dp[i][j]=1+dp[i-1][j-1];
                    length=Math.max(length,dp[i][j]);
                }
                else{
                    dp[i][j]=0;
                }
            }
        }
        return length;
    }
}