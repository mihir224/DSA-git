import javax.lang.model.type.ArrayType;
import java.util.*;

class DP{
    //max product sub-array

    //https://leetcode.com/problems/maximum-product-subarray/

    //brute - find all possible subarrays and their product, return the max one

    //optimal
    //this is an intuitive approach, dp is not being used here
    //3 cases - either all numbers are positive or there are even negatives (in this case ans is product of all), there are
    // odd negatives (in this case, ans will either lie on the left of a negative(prefix)
    // or the right of a negative(suffix)). in this case, the max of this prefix and suffix would be our ans and since we're
    // trying to find the max ans, when prefix or suffix become negative, they'll automatically be discarded. The
    // third case is that there might be a 0 in the number and in that case prefix or suffix would become 0 and they'll make
    // further prefixes and suffixes 0 too thus if we ever encounter such case then we make the prefix and suffix 1 so that
    // future prefix and suffixes aren't affected. we simply iterate over the given array, in each iteration, first we check
    // if prefix or suffix products are 0 (meaning that they encountered a zero while travelling), if they are, we make
    // them 1 so that we can start fresh from elements ahead of 0. Then, we calculate the prefix and suffix product and
    // get the max of them and compare it with the max yet. This ensures that if we encounter a negative element then the
    // product in that iteration is ignored (since we are always taking the max product)

    //O(N) time, O(1) space

    public int maxProduct(int[] nums) {
        int pref=1;
        int suf=1;
        int ans=Integer.MIN_VALUE;
        for(int i=0;i<nums.length;i++){
            if(pref==0){
                pref=1;
            }
            if(suf==0){
                suf=1;
            }
            pref=pref*nums[i];
            suf=suf*nums[nums.length-i-1];
            ans=Math.max(ans,Math.max(pref,suf));
        }
        return ans;
    }

    //longest increasing subsequence
    //https://leetcode.com/problems/longest-common-subsequence

    //brute (recursive)
    //we can recursively try to find all subsequences which are increasing and return the ones that give us the max length.
    // If the current element<prev one, then it means that current sequence is decreasing and thus we only have one option
    // that is to not pick this element and  if the current element is greater than its prev one, then it means that the
    // current sequence is increasing and thus we have two choices - either we can pick the element or we cannot pick the
    // element. So we can recursively find out the ans for both of these cases and return the max one. Initially index of
    // the prev element is set to -1 so that we can pick the first element w/0 any hassle

    //O(2^N)


    public int lengthOfLIS(int[] nums) {
        int n=nums.length;
        return helper(0,-1,n,nums);
    }
    public int helper(int index, int prev_index, int n, int[] nums){
        if(index==n){ //traversed the whole array
            return 0;
        }
        int len=0;
        if(prev_index==-1||nums[index]>nums[prev_index]){
            len=1+helper(index+1,index,n,nums); //pick
        }
        len=Math.max(len, 0+helper(index+1,prev_index,n,nums)); //taking the max from pick and not pick
        return len;
    }


    //memoization
    //O(N2)

    public int lengthOfLIS1(int[] nums) {
        int n=nums.length;
        int[][] dp=new int[n][n+1];
        //we always take prev_index + 1 in the dp table because for prev_index=-1, it would give out of bounds error (this is called coordinate shift)
        // and for this  reason we make dp table of size n*n+1
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        return helperLis(0,-1,dp,n,nums);
    }
    public int helperLis(int index, int prev_index,int[][] dp, int n, int[] nums){
        if(index==n){
            return 0;
        }
        if(dp[index][prev_index+1]!=-1){
            return dp[index][prev_index+1]; //+1 because prevIndex can be -1, thus we store it as 0 based
        }
        int len=0;
        if(prev_index==-1||nums[index]>nums[prev_index]){
            len=1+helperLis(index+1,index,dp,n,nums); //pick
        }
        len=Math.max(len,0+helperLis(index+1,prev_index,dp,n,nums)); //taking the max from pick and not pick
        return dp[index][prev_index+1]=len;
    }

    //tabulation
    //since we moved from start till end in the recursive and memoized solutions, we do the exact opposite here.
    //O(N2)

    public int lengthOfLIS2(int[] nums) {
        int n=nums.length;
        int[][] dp=new int[n+1][n+1]; // i is also n+1 here we the base case is for index==n thus we have to take index==n into account
        //base case in recursion and memoization was that if index==n, we return 0. Here, initially every row and col is 0
        // and thus we don't have to explicitly initialise the dp with the base case
        for(int index=n-1;index>=0;index--){
            for(int prev_index=index-1;prev_index>=-1;prev_index--){
                int len=0;
                if(prev_index==-1||nums[index]>nums[prev_index]){
                    len=1+dp[index+1][index+1];
                }
                dp[index][prev_index+1]=Math.max(len,0+dp[index+1][prev_index+1]);
            }
        }
        return dp[0][-1+1];
    }

    // space optimised O(N) soln - used to print LIS
    // try to dry run this with eg - [5,3,1,9,10,16]
    // Now if we have to print the LIS, it is important to understand the obtainment of LIS from this method, so that we
    // can trace back from it in order to print the LIS

