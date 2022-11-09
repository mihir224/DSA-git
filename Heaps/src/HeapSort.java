import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class HeapSort {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq=new PriorityQueue<>(Collections.reverseOrder());
        pq.add(1);
        pq.add(5);
        pq.add(9);
        pq.add(4);
        pq.add(7);
        pq.add(2);
        while(!pq.isEmpty()){
            System.out.println(pq.poll());
        }

//        HeapSort heap=new HeapSort();
//        int[] arr={-1,52,44,12,45,49};
//        heap.heapSort(arr,arr.length-1);
//        for(int i=1;i<=arr.length-1;i++){
//            System.out.print(arr[i]+ " ");
//        }
//        System.out.println("END");

    }
    public void heapSort(int[] arr, int n){
        buildHeap(arr,n); //converting given array to heap
        int size=n;
        while(size>1){
            swap(arr,1,size);
            size--;
            heapify(arr,size,1); //heapifying remaining nodes
        }
    }
    public void heapify(int arr[], int n, int i){
        int largest=i;
        int left=2*i;
        int right=(2*i)+1;
        if(left<=n&&arr[left]>arr[largest]){
            largest=left;
        }
        if(right<=n&&arr[right]>arr[largest]){
            largest=right;
        }
        if(largest!=i){
           swap(arr,i,largest);
            heapify(arr,n,largest); //recursively converting further nodes into heap
        }
    }
    public void buildHeap(int[] arr, int n){
        for(int i=n/2;i>0;i--){ //starting from non leaf node
            heapify(arr,n,i);
        }

    }
    public void swap(int[] arr,int a, int b){
        int temp=arr[a];
        arr[a]=arr[b];
        arr[b]=temp;
    }


}

