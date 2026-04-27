package com.domchar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class ProfilingDemo {

    private static final Logger logger = Logger.getLogger(ProfilingDemo.class.getName());

    public static void main(String[] args) {

        // Toggle for crash simulation
        boolean simulateCrash = args.length > 0 && args[0].equalsIgnoreCase("crash");

        List<String> data = new ArrayList<>();
        Random random = new Random();

        logger.info("ProfilingDemo started...");
        logger.info("PID will stay active while workload runs.");

        for (int round = 1; round <= 15; round++) {
            logger.info("Round: " + round);

            // Controlled failure (only if 'crash' argument passed)
            if (simulateCrash && round == 7) {
                logger.severe("Simulated failure occurred at round " + round);
                throw new RuntimeException("Simulated crash for testing");
            }

            // Memory workload: create lots of strings
            for (int i = 0; i < 100000; i++) {
                data.add("Item-" + round + "-" + i + "-" + random.nextInt(100000));
            }

            // CPU workload: repeated math operations
            long total = 0;
            for (int i = 0; i < 5000000; i++) {
                total += (i * i) % 123;
            }

            logger.info("Round " + round + " complete. Total = " + total);
            logger.info("Current stored items: " + data.size());

            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            logger.info("Memory usage (bytes): " + usedMemory);

            // Small pause so VisualVM can observe changes
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.severe("Thread interrupted: " + e.getMessage());
            }

            // Free some memory every few rounds
            if (round % 5 == 0) {
                logger.warning("Clearing some memory...");
                data.clear();
                System.gc();
            }
        }

        logger.info("ProfilingDemo finished.");
    }
}