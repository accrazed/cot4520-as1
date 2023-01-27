import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static int MAX_THREADS = 8;
    // static int MAX_NUM = 100000000;
    static int MAX_NUM = 100;

    public static void main(String[] args) throws Exception {
        boolean[] primes = new boolean[MAX_NUM + 1];
        Arrays.fill(primes, true);

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

                System.out.printf("start: %d, stop: %d, step: %d\n", start, stop, step);

                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        markNonPrimes(primes, start, stop, step);
                    }
                });
            }
            es.shutdown();
        }

        for (int i = 0; i < primes.length; i++) {
            if (primes[i]) {
                System.out.println(i);
            }
        }
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
