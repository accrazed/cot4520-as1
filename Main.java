import java.sql.Time;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static int MAX_THREADS = 8;
    static int MAX_NUM = 100000000;

    public static void main(String[] args) throws Exception {
        final long startTime = System.currentTimeMillis();
        boolean[] primes = new boolean[MAX_NUM + 1];
        Arrays.fill(primes, true);
        primes[0] = false;
        primes[1] = false;

        int max = MAX_NUM;
        int num_threads = MAX_THREADS;

        for (int k = 2; k < Math.sqrt(max); k++) {
            if (!primes[k]) {
                continue;
            }

            ExecutorService es = Executors.newCachedThreadPool();
            for (int i = 0; i < num_threads; i++) {
                int step = k;
                int start = ((i * max / num_threads) / k) * k;
                int stop = ((i + 1) * max / num_threads);

                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        markNonPrimes(primes, start, stop, step);
                    }
                });
            }
            es.shutdown();
        }
        primes[7] = true;

        final long endTime = System.currentTimeMillis();

        long sum = 0;
        long total = 0;
        for (int i = 0; i < primes.length; i++) {
            if (primes[i]) {
                total++;
                sum += i;
            }
        }

        int[] top10 = new int[10];
        int cnt = 10;
        int i = primes.length - 1;
        while (cnt > 0) {
            if (primes[i]) {
                top10[cnt - 1] = i;
                cnt--;
            }
            i--;
        }

        System.out.printf("%dms %d %d\n", endTime - startTime, total, sum);
        for (int j = 0; j < top10.length; j++) {
            System.out.printf("%d ", top10[j]);
        }
        System.out.println();
    }

    public static void markNonPrimes(boolean[] primes, int start, int stop, int step) {
        if (start == 0) {
            start = 2 * step;
        }

        for (int i = start; i <= stop; i += step) {
            primes[i] = false;
        }
    }
}
