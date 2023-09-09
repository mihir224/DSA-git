import javax.lang.model.type.ArrayType;
import java.util.*;

class DP {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
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
        int pref = 1;
        int suf = 1;
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (pref == 0) {
                pref = 1;
            }
            if (suf == 0) {
                suf = 1;
            }
            pref = pref * nums[i];
            suf = suf * nums[nums.length - i - 1];
            ans = Math.max(ans, Math.max(pref, suf));
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
        int n = nums.length;
        return helper(0, -1, n, nums);
    }

    public int helper(int index, int prev_index, int n, int[] nums) {
        if (index == n) { //traversed the whole array
            return 0;
        }
        int len = 0;
        if (prev_index == -1 || nums[index] > nums[prev_index]) {
            len = 1 + helper(index + 1, index, n, nums); //pick
        }
        len = Math.max(len, 0 + helper(index + 1, prev_index, n, nums)); //taking the max from pick and not pick
        return len;
    }


    //memoization
    //O(N2)

    public int lengthOfLIS1(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n + 1];
        //we always take prev_index + 1 in the dp table because for prev_index=-1, it would give out of bounds error (this is called coordinate shift)
        // and for this  reason we make dp table of size n*n+1
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return helperLis(0, -1, dp, n, nums);
    }

    public int helperLis(int index, int prev_index, int[][] dp, int n, int[] nums) {
        if (index == n) {
            return 0;
        }
        if (dp[index][prev_index + 1] != -1) {
            return dp[index][prev_index + 1]; //+1 because prevIndex can be -1, thus we store it as 0 based
        }
        int len = 0;
        if (prev_index == -1 || nums[index] > nums[prev_index]) {
            len = 1 + helperLis(index + 1, index, dp, n, nums); //pick
        }
        len = Math.max(len, 0 + helperLis(index + 1, prev_index, dp, n, nums)); //taking the max from pick and not pick
        return dp[index][prev_index + 1] = len;
    }

    //tabulation
    //since we moved from start till end in the recursive and memoized solutions, we do the exact opposite here.
    //O(N2)

    public int lengthOfLIS2(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n + 1][n + 1]; // i is also n+1 here we the base case is for index==n thus we have to take index==n into account
        //base case in recursion and memoization was that if index==n, we return 0. Here, initially every row and col is 0
        // and thus we don't have to explicitly initialise the dp with the base case
        for (int index = n - 1; index >= 0; index--) {
            for (int prev_index = index - 1; prev_index >= -1; prev_index--) {
                int len = 0;
                if (prev_index == -1 || nums[index] > nums[prev_index]) {
                    len = 1 + dp[index + 1][index + 1];
                }
                dp[index][prev_index + 1] = Math.max(len, 0 + dp[index + 1][prev_index + 1]);
            }
        }
        return dp[0][-1 + 1];
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
        int n = nums.length;
        int max = Integer.MIN_VALUE;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (nums[prev] < nums[i]) { //prev is valid
                    dp[i] = Math.max(dp[i], 1 + dp[prev]); //this approach works in such a way that 1+dp[prev] would at min be = dp[i]. It will never be less than dp[i] and thus the current element can form
                }
            }
        }
        for (int i : dp) {
            max = Math.max(max, i);
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


    public ArrayList<Integer> longestIncreasingSubsequence(int n, int nums[]) {
        int max = Integer.MIN_VALUE;
        ArrayList<Integer> lis = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int lastIndex = 0;
        int[] dp = new int[n];
        int[] hash = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 0; i < n; i++) {
            hash[i] = i;
        }
        for (int i = 0; i < nums.length; i++) {
            for (int prev = 0; prev < i; prev++) {
                if ((nums[prev] < nums[i]) && (1 + dp[prev] > dp[i])) { //prev is valid. here we check beforehand if dp[prev]+1 is
                    // greater than dp[i] since we have to assign prev to hash[i] accordingly
                    dp[i] = 1 + dp[prev];
                    hash[i] = prev;
                }
            }
            if (dp[i] > max) {
                max = dp[i];
                lastIndex = i;
            }
        }
        lis.add(0, nums[lastIndex]);
        while (hash[lastIndex] != lastIndex) { //backtracking
            lastIndex = hash[lastIndex];
            lis.add(0, nums[lastIndex]);
        }
        return lis;
    }

    //lcs
    //https://leetcode.com/problems/longest-common-subsequence/

    //recursive (string matching)
    public int longestCommonSubsequence(String a, String b) {
        int m = a.length();
        int n = b.length();
        return lcs(a, b, m, n);
    }

    public int lcs(String a, String b, int m, int n) {
        //base case
        if (m == 0 || n == 0) { //ie if we have traversed either of the two strings completely, there'll be no lcs
            return 0;
        }
        if (a.charAt(m - 1) == b.charAt(n - 1)) {
            return 1 + lcs(a, b, m - 1, n - 1);
        }
        return Math.max(lcs(a, b, m - 1, n), lcs(a, b, m, n - 1));
    }

    //memoization
    public int longestCommonSubsequence1(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        lcs1(a, b, dp, m, n);
        return dp[m][n];
    }

    public int lcs1(String a, String b, int[][] dp, int m, int n) {
        //base case
        if (m == 0 || n == 0) { //ie if we have traversed either of the two strings completely, there'll be no lcs
            return 0;
        }
        if (dp[m][n] != -1) {
            return dp[m][n];
        }
        if (a.charAt(m - 1) == b.charAt(n - 1)) {
            return dp[m][n] = 1 + lcs(a, b, m - 1, n - 1);
        }
        return dp[m][n] = Math.max(lcs(a, b, m - 1, n), lcs(a, b, m, n - 1));
    }

    //tabulation
    public int longestCommonSubsequence2(String a, String b) {
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

    //O/1 knapsack
    //https://practice.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1

    //recursive
    static int knapSack(int W, int wt[], int val[], int n) {
        return zeroOne(wt, val, W, n);
    }

    static int zeroOne(int[] wt, int[] val, int W, int n) {
        // base case
        if (W == 0 || n == 0) {
            return 0;
        }
        if (wt[n - 1] <= W) {
            return Math.max(val[n - 1] + zeroOne(wt, val, W - wt[n - 1], n - 1), zeroOne(wt, val, W, n - 1));
        } else {
            return zeroOne(wt, val, W, n - 1);
        }
    }

    //memoization
    static int knapSac1k(int W, int wt[], int val[], int n) {
        int[][] dp = new int[n + 1][W + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        zeroOne1(wt, val, dp, W, n);
        return dp[n][W];
    }

    static int zeroOne1(int[] wt, int[] val, int[][] dp, int W, int n) {
        // base case
        if (W == 0 || n == 0) {
            return 0;
        }
        if (dp[n][W] != -1) {
            return dp[n][W];
        }
        if (wt[n - 1] <= W) {
            return dp[n][W] = Math.max(val[n - 1] + zeroOne1(wt, val, dp, W - wt[n - 1], n - 1), zeroOne1(wt, val, dp, W, n - 1));
        } else {
            return dp[n][W] = zeroOne1(wt, val, dp, W, n - 1);
        }
    }

    //tabulation
    static int knapSac2k(int W, int wt[], int val[], int n) {
        int[][] dp = new int[n + 1][W + 1];
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
        return ed(word1.length(), word2.length(), word1, word2);
    }

    public int ed(int i, int j, String a, String b) {
        if (i == 0) { //ie string a has 0 length
            return j;
        }
        if (j == 0) { //ie string b has 0 length
            return i;
        }
        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            return 0 + ed(i - 1, j - 1, a, b);
        }
        return 1 + Math.min(ed(i, j - 1, a, b), Math.min(ed(i - 1, j, a, b), ed(i - 1, j - 1, a, b)));
    }

    //memoization
    //O(m*n) time O((m*n)+max(m,n)) space

    public int minDistance1(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return ed1(m, n, dp, a, b);
    }

    public int ed1(int i, int j, int[][] dp, String a, String b) {
        if (i == 0) { //ie string a has 0 length
            return j;
        }
        if (j == 0) { //ie string b has 0 length
            return i;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        if (a.charAt(i - 1) == b.charAt(j - 1)) { //mismatch
            return dp[i][j] = 0 + ed1(i - 1, j - 1, dp, a, b);
        }
        return dp[i][j] = 1 + Math.min(ed1(i, j - 1, dp, a, b), Math.min(ed1(i - 1, j, dp, a, b), ed1(i - 1, j - 1, dp, a, b)));
    }

    //tabulation
    //O(m*n) time and space
    public int minDistance2(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        //converting base case to initialisation of dp table
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                if (j == 0) {
                    dp[i][j] = i;
                }
            }
        }
        //recurrence
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = 0 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }
        return dp[m][n];
    }

    //maximum increasing subsequence
    //brute would be to find all possible increasing subsequences through recursion (we track prev index to check for increasing
    // subsequence), tracking their sum and returning the max one

    public int maxSumIS(int arr[], int n) {
        return helperMis(0, -1, arr, n);
    }

    public int helperMis(int currentIndex, int prevIndex, int[] arr, int n) {
        if (currentIndex == n) {
            return 0;
        }
        int sum = 0;
        if (prevIndex == -1 || arr[currentIndex] > arr[prevIndex]) { //pick
            sum = arr[currentIndex] + helperMis(currentIndex + 1, currentIndex, arr, n);
        }
        sum = Math.max(sum, helperMis(currentIndex + 1, prevIndex, arr, n)); //comparing with not pick
        return sum;
    }

    //memoization
    //O(N2)time, O(N2+N) space
    public int maxSumIS1(int arr[], int n) {
        int[][] dp = new int[n][n + 1];
        for (int[] nums : dp) {
            Arrays.fill(nums, -1);
        }
        return helperMi1s(0, -1, arr, n, dp);
    }

    public int helperMi1s(int currentIndex, int prevIndex, int[] arr, int n, int[][] dp) {
        if (currentIndex == n) {
            return 0;
        }
        int sum = 0;
        if (dp[currentIndex][prevIndex + 1] != -1) {
            return dp[currentIndex][prevIndex + 1];
        }
        if (prevIndex == -1 || arr[currentIndex] > arr[prevIndex]) { //pick
            sum = arr[currentIndex] + helperMi1s(currentIndex + 1, currentIndex, arr, n, dp);
        }
        sum = Math.max(sum, helperMi1s(currentIndex + 1, prevIndex, arr, n, dp)); //comparing with not pick
        return dp[currentIndex][prevIndex + 1] = sum;
    }

    //we can space optimize by using the same approach as we did in LIS ie to use a 1d dp table where dp[i] would give
    // us the max increasing sum of a subsequence that ends with arr[i]

    //O(N2)time, O(N) space

    public int maxSumIS2(int arr[], int n) {
        int[] dp = new int[n];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            dp[i] = arr[i]; //because in the worst case(if the array is in decreasing order) we can only take the ith
            // element itself as the part of the subsequence, thus min possible sum is the element itself
        }
        for (int i = 0; i < arr.length; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (arr[i] > arr[prev]) { //valid prev
                    dp[i] = Math.max(dp[i], dp[prev] + arr[i]);
                }
            }
            if (dp[i] > max) {
                max = dp[i];
            }
        }
        return max;
    }

    //matrix chain multiplication
    //https://practice.geeksforgeeks.org/problems/matrix-chain-multiplication0303/1

    //recursive
    static int matrixMultiplication(int N, int arr[]) {
        return mm(1, N - 1, arr, N);
    }

    static int mm(int i, int j, int[] arr, int N) {
        if (i >= j) {
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            int tempAns = mm(i, k, arr, N) + mm(k + 1, j, arr, N) + arr[i - 1] * arr[k] * arr[j];
            ans = Math.min(ans, tempAns);
        }
        return ans;
    }

    //memoization
    static int matrixMultiplication1(int N, int arr[]) {
        int[][] dp = new int[N + 1][N + 1];
        for (int[] nums : dp) {
            Arrays.fill(nums, -1);
        }
        return mm1(1, N - 1, arr, N, dp);
    }

    static int mm1(int i, int j, int[] arr, int N, int[][] dp) {
        if (i >= j) {
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int ans = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            int tempAns = mm1(i, k, arr, N, dp) + mm1(k + 1, j, arr, N, dp) + arr[i - 1] * arr[k] * arr[j];
            ans = Math.min(ans, tempAns);
        }
        return dp[i][j] = ans;
    }


    //count of subsets with given sum
    //https://practice.geeksforgeeks.org/problems/perfect-sum-problem5633/1

    //recursive

    //we have to also consider the case when sum is 0 and 0 is present in the array because in that case along with
    // an empty subset, the subset containing 0's also count. for eg if sum is 0 and there's a 0 in the array then the
    // number of valid subsets are 2 - {} and {0}. This count increases by a factor of 2. Like if there were 2 zeroes instead
    // of one in the array then the count would've been 4 - {},{0},{0},{0,0}. Thus while picking an element, we skip it if
    // it is zero and after the recursive function has returned its answer, we multiply it by 2^(no. of zeroes) to get the
    // correct ans (with consideration of zeroes). I have used this analogy in the recursive code only. In tabulation,
    // handling this case is much easier as while filling the dp table, for the sum, even though we initialise its base
    // case (ie when sum = 0 count is 1 ie an empty subset (assuming array has no zeroes)), we start it from 0 again
    // instead of 1 to cover the case when sum is 0 and there is a 0 in the array.

    public int perfectSu1m(int arr[], int n, int sum) {
        int mod = (int) 1e9 + 7;
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] == 0) {
                count++;
            }
        }
        return ((int) Math.pow(2, count) * helperPs(arr, n, sum)) % mod;
    }

    public int helperPs(int[] arr, int n, int sum) {
        if (sum == 0) {
            return 1;
        }
        if (n == 0) {
            return 0;
        }

        if (arr[n - 1] <= sum && arr[n - 1] != 0) { //we don't consider the presence of 0 in the array and skip it because we deal with it separately
            return helperPs(arr, n - 1, sum - arr[n - 1]) + helperPs(arr, n - 1, sum);
        }
        return helperPs(arr, n - 1, sum);
    }

    //memoization
    public int perfectSum1(int arr[], int n, int sum) {
        int mod = (int) 1e9 + 7;
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] == 0) {
                count++;
            }
        }
        int[][] dp = new int[n + 1][sum + 1];
        for (int[] nums : dp) {
            Arrays.fill(nums, -1);
        }
        return ((int) Math.pow(2, count) * helperPs1(arr, n, sum, dp)) % mod;
    }

    public int helperPs1(int[] arr, int n, int sum, int[][] dp) {
        int mod = (int) 1e9 + 7;
        if (sum == 0) {
            return 1;
        }
        if (n == 0) {
            return 0;
        }
        if (dp[n][sum] != -1) {
            return dp[n][sum];
        }
        if (arr[n - 1] <= sum && arr[n - 1] != 0) { //we don't consider the presence of 0 in the array and skip it because we deal with it separately
            return dp[n][sum] = (helperPs1(arr, n - 1, sum - arr[n - 1], dp) + helperPs1(arr, n - 1, sum, dp)) % mod;
        }
        return dp[n][sum] = helperPs1(arr, n - 1, sum, dp) % mod;
    }

    //tabulation
    public int perfectSum(int arr[], int n, int sum) {
        int mod = (int) 1e9 + 7;
        int count = 0;
        int[][] dp = new int[n + 1][sum + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < sum + 1; j++) {
                if (i == 0) {
                    dp[i][j] = 0;
                }
                if (j == 0) {
                    dp[i][j] = 1;
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < sum + 1; j++) {
                if (arr[i - 1] <= j) {
                    dp[i][j] = (dp[i - 1][j - arr[i - 1]] + dp[i - 1][j]) % mod;
                } else {
                    dp[i][j] = dp[i - 1][j] % mod;
                }
            }
        }
        return dp[n][sum] % mod;
    }

    //minimum subset sum difference
    //https://practice.geeksforgeeks.org/problems/minimum-sum-partition3317/1?utm_source=geeksforgeeks&utm_medium=article_practice_tab&utm_campaign=article_practice_tab
    public int minDifference(int arr[], int n) {
        int range = 0;
        for (int i = 0; i < arr.length; i++) {
            range += arr[i];
        }
        boolean[][] dp = new boolean[n + 1][range + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < range + 1; j++) {
                if (i == 0) {
                    dp[i][j] = false;
                }
                if (j == 0) {
                    dp[i][j] = true;
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < range + 1; j++) {
                if (arr[i - 1] <= j) {
                    dp[i][j] = dp[i - 1][j - arr[i - 1]] || dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < (range / 2) + 1; i++) {
            if (dp[n][i] == true) {
                list.add(i);
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            ans = Math.min(ans, range - (2 * list.get(i)));
        }
        return ans;
    }

    //count of subsets with given difference (target sum)
    //https://leetcode.com/problems/target-sum/

    public int findTargetSumWays(int[] nums, int target) {
        int s = 0;
        for (int i : nums) {
            s += i;
        }
        if ((s + target) % 2 != 0) {
            return 0;
        }
        int s1 = (s + target) / 2;
        if (s1 < 0) {
            return 0;
        }
        return perfectSum(nums, nums.length, s1);
    }

    //minimum path sum
    //https://leetcode.com/problems/minimum-path-sum/

    //recursive - finding cost of all possible paths, returning the minimum one

    //in this solution, we try to move top down, ie from the last cell of destination to the source
    public int minPathSum(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        return helperMps(n - 1, m - 1, grid);
    }

    public int helperMps(int i, int j, int[][] grid) {
        if (i == 0 && j == 0) { //reached src
            return grid[i][j];
        }
        if (i < 0 || j < 0) {
            return Integer.MAX_VALUE - 1;
        }
        return grid[i][j] + Math.min(helperMps(i - 1, j, grid), helperMps(i, j - 1, grid)); //finding min from both up and left
        // recursive calls and adding cost of current cell to it
    }

    //memoization
    public int minPathSum1(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n][m];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return helperMp1s(n - 1, m - 1, grid, dp);
    }

    public int helperMp1s(int i, int j, int[][] grid, int[][] dp) {
        if (i == 0 && j == 0) { //reached src
            return grid[i][j];
        }
        if (i < 0 || j < 0) {
            return Integer.MAX_VALUE - 1;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        return dp[i][j] = grid[i][j] + Math.min(helperMp1s(i - 1, j, grid, dp), helperMp1s(i, j - 1, grid, dp)); //finding min from both up and left
        // recursive calls and adding cost of current cell to it
    }

    //tabulation
    public int minPathSum2(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n][m];
        //here we have the base case as when i==0&&j==0 we return grid[i][j], ie for every value of j we don't have to return 0
        // when i=0 and vice versa and thus we can initialise and fill the table simultaneously
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = grid[i][j];
                } else {
                    int up = grid[i][j];
                    if (i > 0) {
                        up += dp[i - 1][j]; //if we're at 0th row, there's no way we can go upward
                    } else {
                        up += (int) 1e9;
                    }
                    int left = grid[i][j];
                    if (j > 0) {
                        left += dp[i][j - 1];
                    } else { //ie we're at 0th col, there's no way moving left
                        left += (int) 1e9;
                    }
                    dp[i][j] = Math.min(left, up);
                }
            }
        }
        return dp[n - 1][m - 1];
    }


    //coin change (min no. of coins)
    //https://leetcode.com/problems/coin-change/

    //recursive
    public int coinChang1e(int[] coins, int amount) {
        int ans = helperC1C(coins, amount, coins.length);
        return ans >= Integer.MAX_VALUE - 1 ? -1 : ans;
    }

    public int helperC1C(int[] coins, int amount, int n) {
        if (n == 0) { //no coins, thus no. of coins req to form amount is maximum
            return Integer.MAX_VALUE - 1;
        }
        if (amount == 0) {
            return 0;
        }
        if (coins[n - 1] <= amount) {
            return Math.min(1 + helperC1C(coins, amount - coins[n - 1], n), helperC1C(coins, amount, n - 1));
        }
        return helperC1C(coins, amount, n - 1); //can't take
    }

    //memoization
    public int coinChange1(int[] coins, int amount) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        int ans = helperCC(coins, amount, n, dp);
        return ans >= Integer.MAX_VALUE - 1 ? -1 : ans;
    }

    public int helperCC(int[] coins, int amount, int n, int[][] dp) {
        if (n == 0) { //no coins, thus no. of coins req to form amount is maximum
            return Integer.MAX_VALUE - 1;
        }
        if (amount == 0) {
            return 0;
        }
        if (dp[n][amount] != -1) {
            return dp[n][amount];
        }
        if (coins[n - 1] <= amount) {
            return dp[n][amount] = Math.min(1 + helperCC(coins, amount - coins[n - 1], n, dp), helperCC(coins, amount, n - 1, dp));
        }
        return dp[n][amount] = helperCC(coins, amount, n - 1, dp); //instead of writing this call twice, we can first store
        // the res from pick call and then compare it with this not pick call thus returning the min of both
    }

    //tabulation
    public int coinChange(int[] coins, int amount) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < amount + 1; j++) {
                if (i == 0) {
                    dp[i][j] = Integer.MAX_VALUE - 1;
                }
                if (j == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < amount + 1; j++) {
                if (coins[i - 1] <= j) {
                    dp[i][j] = Math.min(1 + dp[i][j - coins[i - 1]], dp[i - 1][j]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][amount] >= Integer.MAX_VALUE - 1 ? -1 : dp[n][amount];
    }

    //equal sum partition
    //https://leetcode.com/problems/partition-equal-subset-sum

    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if (sum % 2 != 0) {
            return false;
        }
        return subsetSum(sum / 2, nums);
    }

    //this subset sum problem is entirely different from the one we did earlier in which we had to find all possible
    // subset sums. In this we have to check whether there's a subset in the given array which has the same sum as the
    // given sum

    //recursive
    public boolean subsetSum1(int sum, int[] nums) {
        return helperSS(sum, nums, nums.length);
    }

    public boolean helperSS(int sum, int[] nums, int n) {
        if (sum == 0) {
            return true;
        }
        if (n == 0) {
            return false;
        }
        if (nums[n - 1] <= sum) { //can pick
            return helperSS(sum - nums[n - 1], nums, n - 1) || helperSS(sum, nums, n - 1);
        }
        return helperSS(sum, nums, n - 1);
    }

    //tabulation
    public boolean subsetSum(int sum, int[] nums) {
        int n = nums.length;
        boolean[][] dp = new boolean[n + 1][sum + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < sum + 1; j++) {
                if (i == 0) { //n is 0, ie false
                    dp[i][j] = false;
                }
                if (j == 0) { //sum 0, ie true
                    dp[i][j] = true;
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < sum + 1; j++) {
                if (nums[i - 1] <= j) { //can pick
                    dp[i][j] = dp[i - 1][j - nums[i - 1]] || dp[i - 1][j];
                } else { //can't pick
                    dp[i][j] = dp[i - 1][j];
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
    // then the sub problem would've given us the wrong ans because even though 2 belongs to the left partition, we would
    // unnecessarily be adding its cost in the right partition. Moreover if we had made a cut in sequene 4, 2, 5 without
    // sorting the cuts array then 5's recursive call would've been i=5 and j=5 where j+1 would point us to 2 and i-1 would
    // point us to 4 giving us the cost 2-4 which is wrong. Since we're using the partitioning ie
    // MCM approach, there'll be three pointers, i at the start, j at the end and k from i till j. Now to find the length
    // of the stick (ie cost) while making partition at a particular position, we use this analogy: We try to append a 0
    // at the start of the partitions array and the length 7 at the end. Now if we try to make a partition, say at position
    // 4 then we can basically obtain the length of the stick at that point as cuts[j+1]-cuts[i-1]. in this case that would
    // be 7-0 ie 7 since i is at 1 and j is at 5.

    //recursive

    public int minCost(int n, int[] cuts) {
        List<Integer> list = new ArrayList<>();
        Arrays.sort(cuts);
        for (int i : cuts) {
            list.add(i);
        }
        list.add(0, 0);
        list.add(n);
        return helperMc(1, cuts.length, list); //since after adding 0 and N, indices will be shifted and the last element
        // would now point to index cuts.length in the list
    }

    public int helperMc(int i, int j, List<Integer> list) {
        if (i > j) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (int k = i; k <= j; k++) {
            int tempAns = list.get(j + 1) - list.get(i - 1) + helperMc(i, k - 1, list) + helperMc(k + 1, j, list);
            min = Math.min(min, tempAns);
        }
        return min;
    }

    //memoization
    public int minCost1(int n, int[] cuts) {
        List<Integer> list = new ArrayList<>();
        int c = cuts.length;
        int[][] dp = new int[c + 1][c + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        Arrays.sort(cuts);
        for (int i : cuts) {
            list.add(i);
        }
        list.add(0, 0);
        list.add(n);
        return helperMc1(1, c, list, dp); //since after adding 0 and N, indices will be shifted and the last element
        // would now point to index cuts.length in the list
    }

    public int helperMc1(int i, int j, List<Integer> list, int[][] dp) {
        if (i > j) {
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int min = Integer.MAX_VALUE;
        for (int k = i; k <= j; k++) {
            int tempAns = list.get(j + 1) - list.get(i - 1) + helperMc1(i, k - 1, list, dp) + helperMc1(k + 1, j, list, dp);
            min = Math.min(min, tempAns);
        }
        return dp[i][j] = min;
    }

    //tabulation
    public int minCost2(int n, int[] cuts) {
        List<Integer> list = new ArrayList<>();
        int c = cuts.length;
        int[][] dp = new int[c + 2][c + 2];
        Arrays.sort(cuts);
        for (int i : cuts) {
            list.add(i);
        }
        list.add(0, 0);
        list.add(n);
        for (int i = c; i >= 1; i--) { //reversing i and j since this is bottom up
            for (int j = 1; j < c + 1; j++) {
                if (i > j) { //base case
                    continue;
                }
                int min = Integer.MAX_VALUE;
                for (int k = i; k <= j; k++) {
                    int tempAns = list.get(j + 1) - list.get(i - 1) + dp[i][k - 1] + dp[k + 1][j];
                    min = Math.min(min, tempAns);
                }
                dp[i][j] = min;
            }
        }
        return dp[1][c]; //since we have to find the ans for when i is 1 and j is c, basically this will give us the most
        // optimal ans when i is 1 and j is c which is what we were initially trying to find
    }

    //egg drop
    //https://practice.geeksforgeeks.org/problems/egg-dropping-puzzle-1587115620/1

    //recursive
    static int eggDrop(int e, int f) {
        return helperED(e, f);
    }

    static int helperED(int e, int f) {
        if (e == 1 || f == 0 || f == 1) {
            return f;
        }
        int min = Integer.MAX_VALUE;
        for (int k = 1; k <= f; k++) {
            int tempAns = 1 + Math.max(helperED(e - 1, k - 1), helperED(e, f - k)); //current attempt+case if egg breaks, thus checking
            // floors down+case if egg doesn't break, thus checking all floors up calculating the max of worst case for current floor
            min = Math.min(min, tempAns);
        }
        return min;
    }

    //memoization - although nominal soln works fine, but here we can add a check before each recursive call to check if its ans is already stored in
    // the dp table otherwise code might give tle for larger test cases. both approaches fine however.

    //nominal
    static int eggDro23p(int e, int f) {
        int[][] dp = new int[e + 1][f + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return helper(e, f, dp);
    }

    static int helper(int e, int f, int[][] dp) {
        if (e == 1 || f == 0 || f == 1) {
            return f;
        }
        if (dp[e][f] != -1) {
            return dp[e][f];
        }
        int ans = Integer.MAX_VALUE;
        for (int k = 1; k <= f; k++) {
            int tempAns = 1 + Math.max(helper(e - 1, k - 1, dp), helper(e, f - k, dp));
            ans = Math.min(ans, tempAns);
        }
        return dp[e][f] = ans;
    }

    //slightly optimised
    static int eggDro1p(int e, int f) {
        int[][] dp = new int[e + 1][f + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return helperE1D(e, f, dp);
    }

    static int eggDrop1(int e, int f) {
        int[][] dp = new int[e + 1][f + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return helperE1D(e, f, dp);
    }

    static int helperE1D(int e, int f, int[][] dp) {
        if (e == 1 || f == 0 || f == 1) {
            return f;
        }
        int min = Integer.MAX_VALUE;
        for (int k = 1; k <= f; k++) {
            int low = 0;
            int high = 0;
            if (dp[e - 1][k - 1] != -1) {
                low = dp[e - 1][k - 1];
            } else {
                low = helperE1D(e - 1, k - 1, dp);
                dp[e - 1][k - 1] = low;
            }
            if (dp[e][f - k] != -1) {
                high = dp[e][f - k];
            } else {
                high = helperE1D(e, f - k, dp);
                dp[e][f - k] = high;
            }
            int tempAns = 1 + Math.max(low, high);
            min = Math.min(min, tempAns);
        }
        return dp[e][f] = min;
    }

    //word break (leetcode version) - quite similar to wb that we did in recursion. also, this soln is memoized
    //https://leetcode.com/problems/word-break
    public boolean wordBreak(String s, List<String> wordDict) {
        return helper(0, s, wordDict, new HashMap<>());
    }

    public boolean helper(int index, String s, List<String> wordDict, HashMap<String, Boolean> map) {
        if (index > s.length()) {
            return true;
        }

        String key = s.substring(index); //will help identify if the ans for substring from index has already been calculated or not. The thing is that, if we had used index to store the ans from a particular call then that would've resulted in wrong ans because in each recursive call the size of the string is changing and thus for whatever index we would store the ans, in further recursive calls that index might be pointing to other characters and thus we'd return the wrong ans directly in those calls
        if (map.containsKey(key)) {
            return map.get(key);
        }
        int temp = s.length();
        for (int i = index + 1; i <= temp; i++) {
            String str = s.substring(index, i);
            if (wordDict.contains(str)) {
                if (i != temp)
                    s = s.substring(0, i) + " " + s.substring(i);
                if (helper(i + 1, s, wordDict, map)) {
                    map.put(key, true);
                    return true;
                }
                if (i != temp)
                    s = s.substring(0, i) + s.substring(i + 1);
            }
        }
        map.put(key, false);
        return false;
    }

    //palindrome partitioning (find min number of partitions required)
    //https://practice.geeksforgeeks.org/problems/palindromic-patitioning4845/1
    //recursive
    static int palindromicPartition(String str) {
        int n = str.length();
        return helperPP(0, n - 1, str);
    }

    public static int helperPP(int i, int j, String str) {
        if (i >= j) { //traversed the whole string. if i and j point to the same character then that would also mean a palindrome
            // and we'd require no more partitions thus we return 0
            return 0;
        }
        if (isPalindrome(str.substring(i, j + 1))) { //current substring is a valid palindrome, thus no need to partition this
            // substring further
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            int tempAns = 1 + helperPP(i, k, str) + helperPP(k + 1, j, str); //1 signifies the current partition
            min = Math.min(min, tempAns);
        }
        return min;
    }

    public static boolean isPalindrome(String str) {
        int i = 0;
        int n = str.length();
        while (i < n / 2) {
            if (str.charAt(i) != str.charAt(n - i - 1)) {
                return false;
            }
            i++;
        }
        return true;
    }

    //memoization
    static int palindromicPartition1(String str) {
        int n = str.length();
        int[][] dp = new int[n + 1][n + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return helperPP1(0, n - 1, str, dp);
    }

    public static int helperPP1(int i, int j, String str, int[][] dp) {
        if (i >= j) { //traversed the whole string. if i and j point to the same character then that would also mean a palindrome
            // and we'd require no more partitions thus we return 0
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        if (isPalindrome(str.substring(i, j + 1))) { //current substring is a valid palindrome, thus no need to partition this
            // substring further
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            int tempAns = 1 + helperPP1(i, k, str, dp) + helperPP1(k + 1, j, str, dp); //1 signifies the current partition
            min = Math.min(min, tempAns);
        }
        return dp[i][j] = min;
    }


    //Check if There is a Valid Partition For The Array
    //https://leetcode.com/problems/check-if-there-is-a-valid-partition-for-the-array
    //here the catch is that, if we start partitioning from the start, a valid subarray would be of length 2 or 3. if it
    // isn't then it means that the array cannot be partitioned as in this case we would never be able to partition the
    // string (as there's no subarray right from the start which is valid) and thus we'll return 0. otherwise, we'll try
    // to partition it further and if we're able to partition the entire array, we return true
    public boolean validPartition(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);

        return helper(0, nums, dp) == 1 ? true : false;
    }

    public int helper(int i, int[] nums, int[] dp) {
        if (i == nums.length) {
            return 1;
        }
        if (dp[i] != -1) {
            return dp[i];
        }
        int ans = 0;
        if (i + 1 < nums.length && nums[i] == nums[i + 1]) {
            ans = ans | helper(i + 2, nums, dp);
        }
        if (i + 2 < nums.length) {
            if (nums[i] == nums[i + 1] && nums[i + 1] == nums[i + 2]) {
                ans = ans | helper(i + 3, nums, dp);
            }
            if (nums[i] + 1 == nums[i + 1] && nums[i + 1] + 1 == nums[i + 2]) {
                ans = ans | helper(i + 3, nums, dp);
            }
        }
        return dp[i] = ans;
    }

    //longest increasing path (dp on grid)
    //https://leetcode.com/problems/longest-increasing-path-in-a-matrix

    //dfs - TLE
    public int longestIncreasingPath(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int max = 1;
        int[] delrow = {-1, 0, 1, 0};
        int[] delcol = {0, 1, 0, -1};
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max = Math.max(max, help1er(i, j, matrix, n, m, delrow, delcol));
            }
        }
        return max;
    }

    public int help1er(int row, int col, int[][] matrix, int n, int m, int[] delrow, int[] delcol) {
        int maxLen = 1;
        int len = 1;
        for (int i = 0; i < 4; i++) {
            int nrow = row + delrow[i];
            int ncol = col + delcol[i];
            if (nrow < n && nrow >= 0 && ncol < m && ncol >= 0 && matrix[nrow][ncol] > matrix[row][col]) {
                len = 1 + help1er(nrow, ncol, matrix, n, m, delrow, delcol);
                maxLen = Math.max(len, maxLen);
            }
        }
        return maxLen;
    }

    //memoized
    public int longestIncreasin2gPath(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int max = 1;
        int[] delrow = {-1, 0, 1, 0};
        int[] delcol = {0, 1, 0, -1};
        int[][] dp = new int[n][m];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max = Math.max(max, helper(i, j, matrix, n, m, delrow, delcol, dp));
            }
        }
        return max;
    }

    public int helper(int row, int col, int[][] matrix, int n, int m, int[] delrow, int[] delcol, int[][] dp) {
        if (dp[row][col] != -1) {
            return dp[row][col];
        }
        int maxLen = 1;
        int len = 1;
        for (int i = 0; i < 4; i++) {
            int nrow = row + delrow[i];
            int ncol = col + delcol[i];
            if (nrow < n && nrow >= 0 && ncol < m && ncol >= 0 && matrix[nrow][ncol] > matrix[row][col]) {
                len = 1 + helper(nrow, ncol, matrix, n, m, delrow, delcol, dp);
                maxLen = Math.max(len, maxLen);
            }
        }
        return dp[row][col] = maxLen; //stores max len from a particular cell
    }

    //we don't use greedy in these (dp) problems because there is non uniformity. a path might seem better at first but
    // in some cases following that path would make us miss out on other paths which were far better. thus it is imp for
    // us to explore all paths

    //frog jump (leetcode)

    //recursive - we start from the second stone (assuming the distance it took for us to come to this stone was 1) and then
    // we try to recursively jump to the rest of the stones by taking prevJump cost as 1 and the next jump cost as
    // k=stones[i]-stones[currentIndex]

    public boolean canCross(int[] stones) {
        return helper(1, 1, stones);
    }

    public boolean helper(int currentIndex, int prevJump, int[] stones) {
        if (currentIndex == 1 && stones[currentIndex] != 1) { //if the second stone isn't at 1 unit, we wouldn't be able to make
            // the first jump with a cost of 1 unit (this is required as per ques) and thus we return false
            return false;
        }
        if (currentIndex == stones.length - 1) {
            return true;
        }
        for (int i = currentIndex + 1; i < stones.length; i++) {
            int k = stones[i] - stones[currentIndex];
            if (k == prevJump + 1 || k == prevJump - 1 || k == prevJump) {
                if (helper(i, k, stones)) {
                    return true;
                }
            }
        }
        return false;
    }

    //memoized

    public boolean canCros1s(int[] stones) {
        int n = stones.length;
        int[][] dp = new int[n][1001];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return helper(1, 1, stones, dp) == 1 ? true : false;
    }

    public int helper(int currentIndex, int prevJump, int[] stones, int[][] dp) {
        if (currentIndex == 1 && stones[currentIndex] != 1) {
            return 0;
        }
        if (currentIndex == stones.length - 1) {
            return 1;
        }
        if (dp[currentIndex][prevJump] != -1) {
            return dp[currentIndex][prevJump];
        }
        for (int i = currentIndex + 1; i < stones.length; i++) {
            int k = stones[i] - stones[currentIndex];
            if (k == prevJump + 1 || k == prevJump - 1 || k == prevJump) {
                if (helper(i, k, stones, dp) == 1) {
                    return dp[currentIndex][prevJump] = 1;
                }
            }
        }
        return dp[currentIndex][prevJump] = 0;
    }

    //reducing dishes
    //https://leetcode.com/problems/reducing-dishes

    //recursive - O(NlogN+2^N)
    public int maxSatisfactio1n(int[] satisfaction) {
        Arrays.sort(satisfaction); //sorting the given array to maximize profits
        return helper1(0, 1, satisfaction);
    }

    public int helper1(int i, int time, int[] satisfaction) {
        if (i == satisfaction.length) {
            return 0;
        }
        int ltc = satisfaction[i] * time;
        int left = ltc + helper1(i + 1, time + 1, satisfaction);
        int right = helper1(i + 1, time, satisfaction);
        return Math.max(left, right);
    }

    //memoization
    public int maxSatisfaction(int[] satisfaction) {
        Arrays.sort(satisfaction); //sorting the given array to maximize profits
        int n = satisfaction.length;
        int[][] dp = new int[n + 1][n + 2];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return help1er(0, 1, satisfaction, dp);
    }

    public int help1er(int i, int time, int[] satisfaction, int[][] dp) {
        if (i == satisfaction.length) {
            return 0;
        }
        if (dp[i][time] != -1) {
            return dp[i][time];
        }
        int ltc = satisfaction[i] * time;
        int left = ltc + helper(i + 1, time + 1, satisfaction, dp);
        int right = helper(i + 1, time, satisfaction, dp);
        return dp[i][time] = Math.max(left, right);
    }

    //tabulation
    public int maxSatisf1action(int[] satisfaction) {
        Arrays.sort(satisfaction); //sorting the given array to maximize profits
        int n = satisfaction.length;
        int ans = 0;
        int[][] dp = new int[n + 1][n + 2]; //time at max can be n+1, when i becomes n and thus to accomodate that case we take
        // size of time as n+2
        for (int i = n; i >= 0; i--) {
            for (int j = n; j >= 1; j--) {
                if (i == n) {
                    dp[i][j] = 0;
                } else {
                    int ltc = satisfaction[i] * j;
                    int left = ltc + dp[i + 1][j + 1];
                    int right = dp[i + 1][j];
                    dp[i][j] = Math.max(left, right);
                }
            }
        }
        return dp[0][1];
    }

    //frog jump (coding ninja)
    //https://www.codingninjas.com/studio/problems/frog-jump_3621012?utm_source=striver&utm_medium=website&utm_campaign=a_zcoursetuf&leftPanelTab=1

    //greedy approach doesn't work here (we might ignore the best path)

    //brute approach with dp (2d dp) gives tle
    //basically we start with the 0th step and in each recursive call we store the height of the prev step. then we
    // recursively try to go to the next step and next of next step and whatever is the min from that, we add it to our
    // ans which stores the difference between the height of the current step and the prev step. moreover initially since
    // we're at the 0th step, so for that step we store the prev step's ht as -1

    public static int frogJump(int n, int heights[]) {
        int[][] dp = new int[n][1002];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return helper(0, -1, heights, n, dp);
    }

    public static int helper(int i, int prevVal, int[] heights, int n, int[][] dp) {
        if (i >= n) {
            return Integer.MAX_VALUE;
        }
        if (dp[i][prevVal + 1] != -1) {
            return dp[i][prevVal + 1];
        }
        if (i == n - 1) {
            return dp[i][prevVal + 1] = Math.abs(heights[i] - prevVal);
        }
        int ans = 0;
        if (prevVal != -1) {
            ans += Math.abs(heights[i] - prevVal);
        }
        return dp[i][prevVal + 1] = ans + Math.min(helper(i + 1, heights[i], heights, n, dp), helper(i + 2, heights[i], heights, n, dp));
    }

    //optimal (1d dp) O(N)
    //basically b4 jumping, we check beforehand if its possible to jump and then only we jump. thus no need to store prev
    // val in this

    //IMP!!!!!!
    //this is the ideal way of solving such problems. we beforehand check if we can go further or not and find values accordingly
    public static int frogJ2ump(int n, int heights[]) {
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return helper(0, n, heights, dp);
    }

    public static int helper(int i, int n, int[] heights, int[] dp) {
        if (i == n - 1) {
            return 0;
        }
        if (dp[i] != -1) {
            return dp[i];
        }
        int left = Integer.MAX_VALUE;
        int right = Integer.MAX_VALUE;
        if (i + 1 < n) { //can jump 1 step ahead
            left = Math.abs(heights[i] - heights[i + 1]) + helper(i + 1, n, heights, dp);
        }
        if (i + 2 < n) { //can jump 2 steps ahead
            right = Math.abs(heights[i] - heights[i + 2]) + helper(i + 2, n, heights, dp);
        }
        return dp[i] = Math.min(left, right);
    }

    //frog jump k distance
    //https://www.codingninjas.com/studio/problems/minimal-cost_8180930?utm_source=striver&utm_medium=website&utm_campaign=a_zcoursetuf

    public static int minimizeCost(int n, int k, int[] height) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        return helper(0, height, k, n, dp);
    }

    public static int helper(int index, int[] height, int k, int n, int[] dp) {
        if (index == n - 1) {
            return 0;
        }
        if (dp[index] != -1) {
            return dp[index];
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i <= k; i++) {
            if (index + i < n) {
                ans = Math.min(ans, Math.abs(height[index] - height[index + i]) + helper(index + i, height, k, n, dp));
            }
        }
        return dp[index] = ans;
    }

    //house robber
    //https://leetcode.com/problems/house-robber

    //basically for each index, if we decide to pick an el, we cannot take it's adjacent el. thus whenever we pick an el,
    // we make the recursive call at i+2 otherwise at i+1
    public int rob(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, -1);
        return help2er(0, nums, dp);
    }

    public int help2er(int i, int[] nums, int[] dp) {
        if (i == nums.length - 1) {
            return nums[i];
        }
        if (dp[i] != -1) {
            return dp[i];
        }
        int pick = nums[i];
        if (i + 2 < nums.length) {
            pick += help2er(i + 2, nums, dp);
        }
        int notPick = help2er(i + 1, nums, dp);
        return dp[i] = Math.max(pick, notPick);
    }

    //house robber 2
    //https://leetcode.com/problems/house-robber-ii/

    //if we're even considering picking the 1st el, we can never pick the last el. thus we call the helper for two separate cases,
    // one where 1st el is excluded from the arr and one where the last el is excluded and return the max from both of these cases.
    // this way, max from all possibilities would be returned

    public int r1b(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        int[] dp1 = new int[nums.length - 1];
        Arrays.fill(dp1, -1);
        int[] dp2 = new int[nums.length];
        Arrays.fill(dp2, -1);
        return Math.max(helperStart(0, nums, n - 1, dp1), helperEnd(1, nums, n, dp2));
    }

    public int helperStart(int i, int[] nums, int n, int[] dp1) {
        if (i == n - 1) {
            return nums[i];
        }
        if (dp1[i] != -1) {
            return dp1[i];
        }
        int pick = nums[i];
        if (i + 2 < n) {
            pick += helperStart(i + 2, nums, n, dp1);
        }
        int notPick = helperStart(i + 1, nums, n, dp1);
        return dp1[i] = Math.max(pick, notPick);
    }

    public int helperEnd(int i, int[] nums, int n, int[] dp2) {
        if (i == n - 1) {
            return nums[i];
        }
        if (dp2[i] != -1) {
            return dp2[i];
        }
        int pick = nums[i];
        if (i + 2 < n) {
            pick += helperEnd(i + 2, nums, n, dp2);
        }
        int notPick = helperEnd(i + 1, nums, n, dp2);
        return dp2[i] = Math.max(pick, notPick);
    }

    //ninja's training
    //https://www.codingninjas.com/studio/problems/ninja%E2%80%99s-training_3621003?leftPanelTab=0

    //memoization (gives stack overflow error for large test cases)
    public static int ninjaTra2ining(int n, int points[][]) {
        int[][] dp = new int[n + 1][4];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }

        return helper(0, -1, points, n, dp);
    }

    public static int helper(int i, int prev, int points[][], int n, int[][] dp) {
        if (dp[i][prev + 1] != -1) {
            return dp[i][prev + 1];
        }
        if (i == n) {
            return 0;
        }
        int ans = Integer.MIN_VALUE;
        for (int k = 0; k < 3; k++) {
            if (k != prev) {
                ans = Math.max(ans, points[i][k] + helper(i + 1, k, points, n, dp));
            }
        }
        return dp[i][prev + 1] = ans;
    }

    //tabulation (works fine)
    public static int ninjaTraining(int n, int points[][]) {
        int[][] dp = new int[n + 1][4];
        for (int i = n; i >= 0; i--) {
            for (int prev = 2; prev >= -1; prev--) {
                if (i == n) {
                    dp[i][prev + 1] = 0;
                } else {
                    int ans = 0;
                    for (int k = 0; k < 3; k++) {
                        if (k != prev)
                            ans = Math.max(ans, points[i][k] + dp[i + 1][k + 1]);
                    }
                    dp[i][prev + 1] = ans;
                }
            }
        }
        return dp[0][-1 + 1];
    }

    //triangle min path sum
    //https://leetcode.com/problems/triangle

    //using DFS + dp

    //memoization
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n][n];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return helper(0, 0, triangle, n, dp);
    }

    public int helper(int i, int j, List<List<Integer>> triangle, int n, int[][] dp) {
        if (i == n - 1) {
            return triangle.get(i).get(j);
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int left = helper(i + 1, j, triangle, n, dp);
        int right = helper(i + 1, j + 1, triangle, n, dp);
        return dp[i][j] = triangle.get(i).get(j) + Math.min(left, right);
    }

    //tabulation
    public int minimumTot1al(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                if (i == n - 1) {
                    dp[i][j] = triangle.get(i).get(j);
                } else {
                    int left = dp[i + 1][j];
                    int right = dp[i + 1][j + 1];
                    dp[i][j] = triangle.get(i).get(j) + Math.min(left, right);
                }
            }
        }
        return dp[0][0];
    }

    //minimum falling path sum
    //https://leetcode.com/problems/minimum-falling-path-sum

    //memoization
    public int minFall1ingPathSum(int[][] matrix) {
        int n = matrix.length;
        int ans = Integer.MAX_VALUE;
        int[][] dp = new int[n][n];
        for (int[] arr : dp) {
            Arrays.fill(arr, Integer.MIN_VALUE);
        }
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, helper(n - 1, i, matrix, dp));
        }
        return ans;
    }

    public int helper(int i, int j, int[][] matrix, int[][] dp) {
        if (i < 0 || j < 0 || j >= matrix.length) {
            return Integer.MAX_VALUE;
        }
        if (i == 0) {
            return dp[i][j] = matrix[i][j];
        }
        if (dp[i][j] != Integer.MIN_VALUE) {
            return dp[i][j];
        }
        int top = helper(i - 1, j, matrix, dp);
        int topLeft = helper(i - 1, j - 1, matrix, dp);
        int topRight = helper(i - 1, j + 1, matrix, dp);
        return dp[i][j] = matrix[i][j] + Math.min(top, Math.min(topLeft, topRight));
    }

    //tabulation
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int ans = Integer.MAX_VALUE;
        int[][] dp = new int[n][n];
        for (int j = 0; j < n; j++) {
            dp[0][j] = matrix[0][j];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int up = matrix[i][j] + dp[i - 1][j];
                int topLeft = matrix[i][j];
                if (j > 0) {
                    topLeft += dp[i - 1][j - 1];
                } else {
                    topLeft = Integer.MAX_VALUE;
                }
                int topRight = matrix[i][j];
                if (j < n - 1) {
                    topRight += dp[i - 1][j + 1];
                } else {
                    topRight = Integer.MAX_VALUE;
                }
                dp[i][j] = Math.min(up, Math.min(topLeft, topRight));
            }
        }
        for (int j = 0; j < n; j++) {
            ans = Math.min(ans, dp[n - 1][j]);
        }
        return ans;
    }

    //cherry pickup (really nice ques)
    //https://leetcode.com/problems/cherry-pickup-ii/

    //O(M*N*N)*9, since there are M*N*N states and for each state we have 9 possible recursive calls

    //gotta make sure that if alice and bob are at the same cell then we pick the chocolates at that cell only once

    // we could've recursively found the mps for both alice and bob but for that we'd have to trace the path in both cases
    // and take the common cell only once which could get cumbersome and thus we try to write the recurrence together

    //we move both alice and bob in one step. for any movement of alice, bob has 3 movements (in total 9 combinations)

    //recursive
    public int cherryPickup(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        return helper(0, 0, n - 1, grid, m, n);
    }

    public int helper(int i, int j1, int j2, int[][] grid, int m, int n) {
        if (j1 < 0 || j1 >= n || j2 < 0 || j2 >= n) { //in case robo1 or 2 go out of bounds
            return 0;
        }
        if (i == m - 1) {
            if (j1 == j2) {
                return grid[i][j1];
            }
            return grid[i][j1] + grid[i][j2];
        }
        int ans = 0;
        for (int del1 = -1; del1 <= 1; del1++) {
            for (int del2 = -1; del2 <= 1; del2++) {
                if (j1 == j2) {
                    ans = Math.max(ans, grid[i][j1] + helper(i + 1, j1 + del1, j2 + del2, grid, m, n));
                } else {
                    ans = Math.max(ans, grid[i][j1] + grid[i][j2] + helper(i + 1, j1 + del1, j2 + del2, grid, m, n));
                }
            }
        }
        return ans;
    }

    //memoized
    public int cherryP1ickup(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][][] dp = new int[m][n][n];
        //O(M*N*N)
        for (int[][] arr : dp) {
            for (int[] nums : arr) {
                Arrays.fill(nums, -1);
            }
        }
        return helper(0, 0, n - 1, grid, m, n, dp);
    }

    public int helper(int i, int j1, int j2, int[][] grid, int m, int n, int[][][] dp) {
        if (j1 < 0 || j1 >= n || j2 < 0 || j2 >= n) { //in case robo1 or 2 go out of bounds
            return 0;
        }
        if (i == m - 1) {
            if (j1 == j2) {
                return grid[i][j1];
            }
            return grid[i][j1] + grid[i][j2];
        }
        if (dp[i][j1][j2] != -1) {
            return dp[i][j1][j2];
        }
        int ans = 0;
        for (int del1 = -1; del1 <= 1; del1++) {
            for (int del2 = -1; del2 <= 1; del2++) {
                if (j1 == j2) {
                    ans = Math.max(ans, grid[i][j1] + helper(i + 1, j1 + del1, j2 + del2, grid, m, n, dp));
                } else {
                    ans = Math.max(ans, grid[i][j1] + grid[i][j2] + helper(i + 1, j1 + del1, j2 + del2, grid, m, n, dp));
                }
            }
        }
        return dp[i][j1][j2] = ans;
    }

    //house robber 3
    //very good ques based on dp on trees
    //https://leetcode.com/problems/house-robber-iii

    public int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }
        HashMap<Pair, Integer> map = new HashMap<>();
        return Math.max(helper(root, true, map), helper(root, false, map));
    }

    public int helper(TreeNode root, boolean flag, HashMap<Pair, Integer> map) {
        if (root == null) {
            return 0;
        }
        Pair key = new Pair(root, flag);
        if (map.containsKey(key)) {
            return map.get(key);
        }
        int ans = 0;
        int left = 0;
        int right = 0;
        if (flag) {
            int left0 = helper(root.left, !flag, map);
            int right0 = helper(root.right, !flag, map);
            int left1 = helper(root.left, true, map);
            int right1 = helper(root.right, true, map);
            ans = Math.max(root.val + left0 + right0, left1 + right1);
        } else {
            left += helper(root.left, !flag, map);
            right += helper(root.right, !flag, map);
            ans = left + right;
        }
        map.put(key, ans);
        return ans;
    }

    class Pair {
        TreeNode root;
        boolean flag;

        public Pair(TreeNode root, boolean flag) {
            this.root = root;
            this.flag = flag;
        }

        @Override
        public boolean equals(Object o) {
            Pair obj = (Pair) o;
            return this.root.val == obj.root.val && this.flag == obj.flag;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.root, this.flag);
        }
    }

    //burst balloons
    //https://leetcode.com/problems/burst-balloons/

    //recursive
    public int maxCoins1(int[] nums) {
        int n = nums.length;
        List<Integer> list = new ArrayList<>();
        for (int i : nums) {
            list.add(i);
        }
        list.add(0, 1);
        list.add(1);
        return helper(1, n, list);
    }

    public int helper(int i, int j, List<Integer> list) {
        if (i > j) {
            return 0;
        }
        int ans = Integer.MIN_VALUE;
        for (int k = i; k <= j; k++) {
            int tempAns = (list.get(i - 1) * list.get(k) * list.get(j + 1)) + helper(i, k - 1, list) + helper(k + 1, j, list);
            ans = Math.max(tempAns, ans);
        }
        return ans;
    }

    //memoized
    public int maxCoins(int[] nums) {
        int n = nums.length;
        List<Integer> list = new ArrayList<>();
        for (int i : nums) {
            list.add(i);
        }
        list.add(0, 1);
        list.add(1);
        int[][] dp = new int[n + 1][n + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return helper(1, n, list, dp);
    }

    public int helper(int i, int j, List<Integer> list, int[][] dp) {
        if (i > j) {
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int ans = Integer.MIN_VALUE;
        for (int k = i; k <= j; k++) {
            int tempAns = (list.get(i - 1) * list.get(k) * list.get(j + 1)) + helper(i, k - 1, list, dp) + helper(k + 1, j, list, dp);
            ans = Math.max(tempAns, ans);
        }
        return dp[i][j] = ans;
    }

    //tabulation
    public int maxCoi1ns(int[] nums) {
        int n = nums.length;
        List<Integer> list = new ArrayList<>();
        for (int i : nums) {
            list.add(i);
        }
        list.add(0, 1);
        list.add(1);
        int[][] dp = new int[n + 2][n + 2];
        for (int i = n; i >= 1; i--) {
            for (int j = 1; j <= n; j++) {
                if (i > j) {
                    dp[i][j] = 0;
                } else {
                    int ans = Integer.MIN_VALUE;
                    for (int k = i; k <= j; k++) {
                        int tempAns = (list.get(i - 1) * list.get(k) * list.get(j + 1)) + dp[i][k - 1] + dp[k + 1][j];
                        ans = Math.max(tempAns, ans);
                    }
                    dp[i][j] = ans;
                }
            }
        }
        return dp[1][n];
    }

    //wildcard matching
    //https://leetcode.com/problems/wildcard-matching

    //recursive
    public boolean i1sMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        return helper(n, m, s, p) == 1 ? true : false;
    }

    public int helper(int n, int m, String s, String p) {
        if (n == 0 && m == 0) {
            return 1;
        }
        if (n == 0) {
            for (int i = 0; i < m; i++) {
                if (p.charAt(i) != '*') {
                    return 0;
                }
            }
            return 1;
        }
        if (m == 0) {
            return 0;
        }
        if (s.charAt(n - 1) == p.charAt(m - 1) || (p.charAt(m - 1) == '?')) {
            return helper(n - 1, m - 1, s, p);
        } else {
            if (p.charAt(m - 1) == '*') {
                return Math.max(helper(n - 1, m, s, p), helper(n, m - 1, s, p));
            }
        }
        return 0;
    }

    //memoized
    public boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return helper(n, m, s, p, dp) == 1 ? true : false;
    }

    public int helper(int n, int m, String s, String p, int[][] dp) {
        if (n == 0 && m == 0) {
            return 1;
        }
        if (n == 0) {
            for (int i = 0; i < m; i++) {
                if (p.charAt(i) != '*') {
                    return 0;
                }
            }
            return 1;
        }
        if (m == 0) {
            return 0;
        }
        if (dp[n][m] != -1) {
            return dp[n][m];
        }
        if (s.charAt(n - 1) == p.charAt(m - 1) || (p.charAt(m - 1) == '?')) {
            return dp[n][m] = helper(n - 1, m - 1, s, p, dp);
        } else {
            if (p.charAt(m - 1) == '*') {
                return dp[n][m] = Math.max(helper(n - 1, m, s, p, dp), helper(n, m - 1, s, p, dp));
            }
        }
        return dp[n][m] = 0;
    }

    //tabulation
    public boolean isMatc1h(String s, String p) {
        int n = s.length();
        int m = p.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = 1;
                } else {
                    int k = 0;
                    if (i == 0) {
                        for (k = 0; k < j; k++) {
                            if (p.charAt(k) != '*') {
                                break;
                            }
                        }
                        if (k != j) {
                            dp[i][j] = 0;
                        } else {
                            dp[i][j] = 1;
                        }
                    } else if (j == 0) {
                        dp[i][j] = 0;
                    }
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    if (p.charAt(j - 1) == '*') {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    } else {
                        dp[i][j] = 0;
                    }
                }
            }
        }
        return dp[n][m] == 1 ? true : false;
    }

    //distinct subsequences
    //https://leetcode.com/problems/distinct-subsequences/

    //naive approach (using pick/not pick approach)
    public int numD1s1nct(String s, String t) {
        return helper(0, "", s, t);
    }

    public int helper(int i, String str, String s, String t) {
        if (str.equals(t)) {
            return 1;
        }
        if (i == s.length()) {
            return 0;
        }
        int left = helper(i + 1, str + s.charAt(i), s, t);
        int right = helper(i + 1, str, s, t);
        return left + right;
    }

    //memoized
    public static final int MOD = (int) 1e9 + 7;

    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return helper12(n, m, s, t, dp) % MOD;
    }

    public int helper12(int n, int m, String s, String t, int[][] dp) {
        if (m == 0) {
            return 1;
        }
        if (n == 0) {
            return 0;
        }
        if (dp[n][m] != -1) {
            return dp[n][m];
        }
        if (s.charAt(n - 1) == t.charAt(m - 1)) {
            return dp[n][m] = helper12(n - 1, m - 1, s, t, dp) % MOD + helper12(n - 1, m, s, t, dp) % MOD;
        }
        return dp[n][m] = helper(n - 1, m, s, t, dp) % MOD;
    }

    //tabulation
    public int numD1stinct(String s, String t) {
        int n = s.length();
        int m = t.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (j == 0) {
                    dp[i][j] = 1;
                } else if (i == 0) {
                    dp[i][j] = 0;
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] % MOD + dp[i - 1][j] % MOD;
                } else {
                    dp[i][j] = dp[i - 1][j] % MOD;
                }
            }
        }
        return dp[n][m] % MOD;
    }

    //best time to buy/sell stock 2
    //https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/

    //recursive
    //logic - when we're allowed to buy, we won't be allowed to sell and when we're allowed to sell we won't be allowed
    // to buy. the same logic has been implemented below
    //2^n
    public int maxProfit(int[] prices) {
        return helpe1r(0, 1, prices);
    }

    public int helpe1r(int i, int flag, int[] prices) {
        if (i == prices.length) {
            return 0;
        }
        if (flag == 1) { //can buy
            return Math.max(-prices[i] + helpe1r(i + 1, 1 - flag, prices), 0 + helpe1r(i + 1, flag, prices));
        }
        return Math.max(prices[i] + helpe1r(i + 1, 1 - flag, prices), 0 + helpe1r(i + 1, flag, prices));
    }

    //memoized
    public int maxProfi1t(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n + 1][2];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return he1lper(0, 1, prices, dp);
    }

    public int he1lper(int i, int flag, int[] prices, int[][] dp) {
        if (i == prices.length) {
            return 0;
        }
        if (dp[i][flag] != -1) {
            return dp[i][flag];
        }
        if (flag == 1) { //can buy
            return dp[i][flag] = Math.max(-prices[i] + he1lper(i + 1, 1 - flag, prices, dp), 0 + he1lper(i + 1, flag, prices, dp));
        }
        return dp[i][flag] = Math.max(prices[i] + he1lper(i + 1, 1 - flag, prices, dp), 0 + helper(i + 1, flag, prices, dp));
    }

    //tabulation
    public int max1Profit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n + 1][2];
        for (int i = n; i >= 0; i--) {
            for (int j = 0; j <= 1; j++) {
                if (i == n) {
                    dp[i][j] = 0;
                } else {
                    if (j == 1) {
                        dp[i][j] = Math.max(-prices[i] + dp[i + 1][1 - j], 0 + dp[i + 1][j]);
                    } else {
                        dp[i][j] = Math.max(prices[i] + dp[i + 1][1 - j], 0 + dp[i + 1][j]);
                    }
                }
            }
        }
        return dp[0][1];
    }


    //best time to buy and sell stock 3
    //https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/

    //a transaction means both buy and sell operation combined

    //recursive
    public int maxPro2fit(int[] prices) {
        return help2er(0, 1, 0, prices);
    }

    public int help2er(int i, int flag, int count, int[] prices) {
        if (i == prices.length || count == 2) {
            return 0;
        }
        if (flag == 1) {
            return Math.max(-prices[i] + help2er(i + 1, 1 - flag, count, prices), 0 + help2er(i + 1, flag, count, prices));
        }
        return Math.max(prices[i] + help2er(i + 1, 1 - flag, count + 1, prices), 0 + help2er(i + 1, flag, count, prices));
    }

    //memoized
    public int maxProfit3(int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n + 1][2][3]; //count can be 0,1,2 ie it can have 3 values
        for (int[][] arr : dp) {
            for (int[] nums : arr) {
                Arrays.fill(nums, -1);
            }
        }
        return helpe3r(0, 1, 0, prices, dp);
    }

    public int helpe3r(int i, int flag, int count, int[] prices, int[][][] dp) {
        if (i == prices.length || count == 2) {
            return 0;
        }
        if (dp[i][flag][count] != -1) {
            return dp[i][flag][count];
        }
        if (flag == 1) {
            return dp[i][flag][count] = Math.max(-prices[i] + helpe3r(i + 1, 1 - flag, count, prices, dp), 0 + helpe3r(i + 1, flag, count, prices, dp));
        }
        return dp[i][flag][count] = Math.max(prices[i] + helpe3r(i + 1, 1 - flag, count + 1, prices, dp), 0 + helpe3r(i + 1, flag, count, prices, dp));
    }


    //tabulation
    public int max3Profit(int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n + 1][2][3]; //count can be 0,1,2 ie it can have 3 values
        for (int[][] arr : dp) {
            for (int[] nums : arr) {
                Arrays.fill(nums, -1);
            }
        }
        for (int i = n; i >= 0; i--) {
            for (int flag = 0; flag <= 1; flag++) {
                for (int count = 2; count >= 0; count--) {
                    if (i == n || count == 2) {
                        dp[i][flag][count] = 0;
                    } else {
                        if (flag == 1) {
                            dp[i][flag][count] = Math.max(-prices[i] + dp[i + 1][1 - flag][count], 0 + dp[i + 1][flag][count]);
                        } else {
                            dp[i][flag][count] = Math.max(prices[i] + dp[i + 1][1 - flag][count + 1], 0 + dp[i + 1][flag][count]);
                        }
                    }
                }
            }
        }
        return dp[0][1][0];
    }

    //btbss 4
    //https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv
    //a transaction means each buy/sell operation

    //recursive
    public int maxProfit(int k, int[] prices) {
        return helper(0, 1, 0, k, prices);
    }

    public int helper(int i, int flag, int count, int k, int[] prices) {
        if (i == prices.length || count == 2 * k) {
            return 0;
        }
        if (flag == 1) {
            return Math.max(-prices[i] + helper(i + 1, 1 - flag, count + 1, k, prices), 0 + helper(i + 1, flag, count, k, prices));
        }
        return Math.max(prices[i] + helper(i + 1, 1 - flag, count + 1, k, prices), 0 + helper(i + 1, flag, count, k, prices));
    }

    //memoized
    public int maxPr4ofit(int k, int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n + 1][2][(2 * k) + 1];
        for (int[][] arr : dp) {
            for (int[] nums : arr) {
                Arrays.fill(nums, -1);
            }
        }
        return helper(0, 1, 0, k, prices, dp);
    }

    public int helper(int i, int flag, int count, int k, int[] prices, int[][][] dp) {
        if (i == prices.length || count == 2 * k) {
            return 0;
        }
        if (dp[i][flag][count] != -1) {
            return dp[i][flag][count];
        }
        if (flag == 1) {
            return dp[i][flag][count] = Math.max(-prices[i] + helper(i + 1, 1 - flag, count + 1, k, prices, dp), 0 + helper(i + 1, flag, count, k, prices, dp));
        }
        return dp[i][flag][count] = Math.max(prices[i] + helper(i + 1, 1 - flag, count + 1, k, prices, dp), 0 + helper(i + 1, flag, count, k, prices, dp));
    }

    //tabulation
    public int max4Profit(int k, int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n + 1][2][(2 * k) + 1];
        for (int i = n; i >= 0; i--) {
            for (int flag = 0; flag <= 1; flag++) {
                for (int count = 0; count <= 2 * k; count++) {
                    if (i == n || count == 2 * k) {
                        dp[i][flag][count] = 0;
                    } else {
                        if (flag == 1) {
                            dp[i][flag][count] = Math.max(-prices[i] + dp[i + 1][1 - flag][count + 1], 0 + dp[i + 1][flag][count]);
                        } else {
                            dp[i][flag][count] = Math.max(prices[i] + dp[i + 1][1 - flag][count + 1], 0 + dp[i + 1][flag][count]);
                        }
                    }
                }
            }
        }
        return dp[0][1][0];
    }

    //best time to buy and sell stock with cooldown
    //https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown

    //recursive
    public int maxProfi5t(int[] prices) {
        return help5er(0, 1, 0, prices);
    }

    public int help5er(int i, int flag, int coolDown, int[] prices) {
        if (i == prices.length) {
            return 0;
        }
        if (coolDown == 1) {
            return help5er(i + 1, 1, 0, prices);
        }
        if (flag == 1) {
            return Math.max(-prices[i] + help5er(i + 1, 1 - flag, coolDown, prices), 0 + help5er(i + 1, flag, coolDown, prices));
        }
        return Math.max(prices[i] + help5er(i + 1, 1 - flag, 1, prices), 0 + help5er(i + 1, flag, coolDown, prices));
    }

    //memoization
    public int maxPro5fit(int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n + 1][2][2];
        for (int[][] arr : dp) {
            for (int[] nums : arr) {
                Arrays.fill(nums, -1);
            }
        }
        return helper5(0, 1, 0, prices, dp);
    }

    public int helper5(int i, int flag, int coolDown, int[] prices, int[][][] dp) {
        if (i == prices.length) {
            return 0;
        }
        if (coolDown == 1) {
            return helper5(i + 1, 1, 0, prices, dp);
        }
        if (dp[i][flag][coolDown] != -1) {
            return dp[i][flag][coolDown];
        }
        if (flag == 1) {
            return dp[i][flag][coolDown] = Math.max(-prices[i] + helper5(i + 1, 1 - flag, coolDown, prices, dp), 0 + helper5(i + 1, flag, coolDown, prices, dp));
        }
        return dp[i][flag][coolDown] = Math.max(prices[i] + helper5(i + 1, 1 - flag, 1, prices, dp), 0 + helper5(i + 1, flag, coolDown, prices, dp));
    }

    //tabulation
    public int maxPr5ofit(int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n + 1][2][2];
        for (int i = n; i >= 0; i--) {
            for (int flag = 0; flag <= 1; flag++) {
                for (int coolDown = 0; coolDown <= 1; coolDown++) {
                    if (i == n) {
                        dp[i][flag][coolDown] = 0;
                    } else if (coolDown == 1) {
                        dp[i][flag][coolDown] = dp[i + 1][1][0];
                    } else {
                        if (flag == 1) {
                            dp[i][flag][coolDown] = Math.max(-prices[i] + dp[i + 1][1 - flag][coolDown], 0 + dp[i + 1][flag][coolDown]);
                        } else {
                            dp[i][flag][coolDown] = Math.max(prices[i] + dp[i + 1][1 - flag][1], 0 + dp[i + 1][flag][coolDown]);
                        }
                    }
                }
            }
        }
        return dp[0][1][0];
    }

    public int helper(int i, int flag, int coolDown, int[] prices, int[][][] dp) {
        if (i == prices.length) {
            return 0;
        }
        if (coolDown == 1) {
            return helper(i + 1, 1, 0, prices, dp);
        }
        if (dp[i][flag][coolDown] != -1) {
            return dp[i][flag][coolDown];
        }
        if (flag == 1) {
            return dp[i][flag][coolDown] = Math.max(-prices[i] + helper(i + 1, 1 - flag, coolDown, prices, dp), 0 + helper(i + 1, flag, coolDown, prices, dp));
        }
        return dp[i][flag][coolDown] = Math.max(prices[i] + helper(i + 1, 1 - flag, 1, prices, dp), 0 + helper(i + 1, flag, coolDown, prices, dp));
    }

    //checking palindrome using recursion (used in pp2 problem to tabulate every value of i and j to check whether substring(i,j)
    // is palindromic)
    class Solution {
        int isPalindrome(String S) {
            int n = S.length();
            return helper(0, n - 1, S);
        }

        public int helper(int i, int j, String s) {
            if (i >= j) {
                return 1; //only one char, thus palindrome
            }
            if (s.charAt(i) == s.charAt(j) && ((j - i + 1 == 2) || helper(i + 1, j - 1, s) == 1)) {
                return 1;
            }
            return 0;
        }
    }

    ;

    //above palindrome dp logic is used in pp2 leetcode (this will not give TLE)

    public int minCut(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        int[][] palinDp = generatePalindrome(s);
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= n - 1; j++) {
                if (i >= j || palinDp[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    int ans = Integer.MAX_VALUE;
                    for (int k = i; k < j; k++) {
                        int tempAns = 1 + dp[i][k] + dp[k + 1][j];
                        ans = Math.min(ans, tempAns);
                    }
                    dp[i][j] = ans;
                }
            }
        }
        return dp[0][n - 1];
    }

    //for each val of i and j, this function will prepare a table stating whether substring(i,j) is a palindrome or not
    int[][] generatePalindrome(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= n - 1; j++) {
                if (i >= j) {
                    dp[i][j] = 1;
                } else {
                    if (s.charAt(i) == s.charAt(j) && (j - i + 1 == 2 || dp[i + 1][j - 1] == 1)) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = 0;
                    }
                }
            }
        }
        return dp;
    }

    //partition array for max sum
    //https://leetcode.com/problems/partition-array-for-maximum-sum

    //recursive
    public int maxSumAfterPartitioning(int[] arr, int k) {
        return helper(0, arr, k);
    }

    public int helper(int i, int[] arr, int k) {
        if (i == arr.length) {
            return 0;
        }
        int len = 0;
        int max = Integer.MIN_VALUE;
        int ans = 0;
        for (int j = i; j < Math.min(arr.length, i + k); j++) {
            len++;
            max = Math.max(max, arr[j]); //max till current el of the sub array
            int tempAns = (len * max) + helper(j + 1, arr, k);
            ans = Math.max(ans, tempAns);
        }
        return ans;
    }

    //memoization
    public int maxSumAfterParti1tioning(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        return helper(0, arr, k, dp);
    }

    public int helper(int i, int[] arr, int k, int[] dp) {
        if (i == arr.length) {
            return 0;
        }
        if (dp[i] != -1) {
            return dp[i];
        }
        int len = 0;
        int max = Integer.MIN_VALUE;
        int ans = 0;
        for (int j = i; j < Math.min(arr.length, i + k); j++) {
            len++;
            max = Math.max(max, arr[j]); //max till current el of the sub array
            int tempAns = (len * max) + helper(j + 1, arr, k, dp);
            ans = Math.max(ans, tempAns);
        }
        return dp[i] = ans;
    }

    //tabulation
    public int maxSumAfterPartiti1oning(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        for (int i = n; i >= 0; i--) {
            if (i == n) {
                dp[i] = 0;
            } else {
                int len = 0;
                int max = Integer.MIN_VALUE;
                int ans = 0;
                for (int j = i; j < Math.min(n, i + k); j++) {
                    len++;
                    max = Math.max(max, arr[j]);
                    int tempAns = len * max + dp[j + 1];
                    ans = Math.max(ans, tempAns);
                }
                dp[i] = ans;
            }
        }
        return dp[0];
    }

    //questions related to LIS

    //largest divisible subset
    //https://leetcode.com/problems/largest-divisible-subset/

    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
        List<Integer> list = new ArrayList<>();
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int[] hash = new int[n];
        for (int i = 0; i < nums.length; i++) {
            hash[i] = i;
        }
        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if ((nums[i] % nums[prev] == 0) && ((1 + dp[prev]) > dp[i])) {
                    dp[i] = 1 + dp[prev];
                    hash[i] = prev;
                }
            }
            if (dp[i] > dp[max]) {
                max = i;
            }
        }
        while (max != hash[max]) {
            list.add(0, nums[max]);
            max = hash[max];
        }
        list.add(0, nums[max]);
        return list;
    }

    //longest string chain
    //https://leetcode.com/problems/longest-string-chain/
    public int longestStrChain(String[] words) {
        int n = words.length;
        int[] dp = new int[n];
        int max = 0;
        Arrays.sort(words, (x, y) -> x.length() - y.length());
        Arrays.fill(dp, 1);
        for (int i = 0; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (isPredecessor(words[prev], words[i])) {
                    dp[i] = Math.max(dp[i], 1 + dp[prev]);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public boolean isPredecessor(String a, String b) {
        int m = a.length();
        int n = b.length();
        if (n != 1 + m) {
            return false;
        }
        int i = 0;
        int j = 0;
        while (i < m && j < n) {
            if (a.charAt(i) == b.charAt(j)) {
                i++;
                j++;
            } else {
                j++;
            }
        }
        if (i == m) { //we don't explicitly check if j has reached n or j+1==n because in every case it will always have only either of these 2 values
            return true;
        }
        return false;
    }

    //longest bitonic sequence
    //https://practice.geeksforgeeks.org/problems/longest-bitonic-subsequence0824/1
    public int LongestBitonicSequence(int[] nums) {
        int n = nums.length;
        int[] LIS = new int[n];
        int[] LDS = new int[n];
        int max = 0;
        Arrays.fill(LIS, 1);
        Arrays.fill(LDS, 1);
        for (int i = 0; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (nums[i] > nums[prev]) {
                    LIS[i] = Math.max(LIS[i], 1 + LIS[prev]);
                }
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int prev = n - 1; prev > i; prev--) {
                if (nums[i] > nums[prev]) {
                    LDS[i] = Math.max(LDS[i], 1 + LDS[prev]);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            max = Math.max(max, LIS[i] + LDS[i] - 1);
        }
        return max;
    }

    //number of LIS
    //https://leetcode.com/problems/number-of-longest-increasing-subsequence/
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int[] count = new int[n];
        int cnt = 0;
        int max = Integer.MIN_VALUE;
        Arrays.fill(dp, 1);
        Arrays.fill(count, 1);
        for (int i = 0; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (nums[i] > nums[prev]) {
                    if (1 + dp[prev] > dp[i]) {
                        dp[i] = 1 + dp[prev];
                        count[i] = count[prev]; //the number of variations of the prev el that gave us a better length for current el
                    } else if (dp[i] == 1 + dp[prev]) {
                        count[i] += count[prev]; //adding different number of variations of prev el adding 1 to which will give us the same len for the current el that we already have
                    }
                }
            }
            max = Math.max(dp[i], max);
        }
        for (int i = 0; i < n; i++) {
            if (dp[i] == max) {
                cnt += count[i];
            }
        }
        return cnt;
    }
}
