package bci.exceptions;

public class ActiveUserException extends Exception {
    @java.io.Serial
    private static final long serialVersionUID = 202510152231L;

    private final int _userId;

    public ActiveUserException(int userId) {
        _userId = userId;
    }

    public int getUserId() {
        return _userId;
    }

}
