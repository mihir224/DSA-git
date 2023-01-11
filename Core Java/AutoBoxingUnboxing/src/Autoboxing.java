import java.util.ArrayList;

public class Autoboxing {
    public static void main(String[] args) {
        ArrayList<Integer> myInt=new ArrayList<>();
        Integer a=10; //We can declare an Integer object reference like this. Java converts this code to Integer a=Integer.valueOf(10) at compile time;
        int b=a; //Java converts this code to int b=a.intValue(); at compile time
        for(int i=0;i<=10;i++){
            myInt.add(Integer.valueOf(i)); //Autoboxing: converting int type data to Integer type
        }
        for(int i=0;i<=10;i++){
            System.out.println(myInt.get(i).intValue()); //Unboxing: converting Integer object reference to int type
        }
    }

}
