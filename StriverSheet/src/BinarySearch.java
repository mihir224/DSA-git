import java.util.ArrayList;
import java.util.Arrays;

public class BinarySearch {
    //imp - binary search can be implemented to any search space that is monotonic in nature

    //nth root of a number
    //https://www.codingninjas.com/codestudio/problems/1062679?topList=striver-sde-sheet-problems&utm_source=striver&utm_medium=website&leftPanelTab=0
    //although bs is used to search an element in an array, we can also use it in functions which are monotonic (ie they
    // return increasing or decreasing values). Now we know for sure that the nth root of an integer will never be greater than it.
    // Thus we apply binary search from 1 till the given integer such that if the current mid when multiplied n times gives a value>M
    // ie the given integer, we reduce the search space from 1 till mid. Otherwise, if the multiplied value is less than M, we reduce
    // search space from mid till end. Now since we have to return the ans close to 6 decimal places, we stop the search when start
    // and end become nearly equal ie their difference is close to 6 decimal places and since we are doing binary search on elements
    // between 1 to M considering elements upto say, k decimal places, then for that the complexity becomes O(logbase2(M*(10)^k)) and
    // in each iteration we are multiplying mid N times itself and thus the overall complexity becomes - O(N*logbase2(M*(10)^k))

    public static double findNthRootOfM(int n, int m) {
        double start=1;
        double end=m;
        double eps=1e-7;
        while((end-start)>eps){
            double mid=start+(end-start)/2;
            double temp=1.00;
            for(int i=0;i<n;i++){
                temp*=mid;
            }
            if(temp>=m){
                end=mid; //end is not mid-1 and start is not mid+1 because here we are taking 1 based indexing
            }
            else{
                start=mid;
            }
        }
        return (start+(end-start)/2);
    }
    //gfg question for nth root wants us to return the nth root only if it is an integer
    //https://practice.geeksforgeeks.org/problems/find-nth-root-of-m5843/1
    //solution for the same
    public int NthRoot(int n, int m)
    {
        for(int i=0;Math.pow(i,n)<=m;i++){
            if(Math.pow(i,n)==m){
                return i;
            }
        }
        return -1;
    }

