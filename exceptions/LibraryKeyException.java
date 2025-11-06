package bci.exceptions;

public class LibraryKeyException extends Exception{

    @java.io.Serial
    private static final long serialVersionUID = 202510031949L;

    private final String _key;

    public LibraryKeyException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }

}



