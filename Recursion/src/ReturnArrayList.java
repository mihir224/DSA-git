import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ReturnArrayList {
    public static void main(String[] args) {
        int[] arr={3,3,1,2,6,7,4,9};
        List<Integer> list=search2(arr,9,0);
        System.out.println(list);
    }
    //passing an arrayList as an argument
    static ArrayList<Integer> search(int[] arr, int target, int index, ArrayList<Integer> list){

        if(index==arr.length) {
            return list;
        }
        if(arr[index]==target){
            list.add(index);
        }
        return search(arr, target, index+1, list);
    }
    //without passing an arrayList as an argument
    static ArrayList<Integer>search2(int[] arr, int target, int index){
        ArrayList<Integer> list=new ArrayList<>();
        if(index==arr.length){
            return list;
        }
        if(arr[index]==target){
            list.add(index);
        }
        ArrayList<Integer> ansFromBelowCalls=search2(arr,target,index+1); //to save ans from further calls
        list.addAll(ansFromBelowCalls); //to add the ans returned from further calls to the list
        return list;
    }
}
