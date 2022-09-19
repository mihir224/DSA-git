public class OrderAgnosticBinarySearch {
    public static void main(String[] args) {
        int[] arr={37,28,18,10,8,3,-1,-6};
        System.out.println(orderAgnosticBinarySearch(arr, -1));
    }
    public static int orderAgnosticBinarySearch(int[] arr, int target){
            int start=0;
        int end=arr.length-1;
        //to check if the sorted array is ascending or descending
        boolean isAscending=arr[start]<arr[end];
        while(start<=end){
            //int mid=(start+end)/2; //this formula would exceed the int range if the array size is large
            //thus this formula is written as
            int mid=start+(end-start)/2; //this formula doesn't exceed the int range
            if(isAscending){
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
            else {
                if(target<arr[mid]){
                    start=mid+1;
                }
                else if(target>arr[mid]){
                    end=mid-1;
                }
                else {
                    //case when target element is the middle element
                    //ans
                    return mid;
                }
            }
        }
        return -1;
    }
}
