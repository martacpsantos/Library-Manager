package bci.user;
import bci.work.Work;

public class Behaved extends Behaviour {
    @java.io.Serial
    private static final long serialVersionUID = 202510161215L;


    @Override
    public int getMaxRequests() {
        return 5;
    }


    @Override
    public int getMaxReturnDays(Work work) {
        int copies = work.getNCopies();
        int days = switch(copies){
            case 1 -> days = 8;
            case 2,3,4,5 -> days = 15;
            default -> days = 30;
        };
        return days;
    }

    
    @Override
    public boolean isBehaved() {
        return true;
    }

    
    @Override
    public String toString() {
        return "CUMPRIDOR";
    }
}
