package bci.exceptions;


public class NotRequestedException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202510061259L;

    private final int _userId;
    private final int _workId;

    public NotRequestedException(int workId,int userId) {
        _workId = workId;
        _userId = userId;
    }

    public int getUserId() {
        return _userId;
    }

    public int getWorkId() {
        return _workId;
    }

}
