import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurency {
    public static final int THREADS_NUM = 10000;
    private static int counter;
    private static AtomicInteger counterAtomic = new AtomicInteger();
    final private ReentrantLock testLock = new ReentrantLock();
    private static final ThreadLocal<SimpleDateFormat> threadSdf = ThreadLocal.withInitial(SimpleDateFormat::new);

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
        CountDownLatch latch = new CountDownLatch(THREADS_NUM);
        ExecutorService executor = Executors.newCachedThreadPool();
        // CompletionService completionService = new ExecutorCompletionService(executor);
        for (int i = 0; i < THREADS_NUM; i++) {
            Future<Integer> submit = executor.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mc.incCounter4();

                }
                System.out.println(threadSdf.get().format(new Date()));
                latch.countDown();
                return 5;
            });
            //System.out.println(submit.isDone());
//            Thread thread = new Thread(() -> {
//                for (int j = 0; j < 100; j++) {
//                    mc.incCounter3();
//                }
//                latch.countDown();
//            });
//            threadsList.add(thread);
//            thread.start();
        }
        try {
            latch.await(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executor.shutdown();
//        for (var ranThread : threadsList) {
//            try {
//                ranThread.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }

        System.out.println(counterAtomic.get());
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

    public void incCounter4() {
        counterAtomic.addAndGet(1);
    }
}
