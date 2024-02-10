package myThread;

public class helloTh extends Thread {
    @Override
    public void run(){
        for(int i = 1; i <= 50; i++){
            System.out.println("hello");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
    
}
/*
public class worldThread extends Thread {
    @Override
    public void run(){
        for(int i = 1; i <= 5; i++){
            System.out.println("world");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
    
} */