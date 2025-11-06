package bci.notification;
import bci.work.Work;

public class AvailabilityNotification extends Notification {


    private String _message;

    public AvailabilityNotification (Work work) {
        super(work);
        _message = work.toString();
    }


    @Override
    public String toString() {
        return "DISPONIBILIDADE: " + _message;
    }
}
