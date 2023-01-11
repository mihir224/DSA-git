//https://leetcode.com/problems/search-in-rotated-sorted-array/
public class RotatedBinarySearch { //In a rotated array, elements are shifted in right or left
    public static void main(String[] args) {
        int[] arr={5,6,7,8,1,2,3,4};
        System.out.println(search(arr,7));
    }
    static int search(int[] arr,int target){
            int pivot=findPivot(arr);
            if(pivot==-1){ //case when array isn't rotated
                return binarySearch(arr, target,0,arr.length-1);
            }
            if(arr[pivot]==target){
                return pivot;
            }
            if(target>=arr[0]){
                return binarySearch(arr, target, 0,pivot-1);
            }
            return binarySearch(arr, target, pivot+1, arr.length-1);
    }
    public static int binarySearch(int[] arr, int target,int start,int end){
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
    static int findPivot(int[] arr){
        int start=0;
        int end=arr.length-1;
        while(start<=end){
            //4 cases
            int mid=start+(end-start)/2;
            if(mid<end&&arr[mid]>arr[mid+1]){
                return mid;
            }
            if(mid>start&&arr[mid-1]>arr[mid]){
                return mid-1;
            }
            if(arr[mid]<=arr[start]){
                end=mid-1;
            }
            else{
                start=mid+1;
            }
        }
        return -1;
    }

}
