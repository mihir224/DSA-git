import java.util.Arrays;
//9920102054 MIHIR SAINI E2
public class Q3 {
    public static void main(String[] args) {

    }
public static boolean isAscending(int[] arr,int n){
        if(arr[0]<arr[n-1]){
            return true;
        }
        else{
            return false;
        }
}
    static int[] MergeSort(int[] arr) { //to sort parts of the array we use copy of the two parts of array
        boolean flag=isAscending(arr,arr.length);
       if(flag) {
           if (arr.length == 1) {
               return arr;
           }
           int mid = arr.length / 2;
           int[] left = MergeSort(Arrays.copyOfRange(arr, 0, mid)); //Arrays.copyOfRange() copies the contents of an existing array into a
           //new array within the specified range
           int[] right = MergeSort(Arrays.copyOfRange(arr, mid, arr.length));
           return merge(left, right);
       }
       else
           return new int[]{-1,-1};
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

    static void quickSort(int[] arr, int low, int hi) {
        boolean flag=isAscending(arr,arr.length);
        if (low >= hi) {
            return;
        }
        int start = low;
        int end = hi;
        int mid = start + (end - start) / 2;
        int pivot = arr[mid];
        int count = 0;
        while (start <= end) {
            //if already sorted it won't swap
            while (arr[start] < pivot) {
                start++;
                count++;
            }
            while (arr[end] > pivot) {
                end--;
                count++;
            }
            if (start <= end) {
                int temp = arr[start];
                arr[start] = arr[end];
                arr[end] = temp;
                start++;
                end--;
                count++;
            }

        }

        //pivot is now at its correct position
        quickSort(arr, low, end);
        quickSort(arr, start, hi);
        System.out.println("No. of comparisons in quick sort = " + count);
    }

    public static int[] bubbleSort(int[] arr) {
        boolean flag1=isAscending(arr,arr.length);
        boolean flag = true;
        int count = 0;
        while (flag) {
            flag = false;
            for (int i = 0; i < arr.length; i++) {
                for (int j = 1; j < arr.length - i; j++) {
                    if (arr[j] < arr[j - 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j - 1];
                        arr[j - 1] = temp;
                        flag = true;
                        count++;
                    }
                    count++;
                }
            }
        }
        System.out.println("No. of comparisons in bubble sort = " + count);
        return arr;
    }

    public static void insertionSort(int[] arr) {
        boolean flag=isAscending(arr,arr.length);
        int count = 0;
        if(flag){
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                    count++;
                } else {
                    break;
                }
            }
        }
        System.out.println("No. of comparisons in insertion sort = " + count);
        }
    }

    public static void selectionSort(int[] arr) {
        int count = 0;
        boolean flag=isAscending(arr,arr.length);
        if(flag){
        for (int i = 0; i < arr.length; i++) {
            int last = arr.length - i - 1;
            int maxIndex = getMaxIndex(arr, 0, last); //index of largest element
            swap(arr, maxIndex, last);
            count++;
            System.out.println("No. of comparisons in selection sort = " + count);
        }}
    }

    public static int getMaxIndex(int[] arr, int start, int end) {
        int max = start;
        for (int i = start; i <= end; i++) {
            if (arr[max] < arr[i]) {
                max = i; //To obtain the index of the largest element
            }
        }
        return max;
    }

    public static void swap(int[] arr, int first, int second) {
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }
}