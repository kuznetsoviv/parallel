package ru.kuznetsov.locks.auction;

public class AuctionStoppablePessimistic implements AuctionStoppable {

    private final Object lock = new Object();
    private final Notifier notifier;
    private volatile Bid latestBid = new Bid(-1L, -1L, -1L);
    private volatile boolean isOpen = true;

    public AuctionStoppablePessimistic(Notifier notifier) {
        this.notifier = notifier;
    }

    public boolean propose(Bid bid) {
        if (isOpen && bid.getPrice() > latestBid.getPrice()) {
            synchronized (lock) {
                if (isOpen && bid.getPrice() > latestBid.getPrice()) {
                    notifier.sendOutdatedMessage(latestBid);
                    latestBid = bid;
                    return true;
                }
            }
        }
        return false;
    }

    public Bid getLatestBid() {
        return latestBid;
    }

    public Bid stopAuction() {
        synchronized (lock) {
            isOpen = false;
            return latestBid;
        }
    }

}
