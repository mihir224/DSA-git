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

    ///longest common substring
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

    //print lcs
    public String printLcs(String a,String b){
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
        int del=m-dp[m][n];
        int insert=n-dp[m][n];
        return insert+del;
    }

    //longest palindromic subsequence
    //https://leetcode.com/problems/longest-palindromic-subsequence/
    public int longestPalindromeSubseq(String a) {
        StringBuilder sb=new StringBuilder(a);
        String b=sb.reverse().toString();
        int n=a.length();
        int[][] dp=new int[n+1][n+1];
        for(int i=0;i<n+1;i++){
            for(int j=0;j<n+1;j++){
                if(i==0||j==0){
                    dp[i][j]=0;
                }
            }
        }
        for(int i=1;i<n+1;i++){
            for(int j=1;j<n+1;j++){
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j]=1+dp[i-1][j-1];
                }
                else
                    dp[i][j]=Math.max(dp[i][j-1],dp[i-1][j]);
            }
        }
        return dp[n][n];
    }

}
