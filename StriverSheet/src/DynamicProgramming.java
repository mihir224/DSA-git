import java.util.*;

class DynamicProgramming{


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
    //https://leetcode.com/problems/longest-increasing-subsequence/

    //approach - We start from the first element of the array, and for each element, we maintain a previous element such
    // that if the current element is greater than the previous element, then we can pick that element for our LIS. Thus,
    // we move forward, making the current element as prev for next element. If however, the current element is less than
    // the prev element, then we can not pick that element in our LIS, and thus we move onto the next element, not changing
    // the prev element. Now if we pick an element, we take the max of what we got from not picking the element and from
    // what we got from picking that element.

    //we can also decide to make the pick call first and then the not pick call, returning the max from both

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
            // prev_index,dp,nums,n) here instead of len and calculated it beforehand because if we didn't do that,
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

    //print LIS
    //https://practice.geeksforgeeks.org/problems/printing-longest-increasing-subsequence/1

    // now to print LIS, we make use of a hash array (each element initialised with their index) .
    // This hash will store the index of the prev element for each element after the dp array has been formed to help us
    // identify where the current element came from in forming the IS which ended with that element. Then, we can access
    // the prev of the element that gave us the LIS ie the one with the max value in the dp array through the hash array
    // and then we can back track to find all elements involved in forming the LIS
    
