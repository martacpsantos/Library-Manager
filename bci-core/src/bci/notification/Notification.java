package bci.notification;
import bci.work.Work;
import java.io.Serializable;

public abstract class Notification implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202510021843L;

    private final Work _work;
    //string notification 

    public Notification (Work work) {
        _work = work;
    }


    public Work getWork() {
        return _work;
    }


    @Override
    public abstract String toString();
}
