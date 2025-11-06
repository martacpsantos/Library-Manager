package bci;

import bci.notification.Notification;

/**
 * An interface for works that can be observed for borrow and availability notifications.
 */
public interface ObservableWork {

    /**
     * Registers a subscriber to borrow notifications.
     * @param sub
     */
    void addBorrowObserver(Subscriber sub);

    /**
     * Removes a subscriber from borrow notifications.
     * @param sub
     */
    void removeBorrowObserver(Subscriber sub);

    /**
     * Registers a subscriber to availability notifications.
     * @param sub
     */
    void addAvailabilityObserver(Subscriber sub);

    /**
     * Removes a subscriber from availability notifications.
     * @param sub
     */
    void removeAvailabilityObserver(Subscriber sub);

    /**
     * Notifies all borrow observers with the given notification.
     * @param notification
     */
    void notifyBorrowObservers(Notification notification);

    /**
     * Notifies all availability observers with the given notification.
     * @param notification
     */   
    void notifyAvailabilityObservers(Notification notification);
}
