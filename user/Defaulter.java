package bci.user;

import bci.work.Work;

public class Defaulter extends Behaviour {
    @java.io.Serial
    private static final long serialVersionUID = 202510161216L;


    @Override
    public int getMaxRequests() {
        return 1;
    }

    
    @Override
    public int getMaxReturnDays(Work work) {
        return 2;
    }

    @Override
    public boolean isDefaulter() {
        return true;
    }

    @Override
    public String toString() {
        return "FALTOSO";
    }

}
