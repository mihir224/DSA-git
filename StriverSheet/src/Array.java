import java.util.*;

public class Array {
    //Set matrix zeroes
    //https://leetcode.com/problems/set-matrix-zeroes/
    //brute
    public void setZeroes(int[][] matrix) {
        Set<Integer> rows = new HashSet<>();
        Set<Integer> cols = new HashSet<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rows.add(i);
                    cols.add(j);
                }
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (rows.contains(i) || cols.contains(j)) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    //optimal
    public void setZeroes1(int[][] matrix) {
        boolean col = true;
        int rows = matrix.length;
        int cols = matrix[0].length;
        for (int i = 0; i < rows; i++) {
            if (matrix[i][0] == 0) {
                col = false;
            }
            for (int j = 1; j < cols; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = cols - 1; j >= 1; j--) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
            if (!col) {
                matrix[i][0] = 0;
            }
        }
    }

    //Pascal's triangle
    //https://leetcode.com/problems/pascals-triangle/
    public List<List<Integer>> generate(int numRows) {
        //this soln doesn't take into account the case where a row might have numbers with more than one digit
        //        int sq=0;
        //        List<List<Integer>> ans=new ArrayList<>();
        //        for(int i=1;i<=numRows;i++){
        //            List<Integer> list=new ArrayList<>();
        //            sq=(int)Math.pow(11,1);
        //            String s=Integer.toString(sq);
        //            StringTokenizer st=new StringTokenizer(s,",");
        //            while (st.hasMoreTokens()){
        //                int m=Integer.parseInt(st.nextToken());
        //                list.add(m);
        //            }
        //            ans.add(list);
        //        }
        //        return ans;
        //    }
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j <= i; j++) { //for each row, the number of cols==current row number
                if (j == 0 || j == i) { //initialize first col and last col to 1
                    list.add(1);
                } else {
                    list.add(temp.get(j - 1) + temp.get(j));
                }
            }
            temp = list; //to use previous row in next iteration
            ans.add(list);
        }
        return ans;
    }

    //Next permutation
    //https://leetcode.com/problems/next-permutation/
    public void nextPermutation(int[] nums) {
        if (nums.length == 1 || nums.length == 0) {
            return;
        }
        int i = nums.length - 2; //the breakpoint might be at secondlast index
        while (i >= 0 && nums[i] >= nums[i + 1]) { //iterating backwards till we find the break point
            i--;
        }
        if (i >= 0) { //case when breakpoint exists
            int j = nums.length - 1;
            while (nums[j] <= nums[i]) { //iterating backwards to find someone greater than breakpoint
                j--;
            }
            swap(nums, i, j); //swapping breakpoint & someone greater than it
        }
        reverse(nums, i + 1, nums.length - 1); //reversing right half
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void reverse(int[] nums, int i, int j) {
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    //kadane's algo
    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            maxSum = Math.max(sum, maxSum);
            if (sum < 0) {
                sum = 0;
            }
        }
        return maxSum;
    }

    //sort colors
    public void sortColors(int[] nums) {
        int low = 0;
        int mid = 0;
        int hi = nums.length - 1;
        while (mid <= hi) {
            if (nums[mid] == 0) {
                swap2(nums, mid++, low++);
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                swap2(nums, mid, hi--);
            }
        }
    }

    public void swap2(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

    //best time to buy or sell stock
    //https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
    public int maxProfit(int[] prices) {
        int currentProfit = 0;
        int netProfit = 0;
        int lp = Integer.MAX_VALUE;
        for (int i : prices) {
            if (i < lp) {
                lp = i;
            }
            currentProfit = i - lp;
            if (currentProfit > netProfit) {
                netProfit = currentProfit;
            }
        }
        return netProfit;
    }

    //rotate matrix
    //https://leetcode.com/problems/rotate-image/
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        for (int arr[] : matrix) {
            reverse(arr, 0, arr.length - 1);
        }
    }

    //merge intervals
    //https://leetcode.com/problems/merge-intervals/
    public int[][] merge(int[][] intervals) {
        List<int[]> ans = new ArrayList<>();
        if (intervals.length == 0) {
            return ans.toArray(new int[ans.size()][2]); //this parameter is the initialization of a new array to which
            // contents of the list are copied to
        }
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int start = intervals[0][0];
        int end = intervals[0][1];
        for (int[] i : intervals) {
            if (i[0] <= end) { //merge possible
                end = Math.max(end, i[1]);
            } else { //merge not possible
                ans.add(new int[]{start, end});
                start = i[0];
                end = i[1];
            }
        }
        ans.add(new int[]{start, end});
        return ans.toArray(new int[ans.size()][2]);
    }

    //merge sorted array
    //https://leetcode.com/problems/merge-sorted-array/
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        while (n > 0 && m > 0) {
            if (nums1[m - 1] > nums2[n - 1]) {
                nums1[m + n - 1] = nums1[m - 1];
                m--;
            } else {
                nums1[m + n - 1] = nums2[n - 1];
                n--;
            }
        }
        while (n > 0) { //case when there is no element in nums1 & nums2 is non empty
            nums1[m + n - 1] = nums2[n - 1];
            n--;
        }
    }

    //find duplicates
    //https://leetcode.com/problems/find-the-duplicate-number/

    //brute (O(n2)tc,O(1)sc)
    public int findDuplicate(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            int correctIndex = nums[i] - 1;
            if (nums[i] != nums[correctIndex]) {
                int temp = nums[i];
                nums[i] = nums[correctIndex];
                nums[correctIndex] = temp;
            } else {
                i++;
            }
        }
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] != index + 1) {
                return nums[index];
            }
        }
        return -1;
    }

    //better - using hashing, storing frequency of all elements and returning the element which appears more than once
    public int findDuplicate2(int[] nums) {
        int[] vis = new int[nums.length];
        for (int i : nums) {
            vis[i - 1]++;
        }
        for (int i = 0; i < vis.length; i++) {
            if (vis[i] > 1) {
                return i + 1;
            }
        }
        return -1;
    }

    //optimal - using linked-list cycle
    public int findDuplicate3(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        fast = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow; //duplicate is bound to be here
    }

    //set-mismatch (duplicate and missing element)
    //https://www.interviewbit.com/problems/repeat-and-missing-number-array/

    //brute (O(N) both)
    public ArrayList<Integer> repeatedNumber(final List<Integer> A) {
        int[] vis = new int[A.size()];
        ArrayList<Integer> list = new ArrayList<>();
        for (int i : A) {
            vis[i - 1]++;
        }
        for (int index = 0; index < vis.length; index++) {
            if (vis[index] > 1) {
                list.add(0, index + 1);
            }
            if (vis[index] == 0) {
                list.add(index + 1);
            }
        }
        return list;
    }

    //optimal (O(N)tc,O(1)sc)
    public ArrayList<Integer> repeatedNumber2(final List<Integer> A) {
        ArrayList<Integer> list = new ArrayList<>();
        long n = A.size();
        long s = (n * (n + 1)) / 2;
        long p = (n * (n + 1) * (2 * n + 1)) / 6;
        long x = 0;
        long y = 0;
        for (int i = 0; i < A.size(); i++) {
            s -= A.get(i);
            p -= (long) A.get(i) * (long) A.get(i);
        }
        x = (p / s + s) / 2;
        y = x - s;
        list.add((int) y);
        list.add((int) x);
        return list;
    }

    //count inversions
    //https://www.codingninjas.com/codestudio/problems/615?topList=striver-sde-sheet-problems&utm_source=striver&utm_medium=website

    //brute (O(n2)tc,O(1)sc)
    //just check if any element is greater than elements on its right, if it is, increase count
    public static long getInversions(long arr[], int n) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[i] > arr[j]) {
                    count++;
                }
            }
        }
        return count;
    }

    //optimal-merge sort variation (O(nlogn)tc,O(n)sc)
    public static long getInversions1(long arr[], int n) {
        return mergeSort(0,n-1,arr);
    }
    public static int mergeSort(int start,int end, long[] arr){
        int count=0;
        if(start>=end){
            return count;
        }
        int mid=(start+end)/2;
        count+=mergeSort(start,mid,arr);
        count+=mergeSort(mid+1,end,arr);
        count+=merge(start,mid,end,arr);
        return count;
    }
    public static int merge(int start,int mid,int end,long[] arr){
        int left=start;
        int right=mid+1;
        List<Integer> temp=new ArrayList<>();
        int count=0;
        while(left<=mid&&right<=end){
            if(arr[left]<arr[right]){
                temp.add((int)arr[left]);
                left++;
            }
            else{
                temp.add((int)arr[right]);
                count+=mid+1-left;
                right++;
            }
        }
        while(left<=mid){
            temp.add((int)arr[left]);
            left++;
        }
        while(right<=end){
            temp.add((int)arr[right]);
            right++;
        }
        for(int i=start;i<=end;i++){
            arr[i]=temp.get(i-start);
        }
        return count;
    }
    //search in 2d matrix
    //https://leetcode.com/problems/search-a-2d-matrix/

    //brute - traverse the whole matrix and return true if found element - O(n*m),O(1)

    //better-O(n),O(1)
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = 0;
        int col = matrix[0].length - 1;
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }

    //optimal-binary search O(log(m*n)),O(1)
    public boolean searchMatrix1(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;
        if (matrix.length == 0) {
            return false;
        }
        int start = 0;
        int end = (n * m) - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (matrix[mid / m][mid % m] == target) {
                return true;
            } else if (matrix[mid / m][mid % m] > target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return false;
    }

    //pow(x,n)
    //https://leetcode.com/problems/powx-n/

    //brute-iterate till n and keep multiplying x with itself (O(N),O(1))

    //optimal O(logbase2(n),O(1)
    //using binary exponentiation
    //if n is odd, ans*=x & n=n-1, if n is even, x*=x & n=n/2. When n==0, return ans
    public double myPow(double x, int n) {
        long num = n;
        if (num < 0) {
            num = -1 * num;
        }
        double ans = 1.00;
        while (num > 0) {
            if (num % 2 == 1) { //odd power
                ans = ans * x; //instead of doing all possible calculations, we just use the data from when n is even
                // and multiply it to the ans
                num -= 1;
            } else if (num % 2 == 0) {
                x = x * x;
                num /= 2;
            }
        }
        if (n < 0) {
            ans = 1 / ans;
        }
        return ans;
    }

    //majority element (appears > n/2 times) and there's only one
    //https://leetcode.com/problems/majority-element

    //brute O(N2) - iterate over the given array in a nested manner, for each iteration checking if element in the inner loop
    //equals the one in the outer loop and increasing the count everytime it happens. Then return the element for which count>s
    public int majorityElement(int[] nums) {
        int s = nums.length / 2;
        for (int i : nums) {
            int count = 0;
            for (int j : nums) {
                if (i == j) {
                    count++;
                }
            }
            if (count > s) {
                return i;
            }
        }
        return -1;
    }

    //better - storing frequency in hashmap (O(1) best, O(logn) worst - since for more than 8 entries, jdk 8 has tweaked the
    // hashmap structure to be implemented as a tree)tc, O(n) sc where n is the size of hash map
    public int majorityElement2(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int s = nums.length / 2;
        for (int i : nums) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
            if (map.get(i) > s) {
                return i;
            }
        }
        return -1;
    }

    //optimal - Moore's voting algorithm (O(n),O(1))
    public int majorityElement3(int[] nums) {
        int count = 0;
        int candidate = 0;
        for (int i : nums) {
            if (count == 0) {
                candidate = i;
            }
            if (i == candidate) {
                count++;
            } else {
                count--;
            }
        }
        return candidate;
    }

    //majority element 2 - find all elements appearing > n/3 times
    //https://leetcode.com/problems/majority-element-ii/

    //brute O(N2) - pick one element, compare with the rest and add to list if appears > n/3 times


    //better - storing frequency in hashmap (O(1) best, O(logn) worst - since for more than 8 entries, jdk 8 has tweaked the
    // hashmap structure to be implemented as a tree)tc, O(n) sc where n is the size of hash map
    public List<Integer> majorityElement4(int[] nums) {
        List<Integer> list = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        int s = nums.length / 3;
        for (int i : nums) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }
        for (int key : map.keySet()) {
            if (map.get(key) > s) {
                list.add(key);
            }
        }
        return list;
    }

    //optimal
    //we see that at max we can have only two majority elements, thus we apply moore's algo to two elements and
    // add them to the ans in the end
    public List<Integer> majorityElement6(int[] nums) {
        int count1 = 0;
        int count2 = 0;
        int candidate1 = -1;
        int candidate2 = -1;
        List<Integer> list = new ArrayList<>();
        for (int i : nums) {
            if (i == candidate1) {
                count1++;
            } else if (i == candidate2) {
                count2++;
            } else if (count1 == 0) {
                candidate1 = i;
                count1 = 1; //here we have to explicitly set count to 1 whenever it reaches 1 because here we cannot use
                // multiple if statements as we are dealing with 2 elements
            } else if (count2 == 0) {
                candidate2 = i;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }
        count1 = 0;
        count2 = 0;
        //it is not ensured that candidate1 and 2 will always be majority elements as it is not given in the question
        // that there will always be majority elements. There might be a case where each element appears equal number of times
        // & thus we make sure that they actually are in majority

        for (int i : nums) {
            if (i == candidate1) {
                count1++;
            } else if (i == candidate2) {
                count2++;
            }
        }
        if (count1 > nums.length / 3)
            list.add(candidate1);
        if (count2 > nums.length / 3)
            list.add(candidate2);
        return list;
    }

    //unique paths
    //https://leetcode.com/problems/unique-paths/

    //brute - recursive (O(2^n))
    public int uniquePaths(int m, int n) {
        return helper(0, 0, m, n);
    }

    public int helper(int i, int j, int m, int n) {
        if (i >= m || j >= n) {
            return 0;
        }
        if (i == m - 1 && j == n - 1) {
            return 1;
        }
        return helper(i + 1, j, m, n) + helper(i, j + 1, m, n);
    }

    //better - memoization (O(n*m) both)
    public int uniquePaths2(int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (int[] i : dp) {
            Arrays.fill(i, -1);
        }
        return helper(0, 0, m, n, dp);
    }

    public int helper(int i, int j, int m, int n, int[][] dp) {
        if (i >= m || j >= n) {
            return 0;
        }
        if (i == m - 1 && j == n - 1) {
            return 1;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        return dp[i][j] = helper(i + 1, j, m, n, dp) + helper(i, j + 1, m, n, dp);
    }

    //optimal - using combinations approach (O(m-1) or n-1 depending on whether we try to find the possible paths with
    // the possible number of row directions or column directions) tc, O(1) space
    public int uniquePaths3(int m, int n) {
        double ans = 1;
        int totalWays = m + n - 2; // total number of times we are allowed to move in a path
        int r = m - 1; // total number of times we are allowed to go down in a path
        for (int i = 1; i <= r; i++) {
            ans = ans * (totalWays - i + 1) / i;
        }
        return (int) Math.round(ans); // Round the result to the nearest integer
    }

    //reverse pairs
    //https://leetcode.com/problems/reverse-pairs/

    //brute(O(N2))
    public int reversePairs(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j] * 2) {
                    count++;
                }
            }
        }
        return count;
    }

    //optimal  (O(nlogn)+O(n)+O(n))- merge sort variation
    public int reversePairs2(int[] nums) {
        return helperMerge(nums, 0, nums.length - 1);
    }

    public int helperMerge(int[] nums, int low, int hi) {
        int pairs = 0;
        int mid = 0;
        if (low < hi) {
            mid = (low + hi) / 2;
            pairs += helperMerge(nums, low, mid);
            pairs += helperMerge(nums, mid + 1, hi);
            pairs += merge2(nums, low, mid + 1, hi);
        }
        return pairs;
    }

    public int merge2(int[] nums, int low, int mid, int hi) {
        int pairs = 0;
        int j = mid;
        for (int i = low; i < mid; i++) {
            while (j <= hi && nums[i] > 2 * (long) nums[j]) {
                j++;
            }
            pairs += j - mid;
        }
        ArrayList<Integer> temp = new ArrayList<>();
        int left = low;
        int right = mid;
        while (left <= mid - 1 && right <= hi) {
            if (nums[left] <= nums[right]) {
                temp.add(nums[left]);
                left++;
            } else {
                temp.add(nums[right]);
                right++;
            }
        }
        while (left <= mid - 1) {
            temp.add(nums[left]);
            left++;
        }
        while (right <= hi) {
            temp.add(nums[right]);
            right++;
        }
        for (int i = low; i <= hi; i++) {
            nums[i] = temp.get(i - low);
        }
        return pairs;
    }

    //two sum
    //https://leetcode.com/problems/two-sum/

    //brute (O(N2)) - traverse through the array in a nested array, calculating all possible sums and then returning the elements with sum equal to target

    //better (O(nlogn) for sorting the given array, O(n) space for temp array) - two pointer approach
    public int[] twoSum(int[] nums, int target) {
        int[] temp = new int[nums.length];
        for (int ind = 0; ind < temp.length; ind++) {
            temp[ind] = nums[ind];
        }
        int[] ans = new int[2];
        int i = 0;
        int j = nums.length - 1;
        Arrays.sort(nums);
        while (i < j) {
            if (nums[i] + nums[j] == target) {
                break;
            } else if (nums[i] + nums[j] < target) {
                i++;
            } else {
                j--;
            }
        }
        int l = nums[i];
        int r = nums[j];
        for (int ind = 0; ind < temp.length; ind++) {
            if (temp[ind] == l) {
                ans[0] = ind;
                break;
            }
        }
        for (int ind = temp.length - 1; ind >= 0; ind--) {
            if (temp[ind] == r) {
                ans[1] = ind;
                break;
            }
        }
        return ans;
    }

    //optimal
    //using hashmap(hashing) - O(N)- can be n2 in worst case for n collisions but that is quite rare,O(N)
    public int[] twoSum2(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(target - nums[i])) {
                map.put(nums[i], i);
            } else {
                return new int[]{i, map.get(target - nums[i])};
            }
        }
        return new int[]{-1};
    }

    //4 sum
    //https://leetcode.com/problems/4sum/

    //brute (O((N^3)logN)) - three pointer + binary search

    //optimal (O(N3)) - 4 pointer
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums.length == 0) {
            return ans;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            long t1 = target - nums[i]; //we take this expression explicitly instead of just writing t=target-nums[i]-nums[j]
            // because in that case if target and input are very large, subtracting a very large number from target would
            // exceed the integer range for target and will thus produce a wrong output. Thus, we store target-nums[i] in
            // a long variable to avoid this and then we use it with nums[j]
            for (int j = i + 1; j < nums.length; j++) {
                long t = t1 - nums[j];
                int left = j + 1;
                int right = nums.length - 1;
                while (left < right) { //two pointer
                    if (nums[left] + nums[right] < t) {
                        left++;
                    } else if (nums[left] + nums[right] > t) {
                        right--;
                    } else { //left+right==t
                        List<Integer> list = new ArrayList<>(); //to store a quadruple
                        list.add(nums[i]);
                        list.add(nums[j]);
                        list.add(nums[left]);
                        list.add(nums[right]);
                        ans.add(list);
                        //avoiding duplicates
                        while (left < right && nums[left] == list.get(2)) {
                            left++;
                        }
                        while (left < right && nums[right] == list.get(3)) {
                            right--;
                        }
                    }
                }
                //avoiding j duplicates
                while (j + 1 < nums.length && nums[j + 1] == nums[j]) {
                    j++;
                }
            }
            //avoiding i duplicates
            while (i + 1 < nums.length && nums[i + 1] == nums[i]) {
                i++;
            }
        }
        return ans;
    }

    //longest consecutive subsequence
    //https://leetcode.com/problems/longest-consecutive-sequence/

    //brute (O(NlogN)) - sort the array and then iterate over it to find length
    public int longestConsecutive(int[] nums) {
        int count = 1;
        int ans = 1;
        if (nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i + 1] == nums[i] + 1) {
                count++;
            } else if (nums[i + 1] != nums[i]) { //in case we find duplicates, we don't change count
                count = 1;
            }
            ans = Math.max(ans, count);
        }
        return ans;
    }

    //optimal (O(3N), O(N))
    public int longestConsecutive2(int[] nums) {
        int ans = 1;
        if (nums.length == 0) {
            return 0;
        }
        Set<Integer> set = new HashSet<>();
        for (int i : nums) {
            set.add(i);
        }
        for (int i : nums) {
            if (!set.contains(i - 1)) {
                int currentNum = i;
                int count = 1;
                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    count++;
                }
                ans = Math.max(count, ans);
            }
        }
        return ans;
    }

    //largest sub array with zero-sum
    //https://practice.geeksforgeeks.org/problems/largest-subarray-with-0-sum/1

    //brute (O(N3))-better (O(N2)) - find all possible sub arrays, then find the ones with sum 0 and store max length.
    // We can also use a third for loop to track zero-sum instead of only two for loops but that would take O(N3) and
    // would be considered extremely naive, so this is much better than that.
    int maxLen(int arr[], int n) {
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += arr[j];
                if (sum == 0) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }
        return ans;
    }

    //optimal (O(NlogN)-traversal and hashmap)
    int maxLen2(int arr[], int n) {
        int ans = 0;
        int sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            if (sum == 0) {
                ans = i + 1;
            } else if (map.containsKey(sum)) {
                ans = Math.max(ans, i - map.get(sum));
            } else {
                map.put(sum, i);
            }
        }
        return ans;
    }

    //count sub-arrays with xor k (really imp problem which clears concepts of xor with sub-arrays)
    //https://www.interviewbit.com/problems/subarray-with-given-xor/

    //brute - (O(N2))-better(O(N3)) - find all sub-arrays, perform xor in each and see which one equals the given xor,
    // then calculate number of such sub-arrays
    public int solve(ArrayList<Integer> A, int B) {
        int count = 0;
        for (int i = 0; i < A.size(); i++) {
            int current = 0;
            for (int j = i; j < A.size(); j++) {
                current = current ^ A.get(j);
                if (current == B) {
                    count++;
                }
            }
        }
        return count;
    }

    //optimal - (O(NlogN - N for traversing the given array, logN for hashmap worst case), O(N)space)
    public int solve2(ArrayList<Integer> A, int B) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int xor = 0;
        int count = 0;
        for (int i = 0; i < A.size(); i++) {
            xor = xor ^ A.get(i);
            if (map.containsKey(xor ^ B)) { //map contains the prefix xor
                count += map.get(xor ^ B); //retrieving the number of times prefix xor has appeared - this will be the same number of times the xor B appeared
            }
            if (xor == B) { //current xor equals given xor
                count++;
            }
            if (map.containsKey(xor)) {
                map.put(xor, map.get(xor) + 1);
            }  else { //map does not contain the current xor
                map.put(xor, 1);
            }
        }
        return count;
    }

    //longest substring without repeating characters
    //https://leetcode.com/problems/longest-substring-without-repeating-characters/

    //brute - O(N2) - find all possible substrings, ie in each iteration add the particular character into a set if it
    // doesn't already exist and increase the count. If it does, then it means that the character is repeating
    // & so we break from inner j loop.
    // Then we check if the length of total characters ie count from previous iteration exceeds the current and return the
    // max ans accordingly.
    public static int lengthOfLongestSubstring(String s) {
        if (s.length() <= 1) {
            return s.length();
        }
        int count = 0;
        HashSet<Character> set = new HashSet<>();
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            count = 0;
            for (int j = i; j < s.length(); j++) {
                if (set.contains(s.charAt(j))) {
                    break;
                } else {
                    set.add(s.charAt(j));
                    count++;
                }
            }
            set.clear();
            ans = Math.max(ans, count);
        }
        return ans;
    }

    //optimal 1 - using hashset to store range (between two variables left and right) of all unique sub-arrays with no duplicates.
    // If we encounter a duplicate, we keep erasing all elements from the set until the duplicate has been removed.  Then we start again with a new range.
    //(O(2N)tc as we initally loop right pointer from left till we reach a duplicate, then we loop the left pointer till the duplicate has been removed,
    // O(1)space because the set will at most store 26 characters)
    public static int lengthOfLongestSubstring2(String s) {
        if (s.length() <= 1) {
            return s.length();
        }
        int left = 0;
        int ans = 0;
        HashSet<Character> set = new HashSet<>();
        for (int right = 0; right < s.length(); right++) {
            if (set.contains(s.charAt(right))) { //found duplicate
                while (left < right && set.contains(s.charAt(right))) {
                    set.remove(s.charAt(left));
                    left++;
                }
            }
            set.add(s.charAt(right));
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }

    //optimal 2 - optimising the prev solution further by storing the index at which we last encountered the duplicate so that
    // we can just move left by prev duplicate index + 1 instead of having to iterate it over the array till the duplicate has been removed from the set.
    // we achieve this by using a map instead of set to store the current character along with its index.
    //This reduces tc to O(N) as we don't have to iterate the left pointer however sc is increased to O(N) as the map might
    // store all of the elements in the string since we don't remove anything

    public static int lengthOfLongestSubstring3(String s) {
        if (s.length() <= 1) {
            return s.length();
        }
        int left = 0;
        int ans = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int right = 0; right < s.length(); right++) {
            if (map.containsKey(s.charAt(right))) { //found duplicate
                left = Math.max(map.get(s.charAt(right)) + 1, left); //to check whether current left is already ahead of the
                // new left wish to set it to
            }
            map.put(s.charAt(right), right);
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}
