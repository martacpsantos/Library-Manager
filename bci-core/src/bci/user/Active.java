package bci.user;

public class Active extends Status {
    
    @java.io.Serial
    private static final long serialVersionUID = 202510152229L;

    @Override
    public boolean isActive() {
        return true;
    }
}