    //here we take a 1d dp[] of size n, where dp[i] stores the maximum length of an increasing subsequence that ends with nums[i].
    // Then, we can simply iterate over this array and find the max value to obtain the ans. To fill this dp, we first
    // initialize all elements with 1, as in the worst case, length of an increasing subsequence can be 1 (if there's no
    // element before the current element that is less than it). Then, we iterate over the nums array and for each element
    // we check if there's a valid smaller element before it. If there is, we set the current element's value in the dp array
    // as max of its current value, and the value obtained after adding 1(picking current element) to the dp value of the valid prev element ie (the max
    // length of an increasing subsequence that ends with that prev element).

    // basically by adding 1 to the length of the increasing subsequence that ends with the prev valid element
    // and comparing it with the length of the increasing subsequence that ends with current element, we're making sure
    // that at all times, for any particular element in the dp table, we store the max length of the longest subsequence
    // that ends with this element. this helps us save time because we're using the length of the previously calculated
    // elements and appending it to the current element instead of having to calculate the longest length for each element
    // individually

    public int lengthOfLIS3(int[] nums) {
        int n=nums.length;
        int max=Integer.MIN_VALUE;
        int[] dp=new int[n];
        Arrays.fill(dp,1);
        for(int i=0;i<nums.length;i++){
            for(int prev=0;prev<i;prev++){
                if(nums[prev]<nums[i]){ //prev is valid
                    dp[i]=Math.max(dp[i],1+dp[prev]); //this approach works in such a way that 1+dp[prev] would at min be = dp[i]. It will never be less than dp[i] and thus the current element can form
                }
            }
        }
        for(int i:dp){
            max=Math.max(max,i);
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

    //lcs
    //https://leetcode.com/problems/longest-common-subsequence/

    //recursive (string matching)
    public int longestCommonSubsequence(String a, String b) {
        int m=a.length();
        int n=b.length();
        return lcs(a,b,m,n);
    }
    public int lcs(String a, String b, int m, int n){
        //base case
        if(m==0||n==0){ //ie if we have traversed either of the two strings completely, there'll be no lcs
            return 0;
        }
        if(a.charAt(m-1)==b.charAt(n-1)){
            return 1+lcs(a,b,m-1,n-1);
        }
        return Math.max(lcs(a,b,m-1,n),lcs(a,b,m,n-1));
    }

    //memoization
    public int longestCommonSubsequence1(String a, String b) {
        int m=a.length();
        int n=b.length();
        int[][] dp=new int[m+1][n+1];
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        lcs1(a,b,dp,m,n);
        return dp[m][n];
    }
    public int lcs1(String a, String b, int[][] dp,int m, int n){
        //base case
        if(m==0||n==0){ //ie if we have traversed either of the two strings completely, there'll be no lcs
            return 0;
        }
        if(dp[m][n]!=-1){
            return dp[m][n];
        }
        if(a.charAt(m-1)==b.charAt(n-1)){
            return dp[m][n]=1+lcs(a,b,m-1,n-1);
        }
        return dp[m][n]=Math.max(lcs(a,b,m-1,n),lcs(a,b,m,n-1));
    }

    //tabulation
    public int longestCommonSubsequence2(String a, String b) {
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
                }else{
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[m][n];
    }

    //O/1 knapsack
    //https://practice.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1

    //recursive
    static int knapSack(int W, int wt[], int val[], int n)
    {
        return zeroOne(wt,val,W,n);
    }
    static int zeroOne(int[] wt, int[] val, int W, int n){
        // base case
        if(W==0||n==0){
            return 0;
        }
        if(wt[n-1]<=W){
            return Math.max(val[n-1]+zeroOne(wt,val,W-wt[n-1],n-1),zeroOne(wt,val,W,n-1));
        }
        else{
            return zeroOne(wt,val,W,n-1);
        }
    }

    //memoization
    static int knapSac1k(int W, int wt[], int val[], int n)
    {
        int[][] dp=new int[n+1][W+1];
        for(int [] arr:dp){
            Arrays.fill(arr,-1);
        }
        zeroOne1(wt,val,dp,W,n);
        return dp[n][W];
    }
    static int zeroOne1(int[] wt, int[] val, int[][] dp,int W, int n){
        // base case
        if(W==0||n==0){
            return 0;
        }
        if(dp[n][W]!=-1){
            return dp[n][W];
        }
        if(wt[n-1]<=W){
            return dp[n][W]=Math.max(val[n-1]+zeroOne1(wt,val,dp,W-wt[n-1],n-1),zeroOne1(wt,val,dp,W,n-1));
        }
        else{
            return dp[n][W]=zeroOne1(wt,val,dp,W,n-1);
        }
    }

    //tabulation
    static int knapSac2k(int W, int wt[], int val[], int n)
    {
        int[][] dp=new int[n+1][W+1];
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

    //edit distance (min operations(insert, delete or replace) to convert string a to b)
    //https://leetcode.com/problems/edit-distance/

    //recursive (string matching) - try all possible operations on each element of a, return the ans with min operations

    //base case - f(-1,1) would try to find the min distance (ie number of operations) to convert an empty string a to a
    // string b from index 0 to 1. The ans would definitely be 2 (since we wish to convert an empty string to a string from
    // index 0 to 1 ie we need these characters at indices 0 and 1 to be part of string a so that it matches string b from
    // index 0 to 1) ie if i==-1, we return j+1 (since j is at 1). similarly when j==-1, we'd need to make min of i+1 operations
    // to convert a to b(an empty string)

    //we are taking i and j such that they represent the number of characters in a and b thus instead of checking if i==-1
    // or j==-1, we check if i==0 or j==0 and accordingly return j and i

    //finding recurrence
    //2 cases
    //characters match - no operation required, process rest of the strings: 0+f(i-1,j-1)
    //characters don't match - three choices:
    // insert - i stays where it was initially even after insertion thus to process rest of the strings: 1+f(i,j-1)
    // delete - i moves to the char before the one that is deleted, j stays as is, thus: 1+f(i-1,j)
    // replace - char replaced, both chars match now, thus processing the rest: 1+f(i-1,j-1)
    //O(exponential) time, O(max(m,n)) recursive stack space

    public int minDistance(String word1, String word2) {
        return ed(word1.length(),word2.length(),word1,word2);
    }
    public int ed(int i,int j,String a, String b){
        if(i==0){ //ie string a has 0 length
            return j;
        }
        if(j==0){ //ie string b has 0 length
            return i;
        }
        if(a.charAt(i-1)==b.charAt(j-1)){
            return 0+ed(i-1,j-1,a,b);
        }
        return 1+Math.min(ed(i,j-1,a,b),Math.min(ed(i-1,j,a,b),ed(i-1,j-1,a,b)));
    }

    //memoization
    //O(m*n) time O((m*n)+max(m,n)) space

    public int minDistance1(String a, String b) {
        int m=a.length();
        int n=b.length();
        int[][] dp=new int[m+1][n+1];
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        return ed1(m,n,dp,a,b);
    }
    public int ed1(int i,int j,int[][] dp,String a, String b){
        if(i==0){ //ie string a has 0 length
            return j;
        }
        if(j==0){ //ie string b has 0 length
            return i;
        }
        if(dp[i][j]!=-1){
            return dp[i][j];
        }
        if(a.charAt(i-1)==b.charAt(j-1)){ //mismatch
            return dp[i][j]=0+ed1(i-1,j-1,dp,a,b);
        }
        return dp[i][j]=1+Math.min(ed1(i,j-1,dp,a,b),Math.min(ed1(i-1,j,dp,a,b),ed1(i-1,j-1,dp,a,b)));
    }

    //tabulation
    //O(m*n) time and space
    public int minDistance2(String a, String b) {
        int m=a.length();
        int n=b.length();
        int[][] dp=new int[m+1][n+1];
        //converting base case to initialisation of dp table
        for(int i=0;i<m+1;i++){
            for(int j=0;j<n+1;j++){
                if(i==0){
                    dp[i][j]=j;
                }
                if(j==0){
                    dp[i][j]=i;
                }
            }
        }
        //recurrence
        for(int i=1;i<m+1;i++){
            for(int j=1;j<n+1;j++){
                if(a.charAt(i-1)==b.charAt(j-1)){
                    dp[i][j]=0+dp[i-1][j-1];
                }
                else{
                    dp[i][j]=1+Math.min(dp[i-1][j],Math.min(dp[i][j-1],dp[i-1][j-1]));
                }
            }
        }
        return dp[m][n];
    }

    //maximum increasing subsequence
    //brute would be to find all possible increasing subsequences through recursion (we track prev index to check for increasing
    // subsequence), tracking their sum and returning the max one

    public int maxSumIS(int arr[], int n)
    {
        return helperMis(0,-1,arr,n);
    }
    public int helperMis(int currentIndex,int prevIndex,int[] arr, int n){
        if(currentIndex==n){
            return 0;
        }
        int sum=0;
        if(prevIndex==-1||arr[currentIndex]>arr[prevIndex]){ //pick
            sum=arr[currentIndex]+helperMis(currentIndex+1,currentIndex,arr,n);
        }
        sum=Math.max(sum,helperMis(currentIndex+1,prevIndex,arr,n)); //comparing with not pick
        return sum;
    }

    //memoization
    //O(N2)time, O(N2+N) space
    public int maxSumIS1(int arr[], int n)
    {
        int[][] dp=new int[n][n+1];
        for(int[] nums:dp){
            Arrays.fill(nums,-1);
        }
        return helperMi1s(0,-1,arr,n,dp);
    }
    public int helperMi1s(int currentIndex,int prevIndex,int[] arr, int n,int[][] dp){
        if(currentIndex==n){
            return 0;
        }
        int sum=0;
        if(dp[currentIndex][prevIndex+1]!=-1){
            return dp[currentIndex][prevIndex+1];
        }
        if(prevIndex==-1||arr[currentIndex]>arr[prevIndex]){ //pick
            sum=arr[currentIndex]+helperMi1s(currentIndex+1,currentIndex,arr,n,dp);
        }
        sum=Math.max(sum,helperMi1s(currentIndex+1,prevIndex,arr,n,dp)); //comparing with not pick
        return dp[currentIndex][prevIndex+1]=sum;
    }

    //we can space optimize by using the same approach as we did in LIS ie to use a 1d dp table where dp[i] would give
    // us the max increasing sum of a subsequence that ends with arr[i]

    //O(N2)time, O(N) space

    public int maxSumIS2(int arr[], int n)
    {
        int[] dp=new int[n];
        int max=Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            dp[i]=arr[i]; //because in the worst case(if the array is in decreasing order) we can only take the ith
            // element itself as the part of the subsequence, thus min possible sum is the element itself
        }
        for(int i=0;i<arr.length;i++){
            for(int prev=0;prev<i;prev++){
                if(arr[i]>arr[prev]){ //valid prev
                    dp[i]=Math.max(dp[i],dp[prev]+arr[i]);
                }
            }
            if(dp[i]>max){
                max=dp[i];
            }
        }
        return max;
    }

    //matrix chain multiplication
    //https://practice.geeksforgeeks.org/problems/matrix-chain-multiplication0303/1

    //recursive
    static int matrixMultiplication(int N, int arr[])
    {
        return mm(1,N-1,arr,N);
    }
    static int mm(int i, int j, int[] arr, int N){
        if(i>=j){
            return 0;
        }
        int ans=Integer.MAX_VALUE;
        for(int k=i;k<j;k++){
            int tempAns=mm(i,k,arr,N)+mm(k+1,j,arr,N)+arr[i-1]*arr[k]*arr[j];
            ans=Math.min(ans,tempAns);
        }
        return ans;
    }

    //memoization
    static int matrixMultiplication1(int N, int arr[])
    {
        int[][] dp=new int[N+1][N+1];
        for(int[] nums:dp){
            Arrays.fill(nums,-1);
        }
        return mm1(1,N-1,arr,N,dp);
    }
    static int mm1(int i, int j, int[] arr, int N,int[][] dp){
        if(i>=j){
            return 0;
        }
        if(dp[i][j]!=-1){
            return dp[i][j];
        }
        int ans=Integer.MAX_VALUE;
        for(int k=i;k<j;k++){
            int tempAns=mm1(i,k,arr,N,dp)+mm1(k+1,j,arr,N,dp)+arr[i-1]*arr[k]*arr[j];
            ans=Math.min(ans,tempAns);
        }
        return dp[i][j]=ans;
    }



    //count of subsets with given sum
    //https://practice.geeksforgeeks.org/problems/perfect-sum-problem5633/1

    //recursive

    //we have to also consider the case when sum is 0 and the 0 is present in the array because in that case along with
    // an empty subset, the subset containing 0's also count. for eg if sum and 0 and there's a 0 in the array then the
    // number of valid subsets are 2 - {} and {0}. This count increases by a factor of 2. Like if there were 2 zeroes instead
    // of one in the array then the count would've been 4 - {},{0},{0},{0,0}. Thus while picking an element, we skip it if
    // it is zero and after the recursive function has returned its answer, we multiply it by 2^(no. of zeroes) to get the
    // correct ans (with consideration of zeroes). I have used this analogy in the recursive code only. In tabulation,
    // handling this case is much easier as while filling the dp table, for the sum, even though we initialise its base
    // case (ie when sum = 0 count is 1 ie an empty subset (assuming array has no zeroes)), we start it from 0 again
    // instead of 1 to cover the case when sum is 0 and there is a 0 in the array.

    public int perfectSu1m(int arr[],int n, int sum)
    {
        int mod=(int)1e9+7;
        int count=0;
        for(int i=0;i<n;i++){
            if(arr[i]==0){
                count++;
            }
        }
        return ((int)Math.pow(2,count)*helperPs(arr,n,sum))%mod;
    }
    public int helperPs(int[] arr, int n, int sum){
        if(sum==0){
            return 1;
        }
        if(n==0){
            return 0;
        }

        if(arr[n-1]<=sum&&arr[n-1]!=0){ //we don't consider the presence of 0 in the array and skip it because we deal with it separately
            return helperPs(arr,n-1,sum-arr[n-1])+helperPs(arr,n-1,sum);
        }
        return helperPs(arr,n-1,sum);
    }

    //memoization
    public int perfectSum1(int arr[],int n, int sum)
    {
        int mod=(int)1e9+7;
        int count=0;
        for(int i=0;i<n;i++){
            if(arr[i]==0){
                count++;
            }
        }
        int[][] dp=new int[n+1][sum+1];
        for(int[] nums:dp){
            Arrays.fill(nums,-1);
        }
        return ((int)Math.pow(2,count)*helperPs1(arr,n,sum,dp))%mod;
    }
    public int helperPs1(int[] arr, int n, int sum,int[][] dp){
        int mod=(int)1e9+7;
        if(sum==0){
            return 1;
        }
        if(n==0){
            return 0;
        }
        if(dp[n][sum]!=-1){
            return dp[n][sum];
        }
        if(arr[n-1]<=sum&&arr[n-1]!=0){ //we don't consider the presence of 0 in the array and skip it because we deal with it separately
            return dp[n][sum]=(helperPs1(arr,n-1,sum-arr[n-1],dp)+helperPs1(arr,n-1,sum,dp))%mod;
        }
        return dp[n][sum]=helperPs1(arr,n-1,sum,dp)%mod;
    }

    //tabulation
    public int perfectSum(int arr[],int n, int sum)
    {
        int mod=(int)1e9+7;
        int count=0;
        int[][] dp=new int[n+1][sum+1];
        for(int i=0;i<n+1;i++){
            for(int j=0;j<sum+1;j++){
                if(i==0){
                    dp[i][j]=0;
                }
                if(j==0){
                    dp[i][j]=1;
                }
            }
        }
        for(int i=1;i<n+1;i++){
            for(int j=0;j<sum+1;j++){
                if(arr[i-1]<=j){
                    dp[i][j]=(dp[i-1][j-arr[i-1]]+dp[i-1][j])%mod;
                }else{
                    dp[i][j]=dp[i-1][j]%mod;
                }
            }
        }
        return dp[n][sum]%mod;
    }

    //minimum subset sum difference
    //https://practice.geeksforgeeks.org/problems/minimum-sum-partition3317/1?utm_source=geeksforgeeks&utm_medium=article_practice_tab&utm_campaign=article_practice_tab
    public int minDifference(int arr[], int n)
    {
        int range=0;
        for(int i=0;i<arr.length;i++){
            range+=arr[i];
        }
        boolean[][] dp=new boolean[n+1][range+1];
        for(int i=0;i<n+1;i++){
            for(int j=0;j<range+1;j++){
                if(i==0){
                    dp[i][j]=false;
                }
                if(j==0){
                    dp[i][j]=true;
                }
            }
        }
        for(int i=1;i<n+1;i++){
            for(int j=1;j<range+1;j++){
                if(arr[i-1]<=j){
                    dp[i][j]=dp[i-1][j-arr[i-1]]||dp[i-1][j];
                }
                else{
                    dp[i][j]=dp[i-1][j];
                }
            }
        }
        List<Integer> list=new ArrayList<>();
        for(int i=0;i<(range/2)+1;i++){
            if(dp[n][i]==true){
                list.add(i);
            }
        }
        int ans=Integer.MAX_VALUE;
        for(int i=0;i<list.size();i++){
            ans=Math.min(ans,range-(2*list.get(i)));
        }
        return ans;
    }

    //count of subsets with given difference (target sum)
    //https://leetcode.com/problems/target-sum/

    public int findTargetSumWays(int[] nums, int target) {
        int s=0;
        for(int i:nums){
            s+=i;
        }
        if((s+target)%2!=0){
            return 0;
        }
        int s1=(s+target)/2;
        if(s1<0){
            return 0;
        }
        return perfectSum(nums,nums.length,s1);
    }

    //minimum path sum
    //https://leetcode.com/problems/minimum-path-sum/

    //recursive - finding cost of all possible paths, returning the minimum one

    //in this solution, we try to move top down, ie from the last cell of destination to the source
    public int minPathSum(int[][] grid) {
        int n=grid.length;
        int m=grid[0].length;
        return helperMps(n-1,m-1,grid);
    }
    public int helperMps(int i,int j,int[][] grid){
        if(i==0&&j==0){ //reached src
            return grid[i][j];
        }
        if(i<0||j<0){
            return Integer.MAX_VALUE-1;
        }
        return grid[i][j]+Math.min(helperMps(i-1,j,grid),helperMps(i,j-1,grid)); //finding min from both up and left
        // recursive calls and adding cost of current cell to it
    }

    //memoization
    public int minPathSum1(int[][] grid) {
        int n=grid.length;
        int m=grid[0].length;
        int[][] dp=new int[n][m];
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        return helperMp1s(n-1,m-1,grid,dp);
    }
    public int helperMp1s(int i,int j,int[][] grid,int[][] dp){
        if(i==0&&j==0){ //reached src
            return grid[i][j];
        }
        if(i<0||j<0){
            return Integer.MAX_VALUE-1;
        }
        if(dp[i][j]!=-1){
            return dp[i][j];
        }
        return dp[i][j]=grid[i][j]+Math.min(helperMp1s(i-1,j,grid,dp),helperMp1s(i,j-1,grid,dp)); //finding min from both up and left
        // recursive calls and adding cost of current cell to it
    }
    //tabulation
    public int minPathSum2(int[][] grid) {
        int n=grid.length;
        int m=grid[0].length;
        int[][] dp=new int[n][m];
        //here we have the base case as when i==0&&j==0 we return grid[i][j], ie for every value of j we don't have to return 0
        // when i=0 and vice versa and thus we can initialise and fill the table simultaneously
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(i==0&&j==0){
                    dp[i][j]=grid[i][j];
                }
                else{
                    int up=grid[i][j];
                    if(i>0){
                        up+=dp[i-1][j]; //if we're at 0th row, there's no way we can go upward
                    }
                    else{
                        up+=(int)1e9;
                    }
                    int left=grid[i][j];
                    if(j>0){
                        left+=dp[i][j-1];
                    }else{ //ie we're at 0th col, there's no way moving left
                        left+=(int)1e9;
                    }
                    dp[i][j]=Math.min(left,up);
                }
            }
        }
        return dp[n-1][m-1];
    }


