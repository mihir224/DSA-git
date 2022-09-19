public class PeakElement {
 //https://leetcode.com/problems/find-peak-element/
    public static int peakElement(int[] arr, int n){
        int start=0;
        int end=n-1;
       while(start<=end) {
           int mid=start+(end-start)/2;
            if(n==1){
                return 0;
            }
           else if(mid>0&&mid<n-1){
               if((arr[mid]>arr[mid+1])&&(arr[mid]>arr[mid-1])){
               return mid;
               }
               else if(arr[mid]<arr[mid+1]){
                   start=mid+1;
               }
               else{
                   end=mid-1;
               }
           }
           else if((mid==0)&&(n!=0)) {  //case when middle element is the first element
               if(arr[0]>arr[1]){
                   return 0;
               }
               else {
                   return 1;
               }
           }
           else{
               if(arr[n-1]>arr[n-2]){ //case when middle element is the last element
                   return n-1;
               }
               else return n-2;
           }
       }
       return -1;
    }
//       return mid;
//        int start=0;
//        int end=n-1;
//
//        while(start<=end){
//            int mid=start+(end-start)/2;
//            if(arr[mid]>arr[mid+1]){
//                end=mid;
//            }
//            else {start=mid+1;}
//        }
//        return start;

    public static void main(String[] args) {
        int[] arr={15,3,19,1,7,11,1,7,7};
        System.out.println(peakElement(arr, arr.length));
    }

}
