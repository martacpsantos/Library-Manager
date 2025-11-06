package bci.request;

import java.io.Serializable;

public class Request implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 202510152230L;
    private int _workID;
    private int _fine;
    private int _returnDay;
    private int _inLibraryDay;
    private boolean _returned;

    public Request(int workID, int returnDay) {
        _workID = workID;
        _returnDay = returnDay;
        _fine = 0;
        _returned = false;
    }

    public boolean isReturned() {
        return _returned;
    }
    
    public int getWorkID() {
        return _workID;
    
    }

    public int getFine() {
        return _fine;
    }

    public void setFine(int fine) {
        _fine = fine;
    }


    public boolean isReturnedOnTime() {
        return _returned && _inLibraryDay <= _returnDay;
    }


    public int getReturnDay() {
        return _returnDay;
    }


    public int getInLibraryDay() {
        return _inLibraryDay;
    }


    public void markAsReturned(int inlibraryday) {
        _returned = true;
        _inLibraryDay = inlibraryday;
    
    }

    public int calculateFine(int currentDay) {
        int dailyFine = 5;
        int daysLate = currentDay - _returnDay;
        if (daysLate <= 0)
            return 0;
        else 
            return daysLate * dailyFine;

    }

    public boolean isOverdue(int currentDay) {
        return !_returned && currentDay > _returnDay;
    }



}
