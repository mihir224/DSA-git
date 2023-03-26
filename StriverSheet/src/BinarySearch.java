public class BinarySearch {
    //rotated bs
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
}

