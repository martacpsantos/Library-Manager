package bci.exceptions;

public class DoesNotExistUserException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202510061258L;

    private final int _userId;

    public DoesNotExistUserException(int userId) {
        _userId = userId;
    }

    public int getUserId() {
        return _userId;
    }
            
}
