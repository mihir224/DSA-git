public class DynamicStack extends Stack{
    public DynamicStack() {
        super(); //will call a constructor with no parameters in the parent class
    }
    public DynamicStack(int size){
        super(size); //will call a constructor with an int parameter in the parent class
    }

    @Override
    public boolean push(int item) {
        if(isFull()){
            int[] temp=new int[data.length*2]; //making the array size double if it becomes full
            for(int i=0;i<data.length;i++){
                temp[i]=data[i];
            }
            data=temp; //redefining data by setting it to temp
        } //temp array will go to garbage collection when this function call is over
        return super.push(item); //to call the push method in the parent class
    }
}
