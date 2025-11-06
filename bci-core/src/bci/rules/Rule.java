package bci.rules;
import bci.exceptions.RuleNotFollowedException;
import bci.user.User;
import bci.work.Work;
import java.io.Serializable;

public interface Rule extends Serializable {

    /**
     * Represents a validation rule applied when a user attempts to request a work.
     * @param work
     * @param user
     * @throws RuleNotFollowedException
     */
    public void validate(Work work, User user) throws RuleNotFollowedException;
   
}
