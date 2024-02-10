package myThread;

public class worldTh extends Thread {
    @Override
    public void run(){
        for(int i = 1; i <= 50; i++){
            System.out.println("world");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
    
}