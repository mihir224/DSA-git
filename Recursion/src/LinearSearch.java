public class LinearSearch {
    public static void main(String[] args) {
        int[] arr={1,4,6,2,3};
        System.out.println(search(arr,0,7));
    }
    static int search(int[] arr, int index, int target){
        if(index==arr.length){
            return -1;
        }
        if(arr[index]==target){
            return index;
        }
        return search(arr, index+1, target);
    }
}
