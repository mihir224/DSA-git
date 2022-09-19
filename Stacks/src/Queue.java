import java.util.LinkedList;

public class Queue {
    protected int[] data;
    private static final int DEFAULT_SIZE=10;

    private int end=0;

    public Queue() {
       this(DEFAULT_SIZE);
    }

    public Queue(int size) {
        this.data=new int[size];
    }

    public boolean add(int item) throws Exception {
        if(isFull()){
            throw new Exception("Queue is full");
        }
        data[end]=item; //Adding data to end ptr
        end++; //incrementing the end pointer
        //above statement is equivalent to data[end++]=item
        return true;
    }
    public int remove() throws Exception{ //this function increases the complexity of the program as it makes n comparisons
        //of the elements of the queue and hence takes O(n) complexity, thus we use the approach of circular queue
        java.util.Queue<Integer> q=new LinkedList<>();
        
        if(isEmpty()){
            throw new Exception("Queue is empty");
        }
        int remove=data[0]; //first in first out
        //shifting the elements to left
        for(int i=1;i<data.length;i++){
            data[i-1]=data[i];
        }//by the end of this loop, the element that is to be removed will be collected by the garbage collector
        end--;
        return remove;
    }

    public void display() throws Exception{
        if(isEmpty()){
            throw new Exception("Queue is empty");
        }
        for(int i=0;i<end;i++){
            System.out.print(data[i] + "<-");
        }
        System.out.println("END");
    }

    public int head() throws Exception {
      if(isEmpty()){
            throw new Exception("Queue is empty");
        }
        return data[0];
    }
    public boolean isFull() {
        if (end == data.length ) {//ptr at last index
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        return end==0; //arr is empty
    }
}
