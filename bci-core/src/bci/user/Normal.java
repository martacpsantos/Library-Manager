package bci.user;

import bci.work.Work;

public class Normal extends Behaviour {

    @java.io.Serial 
    private static final long serialVersionUID = 202510161214L;

    @Override
    public int getMaxRequests() {
        return 3;
    }


    @Override
    public int getMaxReturnDays(Work work) {
        int copies = work.getNCopies();
        int days = switch(copies){
            case 1 -> days = 3;
            case 2,3,4,5 -> days = 8;
            default -> days = 15;
        };
        return days;
    }

    
    @Override
    public boolean isNormal() {
        return true;
    }

    
    @Override
    public String toString() {
        return "NORMAL";
    }

}