    public ArrayList<Integer> longestIncreasingSubsequence(int n, int nums[]){
        int max=Integer.MIN_VALUE;
        ArrayList<Integer> lis=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        int lastIndex=0;
        int[] dp=new int[n];
        int[] hash=new int[n];
        Arrays.fill(dp,1);
        for(int i=0;i<n;i++){
            hash[i]=i;
        }
        for(int i=0;i<nums.length;i++){
            for(int prev=0;prev<i;prev++){
                if((nums[prev]<nums[i])&&(1+dp[prev]>dp[i])){ //prev is valid. here we check beforehand if dp[prev]+1 is
                    // greater than dp[i] since we have to assign prev to hash[i] accordingly
                    dp[i]=1+dp[prev];
                    hash[i]=prev;
                }
            }
            if(dp[i]>max){
                max=dp[i];
                lastIndex=i;
            }
        }
        lis.add(0,nums[lastIndex]);
        while(hash[lastIndex]!=lastIndex){ //backtracking
            lastIndex=hash[lastIndex];
            lis.add(0,nums[lastIndex]);
        }
        return lis;
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

    //Edit distance
    //https://leetcode.com/problems/edit-distance

    //memoization
    public int minDistance(String a, String b) {
        int m=a.length();
        int n=b.length();
        int[][] dp=new int[m+1][n+1];
        for(int[] i:dp){
            Arrays.fill(i,-1);
        }
        return f(m,n,a,b,dp);
    }
    public int f(int i,int j,String a,String b,int[][] dp){
        if(i==0){
            return j; //1 based indexing
        }
        if(j==0){
            return i;
        }
        if(dp[i][j]!=-1){
            return dp[i][j];
        }
        if(a.charAt(i-1)==b.charAt(j-1)){
            return dp[i][j]=0+f(i-1,j-1,a,b,dp);
        }
        return 1+Math.min(f(i,j-1,a,b,dp),Math.min(f(i-1,j,a,b,dp),f(i-1,j-1,a,b,dp)));
    }

    //tabulation
    public int minDistance2(String a, String b) {
        int m=a.length();
        int n=b.length();
        int[][] dp=new int[m+1][n+1];
        for(int j=0;j<n+1;j++){
            dp[0][j]=j;
        }
        for(int i=0;i<m+1;i++){
            dp[i][0]=i;
        }
        for(int i=1;i<m+1;i++){
            for(int j=1;j<n+1;j++){
                if(a.charAt(i-1)==b.charAt(j-1)){
                    dp[i][j]=0+dp[i-1][j-1];
                }
                else{
                    dp[i][j]=1+Math.min(dp[i][j-1],Math.min(dp[i-1][j],dp[i-1][j-1]));
                }
            }
        }
        return dp[m][n];
    }

    //maximum sum increasing subsequence
    //https://practice.geeksforgeeks.org/problems/maximum-sum-increasing-subsequence4749/1

    //similar to LIS, just track the max sum
    public int maxSumIS(int arr[], int n)
    {
        int[] dp=new int[n];
        int max=Integer.MIN_VALUE;
        dp=Arrays.copyOfRange(arr,0,n);;
        for(int i=0;i<n;i++){
            for(int prev=0;prev<=i-1;prev++){
                if(arr[i]>arr[prev]){
                    dp[i]=Math.max(dp[i],arr[i]+dp[prev]);
                }
            }
            max=Math.max(max,dp[i]);
        }
        return max;
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

    //Matrix chan multiplication
    //https://practice.geeksforgeeks.org/problems/matrix-chain-multiplication0303/1
    static int matrixMultiplication(int N, int nums[])
    {
        int[][] dp=new int[N+1][N+1];
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        return mcm(1,N-1,dp,nums);
    }
    static int mcm(int i, int j, int[][] dp, int[] nums){
        if(i>=j){
            return 0;
        }
        if(dp[i][j]!=-1){
            return dp[i][j];
        }
        int min=Integer.MAX_VALUE;
        for(int k=i;k<j;k++){
            int tempAns=mcm(i,k,dp,nums)+mcm(k+1,j,dp,nums)+nums[i-1]*nums[k]*nums[j];
            min=Math.min(tempAns,min);
        }
        return dp[i][j]=min;
    }

    //boolean parenthesization (imp!!)
    //https://practice.geeksforgeeks.org/problems/boolean-parenthesization5610/1

    //tc(O(N*N*2))
    //sc(O(N*N*2) + recursive stack space)
    static int countWays(int N, String S){
        int[][][] dp=new int[N+1][N+1][2];
        for(int i=0;i<N+1;i++){
            for(int j=0;j<N+1;j++){
                for(int k=0;k<2;k++){
                    dp[i][j][k]=-1;
                }
            }
        }
        return bp(0,N-1,1,dp,S); //isTrue is int here
    }
    static int bp(int i,int j, int isTrue, int[][][] dp, String s){
        if(i>j){ //empty/invalid string, return false
            return 0;
        }
        if(i==j){
            if(isTrue==1){
                return s.charAt(i)=='T'?1:0;
            }
            else{
                return s.charAt(i)=='F'?1:0;
            }
        }
        if(dp[i][j][isTrue]!=-1){
            return dp[i][j][isTrue];
        }
        int mod=1003;
        int ans=0;
        for(int k=i+1;k<j;k+=2){
            int lt=bp(i,k-1,1,dp,s);
            int lf=bp(i,k-1,0,dp,s);
            int rt=bp(k+1,j,1,dp,s);
            int rf=bp(k+1,j,0,dp,s);
            if(s.charAt(k)=='&'){
                if(isTrue==1){
                    ans+=lt*rt;
                }
                else{
                    ans+=(lt*rf)+(lf*rt)+(lf*rf);
                }
            }
            if(s.charAt(k)=='|'){
                if(isTrue==1){
                    ans+=(lt*rt)+(lf*rt)+(lt*rf);
                }
                else{
                    ans+=lf*rf;
                }
            }
            if(s.charAt(k)=='^'){
                if(isTrue==1){
                    ans+=(lf*rt)+(lt*rf);
                }
                else{
                    ans+=(lf*rf)+(lt*rt);
                }
            }
        }
        return dp[i][j][isTrue]=ans%mod;
    }

    //scrambled string
    //https://leetcode.com/problems/scramble-string

    //tc is O(N2) because in each recursive call there's a for loop and there would be n recursive calls in total
    //sc is O(N2) because in the worst case the map can store N2 keys (for all combinations of strings a and b)
    static boolean isScramble(String a,String b)
    {
        if(a.length()!=b.length()){
            return false;
        }
        HashMap<String,Boolean> map=new HashMap<>(); //this map is being used as in place of dp table, does the same job
        // with lesser time and space complexity
        return isSS(a,b,map);
    }
    static boolean isSS(String a, String b, HashMap<String,Boolean> map){
        if(a.equals(b)){
            return true;
        }
        if (a.length() < 1) { //this will cover the case of b.length()<1 as up till now we have made sure a and b have equal length
            return false;
        }
        String key=a + " " + b;
        if(map.containsKey(key)){
            return map.get(key);
        }
        int n=a.length();
        boolean flag=false;
        for(int i=1;i<n;i++){
            if(isSS(a.substring(0,i),b.substring(n-i),map)&&isSS(a.substring(i),b.substring(0,n-i),map)){ //swap
                flag=true;
                break;
            }
            if(isSS(a.substring(0,i),b.substring(0,i),map)&&isSS(a.substring(i),b.substring(i),map)){ //no swap
                flag=true;
                break;
            }
        }
        map.put(key,flag);
        return flag;
    }


    //minimum path sum
    //https://leetcode.com/problems/minimum-path-sum/

    //Tc O(n*m)
    //Sc O(n*m) + path length ie O((m-1)+(n-1)) because to reach from cell (0,0) to (m-1,n-1) we have to travel m-1 rows, and n-1 columns

    //make sure to also watch the space optimised lecture

    //memoization
    public int minPathSum(int[][] grid) {
        int m=grid.length;
        int n=grid[0].length;
        int[][] dp=new int[m][n]; //max val of i and j can be m-1 and n-1
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        return mps(m-1,n-1,dp,grid);
    }
    public int mps(int i,int j,int[][] dp,int[][] grid){
        if(i==0&&j==0){ //reached first cell;
            return grid[0][0];
        }
        if(i<0||j<0){ //case of out of bounds
            return (int)Math.pow(10,9); //returning max possible val so that it will never be considered as valid ans
        }
        if(dp[i][j]!=-1){
            return dp[i][j];
        }
        int up=grid[i][j]+mps(i-1,j,dp,grid); //moving up, processing rest of the matrix
        int left=grid[i][j]+mps(i,j-1,dp,grid); //moving left, processing rest of the matrix
        return dp[i][j]=Math.min(up,left);
    }

    //tabulation
    public int minPathSum2(int[][] grid) {
        int m=grid.length;
        int n=grid[0].length;
        int[][] dp=new int[m][n]; //max val of i and j can be m-1 and n-1
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(i==0&&j==0){
                    dp[i][j]=grid[0][0];
                }
                else{
                    int up=grid[i][j];
                    if(i>0){
                        up+=dp[i-1][j];
                    }
                    else{
                        up+=(int)1e9;
                    }
                    int left=grid[i][j];
                    if(j>0){
                        left+=dp[i][j-1];
                    }
                    else{
                        left+=(int)1e9;
                    }
                    dp[i][j]=Math.min(up,left);
                }
            }
        }
        return dp[m-1][n-1];
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

    //The following step in the initialisation process is not necessary, and it only increases the time. Thus, we can OMIT it.
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

    //egg drop
    //https://leetcode.com/problems/super-egg-drop

    //recursive
    public int superEggDrop(int e, int f) {
        //we don't consider case of e==0 because it is given that e is always >= 1
        if(e==1||f==0||f==1){ // worst case attempts for these cases are f
            return f;
        }
        int min=Integer.MIN_VALUE;
        for(int k=1;k<=f;k++){
            int tempAns=1+Math.max(superEggDrop(e-1,k-1),superEggDrop(e,f-k)); //obtaining max ans ie the worst one
            min=Math.min(min,tempAns); //min attempts out of all worst cases
        }
        return min;
    }

    //memoization
    //here we apply bs instead of linear search because that would give TLE for large test cases
    public int superEggDrop2(int e, int f) {
      int[][] dp=new int[e+1][f+1];
      for(int[] arr:dp){
          Arrays.fill(arr,-1);
      }
      return eggDrop(e,f,dp);
    }
    public int eggDrop(int e,int f,int[][] dp){
        if(e==1||f==0||f==1){
            return f;
        }
        if(dp[e][f]!=-1) {
            return dp[e][f];
        }
        int min=Integer.MAX_VALUE;
        int start=1;
        int end=f;
        while(start<=end){
            int mid=start+(end-start)/2;
            int left=eggDrop(e-1,mid-1,dp);
            int right=eggDrop(e,f-mid,dp);
            int tempAns=1+Math.max(left,right);
            if(left<right){ //chasing the optimal value of worst case ie max attempts in each iteration
                start=mid+1;
            }
            else{
                end=mid-1;
            }
            min=Math.min(tempAns,min);
        }
        return dp[e][f]=min;
    }


    //word break


    //palindrome partitioning
    //https://practice.geeksforgeeks.org/problems/palindromic-patitioning4845/1
    static int palindromicPartition(String str)
    {
        int n=str.length();
        int[][] dp=new int[n+1][n+1];
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        return pp(0,n-1,dp,str);
    }
    static int pp(int i, int j, int[][] dp ,String str){
        if(i>=j){
            return 0;
        }
        if(dp[i][j]!=-1){
            return dp[i][j];
        }
        if(isPalindrome(str.substring(i,j+1))){
            return 0;
        }
        int min=Integer.MAX_VALUE;
        for(int k=i;k<j;k++){
            int tempAns=1+pp(i,k,dp,str)+pp(k+1,j,dp,str);
            min=Math.min(tempAns,min);
        }
        return dp[i][j]=min;
    }
    static boolean isPalindrome(String s){
        int n=s.length();
        for(int i=0;i<n/2;i++){
            if(s.charAt(i)!=s.charAt(n-i-1)){
                return false;
            }
        }
        return true;
    }

    //word break
    //https://practice.geeksforgeeks.org/problems/word-break1352/1

    //memoization
    //O(N2)
    public static int wordBreak(String A, ArrayList<String> B )
    {
        int n=A.length();
        int[][] dp=new int[n+1][n+1];
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        return wb(0,n-1,dp,A,B);
    }
    public static int wb(int i, int j, int[][] dp,String a, ArrayList<String> b){
        if(i>j){
            return 0;
        }
        if(dp[i][j]!=-1){
            return dp[i][j];
        }
        if(b.contains(a.substring(i,j+1))){
            return 1;
        }
        boolean flag=false;
        for(int k=i;k<j;k++){
            if(wb(i,k,dp,a,b)==1&&wb(k+1,j,dp,a,b)==1){
                flag=true;
                break;
            }
        }
        dp[i][j]=flag?1:0;
        return flag?1:0;
    }

    //random 200 LC mark question
    //plus one
    //https://leetcode.com/problems/plus-one/submissions/
    public int[] plusOne(int[] digits) {
        for(int i=digits.length-1;i>=0;i--){
            if(digits[i]<9){
                digits[i]++;
                return digits;
            }
            digits[i]=0; //case when digits[i]==9
        }
        digits=new int[digits.length+1]; //case when all items have become zero
        digits[0]=1;
        return digits;
    }
    
    //job sequencing problem
    //https://practice.geeksforgeeks.org/problems/job-sequencing-problem-1587115620/1


    //since we have to perform the jobs to get the max profit, we sort the given job array in decreasing order of profits
    // so that we can perform the jobs with higher profits at first. This is what we can think of greedily. Now to find when
    // to perform a specific job, we can use the analogy that if we try to perform each job on the last day of its deadline,
    // then we'd be able to accommodate doing more jobs in the days prior to the last day of that job's deadline. Thus we find
    // the max deadline m among all jobs and create an array of size m+1 where each item denotes a day from 1 to last day of
    // max deadline. This array is filled with -1 denoting that no job has been done yet on any day. Then we simply traverse
    // through all jobs and for each job we check if we can do that job on the last day of its deadline ie if no other job
    // has been done on the same day. If we can, we just fill that day in the deadline array with the index of the current
    // job. Otherwise, we check days prior to that day and fill the one that's empty. Each time we do a job on a particular
    // day (ie fill that day in deadline array), we add that job's profit to the total profit yet and simultaneously count
    // the number of jobs done till now. Then we break from the inner loop through which we were traversing the days prior
    // to the last day of current job's deadline to move onto the next job. Through this approach, we try to do each job
    // on the last day of its deadline, and we do the jobs with higher profits first in order to maximize the profits,
    // counting the number of jobs that we could do along the way.

    //intuition: we try to perform the job with the longer deadline as late as possible so that we can perform another job
    // with shorted deadline in the earlier days (this way we can make best use of all jobs)

    //tc O(NLogN + N*M) considering that size of longest deadline is m
    //sc O(M)
    class Job {
        int id, profit, deadline;
        Job(int x, int y, int z){
            this.id = x;
            this.deadline = y;
            this.profit = z;
        }
    }
    int[] JobScheduling(Job arr[], int n)
    {
        int jobCount=0;
        int maxProfit=0;
        int maxDeadline=0; //to store max deadline
        Arrays.sort(arr,(a,b)->b.profit-a.profit);
        for(int i=0;i<arr.length;i++){
            maxDeadline=Math.max(maxDeadline,arr[i].deadline);
        }
        int[] nums=new int[maxDeadline+1];
        Arrays.fill(nums,-1);
        for(int i=0;i<arr.length;i++){
            for(int j=arr[i].deadline;j>0;j--){
                if(nums[j]==-1){
                    nums[j]=i;
                    jobCount++;
                    maxProfit+=arr[i].profit;
                    break; //job done, now do other job
                }
            }
        }
        return new int[]{jobCount,maxProfit};
    }
}