package bci.user;

import java.io.Serializable;

public class Status implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202510031121L;

    public boolean isActive() {
        return false;
    }
    public boolean isSuspended() {
        return false;
    }
}
