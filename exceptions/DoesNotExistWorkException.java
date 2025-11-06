package bci.exceptions;

public class DoesNotExistWorkException extends Exception {
    
    @java.io.Serial
    private static final long serialVersionUID = 202510071147L;

    private final int _workId;

    public DoesNotExistWorkException(int workId){
        _workId = workId;
    }


    public int getWorkId() {
        return _workId;
    }
}
