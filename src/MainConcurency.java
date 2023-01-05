import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurency {
    public static final int THREADS_NUM = 10000;
    private static int counter;
    final private ReentrantLock testLock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread 1: " + getName());
            }
        }.start();

        new Thread(() -> System.out.println("Thread 2: " + Thread.currentThread().getName())).start();

//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        Thread.yield();
//        Thread.yield();
        System.out.println("Main thread: " + Thread.currentThread().getName());

        final MainConcurency mc = new MainConcurency();

        List<Thread> threadsList = new ArrayList<>(THREADS_NUM);
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mc.incCounter3();
                }
            });
            threadsList.add(thread);
            thread.start();
        }

        for (var ranThread : threadsList) {
            try {
                ranThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(counter);
    }

    public static synchronized void incCounter() {
        counter++;
    }

    public void incCounter2() {
        synchronized (this) {
            counter++;
        }
    }

    public void incCounter3() {
        testLock.lock();
        counter++;
        testLock.unlock();
    }
}
