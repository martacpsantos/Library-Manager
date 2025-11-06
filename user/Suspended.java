package bci.user;

public class Suspended extends Status {
    
    @java.io.Serial
    private static final long serialVersionUID = 202510152230L;

    @Override
    public boolean isSuspended() {
        return true;
    }
}
