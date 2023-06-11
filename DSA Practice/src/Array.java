import java.util.ArrayList;
import java.util.List;

public class Arrays {
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

    //brute O(NlogN+N2) tc, O(N) sc
    //sort the intervals array, then iterate over all the intervals and for each interval check if there are any valid
    // intervals on its right that can be merged to it and if there are, then merge them with te current interval.
    // After merging all valid intervals, add the resultant to a DS and then move to the next interval

    //optimal O(NlogN+N) tc, O(N) sc

    class Pair{
        int first;
        int second;
        public Pair (int first, int second){
            this.first=first;
            this.second=second;
        }
    }
    public int[][] merge(int[][] intervals) {
        List<Pair> list=new ArrayList<>();
        if(intervals.length==0){
            return list.toArray(new int[0][0]);
        }
        
    }


}
