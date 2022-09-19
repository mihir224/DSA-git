public class CircularQueue {
    protected int[] data;
    private static final int DEFAULT_SIZE=10;
    protected int front=0; //front represents the head of the queue
    protected int size=0;
    protected int end=0;

    public CircularQueue() {
        this(DEFAULT_SIZE);
    }

    public CircularQueue(int size) {
        this.data=new int[size];
    }

    public boolean add(int item) throws Exception {
        if(isFull()){
            throw new Exception("Queue is full");
        }
        data[end]=item;
        end++;
        end=end% data.length; //new items will override old items
        size++;
        return true;
    }

    public int remove() throws Exception{
        if(isEmpty()){
            throw new Exception("Queue is empty");
    }
        int removed=data[front];
        front++;
        front=front%data.length;
        size--; //decreasing the size
        return removed;
    }

    public boolean isFull() {
        if (size == data.length ) {//ptr at last index
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        return size==0; //arr is empty
    }

    public int head() throws Exception {
        if(isEmpty()){
            throw new Exception("Queue is empty");
        }
        return data[front];
    }
    public void display() throws Exception{
        if(isEmpty()){
            throw new Exception("Queue is empty");
        }
//        for(int i=front;i<end;i++){
//            System.out.println(data[i]+"-<");
//        }
       int i=front;
        do{ //when we remove and insert an element in a full queue, end and full become equal, thus we use a do-while loop
            System.out.print(data[i]+"<-");
            i++;
            i=i% data.length;
        }while(i!=end);
        System.out.println("END");

    }

}

