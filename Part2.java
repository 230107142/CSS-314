import java.util.Random;

public class Part2 {

    static long totalHits = 0;
    static final long TOTAL_POINTS = 50_000_000;
    static final int THREADS = 4;

    static class MyThread extends Thread {
        long points;

        MyThread(long points) {
            this.points = points;
        }

        public void run() {
            Random random = new Random();

            for (long i = 0; i < points; i++) {

                double x = random.nextDouble();
                double y = random.nextDouble();

                if (x * x + y * y <= 1.0) {

                    synchronized (Part2.class) {
                        totalHits++;
                    }

                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        totalHits = 0;

        Thread[] threads = new Thread[THREADS];

        long pointsPerThread = TOTAL_POINTS / THREADS;

        long start = System.currentTimeMillis();

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new MyThread(pointsPerThread);
            threads[i].start();
        }

        for (int i = 0; i < THREADS; i++) {
            threads[i].join();
        }

        long end = System.currentTimeMillis();

        double pi = 4.0 * totalHits / TOTAL_POINTS;

        System.out.println("Pi = " + pi);
        System.out.println("Time = " + (end - start) + " ms");
    }
}