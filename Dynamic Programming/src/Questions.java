import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    static int knapSack2(int W, int wt[], int val[], int n) {
        int[][] dp = new int[n + 1][W + 1];
        //initialization
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < W + 1; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < W + 1; j++) {
                if (wt[i - 1] <= j) {
                    dp[i][j] = Math.max(val[i - 1] + dp[i - 1][j - wt[i - 1]], dp[i - 1][j]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][W];
    }

    //subset sum problem
    //a variation of 0/1 knapsack
    //https://practice.geeksforgeeks.org/problems/subset-sum-problem-1611555638/1
    static Boolean isSubsetSum(int N, int arr[], int sum) {
        boolean[][] dp = new boolean[N + 1][sum + 1];
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < sum + 1; j++) {
                if (i == 0) {
                    dp[i][j] = false; //initialising row n=0 as false initially
                }
                if (j == 0) {
                    dp[i][j] = true; //initialising col sum=0 as true, overwriting (0,0)
                }
            }
        }
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < sum + 1; j++) {
                if (arr[i - 1] <= j) {
                    dp[i][j] = dp[i - 1][j - arr[i - 1]] || dp[i - 1][j];
                } else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[N][sum];
    }

    //equal sum partition
    //a variation of 0/1 knapsack
    //https://leetcode.com/problems/partition-equal-subset-sum/
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if (sum % 2 != 0) {
            return false;
        }
        return isSubsetSum(nums.length, nums, sum / 2);
    }

    //count subsets with given sum
    //https://practice.geeksforgeeks.org/problems/perfect-sum-problem5633/1
    //a variation of 0/1 knapsack
    public int perfectSum(int arr[], int N, int sum) {
        int[][] dp = new int[N + 1][sum + 1];
        int mod = (int) 1e9 + 7;
        //initialisation
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < sum + 1; j++) {
                if (i == 0) {
                    dp[i][j] = 0;
                }
                if (j == 0) {
                    dp[i][j] = 1;
                }
            }
        }
        for (int i = 1; i < N + 1; i++) {
            for (int j = 0; j < sum + 1; j++) {
                if (arr[i - 1] <= j) {
                    dp[i][j] = (dp[i - 1][j - arr[i - 1]] + dp[i - 1][j]) % mod;
                } else
                    dp[i][j] = dp[i - 1][j] % mod;
            }
        }
        return dp[N][sum] % mod;
    }

    //minimum subset difference
    //https://practice.geeksforgeeks.org/problems/minimum-sum-partition3317/1
    //leetcode link- https://leetcode.com/problems/last-stone-weight-ii/
    //a variation of 0/1 knapsack
    public int minDifference(int arr[], int n) {
        int sum = 0;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        boolean[][] dp = new boolean[n + 1][sum + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < sum + 1; j++) {
                if (i == 0) {
                    dp[i][j] = false;
                }
                if (j == 0) {
                    dp[i][j] = true;
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < sum + 1; j++) {
                if (arr[i - 1] <= j) {
                    dp[i][j] = dp[i - 1][j - arr[i - 1]] || dp[i - 1][j];
                } else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        for (int j = 0; j < (sum / 2) + 1; j++) {
            if (dp[n][j] == true) {
                list.add(j); //filtered list of possible elements
            }
        }

        int min = Integer.MAX_VALUE;
        for (int i : list) {
            if ((sum - 2 * i) < 0) { //incase diff becomes negative
                break;
            }
            min = Math.abs(Math.min(min, sum - 2 * i));
        }
        return min;
    }

    //goldmine problem
    //https://practice.geeksforgeeks.org/problems/gold-mine-problem2608/1
    static int maxGold(int n, int m, int M[][]) {
        int[][] dp = new int[n][m];
        int[] delRow = {-1, 0, 1};
        int[] delCol = {1, 1, 1};
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }
        int ans = -1;
        for (int i = 0; i < n; i++) {
            int sum = helper1(i, 0, delRow, delCol, dp, n, m, M);
            ans = Math.max(ans, sum);
        }
        return ans;
    }

    public static int helper1(int row, int col, int[] delRow, int[] delCol, int[][] dp, int n, int m, int[][] M) {
        if (row >= n || col >= m || row < 0 || col < 0) {
            return 0;
        }
        if (dp[row][col] != -1) {
            return dp[row][col];
        }
        int ans = -1;
        for (int i = 0; i < 3; i++) {
            int sum = M[row][col];
            int nrow = row + delRow[i];
            int ncol = col + delCol[i];
            sum = sum + helper1(nrow, ncol, delRow, delCol, dp, n, m, M);
            ans = Math.max(ans, sum);
        }
        dp[row][col] = ans;
        return dp[row][col];
    }

    //count the number of subset sum pairs with given difference
    //target sum on leetcode
    //a variation of 0/1 knapsack
    //https://leetcode.com/problems/target-sum/
    public int findTargetSumWays(int[] arr, int d) {
        int range = 0;
        for (int i : arr) {
            range += i;
        }
        if ((d + range) % 2 != 0) { //edge cases
            return 0;
        }
        int sum1 = (d + range) / 2;
        if (sum1 < 0) { //case when d+range is negative
            return 0;
        }
        return perfectSum(arr, arr.length, sum1);
    }

    //unbounded knapsack
    //https://practice.geeksforgeeks.org/problems/knapsack-with-duplicate-items4201/1
    static int knapSack(int N, int W, int val[], int wt[]) {
        int[][] dp = new int[N + 1][W + 1];
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < W + 1; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < W + 1; j++) {
                if (wt[i - 1] <= j) {
                    dp[i][j] = Math.max(val[i - 1] + dp[i][j - wt[i - 1]], dp[i - 1][j]);
                } else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[N][W];
    }

    //rod-cutting problem
    //a variation of unbounded knapsack
    //https://practice.geeksforgeeks.org/problems/rod-cutting0840/1
    public int cutRod(int price[], int n) {
        int length[] = new int[n];
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 1; i < n + 1; i++) {
            length[i - 1] = i;
        }
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (length[i - 1] <= j) {
                    dp[i][j] = Math.max(price[i - 1] + dp[i][j - length[i - 1]], dp[i - 1][j]);
                } else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[n][n];
    }

    //coin change
    //a variation of unbounded knapsack

    //count number of ways
    //https://practice.geeksforgeeks.org/problems/coin-change2448/1
    public long count(int coins[], int N, int sum) {
        long[][] dp = new long[N + 1][sum + 1];
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < sum + 1; j++) {
                if (i == 0) {
                    dp[i][j] = 0;
                }
                if (j == 0) {
                    dp[i][j] = 1;
                }
            }
        }
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < sum + 1; j++) {
                if (coins[i - 1] <= j) {
                    dp[i][j] = dp[i][j - coins[i - 1]] + dp[i - 1][j];
                } else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[N][sum];
    }

    //min number of coins
    public int coinChange(int[] coins, int amount) {
        int N = coins.length;
        int[][] dp = new int[N + 1][amount + 1];
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < amount + 1; j++) {
                if (i == 0) {
                    dp[i][j] = Integer.MAX_VALUE - 1;
                }
                if (j == 0) {
                    dp[i][j] = 0;
                }
                if (i == 1) {
                    if (j % coins[0] == 0) {
                        dp[i][j] = j / coins[0];
                    } else {
                        dp[i][j] = Integer.MAX_VALUE - 1;
                    }
                }
            }
        }
        for (int i = 2; i < N + 1; i++) {
            for (int j = 1; j < amount + 1; j++) {
                if (coins[i - 1] <= j) {
                    dp[i][j] = Math.min(1 + dp[i][j - coins[i - 1]], dp[i - 1][j]);
                } else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[N][amount] < Integer.MAX_VALUE - 1 ? dp[N][amount] : -1;
    }

    //longest common subsequence
    //https://leetcode.com/problems/longest-common-subsequence/

    //recursive
    public int longestCommonSubsequence(String a, String b) {
        return lcs(a, b, a.length(), b.length());
    }

    public int lcs(String a, String b, int m, int n) {
        if (m == 0 || n == 0) {
            return 0;
        }
        if (a.charAt(m - 1) == b.charAt(n - 1)) {
            return 1 + lcs(a, b, m - 1, n - 1);
        } else {
            return Math.max(lcs(a, b, m - 1, n), lcs(a, b, m, n - 1));
        }
    }

    //memoization
    public int longestCommonSubsequence2(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                dp[i][j] = -1;
            }
        }
        return lcsMem(a, b, m, n, dp);
    }

    public int lcsMem(String a, String b, int m, int n, int[][] dp) {
        if (m == 0 || n == 0) {
            return 0;
        }
        if (dp[m][n] != -1) {
            return dp[m][n];
        }
        if (a.charAt(m - 1) == b.charAt(n - 1)) {
            return dp[m][n] = 1 + lcsMem(a, b, m - 1, n - 1, dp);
        } else {
            return dp[m][n] = Math.max(lcsMem(a, b, m - 1, n, dp), lcsMem(a, b, m, n - 1, dp));
        }
    }

    //tabulation
    public int longestCommonSubsequence3(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    //longest common substring
    //a variation of lcs
    int longestCommonSubstr(String a, String b, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        int length = 0;
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                }
                if (dp[i][j] > length) {
                    length = dp[i][j];
                }

            }
        }
        return length;
    }

    //longest palindromic substring
    //https://leetcode.com/problems/longest-palindromic-substring/submissions/

    //dp approach (O(N2))
    //here we combine three approaches - lps, lcsubstring, & print lcs ie we reverse the given string, then we find the
    // longest commomn substring between them and then print it. Here a problem might occur that there might be a case where
    // the common substring between a string and its reverse is not palindromic. Thus we have to additionally check if the
    // resultant string is palindromic and largest. Moreover, some cases are not passing so there might be a bug in the code
    // which I'm currently not able to find. The expand around center approach works better for this problem as it does not take any space

        public String longestPalindrom(String a) {
            int n=a.length();
            int[][] dp=new int[n+1][n+1];
            for(int i=0;i<n+1;i++) {
                for (int j = 0; j < n + 1; j++) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 0;
                    }
                }
            }
            StringBuilder sb=new StringBuilder(a);
            String b=sb.reverse().toString();
            longestPalinSub(a,b,dp,n);
            StringBuilder ans=new StringBuilder();
            int i=n;
            int j=n;
            while(i>0&&j>0){
                if(a.charAt(i-1)==b.charAt(i-1)){
                    ans.append(a.charAt(i-1));
                    i--;
                    j--;
                }
                else if(dp[i-1][j]>dp[i][j-1]){
                    i--;
                }
                else{
                    j--;
                }
            }
            return ans.reverse().toString();
        }
        public void longestPalinSub(String a, String b, int[][] dp, int n){
            for(int i=1;i<n+1;i++){
                for(int j=1;j<n+1;j++){
                    if(a.charAt(i-1)==b.charAt(i-1)){
                        dp[i][j]=1+dp[i-1][j-1];
                    }
                    else{
                        dp[i][j]=0;
                    }
                }
            }
        }
        //better approach
    //O(N2) - using expand arouund center approach for odd and even lengths of palindromes. We know that a palindrome can
    // have an even length or an odd length. Thus for both cases we take two pointers left and right. In the odd case,
    // we know that there's always going to be an middle element in the palindrome and thus we put left at i-1 and right at i+1
    // for each element and check if they are equal. If they are, we keep iterating left-- and right++ till they are not equal,
    // or they have gone out of bounds. After their iteration is completed we calculate the length of the current palindrome using r-l-1
    // and set two pointers start and end at the end of the palindrome if the obtained length is > max len ie they store the start and
    // end positions of the longest palindromic substring. We do the same for even palindromes but since they don't have any middle element,
    // we directly start comparing l and r by  placing them at i and i+1. Rest procedure is same and in the end we just return a substring from start till end
        public String longestPalindrome(String s) {
            if(s.length()<=1){
                return s;
            }
            int maxLen=1;
            int n=s.length();
            int start=0;
            int end=0;
            for(int i=0;i<n-1;i++){
                int l=i;
                int r=i;
                while(l>=0&&r<n){
                    if(s.charAt(l)==s.charAt(r)){
                        l--;
                        r++;
                    }
                    else{
                        break;
                    }
                }
                int len=r-l-1;
                if(len>maxLen){
                    maxLen=len;
                    start=l+1; //start of palindrome
                    end=r-1; //end of palindrome
                }
            }
            for(int i=0;i<n-1;i++){
                int l=i;
                int r=i+1;
                while(l>=0&&r<n){
                    if(s.charAt(l)==s.charAt(r)){
                        l--;
                        r++;
                    }
                    else{
                        break;
                    }
                }
                int len=r-l-1;
                if(len>maxLen){
                    maxLen=len;
                    start=l+1;
                    end=r-1;
                }
            }
            return s.substring(start,end+1);
        }


    //print lcs
    public String printLcs(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        int i = m;
        int j = n;
        StringBuilder sb = new StringBuilder();
        while (i > 0 && j > 0) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                sb.append(a.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i][j - 1] > dp[i - 1][j]) {
                j--;
            } else {
                i--;
            }
        }
        sb.reverse();
        return sb.toString();
    }

    //shortest common super-sequence
    //a variation of lcs
    //to return length of scs, we just return m+n-lcs
    //https://leetcode.com/problems/shortest-common-supersequence/
    public String shortestCommonSupersequence(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        int i = m;
        int j = n;
        StringBuilder sb = new StringBuilder();
        while (i > 0 && j > 0) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                sb.append(a.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i][j - 1] > dp[i - 1][j]) {
                sb.append(b.charAt(j - 1));
                j--;
            } else {
                sb.append(a.charAt(i - 1));
                i--;
            }
        }
        while (i > 0) {
            sb.append(a.charAt(i - 1));
            i--;
        }
        while (j > 0) {
            sb.append(b.charAt(j - 1));
            j--;
        }
        sb.reverse();
        return sb.toString();
    }

    //min number of insertion & deletions to convert string a to b
    //a variation of lcs
    //https://leetcode.com/problems/delete-operation-for-two-strings/
    public int minDistance(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        int del = m - dp[m][n];
        int insert = n - dp[m][n];
        return insert + del;
    }

    //longest palindromic subsequence
    //a variation of lcs
    //https://leetcode.com/problems/longest-palindromic-subsequence/
    public int longestPalindromeSubseq(String a) {
        StringBuilder sb = new StringBuilder(a);
        String b = sb.reverse().toString();
        int n = a.length();
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
            }
        }
        return dp[n][n];
    }

    //min number of insertions or deletions to make string palindromic
    //a variation of lcs
    //https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/
    public int minInsertions(String a) {
        StringBuilder sb = new StringBuilder(a);
        String b = sb.reverse().toString();
        int n = a.length();
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
            }
        }
        return n - dp[n][n];
    }

    //longest repeating subsequence
    //a variation of lcs
    //https://practice.geeksforgeeks.org/problems/longest-repeating-subsequence2004/1
    public int LongestRepeatingSubsequence(String a) {
        String b = a;
        int n = a.length();
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1) && i != j) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[n][n];
    }

    //sequence pattern matching or is 'a' a subsequence of 'b'
    //a variation of lcs
    //https://leetcode.com/problems/is-subsequence/
    public boolean isSubsequence(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n] == a.length();
    }

    //matrix chain multiplication
    //https://practice.geeksforgeeks.org/problems/matrix-chain-multiplication0303/1

    //recursive
    static int matrixMultiplication(int n, int arr[])
    {
        return solve(arr,1,n-1);
    }
    public static int solve(int[] arr, int i,int j){
        //base condition
        if(i>=j){
            return 0;
        }
        int min=Integer.MAX_VALUE;
        //implementing k scheme
        for(int k=i;k<j;k++){
            int tempAns=solve(arr,i,k)+solve(arr,k+1,j)+arr[i-1]*arr[k]*arr[j];
            min=Math.min(tempAns,min);
        }
        return min;
    }

    //memoization
    static int matrixMultiplication1(int n, int arr[])
    {
        int[][] dp=new int[n+1][n+1];
        for(int i=0;i<n+1;i++){
            for(int j=0;j<n+1;j++){
                dp[i][j]=-1;
            }
        }
        return solveMem(arr,1,n-1,dp);
    }
    public static int solveMem(int[] arr, int i,int j,int[][] dp){
        //base condition
        if(i>=j){
            return 0;
        }
        if(dp[i][j]!=-1){
            return dp[i][j];
        }
        int min=Integer.MAX_VALUE;
        //implementing k scheme
        for(int k=i;k<j;k++){
            int tempAns=solveMem(arr,i,k,dp)+solveMem(arr,k+1,j,dp)+arr[i-1]*arr[k]*arr[j];
            min=Math.min(tempAns,min);
        }
        return dp[i][j]=min;
    }

    //palindrome partitioning (min number of partitions)
    //a variation of mcm
    //https://practice.geeksforgeeks.org/problems/palindromic-patitioning4845/1

    //recursive
    static int palindromicPartition(String str){
        int n=str.length();
        return solveP(str,0,n-1);
    }
    static int solveP(String s,int i,int j){
        if(i>=j){
            return 0;
        }
        if(isPalindrome(s.substring(i,j+1))){
            return 0;
        }
        int min=Integer.MAX_VALUE;
        for(int k=i;k<j;k++){
            int tempAns=solveP(s,i,k)+solveP(s,k+1,j)+1;
            min=Math.min(tempAns,min);
        }
        return min;
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

    //memoization
    static int palindromicPartition1(String str){
        int n=str.length();
        int[][] dp=new int[n+1][n+1];
        for(int i=0;i<n+1;i++){
            for(int j=0;j<n+1;j++){
                dp[i][j]=-1;
            }
        }
        return solveP(str,0,n-1,dp);
    }
    static int solveP(String s,int i,int j,int[][] dp){
        if(i>=j){
            return 0;
        }
        if(dp[i][j]!=-1){ //remember to keep this before isPalindrome() function, to avoid calling isPalindrome if ans is already present
            return dp[i][j];
        }
        if(isPalindrome(s.substring(i,j+1))){
            return 0;
        }
        int min=Integer.MAX_VALUE;
        for(int k=i;k<j;k++){
            int tempAns=solveP(s,i,k,dp)+solveP(s,k+1,j,dp)+1;
            min=Math.min(tempAns,min);
        }
        return dp[i][j]=min;
    }

    //boolean parenthesization
    //https://practice.geeksforgeeks.org/problems/boolean-parenthesization5610/1

    //recursive
    static int countWays(int N, String S){
        return solve(S,0,N-1,true);
    }
    static int solve(String s, int i, int j, boolean isTrue){
        if(i>j){
            return 0;
        }
        int mod=1003;
        if(i==j){
            if(isTrue){
                return s.charAt(i)=='T'?1:0;
            }
            else{
                return s.charAt(i)=='F'?1:0;
            }
        }
        int ans=0;
        for(int k=i+1;k<j;k+=2){
            int lf=solve(s,i,k-1,false);
            int lt=solve(s,i,k-1,true);
            int rf=solve(s,k+1,j,false);
            int rt=solve(s,k+1,j,true);
            if(s.charAt(k)=='&'){
                if(isTrue){
                    ans+=lt*rt;
                }
                else{
                    ans+=(lf*rf)+(lt*rf)+(rt*lf);
                }
            }
            else if(s.charAt(k)=='|'){
                if(isTrue){
                    ans+=(lt*rt)+(lf*rt)+(lt*rf);
                }
                else{
                    ans+=lf*rf;
                }
            }
            else if(s.charAt(k)=='^'){
                if(isTrue){
                    ans+=(lf*rt)+(lt*rf);
                }
                else{
                    ans+=(lf*rf)+(lt*rt);
                }
            }
        }
        return ans%mod;
    }

    //memoization
    static int countWays1(int N, String S){
        int [][][] dp=new int[N+1][N+1][2];
        for(int i=0;i<N+1;i++){
            for(int j=0;j<N+1;j++){
                for(int k=0;k<2;k++){
                    dp[i][j][k]=-1;
                }
            }
        }
        return solve(S,0,N-1,1,dp);
    }
    static int solve(String s, int i, int j, int isTrue,int [][][] dp){
        if(i>j){
            return 0;
        }
        int mod=1003;
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
        int ans=0;
        for(int k=i+1;k<j;k+=2){
            int lf=solve(s,i,k-1,0,dp);
            int lt=solve(s,i,k-1,1,dp);
            int rf=solve(s,k+1,j,0,dp);
            int rt=solve(s,k+1,j,1,dp);
            if(s.charAt(k)=='&'){
                if(isTrue==1){
                    ans+=lt*rt;
                }
                else{
                    ans+=(lf*rf)+(lt*rf)+(rt*lf);
                }
            }
            else if(s.charAt(k)=='|'){
                if(isTrue==1){
                    ans+=(lt*rt)+(lf*rt)+(lt*rf);
                }
                else{
                    ans+=lf*rf;
                }
            }
            else if(s.charAt(k)=='^'){
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
    //https://leetcode.com/problems/scramble-string/

    //recursive
    public boolean isScramble(String s1, String s2) {
        if(s1.length()!=s2.length()){
            return false;
        }
        return solve(s1,s2);
    }
    public boolean solve(String a, String b){
        if(a.equals(b)){
            return true;
        }
        if(a.length()<1){
            return false;
        }
        int n=a.length();
        boolean flag=false;
        for(int i=1;i<n;i++){
            if(solve(a.substring(0,i),b.substring(n-i))&&solve(a.substring(i),b.substring(0,n-i))){ //swap
                flag=true;
                break;
            }
            else if((solve(a.substring(0,i),b.substring(0,i))&&solve(a.substring(i),b.substring(i)))){ //no swap
                flag=true;
                break;
            }
        }
        return flag;
    }

    //memoization
    public boolean isScramble1(String s1, String s2) {
        if(s1.length()!=s2.length()){
            return false;
        }
        Map<String,Boolean> map=new HashMap<>();
        return solve(s1,s2,map);
    }
    public boolean solve(String a, String b, Map<String,Boolean> map){
        if(a.equals(b)){
            return true;
        }
        if(a.length()<1){
            return false;
        }
        String key=a+" "+b;
        if(map.containsKey(key)){
            return map.get(key);
        }
        boolean flag=false;
        int n=a.length();
        for(int i=1;i<n;i++){
            if(solve(a.substring(0,i),b.substring(n-i),map)&&solve(a.substring(i),b.substring(0,n-i),map)){ //swap
                flag=true;
                break;
            }
            else if((solve(a.substring(0,i),b.substring(0,i),map)&&solve(a.substring(i),b.substring(i),map))){ //no swap
                flag=true;
                break;
            }
        }
        map.put(key,flag);
        return map.get(key);
    }


    //egg dropping problem
    //https://leetcode.com/problems/super-egg-drop/

    //recursive
    public int superEggDrop(int e, int f) {
        if(f==0||f==1||e==1){
            return f;
        }
        int min=Integer.MAX_VALUE;
        for(int k=1;k<=f;k++){
            int tempAns=1+Math.max(superEggDrop(e-1,k-1),superEggDrop(e,f-k)); //max because we have to calculate value for worst possible case
            min=Math.min(min,tempAns);
        }
        return min;
    }

    //memoization
    public int superEggDrop1(int e,int f){
        int[][] dp=new int[e+1][f+1];
        for(int[] i:dp){
            Arrays.fill(i,-1);
        }
        return solve(e,f,dp);
    }
    public int solve(int e, int f,int[][] dp) {
        if(f==0||f==1||e==1){
            return f;
        }
        if(dp[e][f]!=-1){
            return dp[e][f];
        }
        int min=Integer.MAX_VALUE;
        int l=1;
        int h=f;
        while(l<=h){ //using binary search instead of linear search to find the optimal value (leetcode gives tle with basic linear search)
            int mid=l+((h-l)/2);
            int left=solve(e-1,mid-1,dp);
            int right=solve(e,f-mid,dp);
            int tempAns=1+Math.max(left,right); //max because we have to calculate value for worst possible case
            if(left<right){
                l=mid+1;
            }
            else{
                h=mid-1;
            }
            min=Math.min(min,tempAns);
        }
        return dp[e][f]=min;
    }

    //dp on trees

    //diameter of a binary tree
    //https://leetcode.com/problems/diameter-of-binary-tree/
    public class TreeNode{
        private TreeNode left;
        private TreeNode right;
        private int val;
        public TreeNode(int val) {
            this.val = val;
        }
    }
    class Solution {
        int res=Integer.MIN_VALUE; //pass by ref not allowed in java
        public int diameterOfBinaryTree(TreeNode root) {
            solve(root);
            return res-1;
        }
        public int solve(TreeNode root){
            if(root==null){
                return 0;
            }
            int left=solve(root.left);
            int right=solve(root.right);
            int tempAns=1+Math.max(left,right);
            int ans=Math.max(tempAns,1+left+right);
            res=Math.max(ans,res);
            return tempAns; //we return temp because we generally need 1+max(left,right) to calc heights in further recursion calls
        }
    }

    //max path sum from any node to any other node
    //https://leetcode.com/problems/binary-tree-maximum-path-sum/
    class Sol {
        public int res = Integer.MIN_VALUE;
        public int maxPathSum(TreeNode root) {
            solve(root);
            return res;
        }

        public int solve(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int left = solve(root.left);
            int right = solve(root.right);
            int tempAns = Math.max(Math.max(left, right) + root.val, root.val); //in case any nodes from the left and right
            // subtrees are negative, we only take value of the current node
            int ans = Math.max(tempAns, left + right + root.val);
            res = Math.max(res, ans);
            return tempAns;
        }
    }

    //max path sum from any leaf to leaf node
    //https://practice.geeksforgeeks.org/problems/maximum-path-sum/1

}