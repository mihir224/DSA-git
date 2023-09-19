import java.util.*;


public class Array {
    //set matrix zeroes
    //https://leetcode.com/problems/set-matrix-zeroes/

    //brute
    //O(2(N*M)) tc, O(N+M) sc
    public void setZeroes(int[][] matrix) {
        int[] dummyRow = new int[matrix.length];
        int[] dummyCol = new int[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    dummyRow[i] = -1;
                    dummyCol[j] = -1;
                }
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (dummyRow[i] == -1 || dummyCol[j] == -1) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    //optimal
    //O(2(N*M)) tc, O(1) sc
    public void setZeroes1(int[][] matrix) {
        boolean col = true;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) { //if any element of col1 is 0, we turn it to zero
                col = false;
            }
            for (int j = 1; j < matrix[0].length; j++) { //starting with j=1 since we already dealt with j=0
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = matrix[0].length - 1; j > 0; j--) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
            if (!col) {
                matrix[i][0] = 0; //dealing with 1st col
            }
        }
    }

    //pascal's triangle
    //https://leetcode.com/problems/pascals-triangle/

    //O(N2) tc, O(M) where M is the avg size of each row
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> prev = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    list.add(1);
                } else {
                    list.add(prev.get(j - 1) + prev.get(j));
                }
            }
            prev = list;
            ans.add(list);
        }
        return ans;
    }

    //next permutation
    //https://leetcode.com/problems/next-permutation/

    //O(3N)
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        if (n == 0 || n == 1) {
            return;
        }
        int breakPoint = -1;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                breakPoint = i;
                break;
            }
        }
        if (breakPoint >= 0) { //case when bp exists
            for (int i = n - 1; i >= 0; i--) {
                if (nums[i] > nums[breakPoint]) {
                    swap(i, breakPoint, nums);
                    break;
                }
            }
        }
        reverse(breakPoint + 1, n - 1, nums);
    }

    public void swap(int a, int b, int[] nums) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

    public void reverse(int i, int j, int[] nums) {
        while (i < j) {
            swap(i, j, nums);
            i++;
            j--;
        }
    }

    //Maximum Subarray
    //https://leetcode.com/problems/maximum-subarray/

    //kadane's algo
    //brute would be to run a 3 nested for loops - i, j(from i to n), k(from i to j) and find max sum
    //better would be to run 2 nested for loops of above i and j and find max sum
    //optimal would be using kadane's algo O(N)

    public int maxSubArray(int[] nums) {
        int sum = 0;
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum > maxSum) {
                maxSum = sum;
            }
            if (sum < 0) {
                sum = 0;
            }
        }
        return maxSum;
    }


    //Sort an array of 0s, 1s and 2s
    //https://leetcode.com/problems/sort-colors/

    //rwb-0,1,2

    //Dutch National Flag Algo
    //using counting sort approach
    //O(N+k) tc a
    public void sortColors(int[] nums) {
        int count0 = 0;
        int count1 = 0;
        int count2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                count0++;
            }
            if (nums[i] == 1) {
                count1++;
            }
            if (nums[i] == 2) {
                count2++;
            }
        }
        int k = 0;
        while (k < count0) {
            nums[k] = 0;
            k++;
        }
        while (k < count0 + count1) {
            nums[k] = 1;
            k++;
        }
        while (k < nums.length) {
            nums[k] = 2;
            k++;
        }
    }

    //using three pointer approach
    //same O(N) tc O(1) sc
    public void sortColors1(int[] nums) {
        int low = 0;
        int mid = 0;
        int hi = nums.length - 1;
        while (mid <= hi) {
            if (nums[mid] == 1) {
                mid++;
            } else if (nums[mid] == 0) {
                swap(low, mid, nums);
                low++;
                mid++;
            } else if (nums[mid] == 2) {
                swap(mid, hi, nums);
                hi--;
            }
        }
    }

    //best time to buy and sell stock
    //https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

    //O(N) tc O(1) sc

    //on each day we're trying to see what would be the profit if we try to sell the stock on the present day considering
    // we bought the stock for the least price till that day
    //this is basically a sort of DP as we store the ans we got from previous iterations (least price till day) and use
    // it in future iterations
    public int maxProfit(int[] prices) {
        int leastPrice = Integer.MAX_VALUE;
        int netProfit = 0;
        for (int i : prices) {
            if (i < leastPrice) {//calculating net profit through least price till day
                leastPrice = i;
            }
            int currentProfit = i - leastPrice;
            if (currentProfit > netProfit) {
                netProfit = currentProfit;
            }
        }
        return netProfit;
    }

    //rotate matrix
    //https://leetcode.com/problems/rotate-image/
    //O(2(N*N));
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) { //observe how j starts from i+1. This is done to handle the condition i!=j and
                // also skip cells that have been swapped
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        for (int[] row : matrix) {
            reverse(0, n, row);
        }
    }


    //merge overlapping intervals
    //https://leetcode.com/problems/merge-intervals/

    //brute O(NlogN+N2) tc, O(N) sc
    //sort the intervals array, then iterate over all the intervals and for each interval check if there are any valid
    // intervals on its right that can be merged to it and if there are, then merge them with the current interval.
    // After merging all valid intervals, add the resultant to a DS and then move to the next interval

    public int[][] merge1(int[][] intervals) {
        int n=intervals.length;
        Arrays.sort(intervals,(a,b)->a[0]-b[0]);
        List<int[]> list=new ArrayList<>();
        int lb=intervals[0][0];
        int ub=intervals[0][1];
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                if(ub>=intervals[j][0]){
                    ub=Math.max(ub,intervals[j][1]);
                }
                else{
                    list.add(new int[]{lb,ub});
                    lb=intervals[j][0];
                    ub=intervals[j][1];
                }
            }
        }
        list.add(new int[]{lb,ub});

        return list.toArray(new int[list.size()][2]);
    }

    //optimal O(NlogN+N) tc, O(N) sc

    public int[][] merge(int[][] intervals) {
        List<int[]> list = new ArrayList<>();
        if (intervals.length == 0) {
            return list.toArray(new int[0][0]);
        }
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int lb = intervals[0][0];
        int ub = intervals[0][1];
        for (int[] arr : intervals) {
            if (ub >= arr[0]) { //found overlapping interval, merge
                ub = Math.max(ub, arr[1]);
            } else { //merge not possible
                list.add(new int[]{lb, ub});
                lb = arr[0];
                ub = arr[1];
            }
        }
        list.add(new int[]{lb, ub}); //adding the last interval
        return list.toArray(new int[list.size()][2]); //here toArray() method converts the list into a 2d array with
        // specified format. Note that this method works only with lists of type int[].
    }

    //merge two sorted arrays
    //https://leetcode.com/problems/merge-sorted-array/

    //it is given that the size of one array is n+m (to accommodate the merged sorted array). Thus, we can follow basic two
    // pointer approach to merge the sorted arrays in place.

    //O(min(m,n)+O(n)worst case tc, O(1) sc)
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        while (m > 0 && n > 0) {
            if (nums1[m - 1] > nums2[n - 1]) {
                nums1[m + n - 1] = nums1[m - 1];
                m--;
            } else {
                nums1[m + n - 1] = nums2[n - 1];
                n--;
            }
        }
        while (n > 0) { //case when some elements in nums2 were smaller than the remaining elements in nums1 and thus m
            // reached 0 first. We didn't do the same for nums1 because if it were the case with elements of nums1, they'd
            // still be at their correct position as we store our merged sorted array in nums1
            nums1[m + n - 1] = nums2[n - 1];
            n--;
        }
    }

    //case when the size of nums1 and nums2 are fixed ie n & m:

    //we know that both nums1 and nums2 are sorted. so we take a pointer (ie left pointer) at the end of nums1 and another
    // pointer (ie right pointer) at the start of nums2. left tends to move towards start of nums1 and right tends to move
    // towards end of nums2. while moving both pointers, we simply check if nums1[left]>nums2[right], and if it is, we swap
    // them both and move both pointers in their respective directions otherwise we break as both array have correct elements
    // (since we move left in nums1 and right in nums2 both of which are sorted, so if nums1[left] is smaller than nums2[right]
    // then it means all elements on the left of it will also be smaller). this way all elements in nums1 are smaller than the
    // ones in nums2, and then we can sort both individual arrays so that if we place them together, nums2 after nums1,
    // all elements appear in a sorted fashion.

    //O(min(m,n) for iterating while swap + O(NlogN) + O(MlogM)) tc, O(1)sc

    //Find the duplicate in an array of N+1 integers
    //https://leetcode.com/problems/find-the-duplicate-number/

    //brute O(NlogN+N) tc, O(1) sc  - sort the given array, if there are duplicate numbers, they'd be adjacent thus after sorting we can iterate
    //the array and check if an element arr[i]==arr[i+1] to find the duplicate

    //better O(N) time and space - we can maintain a freq array and return the element with freq>1

    //optimal O(N) tc and O(1) sc - using fast and slow pointer approach. In this case, we can form the path containing
    // the cycle by following the indices denoted by the numbers in the given array ie we start from element at 0th index,
    // say, 2. Then we go to the item at the 2nd index. Assume that it was 5. Then we go to the index 5 and so on. We can
    // use this analogy to move the slow and fast pointers accordingly so that we can handle everything without having
    // to create the path.

    public int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];
        do {
            //O(N)
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        //after first collision
        fast = nums[0];
        //O(N)
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        //second collision occurred
        return slow;
    }

    //Set Mismatch
    //https://leetcode.com/problems/set-mismatch/

    //brute O(NlogN+N) tc, O(1) sc - sort the array then return the element not at its correct index ie i+1 (since array elements are
    // 1 to N) along with i+1 ie missing number

    //better O(N) time and space - using frequency array
    public int[] findErrorNums(int[] nums) {
        int[] freq = new int[nums.length];
        List<Integer> list = new ArrayList<>();
        for (int i : nums) {
            freq[i - 1]++;
        }
        for (int i = 0; i < nums.length; i++) {
            if (freq[i] > 1) {
                list.add(0, i + 1);
            }
            if (freq[i] == 0) {
                list.add(i + 1);
            }
        }
        int[] arr = new int[2];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    //optimal O(N) time and O(1) space - using maths
    public int[] findErrorNums1(int[] nums) {
        long n = nums.length;
        long s = n * (n + 1) / 2;
        long p = (n * (n + 1) * ((2 * n) + 1)) / 6;
        long s1 = 0;
        long p1 = 0;
        for (int i : nums) {
            s1 += i;
            p1 += (long) i * (long) i;
        }
        long sDash = s - s1;
        long pDash = p - p1;
        long missingNum = ((pDash / sDash) + sDash) / 2;
        long duplicateNum = missingNum - sDash;
        return new int[]{(int) duplicateNum, (int) missingNum};
    }

    //count inversions
    //https://www.codingninjas.com/codestudio/problems/count-inversions_615?leftPanelTab=0


    //brute O(N2) tc, O(1) sc - linearly traverse the whole array and for each element if it is greater than any element
    // on its right and
    //increase the count if it is.

    //optimal O(NlogN) tc, O(1) sc - merge sort variation
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

    public static long getInversions1(long arr[], int n) {
        return mergeSort(0, n - 1, arr);
    }

    public static int mergeSort(int start, int end, long[] arr) {
        int count = 0;
        if (start >= end) {
            return count;
        }
        int mid = (start + end) / 2;
        count += mergeSort(start, mid, arr);
        count += mergeSort(mid + 1, end, arr);
        count += merge(start, mid, end, arr);
        return count;
    }

    public static int merge(int start, int mid, int end, long[] arr) {
        int left = start;
        int right = mid + 1;
        List<Integer> temp = new ArrayList<>();
        int count = 0;
        while (left <= mid && right <= end) {
            if (arr[left] < arr[right]) {
                temp.add((int) arr[left]);
                left++;
            } else {
                temp.add((int) arr[right]);
                count += mid + 1 - left;
                right++;
            }
        }
        while (left <= mid) {
            temp.add((int) arr[left]);
            left++;
        }
        while (right <= end) {
            temp.add((int) arr[right]);
            right++;
        }
        for (int i = start; i <= end; i++) {
            arr[i] = temp.get(i - start);
        }
        return count;
    }


    //search in 2D matrix
    //https://leetcode.com/problems/search-a-2d-matrix/

    //there are two versions of this problem: 1 where it is given that first element of every row is greater than last of
    //prev row (leetcode version) and the other where this condition is not necessarily true (gfg version)

    //brute O(N*M) tc, O(1) sc - linearly search for the element

    //better - this approach is better for leetcode & optimal for gfg
    //O(N) tc, O(1) sc
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = 0;
        int col = matrix[0].length - 1;
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            }
            if (matrix[row][col] > target) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }

    //optimal for leetcode - binary search
    //O(log(M*N)) tc, O(1) sc

    public boolean searchMatrix1(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;
        int start = 0;
        int end = n * m - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            int row = mid / m;
            int col = mid % m;
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return false;
    }

    //pow(x,n)
    //brute O(N) tc, O(1) sc - iterate n times, multiply x with itself in each iteration

    //optimal O(logN) tc, O(1) sc - binary exponentiation
    public double myPow(double x, int n) {
        double ans = 1.0;
        long nCopy = n; //we take a long copy of n because there might be a case where n is negative. In that case we have
        // to convert it to a positive value and calculate pow(x,n) and then return 1/ans. If n is a large negative value,
        // making it positive would cause int overflow and that would throw an error. Thus, we make it long beforehand to
        // handle such cases.
        if (nCopy < 0) {
            nCopy = -1 * nCopy; //making n +ve
        }
        while (nCopy > 0) {
            if (nCopy % 2 == 0) { //even n
                x = x * x;
                nCopy /= 2;
            } else {
                ans *= x;
                nCopy -= 1;
            }
        }
        if (n < 0) {
            return 1 / ans;
        }
        return ans;
    }

    //majority element appearing > N/2 times
    //https://leetcode.com/problems/majority-element/

    //brute O(N2) tc, O(1) sc - traverse the array twice in a nested manner, then check if element in the outer loop equals the one in the
    // inner loop and if it is, increase the count and if for any element count>N/2, then we return it.

    //better (O(NlogN) wc) tc, O(1) sc - using hashmap to store freq of all N elements and return the one with freq>N/2. O(N)
    // because in java avg insertion time in hashmap is O(1) and O(N) for inserting N elements

    //optimal O(N) tc, O(1) sc - using Moore's voting algo
    public int majorityElement(int[] nums) {
        int candidate = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (count == 0) {
                candidate = nums[i];
            }
            if (candidate == nums[i]) {
                count++;
            } else {
                count--;
            }
        }
        return candidate;
    }

    //majority element appearing > N/3 times
    //https://leetcode.com/problems/majority-element-ii/

    //brute, better - same as before
    //optimal O(N) tc, O(1) sc - using Moore's voting algo
    public List<Integer> majorityElement2(int[] nums) {
        int count1=0;
        int count2=0;
        int candidate1=Integer.MIN_VALUE;
        int candidate2=Integer.MIN_VALUE;
        List<Integer> ans=new ArrayList<>();
        for(int i:nums){
            if(i==candidate1){ //this will make sure that candidate 1 and 2 never point to the same candidate otherwise
                // that will cause problems
                count1++;
            }
            else if(i==candidate2){
                count2++;
            }
            else if(count1==0){
                candidate1=i;
                count1=1;//here we have to explicitly set count to 1 whenever it reaches 1 because here we cannot use
                // multiple if statements as we are dealing with 2 elements
            }
            else if(count2==0){
                candidate2=i;
                count2=1;
            }
            else{
                count1--;
                count2--;
            }
        }
        // it is not ensured that candidate 1 and 2 will always be majority elements as it is not given in the question
        // that there will always be majority elements. There might be a case where each element appears equal number of times
        // & thus we make sure that they actually are in majority
        count1=0;
        count2=0;
        for(int i:nums){
            if(i==candidate1){
                count1++;
            }
            if(i==candidate2){
                count2++;
            }
        }
        if(count1>nums.length/3){

            ans.add(candidate1);
        }
        if(count2>nums.length/3){
            ans.add(candidate2);
        }
        return ans;
    }

    //unique grid paths
    //https://leetcode.com/problems/unique-paths/

    //brute O(2^N) tc, O(2^N) sc (recursive stack space) - recursively find all possible paths from start to end (in down
    // and right directions) and return the total unique paths
    public int uniquePaths(int m, int n) {
        return uP(0, 0, m, n);
    }

    public int uP(int i, int j, int m, int n) {
        if (i >= m || j >= n) {
            return 0;
        }
        if (i == m - 1 && j == n - 1) {
            return 1;
        }
        return uP(i + 1, j, m, n) + uP(i, j + 1, m, n);
    }

    //better O(N*M) time and space - memoization
    public int uniquePaths1(int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        return uP(0, 0, m, n, dp);
    }

    public int uP(int i, int j, int m, int n, int[][] dp) {
        if (i >= m || j >= n) {
            return 0;
        }
        if (i == m - 1 && j == n - 1) {
            return 1;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        return dp[i][j] = uP(i + 1, j, m, n, dp) + uP(i, j + 1, m, n, dp);
    }

    //optimal O(N) tc, O(1) - using combinations approach
    public int uniquePaths2(int m, int n) {
        double ans = 1;
        int totalWays = m + n - 2; // total number of times we are allowed to move in a path
        int r = m - 1; // total number of times we are allowed to go down in a path
        for (int i = 1; i <= r; i++) {
            ans = ans * (totalWays - i + 1) / i; //no. of ways we can move down
        }
        return (int) Math.round(ans); // Round the result to the nearest integer
    }

    //reverse pairs
    //https://leetcode.com/problems/reverse-pairs

    //brute O(N2)tc, O(1)sc iterate over the array and for each element check on its right if it is greater than 2wice
    // of any element and increment the count in that case

    //optimal O(NlogN)+O(N)tc, O(N- temp array)sc - Merge sort variation

    public int reversePairs(int[] nums) {
        int n = nums.length;
        return mergeSort2(0, n - 1, nums);
    }

    public int mergeSort2(int start, int end, int[] arr) {
        int pairs = 0;
        if (start >= end) {
            return pairs;
        }
        int mid = (start + end) / 2;
        pairs += mergeSort2(start, mid, arr);
        pairs += mergeSort2(mid + 1, end, arr);
        pairs += merge2(start, mid, end, arr);
        return pairs;
    }

    public int merge2(int start, int mid, int end, int[] arr) {
        int pairs = 0;
        int j = mid + 1;
        for (int i = start; i <= mid; i++) {
            while (j <= end && arr[i] > 2 * (long) arr[j]) {
                j++;
            }
            pairs += j - (mid + 1);
        }
        int left = start;
        int right = mid + 1;
        List<Integer> temp = new ArrayList<>();
        while (left <= mid && right <= end) {
            if (arr[left] < arr[right]) {
                temp.add(arr[left]);
                left++;
            } else {
                temp.add(arr[right]);
                right++;
            }
        }
        while (left <= mid) {
            temp.add(arr[left]);
            left++;
        }
        while (right <= end) {
            temp.add(arr[right]);
            right++;
        }
        for (int i = start; i <= end; i++) {
            arr[i] = temp.get(i - start);
        }
        return pairs;
    }

    //Two Sum
    //https://leetcode.com/problems/two-sum/

    //brute O(N2) time, O(1) space - iterate over the array and for each element check on its right if its sum with an element equals target and
    // in case it does, then return indices of both the elements

    //better O(N2 wc - in case the other matching element is the last one we have to iterate over the whole array) - start
    // inserting elements in the map and if there exists an element in the map such that target-nums[i]=that element, then
    // return the indices of these two elements

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i]))
                return new int[]{map.get(target - nums[i]), nums[i]};
            map.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

    //optimal O(N+NlogN+N) time, O(N) space - make a copy of the original array, then sort the array and apply two pointer.
    // If ans found, retrieve the indices of the two elements from the temp array and return them.

    public int[] twoSum1(int[] nums, int target) {
        int[] temp = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            temp[i] = nums[i];
        }
        Arrays.sort(nums);
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            if (nums[left] + nums[right] == target) {
                break;
            } else if (nums[left] + nums[right] < target) { //use of else if is necessary. otherwise the pointers would be moved unnecessarily
            } else {
                right--;
            }
        }
        int[] ans = {-1, -1};
        for (int i = 0; i < nums.length; i++) {
            if (temp[i] == nums[left]) {
                ans[0] = i;
                break;
            }
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            if (temp[i] == nums[right]) {
                ans[1] = i;
                break;
            }
        }
        return ans;
    }
    //4 Sum
    //https://leetcode.com/problems/4sum/

    //brute O(N4) - take 4 pointers, i from 0 to n, j from i+1 to n, k from j+1 to n, and l from k+1 to n and check if the
    // elements at the 4 positions make a valid sum and return them if they do

    //better O(NlogN+N3logN+M(where M is the number of quadruples, and to iterate over the quadruples in the set and store
    // them in the list it would take O(M) time)) - three pointers+binary search: we basically sort the array, then take three pointers at i at the
    // start, j from i+1, and k from j+1. Now the quadruple includes arr[i],arr[j],arr[k], and the 4th element, which lies
    // in the right half. So we use binary search to find the 4th element. We can use hashset to avoid duplicate quadruples.

    public List<List<Integer>> fourSum(int[] nums, int target) {
        int n = nums.length;
        Arrays.sort(nums);
        HashSet<List<Integer>> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    long sum = nums[i] + nums[j];
                    sum += nums[k];
                    long x = target - sum;
                    if (binarySearch(x, Arrays.copyOfRange(nums, k + 1, n))) {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[i]);
                        list.add(nums[j]);
                        list.add(nums[k]);
                        list.add((int) x);
                        Collections.sort(list); //here we basically sort the list so that set can identify the duplicates better
                        set.add(list);
                    }
                }
            }
        }
        List<List<Integer>> ans = new ArrayList<>(set);
        return ans;
    }

    public boolean binarySearch(long target, int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return true;
            } else if (nums[mid] > target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return false;
    }

    //optimal - O(NlogN+N3) - sort the array, take two pointers at i at start and j at i+1 and two pointers left and right
    // at j+1 and n-1. We basically try to apply two sum in the right of i and j to find the other two elements such that
    // their sum=target-(arr[i]+arr[j])

    //once we sort the list, duplicate quadruples will be removed

    public List<List<Integer>> fourSum1(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums.length == 0) {
            return ans;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                long t=(long)target-(long)(nums[i]+nums[j]);
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
    //https://leetcode.com/problems/longest-consecutive-sequence

    //brute - O(NlogN+N) tc, O(1) sc - sort the array, then iterate over it and check if arr[i]+1=arr[i+1] and if it is, increase count,
    // otherwise set it to 1

    public int longestConsecutive(int[] nums) {
        int n = nums.length;
        if (n == 0 || n == 1) {
            return n;
        }
        Arrays.sort(nums);
        int count = 1;
        int maxCount = 1;
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] + 1 == nums[i + 1]) {
                count++;
            } else if (nums[i + 1] != nums[i]) {
                count = 1;
            }
            maxCount = Math.max(count, maxCount);
        }
        return maxCount;
    }

    //optimal O(N+N+N(while loop wc)) tc , O(N) sc - insert elements into a hash set,then iterate over the array and check if num-1
    // exists in set and if it doesn't then it means that this is the first element of its sequence, and thus, we run a
    // while loop till the current num + 1 exists in the set, increasing the count and current num accordingly. Then,
    // out of all counts, we return the max count

    public int longestConsecutive2(int[] nums) {
        HashSet<Integer> set=new HashSet<>();
        int max=0;
        for(int i:nums){
            set.add(i);
        }
        for(int i:nums){
            if(!set.contains(i-1)){ //since we're using a set, we don't have to check for duplicates
                int currentNum=i;
                int count=1;
                while(set.contains(currentNum+1)){
                    currentNum++;
                    count++;
                }
                max=Math.max(count,max);
            }
        }
        return max;
    }

    //largest sub array with 0 sum
    //https://practice.geeksforgeeks.org/problems/largest-subarray-with-0-sum/1

    //brute - O(N2)tc, O(1)sc - find all possible sub arrays, calculate sum in all and if it is 0, find length of current
    // sub array and update accordingly in max if greater
    int maxLen(int arr[], int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        int maxLen = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum = arr[i];
            for (int j = i + 1; j < n; j++) {
                sum += arr[j];
                if (sum == 0) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen == Integer.MIN_VALUE ? 0 : maxLen;
    }

    //optimal - O(NlogN(map+traversal))tc, O(N)sc
    int maxLen1(int arr[], int n) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int prefixSum = 0;
        int maxSum = 0;
        for (int i = 0; i < n; i++) {
            prefixSum += arr[i];
            if (prefixSum == 0) {
                maxSum = i + 1; //this will be longest yet thus no need to compare
            } else if (!map.containsKey(prefixSum)) {
                map.put(prefixSum, i);
            } else { //map contains prefix sum
                maxSum = Math.max(maxSum, i - map.get(prefixSum));
            }
        }
        return maxSum;
    }

    //number of sub arrays with given xor
    //https://www.interviewbit.com/problems/subarray-with-given-xor/

    //brute - O(N3) or O(N2) - using 3 or 2 pointers (optimized) to find all sub arrays and increase count whenever xor
    // of elements of a sub array equals k. one extra condition to check: if the current element ie the one in at the ith
    // index is itself equal to B, then also we increase the count
    public int solve(ArrayList<Integer> A, int B) {
        int xor = 0;
        int count = 0;
        for (int i = 0; i < A.size(); i++) {
            xor = A.get(i);
            if (xor == B) { //case when the element itself is equal to B
                count++;
            }
            for (int j = i + 1; j < A.size(); j++) {
                xor = xor ^ A.get(j);
                if (xor == B) {
                    count++;
                }
            }
        }
        return count;
    }

    //optimal - O(NlogN: map+traversal)tc, O(N)sc
    public int solve1(ArrayList<Integer> A, int B) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int xor = 0;
        int count = 0;
        for (int i = 0; i < A.size(); i++) {
            xor ^= A.get(i);
            int y = xor ^ B;
            if (xor == B) {
                count++;
            }
            if(map.containsKey(y)){ //this means that through the prefix xor, we found a value for y which already exists in the hashmap. this means that the subarray with xor y has appeared before. Therefore that xor y when xor'ed with B, gave us this prefix xor
                count+=map.get(y);
            }
            map.put(xor,map.getOrDefault(xor,0)+1);
        }
        return count;
    }

    //longest substring without repeating characters
    //https://leetcode.com/problems/longest-substring-without-repeating-characters\

    //brute O(N2)time,O(N (HashSet))space - find all possible substrings and obtain length of the valid ones, then return
    // max length overall
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int len = 0;
        int ans = 0;
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            len = 1;
            set.add(s.charAt(i));
            for (int j = i + 1; j < n; j++) {
                if (set.contains(s.charAt(j))) { //repeating character
                    break;
                } else {
                    set.add(s.charAt(j));
                    len++;
                }
            }
            set.clear();
            ans = Math.max(ans, len);
        }
        return ans;
    }

    //optimal1 - when u revise this 2mrw watch striver video

    //we basically take two pointers left and right within which we try to take our substrings

    //(O(2N)tc as we initially loop right pointer from left till we reach a duplicate, then we loop the left pointer till
    // the duplicate has been removed, O(1)space because the set will at most store 26 characters)

    public int lengthOfLongestSubstring1(String s) {
        int ans = 0;
        HashSet<Character> set = new HashSet<>();
        int left = 0;
        int right = 0;
        while (right < s.length()) {
            if (set.contains(s.charAt(right))) {
                while (left < right && set.contains(s.charAt(right))) {
                    set.remove(s.charAt(left));
                    left++;
                }
            }
            set.add(s.charAt(right));
            ans = Math.max(ans, right - left + 1);
            right++;

        }
        return ans;
    }

    //optimal2
    //This reduces tc to O(N) as we don't have to iterate the left pointer & sc is still O(26) in the worst case as the map might
    // store at max all the 26 letters of alphabet
    public int lengthOfLongestSubstring2(String s) {
        int ans = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        int left = 0;
        int right = 0;
        while (right < s.length()) {
            if (map.containsKey(s.charAt(right))) {
                left = Math.max(left, map.get(s.charAt(right)) + 1); //there might be a case when we find an existent char
                // in the map but left is already ahead of that.this can happen because we aren't removing any el from the
                // map like we were doing previously in the set. consider the eg: abcaabc, now when we skip left over the
                // second 'a', when right eventually moves, it points to the second occurrence of b. now since the map still
                // contains the prev b, there might be a possibility that we might end up moving left backwards(after the
                // first occurrence) even though the first 'b' was never in our range. thus we always take max of left and
                // s.charAt(i)+1. ie there can be a case when the duplicate el isn't part of the current substring between
                // left and right
            }
            map.put(s.charAt(right), right); //storing the most recent occurrence of current element in the map
            ans = Math.max(ans, right - left + 1);
            right++;
        }
        return ans;
    }

    //common elements in 3 sorted arrays
    //https://practice.geeksforgeeks.org/problems/common-elements1132/1

    //brute - find all possible combinations of triplets formed from elements of the three arrays. once we find a triplet
    // in which every el is same, we add that el to the ans list. to ensure there are no duplicates, we make sure that we
    // never take the same element twice from the first array

    //O(N3) time, O(M) space, if there are M common elements
    ArrayList<Integer> commonElements(int A[], int B[], int C[], int n1, int n2, int n3) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n1; i++) {
            if (i > 0 && A[i] == A[i - 1]) {
                continue;
            }
            for (int j = 0; j < n2; j++) {
                if (j > 0 && B[j] == B[j - 1]) {
                    continue;
                }
                for (int k = 0; k < n3; k++) {
                    if (k > 0 && C[k] == C[k - 1]) {
                        continue;
                    }
                    if (A[i] == B[j] && B[j] == C[k]) {
                        list.add(A[i]);
                    }
                }
            }
        }
        return list;
    }

    //better (using intersection of two arrays)- find intersection of arr1 and 2, and then the intersection of that with
    // the third arr
    //O(n1+n2+n3)time, O(m) space where m is the number of common elements

    ArrayList<Integer> commonEleme1nts(int A[], int B[], int C[], int n1, int n2, int n3)
    {
        ArrayList<Integer> temp=new ArrayList<>();
        ArrayList<Integer> ans=new ArrayList<>();
        int i=0;
        int j=0;
        while(i<n1&&j<n2){
            if(i>0&&A[i]==A[i-1]){
                i++;
                continue;
            }
            if(A[i]==B[j]){
                temp.add(A[i]);
                i++;
                j++;
            }
            else if(A[i]>B[j]){
                j++;
            }
            else{
                i++;
            }
        }
        i=0;
        j=0;
        while(i<temp.size()&&j<n3){
            if(i>0&&temp.get(i)==temp.get(i-1)){
                i++;
                continue;
            }
            if(temp.get(i)==C[j]){
                ans.add(temp.get(i));
                i++;
                j++;
            }
            else if(temp.get(i)>C[j]){
                j++;
            }
            else{
                i++;
            }
        }
        return ans;
    }

    //optimal - O(n1+n2+n3) time, O(1) space

    //basically we take three pointers such that if arr[i]<arr[j], then obv el in arr1 that i is pointing to cannot be the
    // common one thus we increase i. otherwise if arr[i]>arr[j] we check if el j is pointing to is > whatever k is pointing
    // to. in that case we know that eventually we'd have to increase j to match whatever i is pointing to but we also need
    // to increase k so that it also matches i. so basically the idea is to move the ptr which is pointing to the smallest
    // el in each iteration. this makes sure that this el matches the other two els. as soon as all point to same el, we
    // add it to a list and move all the pointers by 1.

    ArrayList<Integer> commonEleme2nts(int A[], int B[], int C[], int n1, int n2, int n3)
    {
        int i=0;
        int j=0;
        int k=0;
        ArrayList<Integer> list=new ArrayList<>();
        while(i<n1&&j<n2&&k<n3){
            if(i>0&&A[i]==A[i-1]){
                i++;
                continue;
            }
            if(A[i]==B[j]&&B[j]==C[k]){
                list.add(A[i]);
                i++;
                j++;
                k++;
            }
            else if(A[i]<B[j]){
                i++;
            }
            else if(B[j]<C[k]){
                j++;
            }
            else{
                k++;
            }
        }
        return list;
    }




}
