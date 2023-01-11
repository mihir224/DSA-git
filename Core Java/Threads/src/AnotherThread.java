public class AnotherThread extends Thread {
    public static void main(String[] args) {
        System.out.println("Hello from main thread");
        Thread anotherThread=new AnotherThread();
        anotherThread.run();
        new Thread(){
            @Override
            public void run() {
                System.out.println("Hello from anonymous class");

            }
        }.start();
        Thread myRunnableThread=new Thread(new MyRunnable(){
            @Override
            public void run() {
                try{
                    anotherThread.join(3000); //joins anotherThread to the last thread
                }catch (InterruptedException ex){
                    System.out.println("Couldn't wait, I was interrupted");
                }
            }
        });
        myRunnableThread.start();
        System.out.println("Hello again from main thread"); //outputted before the anonymous class thread
    }

    @Override
    public void run() {
        System.out.println("Hello from another thread");
        try{
            Thread.sleep(5000);

        }
        catch(InterruptedException e){
            System.out.println("Interrupted");
        }
    }
}

