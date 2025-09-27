import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1000);

        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                String route = generateRoute("RLRFR", 100);
                int rCount = countR(route);

                synchronized (sizeToFreq) {
                    sizeToFreq.put(rCount, sizeToFreq.getOrDefault(rCount, 0) + 1);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
        printResults();
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    private static int countR(String route) {
        int count = 0;
        for (int i = 0; i < route.length(); ++i) {
            if (route.charAt(i) == 'R') {
                count++;
            }
        }
        return count;
    }

    private static void printResults() {
        int maxFreq = 0;
        int maxCount = 0;

        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxFreq = entry.getKey();
            }
        }

        System.out.println("Самое частое количество повторений " + maxFreq + " (встретилось " + maxCount + " раз)");
        System.out.println("Другие размеры:");

        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (!entry.getKey().equals(maxFreq)) {
                System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
            }
        }
    }
}
