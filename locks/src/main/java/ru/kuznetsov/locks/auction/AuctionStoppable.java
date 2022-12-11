package ru.kuznetsov.locks.auction;

public interface AuctionStoppable extends Auction {

    Bid stopAuction();

}
