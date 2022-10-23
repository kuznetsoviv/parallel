package ru.kuznetsoviv.priceaggregator.services;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Price retriever for testing.
 */
public class PriceRetriever {

    /**
     * Get random price with random delay.
     *
     * @param itemId item identifier
     * @param shopId shop identifier
     * @return price
     */
    public double getPrice(long itemId, long shopId) {
        int delay = ThreadLocalRandom.current().nextInt(8);
        sleep(delay);
        return ThreadLocalRandom.current().nextDouble(1000);
    }

    private void sleep(int delay) {
        try {
            TimeUnit.SECONDS.sleep(delay);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }


}
