public class Main{
    public static void main(String[] args) {
            CountDown countDown=new CountDown();
            test t1=new test(countDown);
            t1.setName("THREAD 1");
            test t2=new test(countDown);
            t2.setName("THREAD 2");
            t1.start();
            t2.start();
    }
}
class CountDown{
    public void countDown(){
        for(int i=10;i>0;i--){
            System.out.println(Thread.currentThread().getName() + ": " +i);
        }

    }
}
class test extends Thread{
    private CountDown threadCountdown;
    public test(CountDown threadCountdown){
        this.threadCountdown=threadCountdown;
    }
}