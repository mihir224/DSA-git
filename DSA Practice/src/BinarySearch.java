import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class BinarySearch{
    //imp - binary search can be implemented to any search space that is monotonic in nature

    //nth root of a number
    //https://www.codingninjas.com/codestudio/problems/1062679?topList=striver-sde-sheet-problems&utm_source=striver&utm_medium=website&leftPanelTab=0
    // although bs is used to search an element in an array, we mostly use it in functions which are monotonic (ie they
    // return increasing or decreasing values). Now we know for sure that the nth root of an integer will never be greater than it.
    // Thus, we apply binary search from 1 till the given integer such that if the current mid when multiplied n times gives a value>M
    // ie the given integer, we reduce the search space from 1 till mid. Otherwise, if the multiplied value is less than M, we reduce
    // search space from mid till end. Now since we have to return the ans close to 6 decimal places, we stop the search when start
    // and end become nearly equal ie their difference is close to 6 decimal places (10^-6) so that we get an ans upto or
    // greater than 6 decimal places since we are doing binary search on elements between 1 to M considering elements upto
    // say, 6 decimal places, so from 1 to 2, M can vary from 1.000000 to 2.999999 and similarly we can think of all
    // elements till M), and thus the complexity becomes O(logbase2(M*(10)^k)) where k is the number of decimal places
    // upto which we have to find the ans and in each iteration we are multiplying mid N times itself and thus the overall
    // time complexity becomes - O(N*logbase2(M*(10)^k)), sc is O(1)

    //basically we're performing binary search until start and end become nearly equal (equal upto k decimal places). now
    // if we have to find the ans upto 5 decimal places, we will perform bs until difference between start and end becomes
    // almost 1e6 because that would make sure that upto 5 decimal places their values are same

    public static double NthRootofM(int n, int m) {
        double eps=1e-6;
        double start=1;
        double end=m;
        while((end-start)>eps){
            double mid=start+(end-start)/2;
            double temp=1.0;
            for(int i=0;i<n;i++){
                temp*=mid;
            }
            if(mid>=m){
                end=mid; //here we take mid instead of mid-1 or mid+1 because we want to calculate the ans in decimals
                // and adding or subtracting a 1 to that would result in a wrong ans
            }
            else{
                start=mid;
            }
        }
        return (start+(end-start))/2;
    }

    //this problem has been updated on coding ninjas & now we just have to return the ans as integer
    //updated code:
    //O(N*logbase2(M)) tc, O(1) sc

    public static int NthRoot(int n, int m) {
        int start=1;
        int end=m;
        while(start<=end){
            int mid=start+(end-start)/2;
            int temp = (int)Math.pow(mid, n); //here we didn't run a loop till n to calculate temp*=mid because in that
            // case temp might increase the int range & so we'd have to take care of that
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

    //matrix median
    //https://www.interviewbit.com/problems/matrix-median

    //it is given that n*m will always be odd

    //brute - flatten the matrix, then sort and return the middle element - O(N*M + (N*M)log(N*M)) tc, O(N*M) sc

    //optimal - using nested binary search

    //consider the array - [1,2,3,3,6,6,6,9,9]
    //approach - We know that for a number to be a median in a search space (considering number of elements are odd), it should have all numbers less than
    // equal to it on the left equal to all the numbers greater than or equal to it on the right. So, we apply a sort of nested bs
    // where the outer one is to search for the median and the inner one is to search for all numbers less than the current
    // mid in the outer bs in each row. In the outer binary search we know that our median can be any integer in the int range,
    // say 1e9 for this question(we can also take int max, but it is not safe to be on the edge thus we take a smaller number).
    // Then we apply bs in this search space such that for each mid, we count the number of elements that are less than or equal
    // to the mid including mid in each row. Then, if the count is greater than half of the total elements ie n*m/2, we can observe
    // that current mid is not exactly at the middle because the number of elements less than or equal to it on the left are not
    // equal to the number of elements>= to it on the right. Thus, we reduce our search space by end=mid-1. Else if the count<=total
    // elements ie n*m ie number of elements less than or =equal to the mid INCLUDING MID are less than or equal to half of total
    // elements then we reduce our search space so that start=mid+1. This is because ideally we want all the elements on the left
    // as less than or equal than the mid (EXCLUDING MID) equal to total elements greater than or equal to mid EXCLUDING MID on
    // the right. When start will cross end, the start will be at the ideal position of median. This is because if we consider
    // the above example, when mid is anything less than 6, the count we're getting is 4. However when mid is 6, then even
    // though it is the median, all of its occurrences are being taken into consideration in the count. so to find the first
    // occurrence of this 6, we have to go to the immediate next index which happens when start goes beyond end. Now to count the number of elements
    // less than current mid for each row, we apply another bs in each row. The logic is that the number of elements less than or
    // equal to the current element in a sorted array can be obtained by finding the index of the number just greater than the current
    // element. So for the current row, we find the mid and check if it is greater than main mid. If it isn't then it means we have
    // to search in the right and thus start=mid+1 otherwise we search in left because we want to find the immediate value
    // that is greater than the current mid. At the end when start crosses end, it will point to the immediate index
    // where arr[mid]>main_mid. Basically we try to move in the optimal direction until we find the most optimal ans
    // (breakpoint) ie we move right if arr[mid]>main mid otherwise we move left

    // O((logbase2(2^32) ie 32-for applying bs in a search space of 1e9 where 1e9 equates to 2^32)*(N to find less than mid for
    // each row)*logbase2(M) - applying bs inside each row of size m to find the num of elements less than mid in that row)


    //IMP!!!! - VERY IMP CONCEPT AND INTUITION FOR BINARY SEARCH
    //intuition - shrinking the search space in order to reach the break point and find the most optimal value (which in
    // this particular problem is supposed to be the first occurrence of the median)

    public int findMedian(ArrayList<ArrayList<Integer>> matrix)
    {
        int n=matrix.size();
        int m=matrix.get(0).size();
        int start=1;
        int end=(int)1e9;
        while(start<=end){
            int mid=(start+end)/2;
            int count=0;
            for(int i=0;i<n;i++){
                count+=findLessThanMid(matrix.get(i),mid); //count of elements less than equal to mid including mid. We
                // basically try to optimize this count such that it is equal both on the left and right of the current
                // element and the optimal value for which it is will be our ans
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

    //using this function we're trying to search for the element that is immediately greater than the current mid. whatever
    // index this el would have will give us the no. of elements <= mid
    public int findLessThanMid(ArrayList<Integer> list,int mainMid){
        int start=0;
        int end=list.size()-1;
        while(start<=end){
            int mid=(start+end)/2;
            if(list.get(mid)<=mainMid){
                start=mid+1;
            }
            else{
                end=mid-1;
            }
        }
        return start;
    }

    //find element that appears only once in a sorted array
    //https://leetcode.com/problems/single-element-in-a-sorted-array/

    //brute - create a frequency map for each element and return the key with freq<2
    //O(NlogN)tc, O(N) sc

    //better - perform xor of all the numbers in the array. since the xor of two similar numbers is 0 and the xor of a number
    // with 0 is the number itself, at the end after xoring all the elements, the ans would be the unique element
    //O(N)tc, O(1)sc

    //optimal - binary search
    //we simply need to find the break point ie the point where the left half ends because just after that we have our
    // unique element. We can observe a pattern that all elements on the left of the unique element are such that 1st
    // instance of an element is at even index and the other is at an odd index whereas in the second half, 1st instance
    // is at an odd index and other instance is at even index. Thus, for each mid we check if it is equal to its other
    // instance. We do this for both odd and even cases by using the xor trick where simply xor-ing the mid with 1 will
    // return the index before it if mid is odd and, it will return the index after it if mid is even. If the instances are
    // equal ie we're in left half, we simply move the search space using low=mid+1 otherwise ie the case when the instances
    // are not equal, ie we are in the right half, we reduce hi to mid-1 and when low crosses high, then that would be our
    // breakpoint, meaning we've found our single element. The reason we took high at n-2 is that if unique is last, we'll
    // keep on moving start until it meets end. Now we know for sure that there will always be odd number of elements, and
    // thus last index would be even and mid^1 would give out of bounds. This also covers the case when there's only 1 el
    // in the arr as in that case, bs would never be executed

    //SAME INTUITION AS ABOVE - Trying to reduce the search space in order to reach the breakpoint

    //O(logbase2N)tc, O(1) sc

    public int singleNonDuplicate(int[] nums) {
        int start=0;
        int end=nums.length-2;
        while(start<=end){
            int mid=start+(end-start)/2;
            if(nums[mid]==(nums[mid^1])){ //ie we're in left half
                start=mid+1;
            }
            else{ //ie we're in the right half
                end=mid-1;
            }
        }
        return nums[start];
    }

    //search in a rotated sorted array
    //https://leetcode.com/problems/search-in-rotated-sorted-array/

    public int search(int[] nums, int target) {
        int pivot=getPivot(nums);
        if(pivot==-1){ //array wasn't rotated, applying bs in the whole array
            return binarySearch(0,nums.length-1,target,nums);
        }
        if(target==nums[pivot]){ //pivot is the target
            return pivot;
        }
        else if(target>=nums[0]){ //target>start, thus it lies in the left half
            return binarySearch(0,pivot,target,nums);
        }
        return binarySearch(pivot+1,nums.length-1,target,nums); //target<start, thus it lies in the right half
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
                //whatever el is after pivot, would obv be smaller than the start as array has been rotated
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

    //find the median of two sorted arrays

    //brute - merge the 2 sorted arrays and then find the median through the conventional formula. Gives TLE on LC
    //O(M+N) wc tc, O(m+n)sc to store the merged array
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m=nums1.length;
        int n=nums2.length;
        int k=0;
        int[] mergedArray=new int[m+n];
        int i=0;
        int j=0;
        while(i<m&&j<n){
            if(nums1[i]<nums2[j]){
                mergedArray[k]=nums1[i];
                i++;
            }
            else if(nums1[i]>nums2[j]){
                mergedArray[k]=nums2[j];
                j++;
            }
            k++;
        }
        while(i<m){
            mergedArray[k]=nums1[i];
            i++;
            k++;
        }
        while(j<n){
            mergedArray[k]=nums2[j];
            j++;
            k++;
        }
        double ans=0;
        int p=m+n;
        if(p%2==0){
            ans=(mergedArray[(p/2)-1]+mergedArray[(p/2)])/2.0;
        }
        else{
            ans=mergedArray[p/2];
        }
        return ans;
    }

    //better - we can reduce the space complexity of the above approach to O(1) by maintaining a counter which tracks
    // our middle elements(in case of total even length) and a single middle element(in case of total odd length) as we
    // traverse through both the arrays (while merging them) and once we've reached them we can perform our operations
    // on them to find the median

    //optimal - using bs

    //approach - instead of iterating over each element in both the arrays and then merging them, we perform the merging
    // in such a way that we try to split the imaginary merged array into two halves such that elements on the left side
    // are always smaller than the elements on the right side. number of elements in the left half can be calculated through
    // this formula - ((n+m+1)/2). This ensures that for odd length, left half has more capacity so that it can accommodate
    // the middle element which would turn out to be its last element then we can simply return median as max(l1,l2). For eg consider two arrays of sizes 5 and 6 and thus after
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
    // Now to assign values to these pointers, we try to apply bs in the 1st array so as to find the optimal partition point by taking start as 0 as we can take a min of 0
    // elements from the array 1 for left half and end as n as we can take a max of all the elements from array1 for the left half.
    // Then we split or cut array1 from wherever mid is such that everything till mid is what goes in the left half and everything
    // on right is what goes in the right half. Similarly, we cut or split the second array such that the remaining elements in the
    // left and right halves can be filled. for eg if 2 elements have to be added in the left half, we make the cut after two
    // elements in the 2nd array. This way, for the first array mid-1 ie cut1-1 stores left1 (because cut1 stores the number
    // of elements in the left half and thus cut1-1 will give the index of the last element of left half), and cut stored right 1.
    // Similarly, for second array, cut2-1 stores left1 and cut2 stores right1. Now we simply check l1,l2,r1,r2 for the
    // imaginary merged condition ie if everything on left is <= everything on the right. If it is, we return the median
    // such that, if the imaginary merged array has even length, we return the avg of max(l1,l2) and min (r1,r2) (ie the
    // two middle elements of the imaginary merged array) and if the img length is odd, we return max(l1,l2) ie the middle element.
    // If the left<right condition is not satisfied, we apply bs such that if l1>r2 ie we've taken too many elements from array1
    // in left half, we make end=cut1-1 else if l2>r1 then it means we have partitioned array 1 way to early, due to which
    // smaller elements also went to r1. Thus, we make the partition in left half a bit further so that r1 starts from
    // larger elements and, thus we make start=cut1+1

    //O(logbase2(min(m,n))tc - we always apply binary search in the smaller array because there might be a case when arr1 has only
    //1 element and arr2 has 0 elements, due to which arr2[cut2] would give out of bounds error. Moreover, by applying
    // bs on smaller array, complexity is also reduced, O(1)sc

    public double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        int n=nums1.length;
        int m=nums2.length;
        //imp!!!!
        if(n>m){  //we always apply binary search in the smaller array because otherwise what could happen is that if el
            // in arr1 are more than el in arr2, & if we take zero el from arr1, we'd have to take all el from arr2
            // in the left half, this can lead to an error because in this case leftLen would be greater than size of arr2.
            //for eg, if there are 6 elements in arr1 and 4 elements in arr2, then leftLen would be 5. Now if we consider not taking
            // any el from arr1, then we'd have to take 5 from arr2 and thus cuts2 would be 5-0=5. this way arr2[cuts2-1]=arr2[4]
            // would give out of bounds error. moreover, the same thing would happen if we try to take all elements from arr1
            // in the left half because in this case leftlen-cuts1 would give a negative val.
            return findMedianSortedArrays1(nums2,nums1);
        }
        int leftLen=(n+m+1)/2;
        int start=0;
        int end=n; //at max we can take n elements in the left half
        while(start<=end){
            int cut1=start+(end-start)/2; //partition pt of arr1, ie the number of elements we have to take from the 1st
            // array in left half
            int cut2=leftLen-cut1; //partition pt of arr2, stores the number of elements in the left half from arr2
            int l1=cut1==0?Integer.MIN_VALUE:nums1[cut1-1]; //checking in case we took no elements from arr1, thus putting l1 as int min
            int l2=cut2==0?Integer.MIN_VALUE:nums2[cut2-1];
            int r1=cut1==n?Integer.MAX_VALUE:nums1[cut1]; //in case we took all elements from arr1 in the left half, leaving 0 for r1
            int r2=cut2==m?Integer.MAX_VALUE:nums2[cut2];
            if(l1<=r2&&l2<=r1){ // ie all elements in the left half of imaginary merged array < right half
                if((n+m)%2==0){ //merged array of even length
                    return (Math.max(l1,l2)+Math.min(r1,r2))/2.0;
                }
                else{ //merged array of odd length;
                    return Math.max(l1,l2);
                }
            }
            else if(l1>r2){ //ie we took more elements in l1, thus reducing them by partitioning earlier
                end=cut1-1;
            }
            else{ //ie l2>r1 ie r1 starts with smaller elements and thus to avoid this, we partition later in arr1 so that
                // r1 starts from larger elements
                start=cut1+1;
            }
        }
        return -1.00; //not found;
    }


    //kth element of two sorted arrays
    //https://practice.geeksforgeeks.org/problems/k-th-element-of-two-sorted-array1317/1

    //brute - merge the 2 sorted arrays and then return the el at the (k-1)th index. Can be done through the count approach discussed
    // in prev ques's better approach
    //O(n+m) time, O(1) space

    //optimal - using bs (above approach)
    //we modify the above approach such that we try to add k elements of the imaginary merged array in the left half so
    // that we can return the kth element as max(l1,l2). So for the second array, we always make the cut from k-cut1 the position
    // to fill the remaining k-cut1 elements in the left half. We also have to take care of some edge cases (like when k>n2
    // ie we have to pick at least k-n2 elements from array1 so low will be k-n2 in this case or when k<n1 ie max we can
    // pick only k elements from array 1 so high will be k in this case)
    //O(logbase2(min(n1,n2)))
    public long kthElement( int arr1[], int arr2[], int n, int m, int k) {
        if(n>m){
            return kthElement(arr2,arr1,m,n,k);
        }
        int start=k>m?k-m:0; //in case k>n2  ie we have to pick at least k-n2 elements from array 1. we don't have to check
        // this in prev ques because in that problem, k is leftLen which is basically calc through (n+m+1)/2. Now since we
        // make sure m is always greater, the val that we calc for leftLen would always be <= m

        int end=k<n?k:n; //in case k<n1 ie at max we can pick only k elements from array1. again, we didn't check this in
        // prev problem because we knew it with certainty that leftLen would always be >= n
        while(start<=end){
            int cut1=start+(end-start)/2;
            int cut2=k-cut1;  //since we have to find the kth element, we take first k elements in the left half
            int l1=cut1==0?Integer.MIN_VALUE:arr1[cut1-1];
            int l2=cut2==0?Integer.MIN_VALUE:arr2[cut2-1];
            int r1=cut1==n?Integer.MAX_VALUE:arr1[cut1];
            int r2=cut2==m?Integer.MAX_VALUE:arr2[cut2];
            if(l1<=r2&&l2<=r1){
                return Math.max(l1,l2);
            }
            else if(l1>r2){
                end=cut1-1;
            }
            else{
                start=cut1+1;
            }
        }
        return -1;
    }

    //allocate books
    //https://www.interviewbit.com/problems/allocate-books/
    // we wish to return the min number of max pages that can be allocated to a student

    //brute - find all possible combinations, take max num books that can be allocated to each student and return the min
    // of those

    //complexity - O(exponential)

    //better - we know the min and max possible val of the ans (check in the optimal approach). so we can perform a linear
    // search in this search space and look for our min value for all possible values. for every value between min and max
    // possible ans, we can check if it is possible to allocate all books with the that val as a barrier and out of all
    // possible values, we'll return the min one. Can take O(N2) time in worst case since we're checking each el from min
    // possible val to max possible val and for each no. we're checking if it is possible to take the current val as a
    // barrier

    //optimal - bs
    // approach - Suppose there are 4 books and there are 4 students. So we have only one option that we can assign only
    // one book to each student. in this case this is the only way we can allocate the books. thus the min of max would
    // be the max of all the elements in the array. Similarly consider the case where there's only one student, and we have
    // 4 books. Thus, we'd have to allocate all 4 books to that one student and thus the ans in this case would be the
    // sum of all pages. Thus, the max possible ans will be sum of all pages and therefore end is taken as sum of all pages.
    // Now we apply bs by finding mid such that, we check if it is possible to distribute the books max pages allowed
    // as mid to all students. mid here signifies the maximum amt of pages that can be distributed to a student and through bs,
    // we try to optimize the value of mid such that it is minimum. This is done by checking if current value of mid can act as a barrier of pages such that
    // no student gets more than mid pages and each student gets some pages. If it is possible to do this then it means
    // that we have one possible ans of mid, and we see if we can further reduce mid to get a better ans, and we do this
    // by assigning the current mid as our ans and reducing the search space such that high=mid-1. Now, if it is not
    // possible to get a valid ans with current mid - like the case when current mid as a barrier is too low, and we are
    // unable to allocate all pages among all students ie some pages were remaining, we reduce the search space such that
    // start =mid+1. By doing this, we get to a point where start crosses end and the ans stores the most optimal ans. Now to
    // implement the isPossible() function we simply check if we can add more pages to the current allocated student, if we
    // can we do otherwise we increase the allocated student and start assigning pages to him in a similar manner. If at
    // some point the allocated students become more than the total number of students or if a book has more pages than
    // the barrier, we return false else we return true

    //O(NlogN)time and O(1)space

    public class Solution {
        public int books(ArrayList<Integer> A, int B) {
            if(A.size()<B){ //number of books less than number of students, thus can't allocate a min of one book to every student
                return -1;
            }
            int max=Integer.MIN_VALUE;
            int sum=0;
            for(int i:A){
                if(i>max){
                    max=i;
                }
                sum+=i;
            }
            int s=max;
            int e=sum;
            while(s<=e){
                int mid=s+(e-s)/2;
                if(isPossible(mid,A,B)){ //find possible candidate for max num pages that can be allocated to a student
                    e=mid-1; //moving left to find a better value, ie reducing the search space to get to the break point
                }else{
                    s=mid+1;
                }
            }
            return s;
        }
        public boolean isPossible(int barrier, ArrayList<Integer> list, int n){
            int allocatedStudents=1;
            int pages=0; //pages allocated to current student
            for(int i:list){
                if(i>barrier){ //found a book with pages>maxPages allowed, thus we cannot allocate this book to any student
                    return false;
                }
                if(pages+i>barrier)
                {
                    allocatedStudents++;
                    pages=i;
                }
                else{
                    pages+=i;
                }
            }
            if(allocatedStudents>n){ //ie we were unable to allocate all the books among n students with the given barrier ie we needed more students as there were some books left meaning the barrier was too low
                return false;
            }
            return true;
        }
    }



    //aggressive cows
    //https://www.spoj.com/problems/AGGRCOW/

    //we basically have to place C cows among N stalls such that the min distance between two cows is as large as possible

    //approach  - we follow a similar approach as we did in the previous ques. First, we sort our coordinates array.
    // sorting the coordinate array is necessary so that we can obtain the correct value for end and apply binary search accordingly.
    // We see that the min possible ans would be 1 as no two cows can be at the same coordinate and thus start is 1 and
    // the max possible ans is the difference between the last and first coordinate and that will be our end. Now we find
    // the middle and check if it is possible to place all the cows with the mid as the min distance between any 2 cows.
    // mid here signifies the min distance at which any 2 cows can be placed. we wish to maximize this value of mid.
    // If it is possible, then that means we have a valid ans and, thus we put current mid, equal to our ans and try to
    // find a better ans by moving forward and reducing the search space such that start=mid+1. If it is not possible
    // to place the cows with the current mid ie the min distance between any two cows is too large, we reduce the search space such
    // that end=mid-1. Now to implement the canPlace() function we just place the first cow at first coordinate and then
    // try placing other cows at the remaining coordinates such that the distance between them is always >= current mid.
    // We keep a counter to count the number of cows that have been placed and once the counter equals the total number of
    // cows, we return true. Otherwise, we return false. At the end, we can either return end or ans as our ans both are fine.

    //O(NlogN)tc - bs + sorting, O(1)sc if we don't consider coordinates array

    public static void main(String[] args) throws java.lang.Exception{
        Scanner sc=new Scanner(System.in);
        int t=sc.nextInt();
        for(int i=0;i<t;i++){
            int n=sc.nextInt();
            int c=sc.nextInt();
            int[] arr=new int[n]; //coordinate array
            for(int idx=0;idx<n;idx++){
                arr[idx]=sc.nextInt();
            }
            Arrays.sort(arr); //sorting the coordinate array is necessary so that we can obtain the correct value for end
            // and apply binary search accordingly
            int start=1;
            int end=arr[n-1]-arr[0];
            int ans=0;
            while(start<=end){
                int mid=start+(end-start)/2;
                if(canPlace(mid,arr,c)){ //checking if we can place cows at min distance of mid
                    ans=mid;
                    start=mid+1; //moving right to find optimal(max) val of mid
                }
                else {
                    end=mid-1; //mid is too large ie cows cannot be placed at min distance of mid, thus we move left,
                    // to decrease the value of mid
                }
            }
            System.out.println(ans);
        }
        sc.close();
    }
    public static boolean canPlace(int minDistance, int[] coordinates, int c){
        int coOrd=coordinates[0]; //we greedily place the 1st cow at 1st coordinate because that would be the most efficient
        // way to place the 1st cow so that other cows can be accommodated in the min distance as well
        int count=1; //to track the number of cows placed
        for(int i=0;i<coordinates.length;i++){
            if((coordinates[i]-coOrd)>=minDistance){ //can place a cow at coordinate i
                count++;
                coOrd=coordinates[i];
            }
            if(count==c){ //all cows could be placed at min distance of mid
                return true;
            }
        }
        return false;
    }

    //peak element
    //https://leetcode.com/problems/find-peak-element/

    //brute would be to linearly search for the peak
    public int findPeakElement(int[] nums) {
        int n=nums.length;
        if(n==1){
            return 0;
        }
        int start=0;
        int end=n-1;
        while(start<=end){
            int mid=start+(end-start)/2;
            if(mid>0&&mid<n-1){
                if(nums[mid]>nums[mid-1]&&nums[mid]>nums[mid+1]){
                    return mid;
                }
                else if(nums[mid-1]>nums[mid]){
                    end=mid-1;
                }
                else{
                    start=mid+1;
                }
            }
            else if(mid==0){
                if(nums[mid]>nums[mid+1]){
                    return mid;
                }
                else{
                    return mid+1;
                }
            }
            else if(mid==n-1){
                if(nums[mid]>nums[mid-1]){
                    return mid;
                }
                else{
                    return mid-1;
                }
            }
        }
        return -1;
    }


    //peak index in mountain array
    //https://leetcode.com/problems/peak-index-in-a-mountain-array
    public int peakIndexInMountainArray(int[] arr) {
        int n=arr.length;
        int start=0;
        int end=n-1;
        while(start<=end){
            int mid=start+(end-start)/2;
            if(mid>0&&mid<n-1){
                if(arr[mid]>arr[mid+1]&&arr[mid]>arr[mid-1]){
                    return mid;
                }
                else if(arr[mid]>arr[mid-1]){
                    start=mid+1;
                }
                else if(arr[mid]>arr[mid+1]){
                    end=mid-1;
                }
            }
            else if(mid==0){ //we don't check the edge elements because we know for a fact that in a mountain array, there
                // will be atleast one el that is present on the right and left of the peak. moreover we don't need to
                // explicitly check if arr[mid+1] is greater because it is confirmed that if the search space is reduced
                // so as to make mid 0, then it will mean that the search space has the first 2 elements and in that case
                // it would be confirmed that the second el is the peak.
                return mid+1;
            }
            //we don't check for mid=n-1 coz there mid will never reach there as it is given in the question that the arr
            // is mountain with certainty
        }
        return -1;
    }

    //find-first-and-last-position-of-element-in-sorted-array
    //https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array
    public int[] searchRange(int[] nums, int target) {
        int n=nums.length;
        int start=0;
        int end=n-1;
        int s=0;
        int e=0;
        while(start<=end){
            int mid=start+(end-start)/2;
            if(nums[mid]>=target){
                s=mid;
                end=mid-1;
            }
            else{
                start=mid+1;
            }
        }
        start=0;
        end=n-1;
        while(start<=end){
            int mid=start+(end-start)/2;
            if(nums[mid]<=target){
                e=mid;
                start=mid+1;
            }
            else{
                end=mid-1;
            }
        }
        if(n==0||(nums[s]!=target||nums[e]!=target)){
            return new int[]{-1,-1};
        }
        return new int[]{s,e};
    }

    //bs+sliding window

    //minimum-number-of-operations-to-make-array-continuous
    //https://leetcode.com/problems/minimum-number-of-operations-to-make-array-continuous
    public int minOperations(int[] nums) {
        int ans=Integer.MAX_VALUE;
        int duplicates=0;
        int n=nums.length;
        Arrays.sort(nums);
        List<Integer> list=new ArrayList<>();
        for(int i=0;i<n;i++){
            if(i>0&&nums[i]==nums[i-1]){
                continue;
            }
            list.add(nums[i]);
        }
        int max=list.get(list.size()-1);
        for(int i=0;i<list.size();i++){
            int j=i;
            int range=list.get(i)+(n-1);
            int start=i;
            int end=list.size()-1;
            while(start<=end){
                int mid=start+(end-start)/2;
                if(list.get(mid)<=range){
                    j=mid;
                    start=mid+1;
                }
                else{
                    end=mid-1;
                }
            }
            j++;
            ans=Math.min(ans,n-(j-i));
        }
        return ans;
    }

    //koko eating bananas
    //https://leetcode.com/problems/koko-eating-bananas
    //brute - applying linear search on the given range of bananas

    //optimal O(N*log(maxEl)) - binary search
    public int minEatingSpeed(int[] piles, int h) {
        int min=0;
        Arrays.sort(piles);
        int n=piles.length;
        int start=1;
        int end=piles[n-1];
        while(start<=end){
            int mid=start+(end-start)/2;
            if(canEat(mid,piles,h)){
                min=mid;
                end=mid-1;
            }
            else{
                start=mid+1;
            }
        }
        return min;
    }
    public boolean canEat(int b,int[] piles,int h){
        int time=0;
        for(int i:piles){
            time+=Math.ceil((double)i/(double)b);
        }
        return time<=h;
    }
    interface MountainArray {
     public int get(int index);
      public int length();
  }

    //search/find in mountain array
    //https://leetcode.com/problems/find-in-mountain-array/
    public int findInMountainArray(int target, MountainArray mountainArr) {
        int start=0;
        int n=mountainArr.length();
        int end=n-1;
        int min=-1;
        int peak=-1;
        while(start<=end){
            int mid=start+(end-start)/2;
            int midEl=mountainArr.get(mid);
            if(mid>0&&mid<n-1){
                if(midEl>mountainArr.get(mid+1)&&midEl>mountainArr.get(mid-1)){
                    peak=mid;
                    break;
                }
                else if(midEl>mountainArr.get(mid-1)){
                    start=mid+1;
                }
                else if(midEl>mountainArr.get(mid+1)){
                    end=mid-1;
                }
            }
            else if(mid==0){
                peak=mid+1;
                break;
            }
        }
        int peakEl=mountainArr.get(peak);
        if(peakEl==target){
            return peak;
        }
        int ans=binarySearch(0,peak-1,mountainArr,target,true);
        return ans==-1?binarySearch(peak+1,n-1,mountainArr,target,false):ans;
    }
    public int binarySearch(int start, int end, MountainArray mountainArr,int target,boolean flag){
        while(start<=end){
            int mid=start+(end-start)/2;
            int midEl=mountainArr.get(mid);
            if(midEl==target){
                return mid;
            }
            else if(midEl>target){
                if(flag){
                    end=mid-1;
                }
                else{
                    start=mid+1;
                }
            }
            else{
                if(flag){
                    start=mid+1;
                }
                else{
                    end=mid-1;
                }
            }
        }
        return -1;
    }


}
