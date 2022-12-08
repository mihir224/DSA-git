import java.util.Arrays;
//9920102054 MIHIR SAINI E2
public class Q2 {
    public static void main(String[] args) {
        int[] arr1 = {5,2,4,1,3};
        System.out.println(Arrays.toString(MergeSort(arr1)));
        System.out.println(Arrays.toString(bubbleSort(arr1)));
        insertionSort(arr1);
        selectionSort(arr1);
        quickSort(arr1,0,arr1.length-1);
        System.out.println(Arrays.toString(arr1));
    }
    static int[] MergeSort(int[] arr) { //to sort parts of the array we use copy of the two parts of array
        if (arr.length == 1) {
            return arr;
        }
        int mid = arr.length / 2;
        int[] left = MergeSort(Arrays.copyOfRange(arr, 0, mid)); //Arrays.copyOfRange() copies the contents of an existing array into a
        //new array within the specified range
        int[] right = MergeSort(Arrays.copyOfRange(arr, mid, arr.length));
        return merge(left, right);
    }

    private static int[] merge(int[] left, int[] right) {
        int[] mergedArray = new int[left.length + right.length];
        int i = 0;
        int j = 0;
        int k = 0;
        int count = 0;
        while (i < left.length && j < right.length) {
            if (left[i] == right[j]) {
                count++;
            }
            if (left[i] < right[j]) {
                mergedArray[k] = left[i];
                count++;
                i++;
            } else {
                mergedArray[k] = right[j];
                j++;
                count++;
            }
            k++;
        }
        //case when one of the array is out of bounds and the other one is still running
        while (i < left.length) {
            mergedArray[k] = left[i]; //copying the remaining elements as it is
            i++;
            k++;
            count++;

        }
        while (j < right.length) {
            mergedArray[k] = right[j];
            j++;
            k++;
            count++;
        }
        System.out.println("No. of comparisons in merge sort = " + count);
        return mergedArray;
    }

    static void quickSort(int[] arr, int low, int hi){
        if(low>=hi){
            return;
        }
        int start=low;
        int end=hi;
        int mid=start+(end-start)/2;
        int pivot=arr[mid];
        int count=0;
        while(start<=end){
            //if already sorted it won't swap
            while(arr[start]<pivot){
                start++;
                count++;
            }while(arr[end]>pivot){
                end--;
                count++;
            }
            if(start<=end){
                int temp=arr[start];
                arr[start]=arr[end];
                arr[end]=temp;
                start++;
                end--;
                count++;
            }

        }

        //pivot is now at its correct position
        quickSort(arr,low, end);
        quickSort(arr,start,hi);
        System.out.println("No. of comparisons in quick sort = " + count);
    }

    public static int[] bubbleSort(int[] arr){
        boolean flag=true;
        int count=0;
        while(flag){
            flag=false;
            for(int i=0;i<arr.length;i++){
                for(int j=1;j<arr.length-i;j++){
                    if(arr[j]<arr[j-1]){
                        int temp=arr[j];
                        arr[j]=arr[j-1];
                        arr[j-1]=temp;
                        flag=true;
                        count++;
                    }
                    count++;
                }
            }
        }
        System.out.println("No. of comparisons in bubble sort = " + count);
        return arr;
    }
    public static void insertionSort(int[] arr){
        int count=0;
        for(int i=0;i<arr.length-1;i++){
            for(int j=i+1;j>0;j--){
                if(arr[j]<arr[j-1]){
                    swap(arr,j,j-1);
                    count++;
                }
                else {
                    break;
                }
            }
        }
        System.out.println("No. of comparisons in insertion sort = " + count);
    }
    public static void selectionSort(int[] arr){
        int count=0;
        for(int i=0;i<arr.length;i++) {
            int last = arr.length - i - 1;
            int maxIndex=getMaxIndex(arr, 0, last); //index of largest element
            swap(arr, maxIndex, last);
            count++;
            System.out.println("No. of comparisons in selection sort = " + count);
        }
    }
    public static int getMaxIndex(int[] arr, int start, int end){
        int max=start;
        for(int i=start;i<=end;i++){
            if(arr[max]<arr[i]){
                max=i; //To obtain the index of the largest element
            }
        }
        return max;
    }
    public static void swap(int[] arr, int first, int second){
        int temp=arr[first];
        arr[first]=arr[second];
        arr[second]=temp;
    }
}
