package bci.exceptions;

public class FailedUserRegistrationException extends Exception {
    /** Class serial number. */
    @java.io.Serial
    private static final long serialVersionUID = 202510021155L;

    /** User name. */
    private final String _name;

    /** User email. */
    private final String _email;

    /**
     * Constructs a failed user registration exception.
     * 
     * @param name User name.
     * @param email User email.
     */
    public FailedUserRegistrationException(String name, String email) {
        _name = name;
        _email = email;
    }

    /**
     * Returns the user name.
     * 
     * @return the user name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Returns the user email.
     * 
     * @return the user email.
     */
    public String getEmail() {
        return _email;
    }
}
