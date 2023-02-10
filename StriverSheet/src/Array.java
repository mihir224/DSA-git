import java.util.*;

public class Array {
    //Set matrix zeroes
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
    public void setZeroes1(int[][] matrix){
        boolean col=true;
        int rows=matrix.length;
        int cols=matrix[0].length;
        for(int i=0;i<rows;i++){
            if(matrix[i][0]==0){
                col=false;
            }
            for(int j=1;j<cols;j++){
                if(matrix[i][j]==0){
                    matrix[i][0]=0;
                    matrix[0][j]=0;
                }
            }
        }
        for(int i=rows-1;i>=0;i--){
            for(int j=cols-1;j>=1;j--){
                if(matrix[i][0]==0||matrix[0][j]==0){
                    matrix[i][j]=0;
                }
            }
            if(!col){
                matrix[i][0]=0;
            }
        }
    }

    //Pascal's triangle
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
        List<List<Integer>> ans=new ArrayList<>();
        List<Integer> temp=new ArrayList<>();
        for(int i=0;i<numRows;i++){
            List<Integer> list=new ArrayList<>();
            for(int j=0;j<=i;j++){ //for each row, the number of cols==current row number
                if(j==0||j==i){ //initialize first col and last col to 1
                    list.add(1);
                }
                else{
                    list.add(temp.get(j-1)+temp.get(j));
                }
            }
            temp=list; //to use previous row in next iteration
            ans.add(list);
        }
        return ans;
    }

    //Next permutation
    //https://leetcode.com/problems/next-permutation/
    public void nextPermutation(int[] nums) {
        if(nums.length==1||nums.length==0){
            return;
        }
        int i=nums.length-2; //the breakpoint might be at secondlast index
        while(i>=0&&nums[i]>=nums[i+1]){ //iterating backwards till we find the break point
            i--;
        }
        if(i>=0){ //case when breakpoint exists
            int j=nums.length-1;
            while(nums[j]<=nums[i]){ //iterating backwards to find someone greater than breakpoint
                j--;
            }
            swap(nums,i,j); //swapping breakpoint & someone greater than it
        }
        reverse(nums,i+1,nums.length-1); //reversing right half
    }
    public void swap(int[] nums, int i, int j){
        int temp=nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }
    public void reverse(int[] nums, int i,int j){
        while(i<j){
            swap(nums,i,j);
            i++;
            j--;
        }
    }

    //kadane's algo
    public int maxSubArray(int[] nums) {
        int maxSum=Integer.MIN_VALUE;
        int sum=0;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
            maxSum=Math.max(sum,maxSum);
            if(sum<0){
                sum=0;
            }
        }
        return maxSum;
    }

    //sort colors
    public void sortColors(int[] nums) {
        int low=0;
        int mid=0;
        int hi=nums.length-1;
        while(mid<=hi){
            if(nums[mid]==0){
                swap2(nums,mid++,low++);
            }
            else if(nums[mid]==1){
                mid++;
            }
            else{
                swap2(nums,mid,hi--);
            }
        }
    }
    public void swap2(int[] nums,int a,int b){
        int temp=nums[a];
        nums[a]=nums[b];
        nums[b]=temp;
    }

    //best time to buy or sell stock
    //https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
    public int maxProfit(int[] prices) {
        int currentProfit=0;
        int netProfit=0;
        int lp=Integer.MAX_VALUE;
        for(int i:prices){
            if(i<lp){
                lp=i;
            }
            currentProfit=i-lp;
            if(currentProfit>netProfit){
                netProfit=currentProfit;
            }
        }
        return netProfit;
    }

    //rotate matrix
    //https://leetcode.com/problems/rotate-image/
    public void rotate(int[][] matrix) {
        int n=matrix.length;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                int temp=matrix[i][j];
                matrix[i][j]=matrix[j][i];
                matrix[j][i]=temp;
            }
        }
        for(int arr[]:matrix){
            reverse(arr,0,arr.length-1);
        }
    }

    //merge intervals
    //https://leetcode.com/problems/merge-intervals/
    public int[][] merge(int[][] intervals) {
        List<int[]> ans=new ArrayList<>();
        if(intervals.length==0){
            return ans.toArray(new int[ans.size()][2]); //this parameter is the initialization of a new array to which
            // contents of the list are copied to
        }
        Arrays.sort(intervals, (a,b)->a[0]-b[0]);
        int start=intervals[0][0];
        int end=intervals[0][1];
        for(int[] i:intervals){
            if(i[0]<=end){ //merge possible
                end=Math.max(end,i[1]);
            }
            else{ //merge not possible
                ans.add(new int[]{start,end});
                start=i[0];
                end=i[1];
            }
        }
        ans.add(new int[]{start,end});
        return ans.toArray(new int[ans.size()][2]);
    }

    //merge sorted array
    //https://leetcode.com/problems/merge-sorted-array/
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        while(n>0&&m>0){
            if(nums1[m-1]>nums2[n-1]){
                nums1[m+n-1]=nums1[m-1];
                m--;
            }
            else{
                nums1[m+n-1]=nums2[n-1];
                n--;
            }
        }
        while(n>0){ //case when there is no element in nums1 & nums2 is non empty
            nums1[m+n-1]=nums2[n-1];
            n--;
        }
    }

    //find duplicates
    //https://leetcode.com/problems/find-the-duplicate-number/

    //brute (O(n2)tc-O(1)sc)
    public int findDuplicate(int[] nums) {
        int i=0;
        while(i<nums.length) {
            int correctIndex = nums[i] - 1;
            if (nums[i] != nums[correctIndex]) {
                int temp = nums[i];
                nums[i] = nums[correctIndex];
                nums[correctIndex] = temp;
            } else {
                i++;
            }
        }
        for(int index=0;index< nums.length;index++){
           if(nums[index]!=index+1){
               return nums[index];
           }
       }
       return -1;
    }

    //better - using hashing, storing frequency of all elements and returning the element which appears more than once
    public int findDuplicate2(int[] nums) {
        int[] vis=new int[nums.length];
        for(int i:nums){
            vis[i-1]++;
        }
        for(int i=0;i<vis.length;i++){
            if(vis[i]>1){
                return i+1;
            }
        }
        return -1;
    }

    //optimal - using linked-list cycle
    public int findDuplicate3(int[] nums) {
        int slow=nums[0];
        int fast=nums[0];
        do{
            slow=nums[slow];
            fast=nums[nums[fast]];
        }while(slow!=fast);
        fast=nums[0];
        while(slow!=fast){
            slow=nums[slow];
            fast=nums[fast];
        }
        return slow; //duplicate is bound to be here
    }

    //set-mismatch (duplicate and missing element)
    //https://www.interviewbit.com/problems/repeat-and-missing-number-array/

    //brute (O(N) both)
    public ArrayList<Integer> repeatedNumber(final List<Integer> A) {
        int[] vis=new int[A.size()];
        ArrayList<Integer> list=new ArrayList<>();
        for(int i:A){
            vis[i-1]++;
        }
        for(int index=0;index<vis.length;index++){
            if(vis[index]>1){
                list.add(0,index+1);
            }
            if(vis[index]==0){
                list.add(index+1);
            }
        }
        return list;
    }

    //optimal (O(N)tc-O(1)sc)
    //larger testcase stuck
    public ArrayList<Integer> repeatedNumber2(final List<Integer> A) {
        ArrayList<Integer> list=new ArrayList<>();
        long n=A.size();
        long s=(n*(n+1))/2;
        long p=(n*(n+1)*(2*n+1))/6;
        long x=0;
        long y=0;
        for(int i=0;i<A.size();i++){
            s-=A.get(i);
            p-=A.get(i)*A.get(i);
        }
        x=(p/s+s)/2;
        y=x-s;
        list.add((int)y);
        list.add((int)x);
        return list;
    }

    //count inversions
    //https://www.codingninjas.com/codestudio/problems/615?topList=striver-sde-sheet-problems&utm_source=striver&utm_medium=website

    //brute (O(n2)tc,O(1)sc)
    //just check if any element is greater than elements on its right, if it is, increase count
    public static long getInversions(long arr[], int n){
        int count=0;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                if(arr[i]>arr[j]){
                    count++;
                }
            }
        }
        return count;
    }

    //optimal-merge sort variation (O(nlogn)tc,O(n)sc)
    public static long getInversions2(long arr[], int n){
        int count=0;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                if(arr[i]>arr[j]){
                    count++;
                }
            }
        }
        return count;
    }
}
