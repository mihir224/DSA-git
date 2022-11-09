public class Heap {
    private int size;
    private int[] arr;
    public Heap(){
        this.arr=new int[100];
        arr[0]=-1;
        this.size=0;
    }
    public static void main(String[] args) {
        Heap heap=new Heap();
//        heap.insert(50);
//        heap.insert(30);
//        heap.insert(40);
//        heap.insert(10);
//        heap.insert(20);
//        heap.insert(5);
//        heap.printHeap();
//        heap.delete();
//        heap.printHeap();
        int[] arr={-1,54,53,55,52,50};
        heap.buildHeap(arr,5);

    }
    public void insert(int value){
       size=size+1;
       int index=size;
        arr[index]=value;
        while(index>1){
            int parent=index/2;
            if(arr[index]>arr[parent]){
                swap(arr,index,parent);
                index=parent;
            }
            else{
                return; //parent>value
            }
        }
    }
    public void delete(){
        if(size==0){
            System.out.println("Heap in empty");
        }
        arr[1]=arr[size];
        size=size-1; //remove last node
        int index=1;
        heapify(arr,size,index);
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
        for(int i=1;i<=n;i++){
            System.out.print(arr[i]+ " ");
        }
        System.out.println("END");
    }
    public void printHeap(){
        for(int i=1;i<=size;i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println("END");
    }
    public void swap(int[] arr,int a, int b){
        int temp=arr[a];
        arr[a]=arr[b];
        arr[b]=temp;
    }

}

