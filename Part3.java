import java.util.Random;

public class Part3 {

    static final long TOTAL_POINTS = 100_000_000;

    static class MyThread extends Thread {

        long points;
        long localHits = 0;

        MyThread(long points) {
            this.points = points;
        }

        public void run() {

            Random random = new Random();

            for (long i = 0; i < points; i++) {

                double x = random.nextDouble();
                double y = random.nextDouble();

                if (x * x + y * y <= 1.0) {
                    localHits++;
                }
            }
        }
    }

    public static long calculate(int threadCount) throws InterruptedException {

        Thread[] threads = new Thread[threadCount];

        long pointsPerThread = TOTAL_POINTS / threadCount;

        long start = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new MyThread(pointsPerThread);
            threads[i].start();
        }

        long totalHits = 0;

        for (int i = 0; i < threadCount; i++) {
            threads[i].join();

            MyThread thread = (MyThread) threads[i];

            totalHits += thread.localHits;
        }

        long end = System.currentTimeMillis();

        double pi = 4.0 * totalHits / TOTAL_POINTS;

        System.out.println(
            threadCount + " threads: " +
            (end - start) + " ms, Pi = " + pi
        );

        return end - start;
    }

    public static void main(String[] args) throws InterruptedException {

        int[] threadCounts = {1, 2, 4, 8, 16, 32};

        long baseline = 0;

        for (int threads : threadCounts) {

            long time = calculate(threads);

            if (threads == 1) {
                baseline = time;
            }

            double speedup = (double) baseline / time;

            double efficiency = speedup / threads * 100;

            System.out.println(
                "Speedup = " + speedup + "x"
            );

            System.out.println(
                "Efficiency = " + efficiency + "%"
            );

            System.out.println();
        }
    }
}