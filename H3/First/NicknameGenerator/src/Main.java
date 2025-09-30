import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<AtomicInteger> beauties = List.of(new AtomicInteger(0), new AtomicInteger(0), new AtomicInteger(0));
        String[] texts = generateTexts();

        Thread palThread = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrom(text)) {
                    beauties.get(text.length() - 3).addAndGet(1);
                }
            }
        });

        Thread sameThread = new Thread(() -> {
            for (String text : texts) {
                if (isSame(text)) {
                    beauties.get(text.length() - 3).addAndGet(1);
                }
            }
        });

        Thread orderThread = new Thread(() -> {
            for (String text : texts) {
                if (isOrder(text)) {
                    beauties.get(text.length() - 3).addAndGet(1);
                }
            }
        });

        palThread.start();
        sameThread.start();
        orderThread.start();

        orderThread.join();
        sameThread.join();
        palThread.join();

        for (int i = 3; i < 6; ++i) {
            System.out.println("Красивых слов с длиной " + i + ": " + beauties.get(i - 3) + " шт");
        }
    }

    public static String[] generateTexts() {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        return texts;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrom(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            ++left;
            --right;
        }
        return true;
    }

    public static boolean isSame(String text) {
        int len = text.length();
        if (len == 0) {
            return true;
        }
        for ( int i = 1; i < len; ++i) {
            if (text.charAt(i) != text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOrder(String text) {
        int len = text.length();
        if (len == 0) {
            return true;
        }
        for ( int i = 1; i < len; ++i) {
            if (text.charAt(i) > text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
}
