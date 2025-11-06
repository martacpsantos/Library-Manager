package bci;

import bci.notification.Notification;

public interface Subscriber {

    /**
     * Updates the subscriber with a notification.
     * @param notification
     */
    void updateNotification(Notification notification);
}
