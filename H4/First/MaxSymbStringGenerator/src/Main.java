import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    private static final BlockingQueue<String> queueA = new ArrayBlockingQueue<>(100);
    private static final BlockingQueue<String> queueB = new ArrayBlockingQueue<>(100);
    private static final BlockingQueue<String> queueC = new ArrayBlockingQueue<>(100);

    private static volatile String maxA = "";
    private static volatile String maxB = "";
    private static volatile String maxC = "";
    private static volatile int maxCountA = 0;
    private static volatile int maxCountB = 0;
    private static volatile int maxCountC = 0;

    private static volatile boolean generationComplete = false;

    public static void main(String[] args) throws InterruptedException {
        Thread generatorThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10000; i++) {
                    String text = generateText("abc", 100000);

                    queueA.put(text);
                    queueB.put(text);
                    queueC.put(text);
                }
                generationComplete = true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread analyzerA = new Thread(() -> analyzeQueue(queueA, 'a'));
        Thread analyzerB = new Thread(() -> analyzeQueue(queueB, 'b'));
        Thread analyzerC = new Thread(() -> analyzeQueue(queueC, 'c'));

        generatorThread.start();
        analyzerA.start();
        analyzerB.start();
        analyzerC.start();

        generatorThread.join();
        analyzerA.join();
        analyzerB.join();
        analyzerC.join();

        System.out.println("Текст с максимальным количеством 'a': " + maxCountA + " символов");
        System.out.println("Текст с максимальным количеством 'b': " + maxCountB + " символов");
        System.out.println("Текст с максимальным количеством 'c': " + maxCountC + " символов");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static void analyzeQueue(BlockingQueue<String> queue, char targetChar) {
        try {
            while (!generationComplete || !queue.isEmpty()) {
                String text = queue.poll(100, java.util.concurrent.TimeUnit.MILLISECONDS);
                if (text != null) {
                    int count = countChar(text, targetChar);
                    updateMax(text, count, targetChar);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static int countChar(String text, char targetChar) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == targetChar) {
                count++;
            }
        }
        return count;
    }

    private static void updateMax(String text, int count, char targetChar) {
        switch (targetChar) {
            case 'a':
                if (count > maxCountA) {
                    maxCountA = count;
                    maxA = text;
                }
                break;
            case 'b':
                if (count > maxCountB) {
                    maxCountB = count;
                    maxB = text;
                }
                break;
            case 'c':
                if (count > maxCountC) {
                    maxCountC = count;
                    maxC = text;
                }
                break;
        }
    }
}