    //matrix median
    //https://www.codingninjas.com/codestudio/problems/873378?topList=striver-sde-sheet-problems&utm_source=striver&utm_medium=website&leftPanelTab=1
    //brute force - O((n*m)log(n*m)) to sort the flat array and O(n*m) to flatten the given matrix
    //we simply flatten the given matrix, sort it and return the middle element since it is given that m*n will always be odd and thus median will always be middle
    public static int getMedian(ArrayList<ArrayList<Integer>> matrix)
    {
        int n=matrix.size();
        int m=matrix.get(0).size();
        int[] flatArray=new int[n*m];
        int k=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                flatArray[k]=matrix.get(i).get(j);
                k++;
            }
        }
        Arrays.sort(flatArray);
        return flatArray[(n*m)/2];
    }

    //optimal - using binary search
    // O((logbase2(2^32) ie 32-for applying bs in a search space of 1e9 where 1e9 equates to 2^32)*(N-to find less than mid for
    // each low)*logbase2(M) - applying bs inside each row of size m to find the num of elements less than mid in that row))
    public static int getMedian2(ArrayList<ArrayList<Integer>> matrix)
    {
        int n=matrix.size();
        int m=matrix.get(0).size();
        int start=1;
        int end= (int) 1e9;
        while(start<=end){
            int mid=(start+end)/2;
            int count=0;
            for(int i=0;i<n;i++){
                count+=findLessThanMid(matrix.get(i),mid);
            }
            if(count<=(n*m)/2){
                start=mid+1;
            }
            else{
                end=mid-1;
            }
        }
        return start;
    }
    public static int findLessThanMid(ArrayList<Integer> list,int mid){
        int start=0;
        int end=list.size()-1;
        while(start<=end){
            int md=(start+end)/2;
            if(list.get(md)<=mid){
                start=md+1;
            }
            else{
                end=md-1;
            }
        }
        return start;
    }

    //single element in a sorted array
    //https://leetcode.com/problems/single-element-in-a-sorted-array/
    //O(logbase2N) - bs
    //we simply need to find the break point at which we find the single element. We can observe a pattern that all elements
    // on the left of the single element are such that 1st instance of an element is at even index and the other is at an
    // odd index whereas in the second half, 1st instance is at an odd index and other instance is at even index. Thus,
    // for each mid we check if it is equal to its other instance. We do this for both odd and even cases by using the
    // xor trick where simply xor-ing the mid with 1 will return the index before it if mid is odd and it will return
    // the index after it if mid is even. If the instances are equal, we simply move the search space using low=mid+1
    // otherwise ie the case when the instances are not equal, ie we are in the right half, we reduce hi to mid-1 and
    // when low crosses high, we've found our single element

    public int singleNonDuplicate(int[] nums) {
        int start=0;
        int end=nums.length-2;
        while(start<=end){
            int mid=(start+end)/2;
            if(nums[mid]==nums[mid^1]){ //we're in the left half
                start=mid+1;
            }
            else{
                end=mid-1;
            }
        }
        return nums[start];
    }

    //rotated bs
    //https://leetcode.com/problems/search-in-rotated-sorted-array/
    static int search(int[] arr,int target){
        int pivot=findPivot(arr);
        if(pivot==-1){ //no rotation
            return binarySearch(arr,target,0,arr.length-1);
        }
        else if(arr[pivot]==target){
            return pivot;
        }
        else if(target>=arr[0]){ //target lies between start and pivot
            return binarySearch(arr,target,0,pivot);
        }
        return binarySearch(arr,target,pivot+1,arr.length-1); //target lies between pivot and end
    }
    public static int binarySearch(int[] arr, int target,int start,int end){
        while(start<=end){
            int mid=start+(end-start)/2;
            if(arr[mid]==target) {
                return mid;
            }
            else if(arr[mid]<target){
                start=mid+1;
            }
            else {
                end=mid-1;
            }
        }
        return -1;
    }
    static int findPivot(int[] arr){
        int start=0;
        int end=arr.length-1;
        while(start<=end){
            int mid=start+(end-start)/2;
            if(mid<end&&arr[mid]>arr[mid+1]){
                return mid;
            }
            else if(mid>start&&arr[mid-1]>arr[mid]){
                return mid-1;
            }
            else if(arr[start]>=arr[mid]){
                end=mid-1;
            }
            else{
                start=mid+1;
            }
        }
        return -1;
    }
    //median of two sorted arrays
    //https://leetcode.com/problems/median-of-two-sorted-arrays

    //to reduce complexity we apply bs on smaller array and there also might be a possibility that cut2 will get out of
    // bounds for the smaller array
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1=nums1.length;
        int n2=nums2.length;
        if(n1>n2){
            return findMedianSortedArrays(nums2,nums1);
        }
        int start=0;
        int end=n1; //since we can pick up a maximum of all the elements from array1 to be in the left half
        while(start<=end){
            int cut1=(start+end)/2;
            int cut2=(n1+n2+1)/2-cut1;
            int l1=cut1==0?Integer.MIN_VALUE:nums1[cut1-1];
            int l2=cut2==0?Integer.MIN_VALUE:nums2[cut2-1];
            int r1=cut1==n1?Integer.MAX_VALUE:nums1[cut1];
            int r2=cut2==n2?Integer.MAX_VALUE:nums2[cut2];
            if(l1<=r2&&l2<=r1){
                if((n1+n2)%2==0){
                    return (Math.max(l1,l2)+Math.min(r1,r2))/2.0;
                }
                else {
                    return Math.max(l1,l2);
                }
            }
            else if(l1>r2){
                end=cut1-1; //increasing r2 by decreasing l1
            }
            else{
                start=cut1+1; //increasing r1
            }
        }
        return 0.0;
    }

    //kth element of two sorted arrays
    //https://practice.geeksforgeeks.org/problems/k-th-element-of-two-sorted-array1317/1
    //logbase2(min(n1,n2))
    public long kthElement( int nums1[], int nums2[], int n1, int n2, int k) {
        if(n1>n2){
            return kthElement(nums2,nums1,n2,n1,k);
        }
        int start=Math.max(0,k-n2); //in case k>n2  ie we have to pick a atleast k-n2 elements from array 1
        int end=Math.min(k,n1); //in case k<n1 ie at max we can pick only k elements from array1
        while(start<=end){
            int cut1=(start+end)/2;
            int cut2=k-cut1; //since we have to find the kth element, we take first k elements in the left half
            int l1=cut1==0?Integer.MIN_VALUE:nums1[cut1-1];
            int l2=cut2==0?Integer.MIN_VALUE:nums2[cut2-1];
            int r1=cut1==n1?Integer.MAX_VALUE:nums1[cut1];
            int r2=cut2==n2?Integer.MAX_VALUE:nums2[cut2];
            if(l1<=r2&&l2<=r1){
                return Math.max(l1,l2);
            }
            else if(l1>r2){
                end=cut1-1; //increasing r2 by decreasing l1
            }
            else{
                start=cut1+1; //increasing r1
            }
        }
        return -1;
    }
}

