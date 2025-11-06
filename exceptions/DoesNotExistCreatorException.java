package bci.exceptions;

/**
 * Exception thrown when an unknown creator is required.
 */
public class DoesNotExistCreatorException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202510041943L;

    private final String _creatorName;

    public DoesNotExistCreatorException(String creatorName){
        _creatorName = creatorName;
    }

    public String getCreatorName() {
        return _creatorName;
    }
}