    //coin change (min no. of coins)
    //https://leetcode.com/problems/coin-change/

    //recursive
    public int coinChang1e(int[] coins, int amount) {
        int ans=helperC1C(coins,amount,coins.length);
        return ans>=Integer.MAX_VALUE-1?-1:ans;
    }
    public int helperC1C(int[] coins,int amount,int n){
        if(n==0){ //no coins, thus no. of coins req to form amount is maximum
            return Integer.MAX_VALUE-1;
        }
        if(amount==0){
            return 0;
        }
        if(coins[n-1]<=amount){
            return Math.min(1+helperC1C(coins,amount-coins[n-1],n),helperC1C(coins,amount,n-1));
        }
        return helperC1C(coins,amount,n-1); //can't take
    }

    //memoization
    public int coinChange1(int[] coins, int amount) {
        int n=coins.length;
        int[][] dp=new int[n+1][amount+1];
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        int ans=helperCC(coins,amount,n,dp);
        return ans>=Integer.MAX_VALUE-1?-1:ans;
    }
    public int helperCC(int[] coins,int amount,int n,int[][] dp){
        if(n==0){ //no coins, thus no. of coins req to form amount is maximum
            return Integer.MAX_VALUE-1;
        }
        if(amount==0){
            return 0;
        }
        if(dp[n][amount]!=-1){
            return dp[n][amount];
        }
        if(coins[n-1]<=amount){
            return dp[n][amount]=Math.min(1+helperCC(coins,amount-coins[n-1],n,dp),helperCC(coins,amount,n-1,dp));
        }
        return dp[n][amount]=helperCC(coins,amount,n-1,dp); //instead of writing this call twice, we can first store
        // the res from pick call and then compare it with this not pick call thus returning the min of both
    }

