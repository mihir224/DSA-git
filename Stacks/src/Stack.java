public class Stack {
    protected int[] data;
    private static final int DEFAULT_SIZE=10; //final coz we want it to be const, static coz we want all the objects of the
    //stack class to have the same default size
    private int ptr=-1;
    public Stack(){
        this(DEFAULT_SIZE); //calling a constructor from another constructor
    }
    public Stack(int size) {
        this.data = new int[size];
    }

    public boolean push(int item){
        if(isFull()){
            System.out.println("Stack full");
            return false;
        }
        ptr++;
        data[ptr]=item;
        return true;
    }

    public int pop() throws StackException{
        if(isEmpty()){
            throw new StackException("Stack is empty");
        }
        int removed=data[ptr];
        ptr--;
        return removed;
    }

    public int peek() throws StackException{  //returns the last item that was added
        if(isEmpty()){
            throw new StackException("Stack is empty");
        }
        return data[ptr];
    }

    public void display(){
        for(int i=data.length-1;i>=0;i--){
            System.out.println(data[i]);
        }
        System.out.println("//");
    }

    public boolean isFull() {
        if (ptr == data.length - 1) {//ptr at last index
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        return ptr==-1; //arr is empty
    }


}


