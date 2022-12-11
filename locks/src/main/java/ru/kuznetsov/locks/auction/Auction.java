package ru.kuznetsov.locks.auction;

public interface Auction {

    boolean propose(Bid bid);

    Bid getLatestBid();

}