    //tabulation
    public int coinChange(int[] coins, int amount) {
        int n=coins.length;
        int[][] dp=new int[n+1][amount+1];
        for(int i=0;i<n+1;i++){
            for(int j=0;j<amount+1;j++){
                if(i==0){
                    dp[i][j]=Integer.MAX_VALUE-1;
                }
                if(j==0){
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
        return dp[n][amount]>=Integer.MAX_VALUE-1?-1:dp[n][amount];
    }

    //equal sum partition
    //https://leetcode.com/problems/partition-equal-subset-sum

    public boolean canPartition(int[] nums) {
        int sum=0;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
        }
        if(sum%2!=0){
            return false;
        }
        return subsetSum(sum/2,nums);
    }

    //this subset sum problem is entirely different from the one we did earlier in which we had to find all possible
    // subset sums. In this we have to check whether there's a subset in the given array which has the same sum as the
    // given sum

    //recursive
    public boolean subsetSum1(int sum, int[] nums){
        return helperSS(sum,nums,nums.length);
    }
    public boolean helperSS(int sum,int[] nums, int n){
        if(sum==0){
            return true;
        }
        if(n==0){
            return false;
        }
        if(nums[n-1]<=sum){ //can pick
            return helperSS(sum-nums[n-1],nums,n-1)||helperSS(sum,nums,n-1);
        }
        return helperSS(sum,nums,n-1);
    }

    //tabulation
    public boolean subsetSum(int sum, int[] nums){
        int n=nums.length;
        boolean[][] dp=new boolean[n+1][sum+1];
        for(int i=0;i<n+1;i++){
            for(int j=0;j<sum+1;j++){
                if(i==0){ //n is 0, ie false
                    dp[i][j]=false;
                }
                if(j==0){ //sum 0, ie true
                    dp[i][j]=true;
                }
            }
        }
        for(int i=1;i<n+1;i++){
            for(int j=1;j<sum+1;j++){
                if(nums[i-1]<=j){ //can pick
                    dp[i][j]=dp[i-1][j-nums[i-1]]||dp[i-1][j];
                }
                else{ //can't pick
                    dp[i][j]=dp[i-1][j];
                }
            }
        }
        return dp[n][sum];
    }

    //min cost to cut a stick (MCM variation)
    //https://leetcode.com/problems/minimum-cost-to-cut-a-stick/

    //suppose the cuts array is - [1,3,4,5] and the length of stick is 7. We can try to solve this problem by dividing it
    // into 2 sub problems, solving them recursively and returning the optimal solution. Now the reason why the two sub
    // problems can be solved independently is because we initially sort the cuts array. Now see if we try to make a
    // partition at 4 first, the cost would be length of current stick ie 7 and the problem will be recursively divided
    // into two parts - ie [1,3] and [5]. Now since we sorted the array, these are independent because making a partition
    // from either of them would not affect the cost of the other sub problem (like if we make a partition at 5, it will
    // only take into account cost of the stick to which it belongs. If however in the cuts array we included 2 after 5,
    // then the sub problem would've given us the wrong ans because recursively when we would've made a partition at 2,
    // it would've returned us the cost of the stick to which 2 belongs which in this case is greater than the cost of
    // the stick to which 5 belongs and thus it would've given us the wrong cost). Since we're using the partitioning ie
    // MCM approach, there'll be three pointers, i at the start, j at the end and k from i till j. Now to find the length
    // of the stick (ie cost) while making partition at a particular position, we use this analogy. We try to append a 0
    // at the start of the partitions array and the length 7 at the end. Now if we try to make a partition, say at position
    // 4 then we can basically obtain the length of the stick at that point as cuts[j+1]-cuts[i-1]. in this case that would
    // be 7-0 ie 7 since i is at 1 and j is at 5.

    //recursive

    public int minCost(int n, int[] cuts) {
        List<Integer> list=new ArrayList<>();
        Arrays.sort(cuts);
        for(int i:cuts){
            list.add(i);
        }
        list.add(0,0);
        list.add(n);
        return helperMc(1,cuts.length,list); //since after adding 0 and N, indices will be shifted and the last element
        // would now point to index cuts.length in the list
    }
    public int helperMc(int i,int j, List<Integer> list){
        if(i>j){
            return 0;
        }
        int min=Integer.MAX_VALUE;
        for(int k=i;k<=j;k++){
            int tempAns=list.get(j+1)-list.get(i-1)+helperMc(i,k-1,list)+helperMc(k+1,j,list);
            min=Math.min(min,tempAns);
        }
        return min;
    }

    //memoization
    public int minCost1(int n, int[] cuts) {
        List<Integer> list=new ArrayList<>();
        int c=cuts.length;
        int[][] dp=new int[c+1][c+1];
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        Arrays.sort(cuts);
        for(int i:cuts){
            list.add(i);
        }
        list.add(0,0);
        list.add(n);
        return helperMc1(1,c,list,dp); //since after adding 0 and N, indices will be shifted and the last element
        // would now point to index cuts.length in the list
    }
    public int helperMc1(int i,int j, List<Integer> list,int[][] dp){
        if(i>j){
            return 0;
        }
        if(dp[i][j]!=-1){
            return dp[i][j];
        }
        int min=Integer.MAX_VALUE;
        for(int k=i;k<=j;k++){
            int tempAns=list.get(j+1)-list.get(i-1)+helperMc1(i,k-1,list,dp)+helperMc1(k+1,j,list,dp);
            min=Math.min(min,tempAns);
        }
        return dp[i][j]=min;
    }

    //tabulation
    public int minCost2(int n, int[] cuts) {
        List<Integer> list=new ArrayList<>();
        int c=cuts.length;
        int[][] dp=new int[c+2][c+2];
        Arrays.sort(cuts);
        for(int i:cuts){
            list.add(i);
        }
        list.add(0,0);
        list.add(n);
        for(int i=c;i>=1;i--){ //reversing i and j since this is bottom up
            for(int j=1;j<c+1;j++){
                if(i>j){ //base case
                    continue;
                }
                int min= Integer.MAX_VALUE;
                for(int k=i;k<=j;k++){
                    int tempAns=list.get(j+1)-list.get(i-1)+dp[i][k-1]+dp[k+1][j];
                    min=Math.min(min,tempAns);
                }
                dp[i][j]=min;
            }
        }
        return dp[1][c]; //since we have to find the ans for when i is 1 and j is c, basically this will give us the most
        // optimal ans when i is 1 and j is c which is what we were initially trying to find
    }

    //egg drop
    //https://practice.geeksforgeeks.org/problems/egg-dropping-puzzle-1587115620/1

    //recursive
    static int eggDrop(int e, int f)
    {
        return helperED(e,f);
    }
    static int helperED(int e,int f){
        if(e==1||f==0||f==1){
            return f;
        }
        int min=Integer.MAX_VALUE;
        for(int k=1;k<=f;k++){
            int tempAns=1+Math.max(helperED(e-1,k-1),helperED(e,f-k)); //current attempt+case if egg breaks, thus checking
            // floors down+case if egg doesn't break, thus checking all floors up calculating the max of worst case for current floor
            min=Math.min(min,tempAns);
        }
        return min;
    }

    //memoization - although nominal soln works fine, but here we can add a check before each recursive call to check if its ans is already stored in
    // the dp table otherwise code might give tle for larger test cases. both approaches fine however.

    //nominal
    static int eggDro23p(int e, int f)
    {
        int[][] dp=new int[e+1][f+1];
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        return helper(e,f,dp);
    }
    static int helper(int e, int f,int[][] dp){
        if(e==1||f==0||f==1){
            return f;
        }
        if(dp[e][f]!=-1){
            return dp[e][f];
        }
        int ans=Integer.MAX_VALUE;
        for(int k=1;k<=f;k++){
            int tempAns=1+Math.max(helper(e-1,k-1,dp),helper(e,f-k,dp));
            ans=Math.min(ans,tempAns);
        }
        return dp[e][f]=ans;
    }

    //slightly optimised
    static int eggDro1p(int e, int f)
    {
        int[][] dp=new int[e+1][f+1];
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        return helperE1D(e,f,dp);
    }
    static int eggDrop1(int e, int f)
    {
        int[][] dp=new int[e+1][f+1];
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        return helperE1D(e,f,dp);
    }
    static int helperE1D(int e,int f,int[][] dp){
        if(e==1||f==0||f==1){
            return f;
        }
        int min=Integer.MAX_VALUE;
        for(int k=1;k<=f;k++){
            int low=0;
            int high=0;
            if(dp[e-1][k-1]!=-1){
                low=dp[e-1][k-1];
            }else{
                low=helperE1D(e-1,k-1,dp);
                dp[e-1][k-1]=low;
            }
            if(dp[e][f-k]!=-1){
                high=dp[e][f-k];
            }
            else{
                high=helperE1D(e,f-k,dp);
                dp[e][f-k]=high;
            }
            int tempAns=1+Math.max(low,high);
            min=Math.min(min,tempAns);
        }
        return dp[e][f]=min;
    }

    //word break (leetcode version) - quite similar to wb that we did in recursion. also, this soln is memoized
    //https://leetcode.com/problems/word-break
    public boolean wordBreak(String s, List<String> wordDict) {
        return helper(0,s,wordDict,new HashMap<>());
    }
    public boolean helper(int index, String s, List<String> wordDict, HashMap<String,Boolean> map){
        if(index>s.length()){
            return true;
        }

        String key=s.substring(index); //will help identify if the ans for substring from index has already been calculated or not. The thing is that, if we had used index to store the ans from a particular call then that would've resulted in wrong ans because in each recursive call the size of the string is changing and thus for whatever index we would store the ans, in further recursive calls that index might be pointing to other characters and thus we'd return the wrong ans directly in those calls
        if(map.containsKey(key)){
            return map.get(key);
        }
        int temp=s.length();
        for(int i=index+1;i<=temp;i++){
            String str=s.substring(index,i);
            if(wordDict.contains(str)){
                if(i!=temp)
                    s=s.substring(0,i) + " " + s.substring(i);
                if(helper(i+1,s,wordDict,map)){
                    map.put(key,true);
                    return true;
                }
                if(i!=temp)
                    s=s.substring(0,i) + s.substring(i+1);
            }
        }
        map.put(key,false);
        return false;
    }

    //palindrome partitioning (find min number of partitions required)
    //https://practice.geeksforgeeks.org/problems/palindromic-patitioning4845/1
    //recursive
    static int palindromicPartition(String str)
    {
        int n=str.length();
        return helperPP(0,n-1,str);
    }
    public static int helperPP(int i, int j, String str){
        if(i>=j){ //traversed the whole string. if i and j point to the same character then that would also mean a palindrome
            // and we'd require no more partitions thus we return 0
            return 0;
        }
        if(isPalindrome(str.substring(i,j+1))){ //current substring is a valid palindrome, thus no need to partition this
            // substring further
            return 0;
        }
        int min=Integer.MAX_VALUE;
        for(int k=i;k<j;k++){
            int tempAns=1+helperPP(i,k,str)+helperPP(k+1,j,str); //1 signifies the current partition
            min=Math.min(min,tempAns);
        }
        return min;
    }
    public static boolean isPalindrome(String str){
        int i=0;
        int n=str.length();
        while(i<n/2){
            if(str.charAt(i)!=str.charAt(n-i-1)){
                return false;
            }
            i++;
        }
        return true;
    }

    //memoization
    static int palindromicPartition1(String str)
    {
        int n=str.length();
        int[][] dp=new int[n+1][n+1];
        for(int[] arr:dp){
            Arrays.fill(arr,-1);
        }
        return helperPP1(0,n-1,str,dp);
    }
    public static int helperPP1(int i, int j, String str,int[][] dp){
        if(i>=j){ //traversed the whole string. if i and j point to the same character then that would also mean a palindrome
            // and we'd require no more partitions thus we return 0
            return 0;
        }
        if(dp[i][j]!=-1){
            return dp[i][j];
        }
        if(isPalindrome(str.substring(i,j+1))){ //current substring is a valid palindrome, thus no need to partition this
            // substring further
            return 0;
        }
        int min=Integer.MAX_VALUE;
        for(int k=i;k<j;k++){
            int tempAns=1+helperPP1(i,k,str,dp)+helperPP1(k+1,j,str,dp); //1 signifies the current partition
            min=Math.min(min,tempAns);
        }
        return dp[i][j]=min;
    }


    //Check if There is a Valid Partition For The Array
    //https://leetcode.com/problems/check-if-there-is-a-valid-partition-for-the-array
    //here the catch is that, if we start partitioning from the start, a valid subarray would be of length 2 or 3. if it
    // isn't then it means that the array cannot be partitioned as in this case we would never be able to partition the
    // string (as there's no subarray right from the start which is valid) and thus we'll return 0. otherwise, we'll try
    // to partition it further and if we're able to partition the entire array, we return true
    public boolean validPartition(int[] nums) {
        int n=nums.length;
        int[] dp=new int[n];
        Arrays.fill(dp,-1);

        return helper(0,nums,dp)==1?true:false;
    }
    public int helper(int i, int[] nums,int[] dp){
        if(i==nums.length){
            return 1;
        }
        if(dp[i]!=-1){
            return dp[i];
        }
        int ans=0;
        if(i+1<nums.length&&nums[i]==nums[i+1]){
            ans=ans|helper(i+2,nums,dp);
        }
        if(i+2<nums.length){
            if(nums[i]==nums[i+1]&&nums[i+1]==nums[i+2]){
                ans=ans|helper(i+3,nums,dp);
            }
            if(nums[i]+1==nums[i+1]&&nums[i+1]+1==nums[i+2]){
                ans=ans|helper(i+3,nums,dp);
            }
        }
        return dp[i]=ans;
    }
}