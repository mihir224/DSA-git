package com.company;
//MIHIR SAINI 9920102054 E2
class myException3 extends Exception{
    public myException3(String s) {
        super(s);
    }
}
public class TestScores {
    public static void main(String[] args) {
        TestScores testScores=new TestScores(5);
        int[] arr=new int[]{3,2,2,3,5};
        testScores.setArr(arr);
        System.out.println(testScores.printAvg(arr));
        try{
            throw new myException3("Array limit exceeded");
        }
        catch(myException3 ex){
            System.out.println("caught");
            System.out.println(ex.getMessage());
        }
    }
    int n;
    int[] arr;
    public TestScores(int n) {
        this.n = n;
        this.arr = new int[n];
    }


    public void setArr(int[] arr) {
        this.arr = arr;
    }

    public double printAvg(int[] arr){
        double sum=0;
        double avg=0;
        for(int i=0;i<arr.length;i++){
            sum+=arr[i];
        }
        avg=sum/arr.length;
        return avg;
    }

}
