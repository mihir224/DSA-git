//https://leetcode.com/problems/binary-search/submissions/
public class BinarySearch {
    public static void main(String[] args) {
        int[] arr={2,5,6,8,9,10,23,42,58}; //increasing sorted array
        System.out.println(binarySearch(arr, 10));

    }
    public static int binarySearch(int[] arr, int target){
        int start=0;
        int end=arr.length-1;
        while(start<=end){
            //int mid=(start+end)/2; //this formula would exceed the int range if the array size is large
            //thus this formula is written as
            int mid=start+(end-start)/2; //this formula doesn't exceed the int range
            if(target>arr[mid]){
                start=mid+1;
            }
            else if(target<arr[mid]){
                end=mid-1;
            }
            else {
                //case when target element is the middle element
                //ans
                return mid;
            }
        }
        return -1;
    }

}
