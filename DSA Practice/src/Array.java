import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Array {
    //set matrix zeroes
    //https://leetcode.com/problems/set-matrix-zeroes/

    //brute
    //O(2(N*M)) tc, O(N+M) sc
    public void setZeroes(int[][] matrix) {
        int[] dummyRow=new int[matrix.length];
        int[] dummyCol=new int[matrix[0].length];
        for(int i=0;i< matrix.length;i++){
            for(int j=0;j< matrix[0].length;j++){
                if(matrix[i][j]==0){
                    dummyRow[i]=-1;
                    dummyCol[j]=-1;
                }
            }
        }
        for(int i=0;i< matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                if(dummyRow[i]==-1||dummyCol[j]==-1){
                    matrix[i][j]=0;
                }
            }
        }
    }

    //optimal
    //O(2(N*M)) tc, O(1) sc
    public void setZeroes1(int[][] matrix) {
        boolean col=true;
        for(int i=0;i<matrix.length;i++){
            if(matrix[i][0]==0){ //if any element of col1 is 0, we turn it to zero
                col=false;
            }
            for(int j=1;j<matrix[0].length;j++){ //starting with j=1 since we already dealt with j=0
                if(matrix[i][j]==0){
                    matrix[i][0]=0;
                    matrix[0][j]=0;
                }
            }
        }
        for(int i=matrix.length-1;i>=0;i--){
            for(int j=matrix[0].length-1;j>0;j--){
                if(matrix[i][0]==0||matrix[0][j]==0){
                    matrix[i][j]=0;
                }
            }
            if(!col){
                matrix[i][0]=0; //dealing with 1st col
            }
        }
    }

    //pascal's triangle
    //https://leetcode.com/problems/pascals-triangle/

    //O(N2) tc, O(M) where M is the avg size of each row
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans=new ArrayList<>();
        List<Integer> prev=new ArrayList<>();
        for(int i=0;i<numRows;i++){
            List<Integer> list=new ArrayList<>();
            for(int j=0;j<=i;j++){
                if(j==0||j==i){
                    list.add(1);
                }else{
                    list.add(prev.get(j-1)+prev.get(j));
                }
            }
            prev=list;
            ans.add(list);
        }
        return ans;
    }

    //next permutation
    //https://leetcode.com/problems/next-permutation/

    //O(3N)
    public void nextPermutation(int[] nums) {
        int n=nums.length;
        if(n==0||n==1){
            return;
        }
        int breakPoint=-1;
        for(int i=n-2;i>=0;i--){
            if(nums[i]<nums[i+1]){
                breakPoint=i;
                break;
            }
        }
        if(breakPoint>=0) { //case when bp exists
            for (int i = n - 1; i >= 0; i--) {
                if (nums[i] > nums[breakPoint]) {
                    swap(i, breakPoint, nums);
                    break;
                }
            }
        }
        reverse(breakPoint+1,n-1,nums);
    }
    public void swap(int a,int b, int[] nums){
        int temp=nums[a];
        nums[a]=nums[b];
        nums[b]=temp;
    }
    public void reverse(int i, int j, int[] nums){
        while(i<j){
            swap(i,j,nums);
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
        int sum=0;
        int maxSum=Integer.MIN_VALUE;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
            if(sum>maxSum){
                maxSum=sum;
            }
            if(sum<0){
                sum=0;
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
        int count0=0;
        int count1=0;
        int count2=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==0){
                count0++;
            }
            if(nums[i]==1){
                count1++;
            }
            if(nums[i]==2){
                count2++;
            }
        }
        int k=0;
        while(k<count0){
            nums[k]=0;
            k++;
        }
        while(k<count0+count1){
            nums[k]=1;
            k++;
        }
        while(k<nums.length){
            nums[k]=2;
            k++;
        }
    }

    //using three pointer approach
    //same O(N) tc O(1) sc
    public void sortColors1(int[] nums) {
        int low=0;
        int mid=0;
        int hi=nums.length-1;
        while(mid<=hi){
            if(nums[mid]==1){
                mid++;
            }
            else if(nums[mid]==0){
                swap(low,mid,nums);
                low++;
                mid++;
            }
            else if(nums[mid]==2){
                swap(mid,hi,nums);
                hi--;
            }
        }
    }

    //best time to buy and sell stock
    //https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

    //O(N) tc O(1) sc
    //this is basically a sort of DP as we store the ans we got from previous iterations (least price till day) and use
    // it in future iterations
    public int maxProfit(int[] prices) {
        int leastPrice=Integer.MAX_VALUE;
        int netProfit=0;
        for(int i:prices){
            if(i<leastPrice){//calculating net profit through least price till day
                leastPrice=i;
            }
            int currentProfit=i-leastPrice;
            if(currentProfit>netProfit){
                netProfit=currentProfit;
            }
        }
        return netProfit;
    }

    //rotate matrix
    //https://leetcode.com/problems/rotate-image/
    //O(2(N*N));
    public void rotate(int[][] matrix) {
        int n=matrix.length;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){ //observe how j starts from i+1. This is done to handle the condition i!=j and
                // also skip cells that have been swapped
                int temp=matrix[i][j];
                matrix[i][j]=matrix[j][i];
                matrix[j][i]=temp;
            }
        }
        for(int[] row :matrix){
            reverse(0,n,row);
        }
    }


    //merge overlapping intervals
    //https://leetcode.com/problems/merge-intervals/

    //brute O(NlogN+N2) tc, O(N) sc
    //sort the intervals array, then iterate over all the intervals and for each interval check if there are any valid
    // intervals on its right that can be merged to it and if there are, then merge them with te current interval.
    // After merging all valid intervals, add the resultant to a DS and then move to the next interval

    //optimal O(NlogN+N) tc, O(N) sc

    public int[][] merge(int[][] intervals) {
        List<int[]> list=new ArrayList<>();
        if(intervals.length==0){
            return list.toArray(new int[0][0]);
        }
        Arrays.sort(intervals, (a,b)->a[0]-b[0]);
        int lb=intervals[0][0];
        int ub=intervals[0][1];
        for(int[] arr:intervals){
            if(ub>=arr[0]){ //found overlapping interval, merge
                ub=Math.max(ub,arr[1]);
            }
            else{ //merge not possible
                list.add(new int[] {lb,ub});
                lb=arr[0];
                ub=arr[1];
            }
        }
        list.add(new int[]{lb,ub}); //adding the last interval
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
        int slow=nums[0];
        int fast=nums[0];
        do{
            //O(N)
            slow=nums[slow];
            fast=nums[nums[fast]];
        }while(slow!=fast);
        //after first collision
        fast=nums[0];
        //O(N)
        while(slow!=fast){
            slow=nums[slow];
            fast=nums[fast];
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
        int[] freq=new int[nums.length];
        List<Integer> list=new ArrayList<>();
        for(int i:nums){
            freq[i-1]++;
        }
        for(int i=0;i<nums.length;i++){
            if(freq[i]>1){
                list.add(0,i+1);
            }
            if(freq[i]==0){
                list.add(i+1);
            }
        }
        int[] arr=new int[2];
        for(int i=0;i<list.size();i++){
            arr[i]=list.get(i);
        }
        return arr;
    }

    //optimal O(N) time and O(1) space - using maths
    public int[] findErrorNums1(int[] nums) {
        long n=nums.length;
        long s=n*(n+1)/2;
        long p=(n*(n+1)*((2*n)+1))/6;
        long s1=0;
        long p1=0;
        for(int i:nums){
            s1+=i;
            p1+=(long)i*(long)i;
        }
        long sDash=s-s1;
        long pDash=p-p1;
        long missingNum=((pDash/sDash)+sDash)/2;
        long duplicateNum=missingNum-sDash;
        return new int[]{(int)duplicateNum,(int)missingNum};
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


    //search in 2D matrix
    //https://leetcode.com/problems/search-a-2d-matrix/

    //there are two versions of this problem: 1 where it is given that first element of every row is greater than last of
    //prev row (leetcode version) and the other where this condition is not necessarily true (gfg version)

    //brute O(N*M) tc, O(1) sc - linearly search for the element

    //this approach is better for leetcode & optimal for gfg
    //O(N) tc, O(1) sc
    public boolean searchMatrix(int[][] matrix, int target) {
        int row=0;
        int col=matrix[0].length-1;
        while(row<matrix.length&&col>=0){
            if(matrix[row][col]==target){
                return true;
            }
            if(matrix[row][col]>target){
                col--;
            }
            else{
                row++;
            }
        }
        return false;
    }

    //optimal for leetcode - binary search
    //O(log(M*N)) tc, O(1) sc

    public boolean searchMatrix1(int[][] matrix, int target) {
        int n=matrix.length;
        int m=matrix[0].length;
        int start=0;
        int end=n*m-1;
        while(start<=end){
            int mid=start+(end-start)/2;
            int row=mid/m;
            int col=mid%m;
            if(matrix[row][col]==target){
                return true;
            }
            else if(matrix[row][col]<target){
                start=mid+1;
            }
            else{
                end=mid-1;
            }
        }
        return false;
    }

    //pow(x,n)
    //brute O(N) tc, O(1) sc - iterate n times, multiply x with itself in each iteration

    //optimal O(logN) tc, O(1) sc - binary exponentiation
    public double myPow(double x, int n) {
        double ans=1.0;
        long nCopy=n; //we take a long copy of n because there might be a case where n is negative. In that case we have
        // to convert it to a positive value and calculate pow(x,n) and then return 1/ans. If n is a large negative value,
        // making it positive would cause int overflow and that would throw an error. Thus, we make it long beforehand to
        // handle such cases.
        if(nCopy<0){
            nCopy=-1*nCopy; //making n +ve
        }
        while(nCopy>0){
            if(nCopy%2==0){ //even n
                x=x*x;
                nCopy/=2;
            }
            else{
                ans*=x;
                nCopy-=1;
            }
        }
        if(n<0){
            return 1/ans;
        }
        return ans;
    }

    //majority element appearing > N/2 times
    //https://leetcode.com/problems/majority-element/

    //brute O(N2) tc, O(1) sc - traverse the array twice in a nested manner, then check if element in the outer loop equals the one in the
    // inner loop and if it is, increase the count and if for any element count>N/2, then we return it.

    //better (O(N), O(NlogN) wc) tc, O(1) sc - using hashmap to store freq of all N elements and return the one with freq>N/2. O(N)
    // because in java avg insertion time in hashmap is O(1) and for inserting N elements, O(N)

    //optimal O(N) tc, O(1) sc - using Moore's voting algo
    public int majorityElement(int[] nums) {
        int candidate=0;
        int count=0;
        for(int i=0;i<nums.length;i++){
            if(count==0){
                candidate=nums[i];
            }
            if(candidate==nums[i]){
                count++;
            }
            else{
                count--;
            }
        }
        return candidate;
    }

    //majority element appearing > N/3 times
    //https://leetcode.com/problems/majority-element-ii/
    public List<Integer> majorityElement1(int[] nums) {
        int count1=0;
        int count2=0;
        int candidate1=-1;
        int candidate2=-1;
        List<Integer> ans=new ArrayList<>();
        for(int i:nums){
            if(i==candidate1){
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
            if(candidate1==candidate2){
                List<Integer> list=new ArrayList<>();
                list.add(candidate1);
                return list;
            }
            else
                ans.add(candidate1);
        }
        if(count2>nums.length/3){
            ans.add(candidate2);
        }
        return ans;
    }
}
