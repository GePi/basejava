import java.util.concurrent.locks.ReentrantLock;

public class DeadlockDemo2 {
    private static final ReentrantLock lockA = new ReentrantLock();
    private static final ReentrantLock lockB = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        var thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": attempt set lock A ");
                lockA.lock();
                System.out.println(Thread.currentThread().getName() + ": A locked ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ": attempt set lock B ");
                lockB.lock();
                System.out.println(Thread.currentThread().getName() + ": B locked ");
                lockA.unlock();
                lockB.unlock();
            }
        });

        var thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": attempt set lock B ");
                lockB.lock();
                System.out.println(Thread.currentThread().getName() + ": B locked ");
                System.out.println(Thread.currentThread().getName() + ": attempt set lock A ");
                lockA.lock();
                System.out.println(Thread.currentThread().getName() + ": A locked ");
                lockA.unlock();
                lockB.unlock();
            }
        });

        thread1.start();
        thread2.start();
        Thread.sleep(2000);

        System.out.println(thread1.getName() + thread1.getState());
        System.out.println(thread2.getName() + thread2.getState());
    }
}
