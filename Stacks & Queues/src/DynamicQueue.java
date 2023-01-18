public class DynamicQueue extends CircularQueue{
    protected int[] data;
    private static final int DEFAULT_SIZE=10;


    public DynamicQueue() {
        this(DEFAULT_SIZE);
    }

    public DynamicQueue(int size) {
        this.data=new int[size];
    }

    @Override
    public boolean add(int item) throws Exception {
        if(this.isFull()){
            int[] temp=new int[data.length*2];
            for(int i=0;i<data.length;i++){
                temp[i]=data[(front+i)%data.length]; //here we set temp[i] as the element which is at i th position from
                //front ie head in the original array because in the circular array, head is not always at the 0th index
                //and in every case we consider the head element to be the first of the queue. This way the new temp array
                //would be like a custom queue with its head at the 0th index.
            }
            front=0; //the new array would be like the custom queue with the head at the first position
            end=data.length; //at this point end is at position data.length
            data=temp;
        }
        return super.add(item);
    }
}
