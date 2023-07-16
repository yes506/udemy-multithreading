package thread.creation.example2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static final int MAX_PASSWORD = 9999;
    public static void main(String[] args) {

        Random random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        for (Thread thread : threads) {
            System.out.println("Starting thread : " + thread.getName());
            thread.start();
        }

    }

    private static class Vault {

        private int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            return this.password == guess;
        }

    }

    public static abstract class HackerThread extends Thread {

        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(MAX_PRIORITY);
        }

        @Override
        public void run() {
            System.out.println("Starting thread : " + this.getName());
        }
    }

    public static class AscendingHackerThread extends HackerThread {

        public AscendingHackerThread(Vault vault) {
            super(vault);
            this.setName("AscendingHackerThread");
        }

        @Override
        public void run() {
            for (int guess = 0; guess <= MAX_PASSWORD; guess++) {
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {

                }
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    public static class DescendingHackerThread extends HackerThread {

        public DescendingHackerThread(Vault vault) {
            super(vault);
            this.setName("DescendingHackerThread");
        }

        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= 0; guess-- ) {
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {

                }
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    public static class PoliceThread extends Thread {

        public PoliceThread() {
            this.setName("PoliceThread");
        }

        @Override
        public void run() {

            for (int count = 10; count >= 0; count--) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {

                }
                System.out.println("count : " + count);
            }
            System.out.println("Game over for you hacker");
            System.exit(0);
        }
    }

}
