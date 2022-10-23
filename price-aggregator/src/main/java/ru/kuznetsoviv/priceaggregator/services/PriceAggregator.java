package ru.kuznetsoviv.priceaggregator.services;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Price aggregator for testing.
 */
public class PriceAggregator {

    private Collection<Long> shopIds = Set.of(10L, 45L, 66L, 345L, 234L, 333L, 67L, 123L, 768L);
    ;
    private PriceRetriever priceRetriever;

    private final Executor executor = Executors.newCachedThreadPool();

    /**
     * Set collection of shops identifiers.
     *
     * @param shopIds collection of shops identifiers
     */
    public void setShops(Collection<Long> shopIds) {
        this.shopIds = shopIds;
    }

    /**
     * Set price retriever.
     *
     * @param priceRetriever price retriever
     */
    public void setPriceRetriever(PriceRetriever priceRetriever) {
        this.priceRetriever = priceRetriever;
    }

    /**
     * Get min price for specified item.
     *
     * @param itemId item identifier
     * @return min price for specified item
     */
    public double getMinPrice(long itemId) {
        List<CompletableFuture<Double>> completableFutureList =
                shopIds.stream().map(shopId ->
                        CompletableFuture.supplyAsync(
                                        () -> priceRetriever.getPrice(itemId, shopId))
                                .completeOnTimeout(Double.POSITIVE_INFINITY, 2900, TimeUnit.MILLISECONDS)
                                .handle((res, ex) -> res != null ? res : Double.POSITIVE_INFINITY)).toList();
        return completableFutureList
                .stream()
                .mapToDouble(CompletableFuture::join)
                .filter(Double::isFinite)
                .min()
                .orElse(Double.NaN);
    }

}
