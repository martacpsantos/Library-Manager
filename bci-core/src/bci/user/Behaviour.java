package bci.user;

import bci.work.Work;
import java.io.Serializable;

public abstract class Behaviour implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202510042133L;

    /**
     * Gets the maximum number of requests allowed for this behaviour.
     * @return The maximum number of requests.
     */
    public abstract int getMaxRequests();

    /**
     * Gets the maximum number of return days allowed for a work based on this behaviour.
     * @param work The work being requested.
     * @return The maximum number of return days.
     */
    public abstract int getMaxReturnDays(Work work);

    public boolean isBehaved() {
        return false;
    }

    public boolean isNormal() {
        return false;
    }

    public boolean isDefaulter() {
        return false;
    }
}
