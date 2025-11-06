package bci.notification;
import bci.work.Work;

public class BorrowNotification extends Notification {
    public BorrowNotification(Work work) {
        super(work);
    }


    @Override
    public String toString() {
        return "REQUISIÇÃO: " + getWork().toString();
    }
}
