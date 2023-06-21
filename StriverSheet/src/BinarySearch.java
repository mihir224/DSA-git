import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BinarySearch {
    //imp - binary search can be implemented to any search space that is monotonic in nature

    //nth root of a number
    //https://www.codingninjas.com/codestudio/problems/1062679?topList=striver-sde-sheet-problems&utm_source=striver&utm_medium=website&leftPanelTab=0
    // although bs is used to search an element in an array, we can also use it in functions which are monotonic (ie they
    // return increasing or decreasing values). Now we know for sure that the nth root of an integer will never be greater than it.
    // Thus, we apply binary search from 1 till the given integer such that if the current mid when multiplied n times gives a value>M
    // ie the given integer, we reduce the search space from 1 till mid. Otherwise, if the multiplied value is less than M, we reduce
    // search space from mid till end. Now since we have to return the ans close to 6 decimal places, we stop the search when start
    // and end become nearly equal ie their difference is close to 6 decimal places (10^-6) since we are doing binary search
    // on elements between 1 to M considering elements upto say, 6 decimal places, so from 1 to 2, M can vary from 1.000001
    // to 2.999999 and similarly we can think of all elements till M), and thus the complexity becomes O(logbase2(M*(10)^k))
    // where k is the number of decimal places upto which we have to find the ans and in each iteration we are multiplying
    // mid N times itself and thus the overall time complexity becomes - O(N*logbase2(M*(10)^k)), sc is O(1)

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

    //this problem has been updated on coding ninjas and now we just have to return the ans as integer
    //updated code:

    //O(N*logbase2(M)) tc, O(1) sc
    public static int NthRootM(int n, int m) {
        int start=1;
        int end=m;
        while(start<=end){
            int mid=start+(end-start)/2;
            int temp = (int)Math.pow(mid, n);
            if(temp==m){
                return mid;
            }
            else if(temp>m){
                end=mid-1;
            }
            else{
                start=mid+1;
            }

        }
        return -1;
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
    //we simply flatten the given matrix, sort it and return the middle element since it is given that m*n will always be
    // odd and thus median will always be middle
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
        return flatArray[(n*m)/2]; //since it is given that n*m will always be odd
    }

    //optimal - using nested binary search
    // O((logbase2(2^32) ie 32-for applying bs in a search space of 1e9 where 1e9 equates to 2^32)*(N-to find less than mid for
    // each row)*logbase2(M) - applying bs inside each row of size m to find the num of elements less than mid in that row)
    //approach - We know that for a number to be a median in a search space, that number should have all numbers less than
    // equal to it on the left equal to all the numbers greater than or equal to it on the right. So, we apply a sort of nested bs
    // where the outer one is to search for the median and the inner one is to search for all numbers less than the current
    // mid in the outer bs in each row. In the outer binary search we know that out median can be any integer in the int range,
    // say 1e9 for this question(we can also take int max, but it is not safe to be on the edge thus we take a smaller number).
    // Then we apply bs in this search space such that for each mid, we count the number of elements that are less than or equal
    // to the mid including mid in each row. Then, if the count is greater than half of the total elements ie n*m/2, we can observe
    // that current mid is not exactly at the middle because the number of elements less than or equal to it on the left are not
    // equal to the number of elements>= to it on the right. Thus, we reduce our search space by end=mid-1. Else if the count<=total
    // elements ie n*m ie number of elements less than or =equal to the mid INCLUDING MID are less than or equal to half of total
    // elements then we reduce our search space so that start=mid+1. This is because ideally we want all the elements on the left
    // as less than or equal than the mid (EXCLUDING MID) equal to total elements greater than or equal to mid EXCLUDING MID on
    // the right. When start will cross end, the start will be at the ideal position of median. Now to count the number of elements
    // less than current mid for each row, we apply another bs in each row. The logic is that the number of elements less than or
    // equal to the current element in a sorted array can be obtained by finding the index of the number just greater than the current
    // element. So for the current row, we find the mid and check if it is greater than main mid. If it isn't then it means we have
    // to search in the right and thus start=md+1 otherwise we search in left. At the end when start crosses end, it will point
    // to the immediate index where arr[mid]>main_mid. Basically we try to move in the optimal direction until we find the
    // most optimal ans (breakpoint) ie we move left if arr[mid]>main mid otherwise we move left
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
                count+=findLessThanMid(matrix.get(i),mid); //we basically try to saturate this count such that it is equal
                // both on the left and right of the current element and the optimal value for which it is will be our ans
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

    //find element that appears only once in a sorted array
    //https://leetcode.com/problems/single-element-in-a-sorted-array/

    //brute - create a frequency map for each element and return the key with freq<2
    //O(logN + N)tc, O(N) sc

    //better - perform xor of all the numbers in the array. since the xor of two similar numbers is 0 and the xor of a number
    // with 0 is the number itself, at the end after xoring all the elements, the ans would be the unique element
    //O(N)tc, O(1)sc

    //optimal - binary search
    //we simply need to find the break point at which is the point where the left half ends because just after that we have our unique element. We can observe a pattern that all elements
    // on the left of the unique element are such that 1st instance of an element is at even index and the other is at an
    // odd index whereas in the second half, 1st instance is at an odd index and other instance is at even index. Thus,
    // for each mid we check if it is equal to its other instance. We do this for both odd and even cases by using the
    // xor trick where simply xor-ing the mid with 1 will return the index before it if mid is odd and it will return
    // the index after it if mid is even. If the instances are equal ie we're in left half, we simply move the search space using low=mid+1
    // otherwise ie the case when the instances are not equal, ie we are in the right half, we reduce hi to mid-1 and
    // when low crosses high, then that would be our breakpoint, meaning we've found our single element.
    // The reason we took high at n-2 is that there might be a possibility that the unique element lies at the last element.
    // In that case, since we took high at n-2, it will never move and low will automatically reach past high in further
    // iterations, and at the end when it crosses high, it'll stand at the last index at which we have our unique element

    //O(logbase2N)tc, O(1) sc

    public int singleNonDuplicate(int[] nums) {
        int start=0;
        int end=nums.length-2;
        while(start<=end){
            int mid=start+(end-start)/2;
            if(nums[mid]==(nums[mid^1])){ //ie we're in left half
                start=mid+1;
            }
            else{
                end=mid-1;
            }
        }
        return nums[start];
    }

    public int search(int[] nums, int target) {
        int pivot=getPivot(nums);
        if(pivot==-1){ //array wasn't rotated, applying bs in the whole array
            return binarySearch(0,nums.length-1,target,nums);
        }
        if(target==nums[pivot]){ //pivot is the target
            return pivot;
        }
        else if(target>=nums[0]){ //target>start, but is not equal to pivot thus it lies b/w start and pivot
            return binarySearch(0,pivot,target,nums);
        }
        return binarySearch(pivot+1,nums.length-1,target,nums); //target<start, thus it lies b/w pivot+1 and end
    }
    public int getPivot(int[] nums){
        int start=0;
        int end=nums.length-1;
        while(start<=end){
            int mid=start+(end-start)/2;
            //4 cases
            if(mid<end&&nums[mid]>nums[mid+1]){ //mid is the pivot, checking mid<end because end is the upperbound so to
                // make sure that mid is in the valid range
                return mid;
            }
            if(mid>start&&nums[mid-1]>nums[mid]){ //mid-1 is the pivot, checking mid>start because start is the lowerbound
                // so to make sure that mid is in the valid range
                return mid-1;
            }
            if(nums[start]>=nums[mid]){ //pivot lies in the left
                end=mid-1;
            }
            else{
                start=mid+1;  //pivot lies in the right
            }
        }
        return -1; //nothing was returned, ie pivot not found ==> array is not rotated
    }

    public int binarySearch(int start, int end, int target, int[] nums){
        while(start<=end){
            int mid=start+(end-start)/2;
            if(nums[mid]==target){
                return mid;
            }
            if(nums[mid]<target){
                start=mid+1;
            }
            else{
                end=mid-1;
            }
        }
        return -1;
    }
    //median of two sorted arrays
    //https://leetcode.com/problems/median-of-two-sorted-arrays

    //brute force(O(N1+N2) both time and space)
    //use the merge logic of merge sort to merge the two sorted arrays and then return the avg of the two middle
    // elements if the merged array length is even, otherwise return the middle element

    //better - we can reduce the space complexity of the above approach to O(1) by maintaining a counter which tracks
    // our middle elements(in case of total even length) and a single middle element(in case of total odd length) and once
    // we've reached them we can perform our operations on them to find the median

    //optimal (O(logbase2(min(N1,N2)))) - using bs
    //approach - instead of iterating over each element in both the arrays and then merging them, we perform the merging
    // in such a way that we try to split the imaginary merged array into two halves such that elements on the left side
    // are always smaller than the elements on the right side. For eg consider two arrays of sizes 5 and 6 and thus after
    // splitting the imaginary merged array from the middle into two, for the left half, if we take 3 elements from array 1
    // then we have to take 2 elements from array 2 to fill the left half. Similarly, the remaining 2 and 4 elements from 1st
    // and 2nd arrays go into the right half. Now we design these left and right halves in such a way that everything on the
    // left is smaller than everything on the right (so that the imaginary merged array is also sorted). Now since both the
    // two arrays are already sorted, we know for a fact that whatever we picked from the 1st array for left half will be
    // smaller than whatever was remaining from the array1 that we picked for right half. Similar thing for the array2.
    // Now we just have to check whether the elements from array 1 in the left half are smaller than the elements from array2
    // in the right half and the elements from array2 in the left half are smaller than elements from array 1 in the right half.
    // If they are, then we'd know for sure that everything on the left is smaller than everything on the right and that we'd
    // have formed our imaginary merged array. Now to check whether diagonal elements from the left side are less than the
    // diagonal elements on the right side, we take 4 pointers - left1, left2, right1 and right2. These pointers point to
    // the last element on the left side from array1, the last element on the left side from array2, the first element from
    // array1 on the right side and so on. We already know that l1<=r1 and l2<=r2. We just have to check whether l1<=r2 && l2<=r1.
    // Now to assign values to these pointers, we try to apply bs in the 1st array by taking start at 0 as we can take a min of 0
    // elements from the array 1 for left half and end at n as we can take a max of all the elements from array1 for the left half.
    // Then we split or cut array1 from wherever mid is such that everything till mid is what goes in the left half and everything
    // on right is what goes in the right half. Similarly, we cut or split the second array such that the remaining elements in the
    // left and right halves can be filled. for eg if 2 elements have to be added in the left half, we make the cut after two
    // elements in the 2nd array. This way, for the first array mid-1 ie cut1-1 stores left1, and cut stored right 1.
    // Similarly, for second array, cut2-1 stores left1 and cut2 stores right1. Now we simply check l1,l2,r1,r2 for the
    // imaginary merged condition ie if everything on left is <= everything on the right. If it is, we return the median
    // such that, if the imaginary merged array has even length, we return the avg of max(l1,l2) and min (r1,r2) (ie the
    // two middle elements of the imaginary merged array) and if the ima length is odd, we return max(l1,l2) ie the middle element.
    // If the left<right condition is not satisfied, we apply bs such that if l1>r2 ie we've taken too many elements from array1
    // in left half, we make end=cut1-1 else if l2>r1 then it means we've taken smaller elements in right half from array1 and,
    // thus we make start=cut1+1

    //to reduce complexity we apply bs on smaller array and there also might be a possibility that cut2 will get out of
    // bounds for the smaller array thus we always make sure that bs is applied on the smaller array

    //dry run!!
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
                end=cut1-1; //decreasing l1 by taking smaller elements
            }
            else{
                start=cut1+1; //increasing r1 by taking larger elements
            }
        }
        return 0.0;
    }

    //kth element of two sorted arrays
    //https://practice.geeksforgeeks.org/problems/k-th-element-of-two-sorted-array1317/1

    //optimal O(logbase2(min(n1,n2))) - using bs (above approach)
    //we modify the above approach such that we try to add k elements of the imaginary merged array in the left half so
    // that we can return the kth element as max(l1,l2). So for the second array, we always make the cut from k-cut1 the position
    // to fill the remaining k-cut1 elements in the left half. We also have to take care of some edge cases (like when k>n2
    // ie we have to pick at least k-n2 elements from array1 so low will be k-n2 in this case or when k<n1 ie max we can
    // pick only k elements from array 1 so high will be k in this case)

    public long kthElement( int nums1[], int nums2[], int n1, int n2, int k) {
        if(n1>n2){
            return kthElement(nums2,nums1,n2,n1,k);
        }
        int start=Math.max(0,k-n2); //in case k>n2  ie we have to pick at least k-n2 elements from array 1
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

    //allocate minimum number of pages
    //https://www.interviewbit.com/problems/allocate-books/

    //optimal - bs - O(nlogn)time and O(1)space
    //approach - consider the case when there are 4 books each having the same number of pages say, 1 and there are 4
    // students. Thus, we can assign only one book to each student and thus the min number of max pages that can be
    // allowed to each student is 1 and therefore the lowest possible ans is the min of the total number of pages.
    // Thus, we take start as min of all pages. Similarly consider the case where there's only one student, and we have
    // 4 books. Thus, we'd have to allocate all 4 books to that one student and thus the ans in this case would be the
    // sum of all pages. Thus, the max possible ans will be sum of all pages and therefore end is taken as sum of all pages.
    // Now we apply bs by finding mid such that, we check if it is possible to distribute the books with min of max pages
    // as mid to all students. This is done by checking if current value of mid can act as a barrier of pages such that
    // no student gets more than mid pages and each student gets some pages. If it is possible to do this then it means
    // that we have one possible ans of mid, and we see if we can further reduce mid to get a better ans, and we do this
    // by assigning the current mid as our ans and reducing the search space such that high=mid-1. Now, if it is not
    // possible to get a valid ans with current mid - like the case when current mid as a barrier is too low, and we are
    // unable to allocate all pages among all students ie some pages were remaining, we reduce the search space such that
    // start =mid+1. By doing this, we get to a point where start crosses end and the ans stores the optimal ans. Now to
    // implement the isPossible() function we simply check if we can add more pages to the current allocated student, if we
    // can we do otherwise we increase the allocated student and start assigning pages to him in a similar manner. If at
    // some point the allocated students become more than the total number of students or if a book has more pages than
    // the barrier, we return false else we return true
    public int books(ArrayList<Integer> A, int B) {
        if(B>A.size()){
            return -1;
        }
        int min=Integer.MAX_VALUE;
        int total=0;
        for(int i:A){
            min=Math.min(min,i);
            total+=i;
        }
        int start=min;
        int end=total;
        int ans=-1;
        while(start<=end){
            int mid=(start+end)/2;
            if(isPossible(A,mid,B)){
                ans=mid;
                end=mid-1;
            }
            else{
                start=mid+1;
            }
        }
        return ans;
    }
    public boolean isPossible(ArrayList<Integer> arr,int barrier,int students){
        int allocatedStudents=1;
        int pages=0;
        for(int i=0;i<arr.size();i++){
            if(arr.get(i)>barrier){
                return false;
            }
            if(pages+arr.get(i)>barrier){
                allocatedStudents++;
                pages=arr.get(i);
            }
            else
                pages+=arr.get(i);
        }
        if(allocatedStudents>students){
            return false;
        }
        return true;
    }

    //aggressive cows
    //https://www.spoj.com/problems/AGGRCOW/
    //optimal - bs O(NlogN)
    //approach  - we follow a similar approach as we did in the previous ques. First, we sort our coordinates array.
    // We see that the min possible ans would be 1 as no two cows can be at the same coordinate and thus start is 1 and
    // the max possible ans is the difference between the last and first coordinate and that will be our end. Now we find
    // the middle and check if it is possible to place all the cows with the mid as the max of min distance between them.
    // If it is possible, then that means we have a valid ans and, thus we put current mid, equal to our ans a try to
    // find a better ans by moving forward and reducing the search space such that start=mid+1. If it is not possible
    // to place the cows with the current mid ie the distance between them is too large, we reduce the search space such
    // that end=mid-1. Now to implement the canPlace() function we just place the first cow at first coordinate and then
    // try placing other cows at the remaining coordinates such that the distance between them is always >= current mid.
    // We keep a counter to count the number of cows that have been placed and once the counter equals the total number of
    // cows, we return true. Otherwise, we return false. At the end, we can either return end or ans as our ans both are fine.
    public static void main(String[] args) throws java.lang.Exception{
        Scanner sc=new Scanner(System.in);
        int t=sc.nextInt();
        for(int i=0;i<t;i++){
            int n=sc.nextInt();
            int numCows=sc.nextInt();
            int[] arr=new int[n];
            for(int j=0;j<n;j++){
                arr[j]=sc.nextInt();
            }
            Arrays.sort(arr);
            int start=1;
            int end=arr[n-1]-arr[0];
            int ans=-1;
            while(start<=end){
                int mid=(start+end)/2;
                if(canPlace(arr,mid,numCows)){
                    ans=mid;
                    start=mid+1;
                }
                else{
                    end=mid-1;
                }
            }
            System.out.println(end);
        }
        sc.close();
    }
    public static boolean canPlace(int[] coordinates, int minDist,int cows){
        int coORd=coordinates[0]; //placing a cow at 1st coordinate
        int count=1;
        for(int i=1;i<coordinates.length;i++){
            if(coordinates[i]-coORd>=minDist){//can place a cow at ith coordinate
                count++;
                coORd=coordinates[i];
            }
            if(count==cows){
                return true;
            }
        }
        return false;
    }
}

